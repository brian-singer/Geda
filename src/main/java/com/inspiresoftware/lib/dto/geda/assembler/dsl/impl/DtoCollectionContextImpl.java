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

import java.util.ArrayList;
import java.util.Collection;

import com.inspiresoftware.lib.dto.geda.adapter.DtoToEntityMatcher;
import com.inspiresoftware.lib.dto.geda.dsl.DtoCollectionContext;
import com.inspiresoftware.lib.dto.geda.dsl.DtoEntityContext;

/**
 * User: denispavlov Date: 12-09-20 Time: 2:42 PM
 */
public class DtoCollectionContextImpl implements DtoCollectionContext {

	private final DtoEntityContext dtoEntityContext;

	private final String dtoField;
	private String entityField;

	private boolean readOnly;
	private String[] entityBeanKeys;
	private String dtoBeanKey;

	private Class entityCollectionClass;
	private String entityCollectionClassKey;

	private Class dtoCollectionClass;
	private String dtoCollectionClassKey;

	private Class entityGenericType;
	private String entityGenericTypeKey;

	private Class<? extends DtoToEntityMatcher> dtoToEntityMatcher;
	private String dtoToEntityMatcherKey;

	public DtoCollectionContextImpl(final DtoEntityContext dtoEntityContext, final String fieldName) {
		this.dtoEntityContext = dtoEntityContext;
		dtoField = fieldName;
		entityField = fieldName;
		readOnly = false;
		entityCollectionClass = ArrayList.class;
		dtoCollectionClass = ArrayList.class;
		entityGenericType = Object.class;
		dtoToEntityMatcher = DtoToEntityMatcher.class;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext forField(final String fieldName) {
		entityField = fieldName;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext readOnly() {
		readOnly = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext entityCollectionClass(final Class<? extends Collection> entityCollectionClass) {
		this.entityCollectionClass = entityCollectionClass;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext entityCollectionClassKey(final String entityCollectionClassKey) {
		this.entityCollectionClassKey = entityCollectionClassKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext dtoCollectionClass(final Class<? extends Collection> dtoCollectionClass) {
		this.dtoCollectionClass = dtoCollectionClass;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext dtoCollectionClassKey(final String dtoCollectionClassKey) {
		this.dtoCollectionClassKey = dtoCollectionClassKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext entityBeanKeys(final String... entityBeanKeys) {
		this.entityBeanKeys = entityBeanKeys;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext dtoBeanKey(final String dtoBeanKey) {
		this.dtoBeanKey = dtoBeanKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext entityGenericType(final Class entityGenericType) {
		this.entityGenericType = entityGenericType;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext entityGenericTypeKey(final String entityGenericTypeKey) {
		this.entityGenericTypeKey = entityGenericTypeKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext dtoToEntityMatcher(final Class<? extends DtoToEntityMatcher> dtoToEntityMatcher) {
		this.dtoToEntityMatcher = dtoToEntityMatcher;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoCollectionContext dtoToEntityMatcherKey(final String dtoToEntityMatcherKey) {
		this.dtoToEntityMatcherKey = dtoToEntityMatcherKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoEntityContext and() {
		return dtoEntityContext;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfDtoField() {
		return dtoField;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfEntityField() {
		return entityField;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getValueOfReadOnly() {
		return readOnly;
	}

	/** {@inheritDoc} */
	@Override
	public String[] getValueOfEntityBeanKeys() {
		return entityBeanKeys;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfDtoBeanKey() {
		return dtoBeanKey;
	}

	/** {@inheritDoc} */
	@Override
	public Class getValueOfEntityCollectionClass() {
		return entityCollectionClass;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfEntityCollectionClassKey() {
		return entityCollectionClassKey;
	}

	/** {@inheritDoc} */
	@Override
	public Class getValueOfDtoCollectionClass() {
		return dtoCollectionClass;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfDtoCollectionClassKey() {
		return dtoCollectionClassKey;
	}

	/** {@inheritDoc} */
	@Override
	public Class getValueOfEntityGenericType() {
		return entityGenericType;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfEntityGenericTypeKey() {
		return entityGenericTypeKey;
	}

	/** {@inheritDoc} */
	@Override
	public Class<? extends DtoToEntityMatcher> getValueOfDtoToEntityMatcher() {
		return dtoToEntityMatcher;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfDtoToEntityMatcherKey() {
		return dtoToEntityMatcherKey;
	}

}
