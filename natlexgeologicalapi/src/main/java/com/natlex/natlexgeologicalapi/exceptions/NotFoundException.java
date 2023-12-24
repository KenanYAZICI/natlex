package com.natlex.natlexgeologicalapi.exceptions;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundException(Class<?> entityClass, String name) {
	}

	public NotFoundException(Class<?> entityClass, Long id) {
	}
}
