package Main;

import java.util.ArrayList;
import Features.*;

public class Checker {
	ArrayList<Feature> features = new ArrayList<Feature>();
	IS_OF isOf = new IS_OF();
	IS_PP isPP = new IS_PP();
	IS_PW isPW = new IS_PW();
	IS_STOP isStop = new IS_STOP();
	IS_THAT isThat = new IS_THAT();
	IS_THE isThe = new IS_THE();
	
	private String target;
	public Checker(){
		features.add(isOf);
		features.add(isThe);
		features.add(isPP);
		features.add(isPW);
		features.add(isStop);
		features.add(isThat);
	}
	
	public void check(String target){
		for(int i = 0; i < this.features.size(); i++){
			features.get(i).execute(target);
		}
	}
	
}
