package uom.cse.itpa.qc;

import java.util.HashMap;
import java.util.List;

public class FeatureHolder {
	private QuestionHolder question;
	private String headword;
	private String[] synomums=new String[3];
	private HashMap<String,Integer> NER=new HashMap<String, Integer>();
	private boolean allcaps;
	private boolean alllowercase;
	private boolean allnumbers;
	private boolean allletters;
	private String whword;
	private List<String> dbCatgoeries;
	
	public FeatureHolder(){
		//Location, Person, Organization, Money, Percent, Date, Time

		NER.put("LOCATION", 0);
		NER.put("PERSON", 0);
		NER.put("ORGANIZATION",0);
		NER.put("MONEY", 0);
		NER.put("PERCENT", 0);
		NER.put("DATE", 0);
		NER.put("TIME", 0);
		NER.put("DURATION", 0);
		NER.put("NUMBER", 0);
		NER.put("MISC", 0);
		NER.put("SET", 0);
		NER.put("ORDINAL", 0);
	}
	
	
	
	public List<String> getDbCatgoeries() {
		return dbCatgoeries;
	}



	public void setDbCatgoeries(List<String> dbCatgoeries) {
		this.dbCatgoeries = dbCatgoeries;
	}



	public QuestionHolder getQuestion() {
		return question;
	}
	public void setQuestion(QuestionHolder question) {
		this.question = question;
	}
	public String getHeadword() {
		return headword;
	}
	public void setHeadword(String headword) {
		this.headword = headword;
	}
	public String[] getSynomums() {
		return synomums;
	}
	public void setSynomums(String[] synomums) {
		this.synomums = synomums;
	}
	public HashMap<String, Integer> getNER() {
		return NER;
	}
	public void setNER(HashMap<String, Integer> nER) {
		NER = nER;
	}
	public boolean isAllcaps() {
		return allcaps;
	}
	public void setAllcaps(boolean allcaps) {
		this.allcaps = allcaps;
	}
	public boolean isAlllowercase() {
		return alllowercase;
	}
	public void setAlllowercase(boolean alllowercase) {
		this.alllowercase = alllowercase;
	}
	public boolean isAllnumbers() {
		return allnumbers;
	}
	public void setAllnumbers(boolean allnumbers) {
		this.allnumbers = allnumbers;
	}
	public boolean isAllletters() {
		return allletters;
	}
	public void setAllletters(boolean allletters) {
		this.allletters = allletters;
	}
	public String getWhword() {
		return whword;
	}
	public void setWhword(String whword) {
		this.whword = whword;
	}
	

}
