import java.util.List;

import org.apache.jena.iri.impl.Main;

import uom.cse.itpa.dbp.conncection.DbpediaConectionService;

public class test {
public static void main(String[] args) {
	DbpediaConectionService db=new DbpediaConectionService();
	List<String> dbss=db.getDbpediaCatgerois("Sri_Lanka");
	for(String dbs:dbss){
		System.out.println(dbs);
	}
}
}
