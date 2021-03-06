/*
 * This code is distributed under The GNU Lesser General Public License (LGPLv3)
 * Please visit GNU site for LGPLv3 http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright Denis Pavlov 2009
 * Web: http://www.genericdtoassembler.org
 * SVN: https://svn.code.sf.net/p/geda-genericdto/code/trunk/
 * SVN (mirror): http://geda-genericdto.googlecode.com/svn/trunk/
 */

package com.inspiresoftware.lib.dto.geda.assembler.dsl.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.inspiresoftware.lib.dto.geda.adapter.ExtensibleBeanFactory;
import com.inspiresoftware.lib.dto.geda.dsl.DtoContext;
import com.inspiresoftware.lib.dto.geda.dsl.DtoEntityContext;
import com.inspiresoftware.lib.dto.geda.exception.BeanFactoryUnableToLocateRepresentationException;
import com.inspiresoftware.lib.dto.geda.exception.GeDARuntimeException;

/**
 * User: denispavlov Date: 12-09-20 Time: 1:30 PM
 */
public class DtoContextByClass implements DtoContext {

	private final Class dtoClass;
	private final ExtensibleBeanFactory beanFactory;

	private final Map<Integer, DtoEntityContext> contexts = new ConcurrentHashMap<Integer, DtoEntityContext>();

	public DtoContextByClass(final Class dtoClass, final ExtensibleBeanFactory beanFactory) {
		this.dtoClass = dtoClass;
		this.beanFactory = beanFactory;
	}

	/** {@inheritDoc} */
	@Override
	public Class getDtoClass() {
		return dtoClass;
	}

	/** {@inheritDoc} */
	@Override
	public DtoContext alias(final String beanKey) {
		if (beanFactory == null) {
			throw new GeDARuntimeException(
					"Alias for "
							+ beanKey
							+ " cannot be registered. Bean factory must be specified. Use constructor DefaultDSLRegistry(BeanFactory)");
		}
		beanFactory.registerDto(beanKey, dtoClass.getCanonicalName());
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoEntityContext forEntity(final Class entityClass) {
		if (entityClass == null) {
			throw new GeDARuntimeException("entityClass must not be null");
		}
		final int hash = entityClass.hashCode();
		if (contexts.containsKey(hash)) {
			return contexts.get(hash);
		}
		final DtoEntityContext ctx = new DtoEntityContextByClass(dtoClass, entityClass, beanFactory);
		contexts.put(hash, ctx);
		return ctx;
	}

	/** {@inheritDoc} */
	@Override
	public DtoEntityContext forEntity(final Object entityInstance) {
		if (entityInstance == null) {
			throw new GeDARuntimeException("entityInstance must not be null");
		}
		return forEntity(entityInstance.getClass());
	}

	/** {@inheritDoc} */
	@Override
	public DtoEntityContext forEntity(final String beanKey) {
		if (beanFactory == null) {
			throw new GeDARuntimeException(
					"Bean factory must be specified. Use constructor DefaultDSLRegistry(BeanFactory)");
		}
		final Class representative = beanFactory.getClazz(beanKey);
		if (representative == null) {
			throw new BeanFactoryUnableToLocateRepresentationException(beanFactory.toString(), "top level", beanKey,
					false);
		}
		return forEntity(representative);
	}

	/** {@inheritDoc} */
	@Override
	public DtoEntityContext forEntityGeneric() {
		return forEntity(Object.class);
	}

	/** {@inheritDoc} */
	@Override
	public DtoEntityContext has(final Class entityClass) {
		if (entityClass == null) {
			throw new GeDARuntimeException("entityClass must not be null");
		}
		int hash = entityClass.hashCode();
		if (contexts.containsKey(hash)) {
			return contexts.get(hash);
		}

		// try immediate interfaces
		for (Class iFace : entityClass.getInterfaces()) {
			hash = iFace.hashCode();
			if (contexts.containsKey(hash)) {
				return contexts.get(hash);
			}
		}

		// try superclass for proxies and wrappers
		if (entityClass.getSuperclass() != null) {
			hash = entityClass.getSuperclass().hashCode();
			if (contexts.containsKey(hash)) {
				return contexts.get(hash);
			}
		}

		// try generic context (if one was provided)
		hash = Object.class.hashCode();
		if (contexts.containsKey(hash)) {
			return contexts.get(hash);
		}

		// no context for this entity
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public DtoEntityContext useContextFor(final DtoEntityContext ctx, final Class entityClass) {

		if (!contexts.containsValue(ctx)) {
			throw new IllegalArgumentException("This dto does not have a mapping for context with entity: "
					+ ctx.getEntityClass());
		}

		int hash = entityClass.hashCode();
		contexts.put(hash, ctx);

		return ctx;
	}

	/** {@inheritDoc} */
	@Override
	public DtoEntityContext useContextFor(final DtoEntityContext ctx, final String beanKey) {

		if (!contexts.containsValue(ctx)) {
			throw new IllegalArgumentException("This dto does not have a mapping for context with entity: "
					+ ctx.getEntityClass());
		}

		if (beanFactory == null) {
			throw new GeDARuntimeException(
					"Bean factory must be specified. Use constructor DefaultDSLRegistry(BeanFactory)");
		}
		final Class representative = beanFactory.getClazz(beanKey);
		if (representative == null) {
			throw new BeanFactoryUnableToLocateRepresentationException(beanFactory.toString(), "top level", beanKey,
					false);
		}
		return useContextFor(ctx, representative);
	}
}
