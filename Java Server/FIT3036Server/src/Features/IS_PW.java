package Features;

import java.util.ArrayList;
import Main.Feature;

/**
 * is the word in the list of position words?
 */
public class IS_PW extends Feature{
	ArrayList<String> pwWords = new ArrayList<String>();

	public IS_PW(){
		for(String word: "back, center, corner, edge, end, front, right, left, middle, side, top ".split("\\P{Alpha}+")){
			pwWords.add(word);
		}
	}

	@Override
	public void execute(String[] aWord) {
		if(pwWords.contains(aWord[0].toLowerCase())){
			this.write("yes-pw, ");
		} else {
			this.write("no-pw, ");
		}
	}
}
