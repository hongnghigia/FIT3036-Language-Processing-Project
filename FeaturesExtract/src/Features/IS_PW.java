package Features;
import java.util.ArrayList;

import Main.Feature;

/*
 * IS_PW feature
 */
public class IS_PW extends Feature{
	ArrayList<String> pwWords = new ArrayList<String>();

	public IS_PW(){
		for(String word: "back, center, corner, edge, end, front, right, left, middle, side, top ".split("\\P{Alpha}+")){
			pwWords.add(word);
		}
	}

	@Override
	public void execute(String aWord) {
		if(pwWords.contains(aWord)){
			//writes to textfile yes-pw
			this.write("yes-pw ");
		} else {
			//writes to textfile no-pw
			this.write("no-pw ");
		}
		
	}
}
