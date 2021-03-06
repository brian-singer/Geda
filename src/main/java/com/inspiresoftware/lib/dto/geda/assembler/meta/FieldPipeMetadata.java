/*
 * This code is distributed under The GNU Lesser General Public License (LGPLv3)
 * Please visit GNU site for LGPLv3 http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright Denis Pavlov 2009
 * Web: http://www.genericdtoassembler.org
 * SVN: https://svn.code.sf.net/p/geda-genericdto/code/trunk/
 * SVN (mirror): http://geda-genericdto.googlecode.com/svn/trunk/
 */

package com.inspiresoftware.lib.dto.geda.assembler.meta;

/**
 * Metadata specific to field pipes.
 *
 * @author DPavlov
 */
public interface FieldPipeMetadata extends PipeMetadata {

	/**
	 * @return converter key to use.
	 */
	String getConverterKey();

	/**
	 * @return true if field has {@link com.inspiresoftware.lib.dto.geda.annotations.DtoParent} annotation
	 */
	boolean isChild();

	/**
	 * @return specified for child=true to use as PK for parent
	 */
	String getParentEntityPrimaryKeyField();

	/**
	 * @return key for entity retriever.
	 */
	String getEntityRetrieverKey();

}
