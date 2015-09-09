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

import java.util.HashMap;
import java.util.Map;

import com.inspiresoftware.lib.dto.geda.adapter.DtoToEntityMatcher;
import com.inspiresoftware.lib.dto.geda.dsl.DtoEntityContext;
import com.inspiresoftware.lib.dto.geda.dsl.DtoMapContext;

/**
 * User: denispavlov Date: 12-09-20 Time: 2:43 PM
 */
public class DtoMapContextImpl implements DtoMapContext {

	private final DtoEntityContext dtoEntityContext;

	private final String dtoField;
	private String entityField;

	private boolean readOnly;
	private String[] entityBeanKeys;
	private String dtoBeanKey;

	private Class entityMapOrCollectionClass;
	private String entityMapOrCollectionClassKey;

	private Class dtoMapClass;
	private String dtoMapClassKey;

	private Class entityGenericType;
	private String entityGenericTypeKey;

	private String entityCollectionMapKey;
	private boolean useEntityMapKey;

	private Class<? extends DtoToEntityMatcher> dtoToEntityMatcher;
	private String dtoToEntityMatcherKey;

	public DtoMapContextImpl(final DtoEntityContext dtoEntityContext, final String fieldName) {
		this.dtoEntityContext = dtoEntityContext;
		dtoField = fieldName;
		entityField = fieldName;
		readOnly = false;
		useEntityMapKey = false;
		entityMapOrCollectionClass = HashMap.class;
		dtoMapClass = HashMap.class;
		entityGenericType = Object.class;
		dtoToEntityMatcher = DtoToEntityMatcher.class;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext forField(final String fieldName) {
		entityField = fieldName;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext readOnly() {
		readOnly = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext entityMapOrCollectionClass(final Class entityMapOrCollectionClass) {
		this.entityMapOrCollectionClass = entityMapOrCollectionClass;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext entityMapOrCollectionClassKey(final String entityMapOrCollectionClassKey) {
		this.entityMapOrCollectionClassKey = entityMapOrCollectionClassKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext dtoMapClass(final Class<? extends Map> dtoMapClass) {
		this.dtoMapClass = dtoMapClass;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext dtoMapClassKey(final String dtoMapClassKey) {
		this.dtoMapClassKey = dtoMapClassKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext entityBeanKeys(final String... entityBeanKeys) {
		this.entityBeanKeys = entityBeanKeys;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext dtoBeanKey(final String dtoBeanKey) {
		this.dtoBeanKey = dtoBeanKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext entityGenericType(final Class entityGenericType) {
		this.entityGenericType = entityGenericType;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext entityGenericTypeKey(final String entityGenericTypeKey) {
		this.entityGenericTypeKey = entityGenericTypeKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext entityCollectionMapKey(final String entityCollectionMapKey) {
		this.entityCollectionMapKey = entityCollectionMapKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext useEntityMapKey() {
		useEntityMapKey = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext dtoToEntityMatcher(final Class<? extends DtoToEntityMatcher> dtoToEntityMatcher) {
		this.dtoToEntityMatcher = dtoToEntityMatcher;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoMapContext dtoToEntityMatcherKey(final String dtoToEntityMatcherKey) {
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
	public Class getValueOfEntityMapOrCollectionClass() {
		return entityMapOrCollectionClass;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfEntityMapOrCollectionClassKey() {
		return entityMapOrCollectionClassKey;
	}

	/** {@inheritDoc} */
	@Override
	public Class getValueOfDtoMapClass() {
		return dtoMapClass;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfDtoMapClassKey() {
		return dtoMapClassKey;
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
	public String getValueOfEntityCollectionMapKey() {
		return entityCollectionMapKey;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getValueOfUseEntityMapKey() {
		return useEntityMapKey;
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
