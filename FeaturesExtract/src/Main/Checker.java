package Main;

import java.util.ArrayList;
import Features.*;

/**
 * stores the features that are to be checked for each word of the input
 */
public class Checker {
	// array list of features to check for each word
	ArrayList<Feature> features = new ArrayList<Feature>();	
	IS_FINAL isFinal = new IS_FINAL();
	IS_THAT isThat = new IS_THAT();
	IS_THE isThe = new IS_THE();
	POS pos = new POS();
	IS_OF isOf = new IS_OF();
	IS_PW isPW = new IS_PW();
	IS_PP isPP = new IS_PP();
	IS_STOP isStop = new IS_STOP();
	FIRST_THE firstThe = new FIRST_THE();
	THE_PW thePw = new THE_PW();
	FIRST_PP firstpp = new FIRST_PP();
	RELATIVE_POS relativePos = new RELATIVE_POS();
	PWOF pwOf = new PWOF();
	NNP nnp = new NNP();
	PWPW pwPw = new PWPW();
	CLASS isClass = new CLASS();

	public Checker(){
		features.add(isFinal);
		features.add(isThat);
		features.add(isThe);
		features.add(pos);
		features.add(isOf);
		features.add(isPW);
		features.add(isPP);
		features.add(isStop);
		features.add(firstThe);
		features.add(firstpp);
		features.add(relativePos);
		features.add(pwOf);
		features.add(nnp);
		features.add(pwPw);
		features.add(isClass);
	}
	
	/**
	 * check the target against each feature in the list
	 * @param target
	 */
	public void check(String[] target){
		// runs the execute method for each feature
		for(int i = 0; i < this.features.size(); i++){
			features.get(i).execute(target);
	
		}
		// add new line after every word
		Writer w = new Writer();
		w.newLine();
	}
	
}
