package uom.cse.itpa.wn.connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;


public class WordNetConnectionService {
	String wordNetDirectory = "/home/dilshan/Extra/WordNet-2.0";
    String path = wordNetDirectory + File.separator + "dict";
   IDictionary dict=null ;
   
public static void main(String[] args) throws IOException
{

	WordNetConnectionService wn=new WordNetConnectionService();
	wn.LoadWoraNet();
	wn.getSynset("cost", POS.VERB);
    }
    public void LoadWoraNet() throws IOException{
    	 URL url = new URL("file", null, path);
    	 dict= new Dictionary(url);
    	 dict.open();
    }
    
    public String[] getSynset(String arg0,POS arg1){
    	String hypernyms[]={"none","none","none"};
    	
    	 IIndexWord idxWord = dict.getIndexWord(arg0, arg1);
    	 if(idxWord!=null){
    	 IWordID wordID = idxWord.getWordIDs().get(0) ;
    	 
    	 IWord word = dict.getWord (wordID);  
    	 ISynset iSynset=word.getSynset();
    	 
    	 int i=0;
      for (IWord w : iSynset.getWords()) {
    	  if(i<3){
    	  hypernyms[i]=w.getLemma();
    	  System.out.println(hypernyms[i]);
    	  }
    	  i++;
    	  
      }
    	 }
    	 else{
    		 int i=0;
    	      for (i=0;i<3;i++) {
    	    	  hypernyms[i]="none";
    	    	  
    	      }
    	    	
    	 }
    	 return  hypernyms;
    }
}

