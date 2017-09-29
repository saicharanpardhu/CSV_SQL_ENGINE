package com.stackroute.datamunger.reader;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.stackroute.datamunger.query.DataSet;
import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Filter;
import com.stackroute.datamunger.query.Header;
import com.stackroute.datamunger.query.Row;
import com.stackroute.datamunger.query.RowDataTypeDefinitions;
import com.stackroute.datamunger.query.parser.QueryParameter;
import com.stackroute.datamunger.query.parser.Restriction;

import java.util.ListIterator;

public class CsvQueryProcessor implements QueryProcessingEngine {
	private String file = new String();
	private BufferedReader crunchifyBuffer = null;
	private BufferedReader crunchifyBuffer2 = null;
	public String[] frow;
	public String[] srow;
	public List<Restriction> restrictions;
	public String propertyName;
	public String condition;
	public String propertyValue;
	public String logicaloperator;
	public Boolean ans;
	public List<String> fields;
	public String field;
	
	
	/*
	 * This method will take QueryParameter object as a parameter which contains the
	 * parsed query and will process and populate the ResultSet
	 */
	public DataSet getResultSet(QueryParameter queryParameter) {
		DataSet dataSet = new DataSet();
		 this.file = queryParameter.file;
		 try {
			this.crunchifyBuffer = new BufferedReader(new FileReader(file.trim()));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 try {
			this.frow =this.crunchifyBuffer.readLine().split(",");
			this.srow =this.crunchifyBuffer.readLine().split(",");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * initialize BufferedReader to read from the file which is mentioned in
		 * QueryParameter. Consider Handling Exception related to file reading.
		 *
		 * read the first line which contains the header. Please note that the headers
		 * can contain spaces in between them. For eg: city, winner
		 *
		 * read the next line which contains the first row of data. We are reading this
		 * line so that we can determine the data types of all the fields. Please note
		 * that ipl.csv file contains null value in the last column. If you do not
		 * consider this while splitting, this might cause exceptions later
		 */
		Header header =new Header(); 
		int b = this.frow.length;
		for (int i=0;i< b; i++)
		{
			header.hashMap.put(frow[i],i+1);
			
		}
		/*
		 * populate the header Map object from the header array. header map is having
		 * data type <String,Integer> to contain the header and it's index.
		 */
		DataTypeDefinitions dataTypeDefinitions = new DataTypeDefinitions();
	    RowDataTypeDefinitions rowDataTypeDefinition = new RowDataTypeDefinitions();
		int c = this.srow.length;
		String type = new String();
		for (int i=0;i< c; i++)
		{
			
			type = (String) dataTypeDefinitions.getDataType(srow[i].trim());
			rowDataTypeDefinition.hashMap.put(i+1,type);
		}
		
		
		/*
		 * We have read the first line of text already and kept it in an array. Now, we
		 * can populate the RowDataTypeDefinition Map object. RowDataTypeDefinition map is
		 * having data type <Integer,String> to contain the index of the field and it's
		 * data type. To find the dataType by the field value, we will use getDataType()
		 * method of DataTypeDefinitions class
		 */
		
		
		/*
		 * once we have the header and dataTypeDefinitions maps populated, we can start
		 * reading from the first line. We will read one line at a time, then check
		 * whether the field values satisfy the conditions mentioned in the query,if
		 * yes, then we will add it to the resultSet. Otherwise, we will continue to
		 * read the next line. We will continue this till we have read till the last
		 * line of the CSV file.
		 */
		int d=0 ;
		int g=0;
		this.restrictions = queryParameter.restrictions;
		if(queryParameter.logicalOperators!=null&&queryParameter.logicalOperators.size()!=0) {
			System.out.println("jhblbkbjkhhgj"+queryParameter.logicalOperators.size());
		this.logicaloperator = queryParameter.logicalOperators.get(0);}
		this.fields = queryParameter.fields;
		if (this.restrictions!=null)
		d = this.restrictions.size();
		if (this.fields!=null)
		
		
		
		
		try {
			this.crunchifyBuffer2 = new BufferedReader(new FileReader(file.trim()));
			this.frow =this.crunchifyBuffer2.readLine().split(",");
			String line = new String();
			String[] arr;
			long v=1;
			/* reset the buffered reader so that it can start reading from the first line */
			
			
			/*
			 * skip the first line as it is already read earlier which contained the header
			 */
			/* read one line at a time from the CSV file till we have any lines left */
			
			while ((line = this.crunchifyBuffer2.readLine()) != null) {
				arr = line.split(",",-1);
				
				
				Row row = new Row();
				/*
				 * once we have read one line, we will split it into a String Array. This array
				 * will continue all the fields of the row. Please note that fields might
				 * contain spaces in between. Also, few fields might be empty.
				 */
				if (this.restrictions==null)
			    	this.ans=true;
				
				else {
				for( int f=0;f < d;f++) {
					
					
					Filter filter = new Filter();
					this.ans = filter.evaluateExpressions(this.restrictions.get(f), dataTypeDefinitions, this.frow,this.srow, arr);
					if(queryParameter.logicalOperators!=null&&queryParameter.logicalOperators.size()!=0) {
					if (!this.ans&& this.logicaloperator.equals("and"))
				    	break;
				    if (this.ans&& this.logicaloperator.equals("or"))
				    	break;
					}
				}}
				
				/*
				 * if there are where condition(s) in the query, test the row fields against
				 * those conditions to check whether the selected row satifies the conditions
				 */
				if (this.ans&&this.fields!=null) {
					
				    this.field = this.fields.get(0).toString();
				    g =this.fields.size();
					if(this.field.equals("*"))
					{
						for(int m=0;m<18;m++)
						{   System.out.println("jhblbkbjkhhgj"+arr[2]);
							row.put(this.frow[m],arr[m]);
							
						}
					}
					else {
					for( int h=0;h < g;h++) {
						int index;
						 if(header.hashMap!=null) {
					     this.field = this.fields.get(h).toString();
						 index =header.hashMap.get(this.field);
						 
					     row.put(this.field,arr[index-1]);}
						}
					}
					
					dataSet.put(v,row);
					v++;
					}
				
				
			}
			
			
			
			
			
			
			
			
			
			
			/*
			 * from QueryParameter object, read one condition at a time and evaluate the
			 * same. For evaluating the conditions, we will use evaluateExpressions() method
			 * of Filter class. Please note that evaluation of expression will be done
			 * differently based on the data type of the field. In case the query is having
			 * multiple conditions, you need to evaluate the overall expression i.e. if we
			 * have OR operator between two conditions, then the row will be selected if any
			 * of the condition is satisfied. However, in case of AND operator, the row will
			 * be selected only if both of them are satisfied.
			 */
			
			
			/*
			 * check for multiple conditions in where clause for eg: where salary>20000 and
			 * city=Bangalore for eg: where salary>20000 or city=Bangalore and dept!=Sales
			 */
			
			
			/*if the overall condition expression evaluates to true, then we 
			 * need to check if all columns are to be selected(select *) or few
			 * columns are to be selected(select col1,col2).
			 * In either of the cases, we will have to populate the row map object.
			 * Row Map object is having type <String,String> to contain field Index
			 * and field value for the selected fields.
			 * Once the row object is populated, add it to DataSet Map Object.
			 * DataSet Map object is having type <Long,Row> to hold the rowId
			 * (to be manually generated by incrementing a Long variable) and
			 * it's corresponding Row Object. */
			
			
			/*return dataset object*/
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long l=1;
		System.out.println("Output written to data/result.json" +dataSet);
		System.out.println("Output written to data/result.json" +dataSet.size());
		return dataSet;
	}
	
	
	
	
	
}
