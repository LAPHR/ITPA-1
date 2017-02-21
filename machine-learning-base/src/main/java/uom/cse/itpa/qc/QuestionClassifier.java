package uom.cse.itpa.qc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import edu.mit.jwi.item.POS;
import nu.xom.jaxen.util.PrecedingAxisIterator;
import uom.cse.itpa.dbp.conncection.DbpediaConectionService;
import uom.cse.itpa.nlppb.core.StandfordCoreNLPService;
import uom.cse.itpa.nlppb.core.StringMatcher;
import uom.cse.itpa.nlppb.entities.HeadWord;
import uom.cse.itpa.wn.connection.WordNetConnectionService;

public class QuestionClassifier {
	String FILENAME="/home/dilshan/Downloads/FINAL.xlsx";
	ArrayList<QuestionHolder> questionHolders=new ArrayList<QuestionHolder>();
	ArrayList<FeatureHolder>  featureHolders=new ArrayList<FeatureHolder>();
	StringMatcher stringMatcher=new StringMatcher();
	//String [] Food={Restaurants,Street Food,Food Festivals,Local Cuisine,Cafes/Deliverance Shops,Breweries and Wineries,Authenic Food,English Food,Steak Food,BBQ,English Food,bakery}
	public void createClassifierdataset() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
	    StandfordCoreNLPService standfordCoreNLPService=new StandfordCoreNLPService();
	    WordNetConnectionService wordNetConnectionService =new WordNetConnectionService();
        try {
			wordNetConnectionService.LoadWoraNet();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    DbpediaConectionService dbpediaConectionService=new DbpediaConectionService();
	    
	    standfordCoreNLPService.LoadService(props);
		for(QuestionHolder questionHolder:questionHolders){
			
			String question=questionHolder.getGetquestion();
			FeatureHolder featureHolder=new FeatureHolder();
			HeadWord headword=standfordCoreNLPService.getHeadWord(question);
			featureHolder.setHeadword(headword.getWord());

			System.out.println(headword.getWord());
     		featureHolder.setQuestion(questionHolder);
			if(headword.getPos().contains("N")){
				featureHolder.setSynomums(wordNetConnectionService.getSynset(headword.getWord(), POS.NOUN));	
			}
			else if(headword.getPos().contains("V")){
				featureHolder.setSynomums(wordNetConnectionService.getSynset(headword.getWord(), POS.VERB));	
			}
			else if(headword.getPos().contains("J")){
				featureHolder.setSynomums(wordNetConnectionService.getSynset(headword.getWord(), POS.ADJECTIVE));	
			}
			else if(headword.getPos().contains("R")){
				featureHolder.setSynomums(wordNetConnectionService.getSynset(headword.getWord(), POS.ADVERB));	
			}
			featureHolder.setAllcaps(stringMatcher.AllUpperCase(question));
			featureHolder.setAlllowercase(stringMatcher.AllLoweCase(question));
			featureHolder.setAllnumbers(stringMatcher.AllDigits(question));
			featureHolder.setAllletters(stringMatcher.AllDigits(question));
			List<String> ners=standfordCoreNLPService.getNER(question);
			HashMap<String,Integer> map=featureHolder.getNER();
			for(String ner : ners){
				
				if(!ner.equals("O")){
				
				map.put(ner,map.get(ner)+1);
				
				}
			}
			featureHolder.setNER(map);
			featureHolder.setWhword(stringMatcher.Whwords(question));
			List<String> dBpediacatgories=new ArrayList<String>();
			
			LinkedHashMap<String, String> nerWors=standfordCoreNLPService.getNERWords(question);
			List<String> list =standfordCoreNLPService.getConnectedNer(nerWors);
			for(String ner:list){
				ner=ner.replaceAll("[^A-Za-z0-9]", "");
				if(ner.contains(" ")){
					ner=ner.replace(" ", "_");
					
				}
				if(ner.contains(".")){
					ner=ner.replace(".", "");
					
				}
				if(ner.contains("/")){
					
					ner=ner.replaceAll("/", "");
				}
				if(ner.contains("?")){
					continue;
				}
				dBpediacatgories.addAll(dbpediaConectionService.getDbpediaCatgerois(ner));
			}
			
			
			featureHolder.setDbCatgoeries(dBpediacatgories);
			featureHolders.add(featureHolder);
			
		}
		System.out.println(featureHolders.size());
		
	     }
	public void createQuestionholders() throws IOException, InvalidFormatException{
		 
		File file=new File(FILENAME);
		// POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
		  XSSFWorkbook wb = new XSSFWorkbook(file);
		    XSSFSheet sheet = wb.getSheetAt(0);
		    XSSFRow row;
		    XSSFCell cell;

		    int rows=10; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();

		    int cols = 2; // No of columns
		    int tmp = 0;

		    // This trick ensures that we get the data properly even if it doesn't start from first few rows
		    for(int i = 0; i < 10 || i < rows; i++) {
		        row = sheet.getRow(i);
		        if(row != null) {
		            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
		            if(tmp > cols) cols = tmp;
		        }
		    }

		    for(int r = 0; r < 5000; r++) {
		        row = sheet.getRow(r);
		        System.out.println(r);
		        if(row != null) {
		        	QuestionHolder questionHolder=new QuestionHolder();
		            for(int c = 0; c < cols; c++) {
		                cell = row.getCell((short)c);
		                if(cell != null) {
		                    if(c==0){
		                    	String s=row.getCell(c).toString();
		                    	questionHolder.setGetquestion(s);
		                    	System.out.println(s);
		                    }
		                    else if(c==1){
		                    	String s=row.getCell(c).toString();
		                    	questionHolder.setQcalss1(s);
		                    	System.out.println(s);
		                    }
		                    else if(c==2){
		                    	String s=row.getCell(c).toString();
		                    	questionHolder.setQcalss2(s);
		                    	System.out.println(s);
		                    }
		                    
		                }
		               
		            }
		            questionHolders.add(questionHolder);
		        }
		    }
		System.out.println(questionHolders.size());
	}
	
	public void writetoCsv() throws IOException{
	     CSVWriter writer = new CSVWriter(new FileWriter("finalboth4.csv"), ',');
	     System.out.println(featureHolders.size());
     for(FeatureHolder featureHolder:featureHolders){
	    	 
	    	 String question=featureHolder.getQuestion().getGetquestion().trim();
	    	 String class1=featureHolder.getQuestion().getQcalss1().trim();
	    	 String class2=featureHolder.getQuestion().getQcalss2().trim();
	    	 if(class1.equals("")){
	    		 continue;
	    	 }
	    	 if(class1.equals("\n")){
	    		 continue;
	    	 }
	    	 else{
	    	 String headWord=featureHolder.getHeadword();
	    	 String synomums1=null;
	    	 String synomums2=null;
	    	 String synomums3=null;
			if(featureHolder.getSynomums().length!=0){
				synomums1=	featureHolder.getSynomums()[0];
				synomums2=	featureHolder.getSynomums()[1];
				synomums3=	featureHolder.getSynomums()[2];
			}
			
				String others="flase";
				String AllCaps=Boolean.toString(featureHolder.isAllcaps());
				String AllLowerCase=Boolean.toString(featureHolder.isAlllowercase());
				String AllDigits=Boolean.toString(featureHolder.isAllnumbers());
				String AllLetters=Boolean.toString(featureHolder.isAllletters());
				if(!featureHolder.isAllcaps() && !featureHolder.isAlllowercase() && !featureHolder.isAllnumbers() && !featureHolder.isAllletters()){
					others="true";
				}
				String Location=Integer.toString(featureHolder.getNER().get("LOCATION"));
				String Person=Integer.toString(featureHolder.getNER().get("PERSON"));
				String Organization=Integer.toString(featureHolder.getNER().get("ORGANIZATION"));
				String Money=Integer.toString(featureHolder.getNER().get("MONEY"));
				String Percent=Integer.toString(featureHolder.getNER().get("PERCENT"));
				String Date=Integer.toString(featureHolder.getNER().get("DATE"));
				String Time=Integer.toString(featureHolder.getNER().get("TIME"));
				String DURATION=Integer.toString(featureHolder.getNER().get("DURATION"));
				String MISC=Integer.toString(featureHolder.getNER().get("MISC"));
				String NUMBER=Integer.toString(featureHolder.getNER().get("NUMBER"));
				String SET=Integer.toString(featureHolder.getNER().get("SET"));
				String ORDINAL=Integer.toString(featureHolder.getNER().get("ORDINAL"));
				String wh=featureHolder.getWhword();
				List<String> dbpedias=featureHolder.getDbCatgoeries();
				String db="";
				for(String s: dbpedias){
					db=db+" "+s;
				}
				String[] entries = {question,class1,class2,wh,headWord,synomums1,synomums2,synomums3,AllCaps,AllLowerCase,AllDigits,AllLetters,others,Location,Person,Organization,Money,Percent,Date,Time,DURATION,MISC,NUMBER,SET,ORDINAL,db};
			    writer.writeNext(entries);
	    	 }
	     }
	     
		 writer.close();
	}
	}

