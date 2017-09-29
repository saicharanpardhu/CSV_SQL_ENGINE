package com.stackroute.datamunger.query.parser;

import java.util.List;
import java.util.Map;
/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */
public class QueryParameter {
	
	public String queryString;
	public String file;
	public String baseQuery;
	public String QUERY_TYPE;
	public List<Restriction> restrictions;
	public List<String> fields;
	public List<String> logicalOperators;
	public List<String> orderByField;
	public List<String> groupByField;
	public List<AggregateFunction> aggregateFunctions;
	
	public String getFile() {
		return this.file;
	}
	
	public List<Restriction> getRestrictions() {
		return this.restrictions;
	}
	
	public List<String> getLogicalOperators() {
		return this.logicalOperators;
	}
	
	public List<String> getFields() {
		return this.fields;
	}
	
	public List<AggregateFunction> getAggregateFunctions() {
		return this.aggregateFunctions;
	}
	
	public List<String> getGroupByFields() {

		//for(int i = 0;i<this.groupByField.size();i++) System.out.println("Group by" + this.groupByField.get(i));
		return this.groupByField;
	}
	
	public List<String> getOrderByFields() {
		return this.orderByField;
	}
	public String getBaseQuery() {
		return this.baseQuery;
	}
	public String getQUERY_TYPE() {
		return this.QUERY_TYPE;
	}
}
