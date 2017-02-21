package uom.cse.itpa.nlppb.core;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.HeadFinder;
import edu.stanford.nlp.trees.LabeledScoredTreeNode;
import edu.stanford.nlp.trees.SemanticHeadFinder;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.CoreMap;
import uom.cse.itpa.nlppb.entities.HeadWord;

public class StandfordCoreNLPService {
	 private StanfordCoreNLP pipeline=null;
	 String [] parents={"SBARQ","SQ","VP","VB","VBD","VBG","VBN","VBZ","VBP","SNIV"};
	 String [] verbpostags={"VP","VB","VBD","VBG","VBN","VBZ","VBP"};
	 String [] nounpostags={"NN","NNS","NNP","NNPS","NP"};
	 String [] wrongheadwords={"name","recommendation","recommendations","type","kind","anyone","someone","suggestions","please","suggestion","I","you","it","we","there"};
	 public void LoadService(Properties props){
    pipeline = new StanfordCoreNLP(props);
}

public HeadWord getHeadWord(String sentence){
	Annotation document = new Annotation(sentence);
    pipeline.annotate(document);
    // these are all the sentences in this document
    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
   CoreMap question=sentences.get(0);
      // traversing the words in the current sentence
      // a CoreLabel is a CoreMap with additional token-specific methods
      SemanticHeadFinder headFinder=new SemanticHeadFinder();
      // this is the parse tree of the current sentence
     Tree tree = question.get(TreeAnnotation.class);
     String headword=findHeadWord(tree, headFinder);
     getCollinsHeadWord(tree, headFinder);
     

     pipeline.annotate(document);
    HeadWord headWord2=new HeadWord();
    for (CoreLabel token: question.get(TokensAnnotation.class)) {
 	    String hw = token.originalText();
 	    if(hw.equals(headword)){
 	    	String hw2=token.get(LemmaAnnotation.class);
 	    	headWord2.setWord(hw2);
 	    	headWord2.setPos(token.get(PartOfSpeechAnnotation.class));
 	    }
    }
    return headWord2;
    }
public List<String> getNER(String sentence){
	List<String> ners=new ArrayList<String>();
	Annotation document = new Annotation(sentence);
    pipeline.annotate(document);
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
   CoreMap question=sentences.get(0);
   for (CoreLabel token: question.get(TokensAnnotation.class)) {
	    String ne = token.get(NamedEntityTagAnnotation.class);
	    ners.add(ne);
	    System.out.println(ne);
	  }
   return ners;
}

public LinkedHashMap<String,String> getNERWords(String sentence){
	LinkedHashMap<String,String> ners=new LinkedHashMap<String,String>();
	Annotation document = new Annotation(sentence);
    pipeline.annotate(document);
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
   CoreMap question=sentences.get(0);
   for (CoreLabel token: question.get(TokensAnnotation.class)) {
	    String ne = token.get(NamedEntityTagAnnotation.class);
	    System.out.println("+++"+ne+"+++");
	    	ners.put(token.word(),ne);
	    
//	    try{
//	    int i=Integer.parseInt(ne);
//	    }
//	    catch(Exception e){
//	    	System.out.println("+++"+token.lemma()); 
//	    	ners.add(token.lemma());
//	    	 
//	    }
	   }
   ners.put("final","O");
   return ners;
}

public List<String> getConnectedNer(LinkedHashMap<String,String> ners){
	int i=-1;
	int j=-1;
	boolean chaining=false;
	String lastner="o";
	String last="o";
	List<String> words=new ArrayList<String>();
	String word="";
	for (Map.Entry<String, String> entry : ners.entrySet()) {
		
		i++;
		
	 
	  if(!last.equalsIgnoreCase("o") && i==j+1){
		  if(last.equalsIgnoreCase(entry.getValue()) && i==j+1){
			  word=word+"_"+entry.getKey();
			  chaining=true;
		  }
		  else{
		  words.add(word);
		  chaining=false;
		  }
	  }
	  
	  if(!entry.getValue().equalsIgnoreCase("o")){
		j=i;	
		lastner=entry.getKey();
		last=entry.getValue();
		if(!chaining){
		word=lastner;
		}
		}
	}
	return words;
}
public String findHeadWord(Tree tree,SemanticHeadFinder headFinder){
	Tree treeTemp=tree;//tree=FindSubtreewithSpecial(tree);
	System.out.println(tree);
    Tree returnedTree=iterateHeadWord(tree, headFinder);
    if(returnedTree.nodeString().equals("VB")){
    	returnedTree=iterateHeadWord(FindSubtreewithSpecial(treeTemp),headFinder);
    }
    String hw=returnedTree.toString();
	 for(String whw:wrongheadwords){
		 if(hw.equals(whw)){
			if(FindSubtreewithSpecial(treeTemp)!=null){
			hw= iterateHeadWord(FindSubtreewithSpecial(treeTemp),headFinder).toString();
			}
			break;
		 }
	 }
	 
	 System.out.println(hw);
    return hw;
	
}
public Tree FindSubtreewithSpecial(Tree tree){
	System.out.println("special"+tree);
	for(Tree t:tree.subTreeList()){
	if(t.nodeString().equals("PP") && t.numChildren()>=2){
		Tree child1=t.getChild(0);
		Tree child2=t.getChild(1);
		if(child1.nodeString().equals("IN") && child2.nodeString().equals("NP")){
			System.out.println(t);
			return child2;
		}
	}
	}
	
	
	
	
	return null;
}
public Tree FindSubtreewithHeadWord(Tree tree){
	
	for(Tree t:tree.getChildrenAsList()){
		System.out.println("fs"+t);
		if(t.nodeString().equals("S")){
			
			for(Tree tt:tree.subTreeList()){	
			for(String nouns:nounpostags){
				if(tt.nodeString().equals(nouns)){
					System.out.println(tt);
					return tt;
				}
			}
			}
		}
		for(String nouns:nounpostags){
			if(t.nodeString().equals(nouns)){
				System.out.println("uu"+t);
				return t;
			}
		}
		
	}
	
	return null;
}
public Tree iterateHeadWord(Tree tree,HeadFinder headFinder){
	
	
	while(!tree.isLeaf()){
	    boolean parentInarray=false;
	    Tree tempTree=tree;
	    tree=headFinder.determineHead(tree); 
	    for(String parent:parents){
	    	if(tempTree.nodeString().equals(parent)){
	    	for(String verb:verbpostags){
	    		if(tree.nodeString().equals(verb)){
	    			System.out.println("verb found with parent");
	    			parentInarray=true;
	    			Tree tree2=FindSubtreewithHeadWord(tempTree);
	    			if(tree2!=null){
	    				tree=tree2;
	    				
	    			}
	    			else{
	    				System.out.println("null");
	    			}
	    			break;
	    			
	    		}
	    	}
	    	
	    	}
	    	if(parentInarray){
	    		System.out.println("verb found with parent");
	    		break;
	    	}
	    
	    }
	    System.out.println(tree);
	    }
	return tree;
}
public void getCollinsHeadWord(Tree tree,HeadFinder headFinder){
	while(!tree.isLeaf()){
	  System.out.println(tree);
	   tree=headFinder.determineHead(tree);
	   
	}
	System.out.println(tree);

}
}
