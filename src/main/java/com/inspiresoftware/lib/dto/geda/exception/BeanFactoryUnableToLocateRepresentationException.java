/*
 * This code is distributed under The GNU Lesser General Public License (LGPLv3)
 * Please visit GNU site for LGPLv3 http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright Denis Pavlov 2009
 * Web: http://www.genericdtoassembler.org
 * SVN: https://svn.code.sf.net/p/geda-genericdto/code/trunk/
 * SVN (mirror): http://geda-genericdto.googlecode.com/svn/trunk/
 */

package com.inspiresoftware.lib.dto.geda.exception;

/**
 * Denotes exception when bean factory is unable to create instance of dto/entity.
 *
 * @author denispavlov
 *
 * @since 2.1.0
 */
public class BeanFactoryUnableToLocateRepresentationException extends GeDAException {

	private static final long serialVersionUID = 20120919L;

	private final String factoryToString;
	private final boolean dto;
	private final String fieldName;
	private final String beanKey;

	/**
	 * @param factoryToString
	 *            factory to string representation
	 * @param fieldName
	 *            field name of the dto instance
	 * @param beanKey
	 *            bean key to use in bean factory
	 * @param dto
	 *            true if is dto, false if is entity
	 */
	public BeanFactoryUnableToLocateRepresentationException(final String factoryToString, final String fieldName,
			final String beanKey, final boolean dto) {
		super("Unable to locate " + (dto ? "dto " : "entity ") + " class/interface representation with key: " + beanKey
				+ " using beanFactory: " + factoryToString + " for: " + fieldName);
		this.beanKey = beanKey;
		this.factoryToString = factoryToString;
		this.fieldName = fieldName;
		this.dto = dto;
	}

	/**
	 * @return true if cause by dto mapping (false if by entity mapping)
	 */
	public boolean isDto() {
		return dto;
	}

	/**
	 * @return field name
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @return bean key
	 */
	public String getBeanKey() {
		return beanKey;
	}

	/**
	 * @return factory to string representation
	 */
	public String getFactoryToString() {
		return factoryToString;
	}

}
