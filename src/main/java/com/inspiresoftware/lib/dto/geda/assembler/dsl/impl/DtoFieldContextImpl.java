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

import com.inspiresoftware.lib.dto.geda.dsl.DtoEntityContext;
import com.inspiresoftware.lib.dto.geda.dsl.DtoFieldContext;
import com.inspiresoftware.lib.dto.geda.dsl.DtoParentContext;
import com.inspiresoftware.lib.dto.geda.dsl.DtoVirtualFieldContext;

/**
 * User: denispavlov Date: 12-09-20 Time: 2:41 PM
 */
public class DtoFieldContextImpl implements DtoFieldContext {

	private final DtoEntityContext dtoEntityContext;

	private final String dtoField;
	private String entityField;

	private boolean readOnly;
	private String[] entityBeanKeys;
	private String dtoBeanKey;

	private boolean virtual;
	private String converter;

	private boolean dtoParent;
	private String dtoParentPrimaryKey;
	private String dtoParentRetriever;

	public DtoFieldContextImpl(final DtoEntityContext dtoEntityContext, final String fieldName) {
		this.dtoEntityContext = dtoEntityContext;
		dtoField = fieldName;
		entityField = fieldName;
		virtual = false;
		readOnly = false;
		dtoParent = false;
	}

	/** {@inheritDoc} */
	@Override
	public DtoFieldContext forField(final String fieldName) {
		entityField = fieldName;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoVirtualFieldContext forVirtual() {
		virtual = true;
		return new DtoVirtualFieldContext() {
			@Override
			public DtoFieldContext converter(final String converter) {
				return DtoFieldContextImpl.this.converter(converter);
			}
		};
	}

	/** {@inheritDoc} */
	@Override
	public DtoFieldContext readOnly() {
		readOnly = true;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoFieldContext converter(final String converter) {
		this.converter = converter;
		return this;
	}

	@Override
	public DtoFieldContext entityBeanKeys(final String... entityBeanKeys) {
		this.entityBeanKeys = entityBeanKeys;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoFieldContext dtoBeanKey(final String dtoBeanKey) {
		this.dtoBeanKey = dtoBeanKey;
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public DtoParentContext dtoParent(final String primaryKeyFieldName) {
		dtoParent = true;
		dtoParentPrimaryKey = primaryKeyFieldName;
		return new DtoParentContext() {
			@Override
			public DtoFieldContext retriever(final String retriever) {
				dtoParentRetriever = retriever;
				return DtoFieldContextImpl.this;
			}
		};
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
	public boolean getValueOfVirtual() {
		return virtual;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfConverter() {
		return converter;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getValueOfDtoParent() {
		return dtoParent;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfDtoParentPrimaryKey() {
		return dtoParentPrimaryKey;
	}

	/** {@inheritDoc} */
	@Override
	public String getValueOfDtoParentRetriever() {
		return dtoParentRetriever;
	}

}
