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
 * General GeDA exception for unimplemented/unhandled situations.
 *
 * @author denispavlov
 *
 * @since 1.1.2
 */
public class GeDARuntimeException extends GeDAException {

	private static final long serialVersionUID = 20110609L;

	/**
	 * GeDA exception.
	 */
	public GeDARuntimeException() {
		super();
	}

	/**
	 * GeDA exception.
	 *
	 * @param message
	 *            message
	 * @param cause
	 *            cause
	 */
	public GeDARuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * GeDA exception.
	 *
	 * @param cause
	 *            cause
	 */
	public GeDARuntimeException(final Throwable cause) {
		super(cause);
	}

	/**
	 * GeDA exception.
	 *
	 * @param message
	 *            message
	 */
	public GeDARuntimeException(final String message) {
		super(message);
	}

}
