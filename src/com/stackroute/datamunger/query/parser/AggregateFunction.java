package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for 
 * each aggregate function
 * */
public class AggregateFunction {
	public AggregateFunction(String field, String function) {
		super();
		this.field = field;
		this.function = function;
	}

	public String field;
	public String function;
	
	public String getField() {
		return this.field;
	}
	
	public String getFunction() {
		return this.function;
	}
}
