package uom.cse.itpa.qc;

public class QuestionHolder {
private String qcalss1;
private String qcalss2;
private String getquestion;
public String getQcalss1() {
	return qcalss1;
}
public void setQcalss1(String qcalss1) {
	this.qcalss1 = qcalss1;
}
public String getQcalss2() {
	return qcalss2;
}
public void setQcalss2(String qcalss2) {
	this.qcalss2 = qcalss2;
}
public String getGetquestion() {
	return getquestion;
}
public void setGetquestion(String getquestion) {
	this.getquestion = getquestion;
}
public QuestionHolder(){
	
}
public QuestionHolder(String qcalss1, String qcalss2, String getquestion) {
	this.qcalss1 = qcalss1;
	this.qcalss2 = qcalss2;
	this.getquestion = getquestion;
}


}
