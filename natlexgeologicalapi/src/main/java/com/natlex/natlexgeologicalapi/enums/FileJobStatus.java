package com.natlex.natlexgeologicalapi.enums;

public enum FileJobStatus {

	IN_PROGRESS("IN PORGRESS"), DONE("DONE"), ERROR("ERROR");

	private String stringValue;

	private FileJobStatus(String stringValue) {
		this.stringValue = stringValue;
	}

	public String getStringValue() {
		return this.stringValue;
	}
}
