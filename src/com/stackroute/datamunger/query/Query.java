package com.stackroute.datamunger.query;

import java.util.HashMap;
import java.util.List;

import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.QueryParser;
import com.stackroute.datamunger.query.parser.Restriction;
import com.stackroute.datamunger.reader.CsvQueryProcessor;
import com.stackroute.datamunger.reader.QueryProcessingEngine;

public class Query {
	public List<Restriction> restrictions;
	public int a;
	/*
	 * This method will: 
	 * 1.parse the query and populate the QueryParameter object
	 * 2.Based on the type of query, it will select the appropriate Query processor.
	 * 
	 * In this example, we are going to work with only one Query Processor which is
	 * CsvQueryProcessor, which can work with select queries containing zero, one or
	 * multiple conditions
	 */
	public HashMap executeQuery(String queryString) {
		DataSet dataSet = new DataSet();
		QueryParser queryparser = new QueryParser();
		QueryParameter queryparameter  = new QueryParameter();
		/* instantiate QueryParser class */
		queryparameter = queryparser.parseQuery(queryString);
		/*
		 * call parseQuery() method of the class by passing the queryString which will
		 * return object of QueryParameter
		 */
		
		this.restrictions = queryparameter.restrictions;
//		this.a = this.restrictions.size();
		
		/*
		 * Check for Type of Query based on the QueryParameter object. In this
		 * assignment, we will process only queries containing zero, one or multiple
		 * where conditions i.e. conditions without aggregate functions, order by clause
		 * or group by clause
		 */
		CsvQueryProcessor csvQueryprocessor = new CsvQueryProcessor();
		
		dataSet = csvQueryprocessor.getResultSet(queryparameter);
		/*
		 * call the getResultSet() method of CsvQueryProcessor class by passing the
		 * QueryParameter Object to it. This method is supposed to return resultSet
		 * which is a HashMap
		 */
		
		
	
		return dataSet;
	}

}
