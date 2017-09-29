package com.stackroute.datamunger.query;

import com.stackroute.datamunger.query.parser.Restriction;

//this class contains methods to evaluate expressions
public class Filter {
	Boolean ans =false;
	int a;
	public Boolean evaluateExpressions(Restriction restriction,DataTypeDefinitions dataTypeDefinitions,String[] frow,String[] srow,String[] arr) {
		
		String propertyName= restriction.propertyName;
		String propertyValue = restriction.propertyValue;
		String condition = restriction.condition;
		int c = srow.length;
		
		String type = new String();
		for (int i=0;i< c+1; i++)
		{
			if (frow[i].equals(propertyName)) {
			type = (String) dataTypeDefinitions.getDataType(srow[i].trim());
			this.a=i;
			}
		}
		if(type == "java.lang.Integer") {
			if(condition.equals(">=")&&Integer.parseInt(arr[this.a])>=Integer.parseInt(propertyValue) )
				this.ans=true;
			if(condition.equals("==")&&Integer.parseInt(arr[this.a])==Integer.parseInt(propertyValue) )
					this.ans=true;
			if(condition.equals("<=")&&Integer.parseInt(arr[this.a])<=Integer.parseInt(propertyValue) )
				this.ans=true;
			if(condition.equals(">")&&Integer.parseInt(arr[this.a])>Integer.parseInt(propertyValue) )
					this.ans=true;
			if(condition.equals("<")&&Integer.parseInt(arr[this.a])<Integer.parseInt(propertyValue) )
				this.ans=true;
			if(condition.equals("!=")&&Integer.parseInt(arr[this.a])!=Integer.parseInt(propertyValue) )
					this.ans=true;			
				
				
		}
		else {
			if(condition.equals("==")&&arr[this.a].equals(propertyValue) )
				this.ans=true;
			if(condition.equals("!=")&&!arr[this.a].equals(propertyValue) )
				this.ans=true;
			
		}
		
		
		
		
		return ans;
	}
	/* 
	 * the evaluateExpression() method of this class is responsible for evaluating 
	 * the expressions mentioned in the query. It has to be noted that the process 
	 * of evaluating expressions will be different for different data types. there 
	 * are 6 operators that can exist within a query i.e. >=,<=,<,>,!=,= This method 
	 * should be able to evaluate all of them. 
	 * Note: while evaluating string expressions, please handle uppercase and lowercase 
	 * 
	 */
	
	
	
	
	
	
	//method containing implementation of equalTo operator
	
	
	
	
	
	//method containing implementation of notEqualTo operator
	
	
	
	
	
	
	
	//method containing implementation of greaterThan operator
	
	
	
	
	
	
	
	//method containing implementation of greaterThanOrEqualTo operator
	
	
	
	
	
	
	//method containing implementation of lessThan operator
	  
	
	
	
	
	//method containing implementation of lessThanOrEqualTo operator
	
}
