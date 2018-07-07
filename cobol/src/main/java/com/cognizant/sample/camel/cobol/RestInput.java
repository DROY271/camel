package com.cognizant.sample.camel.cobol;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.FixedLengthRecord;

@FixedLengthRecord(length=80, paddingChar=' ', crlf="")
public class RestInput {

	@DataField(pos=1, length=10, paddingChar=' ', align="R")
	private String name;
	
	@DataField(pos=2, length=70, paddingChar=' ', align="L")
	private String value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
