package com.stackroute.datamunger.query.parser;

/*
 * This class is used for storing name of field, condition and value for 
 * each conditions
 * */
public class Restriction {
	public String propertyName;
	public String condition;
	public String propertyValue;
	
	public Restriction(String propertyName, String condition, String propertyValue) {
		super();
		this.propertyName = propertyName;
		this.condition = condition;
		this.propertyValue = propertyValue;
	}
	public String getPropertyName() {
		return this.propertyName;
	}
	public String getPropertyValue() {
		return this.propertyValue;
	}
	public String getCondition() {
		return this.condition;
	}

}