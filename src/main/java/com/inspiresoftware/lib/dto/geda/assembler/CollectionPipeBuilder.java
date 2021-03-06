/*
 * This code is distributed under The GNU Lesser General Public License (LGPLv3)
 * Please visit GNU site for LGPLv3 http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright Denis Pavlov 2009
 * Web: http://www.genericdtoassembler.org
 * SVN: https://svn.code.sf.net/p/geda-genericdto/code/trunk/
 * SVN (mirror): http://geda-genericdto.googlecode.com/svn/trunk/
 */

package com.inspiresoftware.lib.dto.geda.assembler;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import com.inspiresoftware.lib.dto.geda.assembler.extension.DataReader;
import com.inspiresoftware.lib.dto.geda.assembler.extension.DataWriter;
import com.inspiresoftware.lib.dto.geda.assembler.extension.MethodSynthesizer;
import com.inspiresoftware.lib.dto.geda.assembler.meta.CollectionPipeMetadata;
import com.inspiresoftware.lib.dto.geda.exception.AnnotationValidatingBindingException;
import com.inspiresoftware.lib.dto.geda.exception.GeDARuntimeException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionBindingNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionPropertyNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.UnableToCreateInstanceException;

/**
 * Assembles CollectionPipe.
 *
 * @author Denis Pavlov
 * @since 1.0.0
 *
 */
@SuppressWarnings("unchecked")
class CollectionPipeBuilder extends BasePipeBuilder<CollectionPipeMetadata> {

	/**
	 * {@inheritDoc}
	 *
	 * @throws InspectionBindingNotFoundException
	 *             when inspecting entity
	 * @throws UnableToCreateInstanceException
	 *             if unable to create instance of data reader/writer
	 * @throws InspectionPropertyNotFoundException
	 *             when unable to locate required property for data reader/writer
	 * @throws AnnotationValidatingBindingException
	 *             when data reader/writer have mismatching parameters/return types
	 * @throws GeDARuntimeException
	 *             unhandled cases - this is (if GeDA was not tampered with) means library failure and should be
	 *             reported
	 */
	@Override
	public Pipe build(final AssemblerContext context, final Class dtoClass, final Class entityClass,
			final PropertyDescriptor[] dtoPropertyDescriptors, final PropertyDescriptor[] entityPropertyDescriptors,
			final CollectionPipeMetadata meta, final Pipe pipe) throws InspectionBindingNotFoundException,
			InspectionPropertyNotFoundException, UnableToCreateInstanceException, AnnotationValidatingBindingException,
			GeDARuntimeException {

		final PropertyDescriptor dtoFieldDesc = PropertyInspector.getDtoPropertyDescriptorForField(dtoClass,
				meta.getDtoFieldName(), dtoPropertyDescriptors);

		final MethodSynthesizer synthesizer = context.getMethodSynthesizer();

		final DataReader dtoFieldRead = meta.isReadOnly() ? null : synthesizer.synthesizeReader(dtoFieldDesc);
		final DataWriter dtoFieldWrite = synthesizer.synthesizeWriter(dtoFieldDesc);

		final boolean isMapEntity = Map.class.isAssignableFrom(entityClass);
		final boolean isListEntity = !isMapEntity && List.class.isAssignableFrom(entityClass);

		final MethodSynthesizer entitySynthesizer;
		final PropertyDescriptor entityFieldDesc;

		if (isMapEntity || isListEntity) {
			if (isMapEntity) {
				entitySynthesizer = mapSynthesizer;
			} else {
				entitySynthesizer = listSynthesizer;
			}
			entityFieldDesc = dtoFieldDesc;
		} else {
			entitySynthesizer = synthesizer;
			entityFieldDesc = PropertyInspector.getEntityPropertyDescriptorForField(dtoClass, entityClass,
					meta.getDtoFieldName(), meta.getEntityFieldName(), entityPropertyDescriptors);
		}

		final DataReader entityFieldRead = entitySynthesizer.synthesizeReader(entityFieldDesc);
		final DataWriter entityFieldWrite = meta.isReadOnly() ? null : entitySynthesizer
				.synthesizeWriter(entityFieldDesc);

		return new CollectionPipe(context, dtoFieldRead, dtoFieldWrite, entityFieldRead, entityFieldWrite, meta);
	}

}
