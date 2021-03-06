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
import com.inspiresoftware.lib.dto.geda.assembler.meta.PipeMetadata;
import com.inspiresoftware.lib.dto.geda.exception.GeDARuntimeException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionBindingNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionPropertyNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.UnableToCreateInstanceException;

/**
 * Assembles DataPipe.
 *
 * @author Denis Pavlov
 * @since 1.0.0
 *
 */
@SuppressWarnings("unchecked")
class DataPipeChainBuilder extends BasePipeBuilder<PipeMetadata> {

	/**
	 * {@inheritDoc}
	 *
	 * @throws InspectionBindingNotFoundException
	 *             when fails to find descriptors for fields
	 * @throws UnableToCreateInstanceException
	 *             when fails to create a data reader/writer
	 * @throws InspectionPropertyNotFoundException
	 *             when fails to create a data reader/writer
	 * @throws GeDARuntimeException
	 *             unhandled cases - this is (if GeDA was not tampered with) means library failure and should be
	 *             reported
	 */
	@Override
	public Pipe build(final AssemblerContext context, final Class dtoClass, final Class entityClass,
			final PropertyDescriptor[] dtoPropertyDescriptors, final PropertyDescriptor[] entityPropertyDescriptors,
			final PipeMetadata meta, final Pipe pipe) throws InspectionBindingNotFoundException,
			InspectionPropertyNotFoundException, UnableToCreateInstanceException, GeDARuntimeException {

		final boolean isMapEntity = Map.class.isAssignableFrom(entityClass);
		final boolean isListEntity = !isMapEntity && List.class.isAssignableFrom(entityClass);

		final PropertyDescriptor dtoFieldDesc = PropertyInspector.getDtoPropertyDescriptorForField(dtoClass,
				meta.getDtoFieldName(), dtoPropertyDescriptors);

		final MethodSynthesizer synthesizer = context.getMethodSynthesizer();
		final MethodSynthesizer entitySynthesizer;
		final PropertyDescriptor entityFieldDesc;

		if (isMapEntity || isListEntity) {
			if (isMapEntity) {
				entitySynthesizer = mapSynthesizer;
			} else {
				entitySynthesizer = listSynthesizer;
			}
			entityFieldDesc = PropertyInspector.getDtoPropertyDescriptorForField(dtoClass, meta.getDtoFieldName(),
					dtoPropertyDescriptors);
		} else {
			entitySynthesizer = synthesizer;
			entityFieldDesc = PropertyInspector.getEntityPropertyDescriptorForField(dtoClass, entityClass,
					meta.getDtoFieldName(), meta.getEntityFieldName(), entityPropertyDescriptors);
		}

		final DataReader entityFieldRead = entitySynthesizer.synthesizeReader(entityFieldDesc);
		final DataWriter entityFieldWrite = meta.isReadOnly() ? null : entitySynthesizer
				.synthesizeWriter(entityFieldDesc);

		return new DataPipeChain(meta.isReadOnly() ? null : synthesizer.synthesizeReader(dtoFieldDesc),
				entityFieldRead, entityFieldWrite, pipe, meta);

	}

}
