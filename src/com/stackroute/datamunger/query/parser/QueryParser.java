package com.stackroute.datamunger.query.parser;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();
	public String field;
	/*
	 * this method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		
		
		queryParameter.baseQuery = getBasequery(queryString); 
		queryParameter.file = getFile(queryString);
		queryParameter.orderByField = getOrderByFields(queryString);
		queryParameter.groupByField = getGroupByFields(queryString);
		queryParameter.fields = getFields(queryString);
		queryParameter.restrictions = getConditions(queryString);
		queryParameter.logicalOperators = getLogicalOperators(queryString);
		queryParameter.aggregateFunctions = getAggregateFunctions(queryString);
		return queryParameter;
		
	}
		/*
		 * extract the name of the file from the query. File name can be found after the
		 * "from" clause.
		 */
	public String getBasequery(String queryString) { 
		
		if(queryString.matches(".*\\bwhere\\b.*"))
		this.field =queryString.split("where")[0].trim(); 
		else if(queryString.matches(".*\\bgroup by\\b.*"))
			this.field =queryString.split("group by")[0].trim();
		else if(queryString.matches(".*\\border by\\b.*"))
			this.field =queryString.split("order by")[0].trim();
		return this.field;
	}
		public String getFile(String queryString) { 
			return queryString.split("from ")[1].split(" ")[0]; 
		}
		
		/*
		 * extract the order by fields from the query string. Please note that we will
		 * need to extract the field(s) after "order by" clause in the query, if at all
		 * the order by clause exists. For eg: select city,winner,team1,team2 from
		 * data/ipl.csv order by city from the query mentioned above, we need to extract
		 * "city". Please note that we can have more than one order by fields.
		 */
		public List<String> getOrderByFields(String queryString) { 
			if(!queryString.contains("order by")) return null;
			String arr[] = queryString.split(" ");
			boolean bool = false;
			String ret = "";
			for(int i = 0; i < arr.length; i++) {
				if(arr[i].equals("order")) bool = true;
				else if(arr[i].equals("group")) bool = false;
				if(bool && !arr[i].equals("by") && !arr[i].equals("order")) ret += arr[i] + " ";
			}
			String[] array = ret.split(" ");
			List<String> list = Arrays.asList(array);
			return list;
		}
		
		/*
		 * extract the group by fields from the query string. Please note that we will
		 * need to extract the field(s) after "group by" clause in the query, if at all
		 * the group by clause exists. For eg: select city,max(win_by_runs) from
		 * data/ipl.csv group by city from the query mentioned above, we need to extract
		 * "city". Please note that we can have more than one group by fields.
		 */
		
		public List<String> getGroupByFields(String queryString) { 
			if(!queryString.contains("group by")) return null;
			String arr[] = queryString.split(" ");
			boolean bool = false;
			String ret = "";
			for(int i = 0; i < arr.length; i++) {
				if(arr[i].equals("group")) bool = true;
				else if(arr[i].equals("order")) bool = false;
				if(bool && !arr[i].equals("by") && !arr[i].equals("group") && !arr[i].equals("order")) {
					ret += arr[i] + " ";
					//System.out.println(ret);
				}
			}
			//System.out.println("Groupby + " + ret.length());
			if(ret.length() == 0) return null;
			String[] array = ret.split(" ");
			List<String> list = Arrays.asList(array);
			return list;
		}
		
		/*
		 * extract the selected fields from the query string. Please note that we will
		 * need to extract the field(s) after "select" clause followed by a space from
		 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
		 * query mentioned above, we need to extract "city" and "win_by_runs". Please
		 * note that we might have a field containing name "from_date" or "from_hrs".
		 * Hence, consider this while parsing.
		 */
		public List<String> getFields(String queryString) {
			queryString = queryString.split("from")[0];
			queryString = queryString.substring(queryString.indexOf(" "), queryString.length()).trim();
			String[] arr = queryString.split("\\s*,\\s*");;
			//System.out.println(queryString);
			for(int i = 0; i< arr.length; i++) {
				arr[i] = arr[i].trim();
				//System.out.println("Get Fields: " + arr[i]); 
			}
			List<String> list = Arrays.asList(arr);
			return list;
		}
		
		/*
		 * extract the conditions from the query string(if exists). for each condition,
		 * we need to capture the following: 
		 * 1. Name of field 
		 * 2. condition 
		 * 3. value
		 * 
		 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
		 * where season >= 2008 or toss_decision != bat
		 * 
		 * here, for the first condition, "season>=2008" we need to capture: 
		 * 1. Name of field: season 
		 * 2. condition: >= 
		 * 3. value: 2008
		 * 
		 * the query might contain multiple conditions separated by OR/AND operators.
		 * Please consider this while parsing the conditions.
		 * 
		 */
		public String getConditionsPartQuery(String queryString) {
			//System.out.println("conditions" + !queryString.contains("where"));
			if(!queryString.contains("where")) return null ;
			String[] arr = queryString.split(" "); 
			String ret = "";
			boolean bool = true;
			boolean groupBy = false;
			for(int i = 0; i < arr.length; i++) {
				if(arr[i].toLowerCase().equals("where")) bool = false;
				else if(arr[i].toLowerCase().equals("order") || arr[i].toLowerCase().equals("group") ) break;
				
				if(!bool) {
					ret += arr[i] + " ";
				}
			} 
			ret = ret.trim();
			
			ret = ret.substring(ret.indexOf(" "), ret.length()).replaceFirst("\\s++$", ""); 
			//System.out.println(" season > 2014 and city ='bangalore' " == ret);
			//System.out.println("Condition part:" + (" season > 2014 and city ='bangalore' " ==ret));
			if(queryString.contains("group") ||queryString.contains("order")  ) return ret + " ";
			return ret;   
		}
		public List<Restriction>  getConditions(String queryString)  {
			if(!queryString.contains("where")) return null; 
			queryString = getConditionsPartQuery(queryString);
			List<Restriction> list = new ArrayList<Restriction>();
			queryString = queryString.substring(queryString.indexOf(" "), queryString.length()).trim();
			String[] arr = queryString.split("(\\band\\b|\\bor\\b)");
			for(int i = 0; i< arr.length; i++) {
				arr[i] = arr[i].trim();
				//System.out.println("condition " + (i+1) + ": " + arr[i]);
				String propertyName = "";
				String propertyValue = "";
				if(arr[i].contains(">=")) {
					String ret[] = arr[i].split(">=");
					propertyName = ret[0].trim();
					propertyValue = ret[1].trim();   
					list.add(new Restriction(propertyName,">=",propertyValue));
				}else if(arr[i].contains("<=")) {
					String ret[] = arr[i].split("<=");
					propertyName = ret[0].trim();
					propertyValue = ret[1].trim(); 
					System.out.println(propertyName + "=" + propertyValue);
					list.add(new Restriction(propertyName,"<=",propertyValue));
				}else if(arr[i].contains("!=")) {
					String ret[] = arr[i].split("!=");
					propertyName = ret[0].trim();
					propertyValue = ret[1].replaceAll("^\"|\"$","").trim(); 
					System.out.println(propertyName + "=" + propertyValue);
					list.add(new Restriction(propertyName,"!=",propertyValue));
				}else if(arr[i].contains("=")) {
					String ret[] = arr[i].split("=");
					propertyName = ret[0].trim();
					propertyValue = ret[1].replaceAll("^\"|\"$","").trim(); 
					System.out.println(propertyName + "=" + propertyValue);
					list.add(new Restriction(propertyName,"=",propertyValue));
				}else if(arr[i].contains(">")) {
					String ret[] = arr[i].split(">");
					propertyName = ret[0].trim();
					propertyValue = ret[1].trim(); 
					System.out.println(propertyName + "=" + propertyValue);
					list.add(new Restriction(propertyName,">",propertyValue));
				}else if(arr[i].contains("<")) {
					String ret[] = arr[i].split("<");
					propertyName = ret[0].trim();
					propertyValue = ret[1].trim(); 
					System.out.println(propertyName + "=" + propertyValue);
					list.add(new Restriction(propertyName,"<",propertyValue));
				}
			}
			return list;
		}
		
		/*
		 * extract the logical operators(AND/OR) from the query, if at all it is
		 * present. For eg: select city,winner,team1,team2,player_of_match from
		 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
		 * bangalore
		 * 
		 * the query mentioned above in the example should return a List of Strings
		 * containing [or,and]
		 */
		public List<String> getLogicalOperators(String queryString) {

			if(!queryString.contains("where")) return null; 
			queryString = getConditionsPartQuery(queryString);
			queryString = queryString.substring(queryString.indexOf(" "), queryString.length()).trim();
			String arr[] = queryString.split(" ");
			List<String> ret = new ArrayList<String>();
			int operators = 1;
			for(int i = 0; i < arr.length; i++ ) {
				if(arr[i].toLowerCase().equals("and") || arr[i].toLowerCase().equals("or") || arr[i].toLowerCase().equals("not")) {
					ret.add(arr[i]);
					//System.out.println("operator " + (operators) + ": " + arr[i]);
					operators++;
				}
			}
			return ret; 
		}
		
		
		/*
		 * extract the aggregate functions from the query. The presence of the aggregate
		 * functions can determined if we have either "min" or "max" or "sum" or "count"
		 * or "avg" followed by opening braces"(" after "select" clause in the query
		 * string. in case it is present, then we will have to extract the same. For
		 * each aggregate functions, we need to know the following: 
		 * 1. type of aggregate function(min/max/count/sum/avg) 
		 * 2. field on which the aggregate function is being applied
		 * 
		 * Please note that more than one aggregate function can be present in a query
		 * 
		 * 
		 */ 
		public List<AggregateFunction> getAggregateFunctions(String queryString) { 
			//System.out.println("Regex");
			if(!(queryString.contains("(")) && !(queryString.contains(")"))) return null;
			//System.out.println("Regex2");
			queryString = queryString.split("from")[0].split("select")[1].trim();  
			String[] arr;
			if(queryString.contains(",")) arr = queryString.split(",");
			else {
				arr = new String[]{queryString};
			}
			//System.out.println("Regex3");
			List<AggregateFunction> list = new ArrayList<AggregateFunction>();
	        if(arr[0].equals("*")) return null;
	        for (int i = 0; i < arr.length; i++) {
	        	if(!arr[i].contains("(")) return null;
	        	String[] arr2 = arr[i].split("\\("); 
	        	//System.out.println( + " " +  );
	        	list.add(new AggregateFunction(arr2[1].substring(0, arr2[1].length()-1),arr2[0]));
            }
			return list;
		}
	
	
	
}
