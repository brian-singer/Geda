package com.inspiresoftware.lib.dto.geda.adapter.impl;

import com.inspiresoftware.lib.dto.geda.adapter.BeanFactory;
import com.inspiresoftware.lib.dto.geda.adapter.ValueConverter;

public class ParentEntityValueConverter implements ValueConverter {

	private Object parent;

	public ParentEntityValueConverter(Object parent) {
		this.parent = parent;
	}

	@Override
	public Object convertToDto(final Object object, final BeanFactory beanFactory) {
		return parent;
	}

	@Override
	public Object convertToEntity(final Object object, final Object oldEntity, final BeanFactory beanFactory) {
		return parent;
	}

}
