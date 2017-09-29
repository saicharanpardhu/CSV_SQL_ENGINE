package com.stackroute.datamunger;

import java.util.HashMap;
import java.util.Scanner;

import com.stackroute.datamunger.query.Query;
import com.stackroute.datamunger.writer.JsonWriter;


public class DataMunger {
	
	public static void main(String[] args){
		
		String queryString=null;
		//read the query from the user
		Scanner sc = new Scanner(System.in);
		queryString = sc.nextLine();
		sc.close();
		/*
		 * Instantiate Query class. This class is responsible for: 
		 * 1. Parsing the query
		 * 2. Select the appropriate type of query processor 3. Get the resultSet which
		 * is populated by the Query Processor
		 */
		Query query=new Query();
		
		/*
		 * Instantiate JsonWriter class. This class is responsible for writing the
		 * ResultSet into a JSON file
		 */
		JsonWriter writer=new JsonWriter();
		/*
		 * call executeQuery() method of Query class to get the resultSet. Pass this
		 * resultSet as parameter to writeToJson() method of JsonWriter class to write
		 * the resultSet into a JSON file
		 */
		HashMap dataSet = query.executeQuery(queryString);
		if(writer.writeToJson(dataSet)) {
			System.out.println("Output written to data/result.json"+dataSet.get(1));
		}

	}
}
