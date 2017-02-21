import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import uom.cse.itpa.qc.FeatureHolder;
import uom.cse.itpa.qc.QuestionClassifier;

public class test {

	public static void main(String[] args) {
		QuestionClassifier questionClassifier=new QuestionClassifier();
		try {
			questionClassifier.createQuestionholders();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		questionClassifier.createClassifierdataset();
		try {
			questionClassifier.writetoCsv();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
