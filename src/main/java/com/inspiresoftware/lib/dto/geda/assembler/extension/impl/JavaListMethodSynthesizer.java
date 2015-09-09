/*
 * This code is distributed under The GNU Lesser General Public License (LGPLv3)
 * Please visit GNU site for LGPLv3 http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright Denis Pavlov 2009
 * Web: http://www.genericdtoassembler.org
 * SVN: https://svn.code.sf.net/p/geda-genericdto/code/trunk/
 * SVN (mirror): http://geda-genericdto.googlecode.com/svn/trunk/
 */

package com.inspiresoftware.lib.dto.geda.assembler.extension.impl;

import java.beans.PropertyDescriptor;
import java.util.List;

import com.inspiresoftware.lib.dto.geda.assembler.extension.DataReader;
import com.inspiresoftware.lib.dto.geda.assembler.extension.DataWriter;
import com.inspiresoftware.lib.dto.geda.assembler.extension.MethodSynthesizer;
import com.inspiresoftware.lib.dto.geda.exception.GeDAException;
import com.inspiresoftware.lib.dto.geda.exception.GeDARuntimeException;
import com.inspiresoftware.lib.dto.geda.exception.InspectionPropertyNotFoundException;
import com.inspiresoftware.lib.dto.geda.exception.UnableToCreateInstanceException;

/**
 * List class method synthesizer.
 *
 * @since 2.1.0
 *
 *        User: denispavlov Date: 12-09-18 Time: 10:10 AM
 */
public class JavaListMethodSynthesizer implements MethodSynthesizer {

	/** {@inheritDoc} */
	@Override
	public DataReader synthesizeReader(final PropertyDescriptor descriptor) throws InspectionPropertyNotFoundException,
	UnableToCreateInstanceException, GeDARuntimeException {

		final String propName = descriptor.getName();
		final Class returnType = descriptor.getReadMethod().getReturnType();

		return new DataReader() {

			private String property = propName;
			private Class type = returnType;

			@Override
			public Object read(final Object source) {
				final List<Object> listSource = (List) source;
				final int index = listSource.indexOf(property);
				if (index != -1 && listSource.size() > index + 1) {
					return listSource.get(index + 1);
				}
				return null;
			}

			@Override
			public Class<?> getReturnType() {
				return type;
			}
		};
	}

	/** {@inheritDoc} */
	@Override
	public DataWriter synthesizeWriter(final PropertyDescriptor descriptor) throws InspectionPropertyNotFoundException,
	UnableToCreateInstanceException, GeDARuntimeException {

		final String propName = descriptor.getName();
		final Class paramType = descriptor.getWriteMethod().getParameterTypes()[0];

		return new DataWriter() {

			private String property = propName;
			private Class type = paramType;

			@Override
			public void write(final Object source, final Object value) {
				final List<Object> listSource = (List) source;
				final int index = listSource.indexOf(property);
				if (index != -1) {
					listSource.set(index + 1, value);
				} else {
					listSource.add(property);
					listSource.add(value);
				}
			}

			@Override
			public Class<?> getParameterType() {
				return type;
			}
		};
	}

	/** {@inheritDoc} */
	@Override
	public boolean configure(final String configuration, final Object value) throws GeDAException {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void releaseResources() {
		// none
	}

}
