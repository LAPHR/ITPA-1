package uom.cse.itpa.dbp.conncection;



import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;




public class DbpediaConectionService {
	Pattern allLettersPattern=Pattern.compile("[A-Za-z ]+");
	public DbpediaConectionService(){
//		System.getProperties().put("proxySet","true");
//		System.getProperties().put("http.proxyHost", "cache.mrt.ac.lk");
//		System.getProperties().put("http.proxyPort","3128");
	}
public List<String> getDbpediaCatgerois(String country) {
	
	String query2="PREFIX dbres: <http://dbpedia.org/resource/>"
+"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
+"select ?o where {dbres:"+country+" rdf:type ?o} LIMIT 10";
	String query1="PREFIX dbres: <http://dbpedia.org/resource/>"
		+"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
		+"select ?o where {dbres:"+country+" rdf:type ?o} LIMIT 100";
	
	ResultSet results = dbpediaQuery(query1);
	//ResultSetFormatter.out(System.out, results);

	
	List<String> dbpediaCatergories=new ArrayList<String>();
		while ( results.hasNext() ) {
            QuerySolution qs = results.next();
            String link=qs.toString();
            String [] s=link.split("/");
            String catergeory=AllLettersReturn(s[s.length-1]);
           dbpediaCatergories.add(catergeory);
          }
		return dbpediaCatergories;

}

public String AllLettersReturn(String sentence){
	
	String question=sentence.substring(0, 1).toLowerCase() + sentence.substring(1);
    Matcher allLettersMatcher = allLettersPattern.matcher(question);
    
    if(allLettersMatcher.find()){
    return allLettersMatcher.group(0);
    }
    return null;

}

public static  ResultSet dbpediaQuery (String query)
{
	QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
	
	ResultSet results = queryExecution.execSelect();
	
	return results;
}
}
