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
import java.util.Map;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.adapter.DtoToEntityMatcher;
import com.inspiresoftware.lib.dto.geda.exception.BeanFactoryNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.BeanFactoryUnableToLocateRepresentationException;
import com.inspiresoftware.lib.dto.geda.exception.DtoToEntityMatcherNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.NotDtoToEntityMatcherException;
import com.inspiresoftware.lib.dto.geda.exception.UnableToCreateInstanceException;

/**
 * Collection pipe meta contains information on the metadata of the pipe.
 *
 * @author DPavlov
 */
@SuppressWarnings("unchecked")
public class CollectionPipeMetadata extends BasePipeMetadata implements
com.inspiresoftware.lib.dto.geda.assembler.meta.CollectionPipeMetadata {

	private static final Map<Class, DtoToEntityMatcher> CACHE = new HashMap<Class, DtoToEntityMatcher>();

	private final Class<? extends Collection> dtoCollectionClass;
	private final String dtoCollectionClassKey;
	private final Class<? extends Collection> entityCollectionClass;
	private final String entityCollectionClassKey;

	private final Class<?> returnType;
	private final String returnTypeKey;
	private final DtoToEntityMatcher dtoToEntityMatcher;
	private final String dtoToEntityMatcherKey;

	/**
	 *
	 * @param dtoFieldName
	 *            key for accessing field on DTO object
	 * @param entityFieldName
	 *            key for accessing field on Entity bean
	 * @param dtoBeanKey
	 *            key for constructing DTO bean
	 * @param entityBeanKey
	 *            key for constructing Entity bean
	 * @param readOnly
	 *            read only marker (true then write to entity is omitted)
	 * @param dtoCollectionClass
	 *            the dto collection class for creating new collection instance
	 * @param dtoCollectionClassKey
	 *            key for dto collection class fetched from beanFactory
	 * @param entityCollectionClass
	 *            the entity collection class for creating new collection instance
	 * @param entityCollectionClassKey
	 *            key for entity collection class fetched from beanFactory
	 * @param returnType
	 *            the generic type for entity collection item
	 * @param returnTypeKey
	 *            bean factory key for generic type for entity collection item
	 * @param dtoToEntityMatcherClass
	 *            matcher for synchronising collections
	 * @param dtoToEntityMatcherKey
	 *            key of matcher in the converters map
	 *
	 * @throws UnableToCreateInstanceException
	 *             if unable to create item matcher
	 */
	public CollectionPipeMetadata(final String dtoFieldName, final String entityFieldName, final String dtoBeanKey,
			final String entityBeanKey, final boolean readOnly, final Class<? extends Collection> dtoCollectionClass,
			final String dtoCollectionClassKey, final Class<? extends Collection> entityCollectionClass,
			final String entityCollectionClassKey, final Class<?> returnType, final String returnTypeKey,
			final Class<? extends DtoToEntityMatcher> dtoToEntityMatcherClass, final String dtoToEntityMatcherKey)
					throws UnableToCreateInstanceException {

		super(dtoFieldName, entityFieldName, dtoBeanKey, entityBeanKey, readOnly);
		this.dtoCollectionClass = dtoCollectionClass;
		this.returnTypeKey = returnTypeKey != null && returnTypeKey.length() > 0 ? returnTypeKey : null;
		this.dtoCollectionClassKey = dtoCollectionClassKey != null && dtoCollectionClassKey.length() > 0 ? dtoCollectionClassKey
				: null;
		this.entityCollectionClass = entityCollectionClass;
		this.entityCollectionClassKey = entityCollectionClassKey != null && entityCollectionClassKey.length() > 0 ? entityCollectionClassKey
				: null;
		this.returnType = returnType;

		if (dtoToEntityMatcherKey == null || dtoToEntityMatcherKey.length() == 0) {
			if (CACHE.containsKey(dtoToEntityMatcherClass)) {
				dtoToEntityMatcher = CACHE.get(dtoToEntityMatcherClass);
			} else {
				dtoToEntityMatcher = newBeanForClass(dtoToEntityMatcherClass,
						"Unable to create matcher: {0} for: {1} - {2}", dtoToEntityMatcherClass, getDtoBeanKey(),
						getEntityBeanKey());
				CACHE.put(dtoToEntityMatcherClass, dtoToEntityMatcher);
			}
			this.dtoToEntityMatcherKey = null;
		} else {
			dtoToEntityMatcher = null;
			this.dtoToEntityMatcherKey = dtoToEntityMatcherKey;
		}

	}

	/** {@inheritDoc} */
	@Override
	public Collection newDtoCollection(final BeanFactory beanFactory) throws UnableToCreateInstanceException,
	BeanFactoryNotFoundException {
		if (dtoCollectionClassKey != null) {
			return newCollection(dtoCollectionClassKey, beanFactory, true);
		}
		return newCollection(dtoCollectionClass, " Dto field: ", getDtoFieldName());
	}

	/** {@inheritDoc} */
	@Override
	public Collection newEntityCollection(final BeanFactory beanFactory) throws UnableToCreateInstanceException,
	BeanFactoryNotFoundException {
		if (entityCollectionClassKey != null) {
			return newCollection(entityCollectionClassKey, beanFactory, false);
		}
		return newCollection(entityCollectionClass, " Entity field: ", getEntityFieldName());
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getReturnType(BeanFactory beanFactory) throws BeanFactoryUnableToLocateRepresentationException,
	BeanFactoryNotFoundException {
		if (returnTypeKey == null) {
			return returnType;
		}
		return getRepresentation(returnTypeKey, beanFactory, false);
	}

	/** {@inheritDoc} */
	@Override
	public DtoToEntityMatcher getDtoToEntityMatcher(final Map<String, Object> converters)
			throws DtoToEntityMatcherNotFoundException, NotDtoToEntityMatcherException {
		if (dtoToEntityMatcherKey == null) {
			return dtoToEntityMatcher;
		}
		if (converters == null) {
			throw new DtoToEntityMatcherNotFoundException(getDtoFieldName(), getEntityFieldName(),
					dtoToEntityMatcherKey);
		}
		final Object matcher = converters.get(dtoToEntityMatcherKey);
		if (matcher == null) {
			throw new DtoToEntityMatcherNotFoundException(getDtoFieldName(), getEntityFieldName(),
					dtoToEntityMatcherKey);
		}
		if (matcher instanceof DtoToEntityMatcher) {
			return (DtoToEntityMatcher) matcher;
		}
		throw new NotDtoToEntityMatcherException(getDtoFieldName(), getEntityFieldName(), dtoToEntityMatcherKey);
	}

	private Collection newCollection(final String clazzKey, final BeanFactory beanFactory, final boolean isDto)
			throws UnableToCreateInstanceException, BeanFactoryNotFoundException {
		if (beanFactory == null) {
			throw new BeanFactoryNotFoundException(getDtoFieldName(), clazzKey, isDto);
		}
		final Object coll = beanFactory.get(clazzKey);
		if (coll instanceof Collection) {
			return (Collection) coll;
		}
		throw new UnableToCreateInstanceException(clazzKey, " Collection" + (isDto ? " Dto" : " Entity") + " field: "
				+ getDtoFieldName() + "@key:" + clazzKey + " (Check if beanFactory [" + beanFactory
				+ "] returns a correct instance)", null);

	}

	private Collection newCollection(final Class<? extends Collection> clazz, final String type, final String field)
			throws UnableToCreateInstanceException {
		return newBeanForClass(clazz, "Unable to create collection: {0} for {1} {2}", clazz, type, field);
	}

	private <T> T newBeanForClass(final Class<T> clazz, final String errMsg, final Object... msgParams)
			throws UnableToCreateInstanceException {
		try {
			return clazz.newInstance();
		} catch (Exception iex) {
			throw new UnableToCreateInstanceException(clazz.getCanonicalName(), String.format(errMsg, msgParams), iex);
		}
	}

}
