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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.assembler.extension.MethodSynthesizer;
import com.inspiresoftware.lib.dto.geda.dsl.Registry;
import com.inspiresoftware.lib.dto.geda.exception.AnnotationDuplicateBindingException;
import com.inspiresoftware.lib.dto.geda.exception.AnnotationMissingBeanKeyException;
import com.inspiresoftware.lib.dto.geda.exception.AnnotationMissingBindingException;
import com.inspiresoftware.lib.dto.geda.exception.AnnotationMissingException;
import com.inspiresoftware.lib.dto.geda.exception.AnnotationValidatingBindingException;
import com.inspiresoftware.lib.dto.geda.exception.BeanFactoryNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.BeanFactoryUnableToCreateInstanceException;
import com.inspiresoftware.lib.dto.geda.exception.CollectionEntityGenericReturnTypeException;
import com.inspiresoftware.lib.dto.geda.exception.DtoToEntityMatcherNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.EntityRetrieverNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.GeDARuntimeException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionBindingNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionInvalidDtoInstanceException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionInvalidEntityInstanceException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionPropertyNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionScanningException;
import com.inspiresoftware.lib.dto.geda.exception.InvalidDtoCollectionException;
import com.inspiresoftware.lib.dto.geda.exception.InvalidEntityCollectionException;
import com.inspiresoftware.lib.dto.geda.exception.NotDtoToEntityMatcherException;
import com.inspiresoftware.lib.dto.geda.exception.NotEntityRetrieverException;
import com.inspiresoftware.lib.dto.geda.exception.NotValueConverterException;
import com.inspiresoftware.lib.dto.geda.exception.UnableToCreateInstanceException;
import com.inspiresoftware.lib.dto.geda.exception.ValueConverterNotFoundException;

/**
 * Composite assembler to allow many entities to become a single Dto.
 *
 * @author Denis Pavlov
 * @since 2.0.2
 *
 */
@SuppressWarnings("unchecked")
public class DTOtoEntitiesAssemblerDecoratorImpl implements Assembler {

	private final Map<Class, Assembler> composite = new HashMap<Class, Assembler>();

	private final Class dtoClass;

	DTOtoEntitiesAssemblerDecoratorImpl(final Class dto, final Class[] entities, final ClassLoader classLoader,
			final MethodSynthesizer synthesizer, final Registry registry) throws InspectionScanningException,
			UnableToCreateInstanceException, InspectionPropertyNotFoundException, InspectionBindingNotFoundException,
			AnnotationMissingBindingException, AnnotationValidatingBindingException, GeDARuntimeException,
			AnnotationDuplicateBindingException {

		dtoClass = dto;

		for (final Class entity : entities) {

			composite.put(entity, new DTOtoEntityAssemblerImpl(dto, entity, classLoader, synthesizer, registry, false));

		}

	}

	/** {@inheritDoc} */
	@Override
	public void assembleDto(final Object dto, final Object entity, final Map<String, Object> converters,
			final BeanFactory dtoBeanFactory) throws InspectionInvalidDtoInstanceException,
			InspectionInvalidEntityInstanceException, BeanFactoryNotFoundException,
			BeanFactoryUnableToCreateInstanceException, AnnotationMissingException, NotValueConverterException,
			ValueConverterNotFoundException, UnableToCreateInstanceException,
			CollectionEntityGenericReturnTypeException, InspectionScanningException,
			InspectionPropertyNotFoundException, InspectionBindingNotFoundException, AnnotationMissingBindingException,
			AnnotationValidatingBindingException, GeDARuntimeException, AnnotationDuplicateBindingException {

		final Object[] values;
		if (entity instanceof Object[]) {
			values = (Object[]) entity;
		} else {
			values = new Object[] { entity };
		}

		for (final Object value : values) {
			if (value != null) {
				for (final Class type : composite.keySet()) {
					if (type.isAssignableFrom(value.getClass())) {
						final Assembler asm = composite.get(type);
						asm.assembleDto(dto, value, converters, dtoBeanFactory);
						break;
					}
				}
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public void assembleDtos(final Collection dtos, final Collection entities, final Map<String, Object> converters,
			final BeanFactory dtoBeanFactory) throws InvalidDtoCollectionException, UnableToCreateInstanceException,
			InspectionInvalidDtoInstanceException, InspectionInvalidEntityInstanceException,
			BeanFactoryNotFoundException, BeanFactoryUnableToCreateInstanceException, AnnotationMissingException,
			NotValueConverterException, ValueConverterNotFoundException, CollectionEntityGenericReturnTypeException,
			InspectionScanningException, InspectionPropertyNotFoundException, InspectionBindingNotFoundException,
			AnnotationMissingBindingException, AnnotationValidatingBindingException, GeDARuntimeException,
			AnnotationDuplicateBindingException {

		if (dtos instanceof Collection && dtos.isEmpty() && entities instanceof Collection) {

			for (Object entity : entities) {
				try {
					final Object dto = dtoClass.newInstance();
					assembleDto(dto, entity, converters, dtoBeanFactory);
					dtos.add(dto);
				} catch (InstantiationException exp) {
					throw new UnableToCreateInstanceException(dtoClass.getCanonicalName(),
							"Unable to create dto instance for: " + dtoClass.getName(), exp);
				} catch (IllegalAccessException exp) {
					throw new UnableToCreateInstanceException(dtoClass.getCanonicalName(),
							"Unable to create dto instance for: " + dtoClass.getName(), exp);
				}
			}

		} else {
			throw new InvalidDtoCollectionException();
		}

	}

	/** {@inheritDoc} */
	@Override
	public void assembleEntity(final Object dto, final Object entity, final Map<String, Object> converters,
			final BeanFactory entityBeanFactory) throws InspectionInvalidDtoInstanceException,
			InspectionInvalidEntityInstanceException, BeanFactoryNotFoundException,
			BeanFactoryUnableToCreateInstanceException, NotEntityRetrieverException, EntityRetrieverNotFoundException,
			NotValueConverterException, ValueConverterNotFoundException, AnnotationMissingBeanKeyException,
			AnnotationMissingException, UnableToCreateInstanceException, CollectionEntityGenericReturnTypeException,
			InspectionScanningException, InspectionPropertyNotFoundException, InspectionBindingNotFoundException,
			AnnotationMissingBindingException, AnnotationValidatingBindingException, GeDARuntimeException,
			AnnotationDuplicateBindingException, DtoToEntityMatcherNotFoundException, NotDtoToEntityMatcherException {

		final Object[] values;
		if (entity instanceof Object[]) {
			values = (Object[]) entity;
		} else {
			values = new Object[] { entity };
		}

		for (final Object value : values) {
			if (value != null) {
				for (final Class type : composite.keySet()) {
					if (type.isAssignableFrom(value.getClass())) {
						final Assembler asm = composite.get(type);
						asm.assembleEntity(dto, value, converters, entityBeanFactory);
						break;
					}
				}
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public void assembleEntities(final Collection dtos, final Collection entities,
			final Map<String, Object> converters, final BeanFactory entityBeanFactory)
					throws UnableToCreateInstanceException, InvalidEntityCollectionException,
					InspectionInvalidDtoInstanceException, InspectionInvalidEntityInstanceException,
					BeanFactoryNotFoundException, BeanFactoryUnableToCreateInstanceException, NotEntityRetrieverException,
					EntityRetrieverNotFoundException, NotValueConverterException, ValueConverterNotFoundException,
					AnnotationMissingBeanKeyException, AnnotationMissingException, CollectionEntityGenericReturnTypeException,
					InspectionScanningException, InspectionPropertyNotFoundException, InspectionBindingNotFoundException,
					AnnotationMissingBindingException, AnnotationValidatingBindingException, GeDARuntimeException,
					AnnotationDuplicateBindingException, DtoToEntityMatcherNotFoundException, NotDtoToEntityMatcherException {

		throw new UnsupportedOperationException(
				"Unsupported conversion of collection of composite DTO's to collection of entities");
	}

	/** {@inheritDoc} */
	@Override
	public void releaseResources() {

		final Iterator<Map.Entry<Class, Assembler>> it = composite.entrySet().iterator();
		while (it.hasNext()) {
			final Map.Entry<Class, Assembler> entry = it.next();
			entry.getValue().releaseResources();
			it.remove();
		}

	}
}
