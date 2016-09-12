package Features;

import java.util.ArrayList;
import Main.Feature;

/**
 * is this word in the list of position words, while the
 * next word (if exists) is also in the list?
 */
public class PWPW extends Feature{
	ArrayList<String> pwWords = new ArrayList<String>();
	
	public PWPW() {
		for(String word: "back, center, corner, edge, end, front, right, left, middle, side, top ".split("\\P{Alpha}+")){
			pwWords.add(word);
		}
	}
	
	@Override
	public void execute(String[] aWord) {
		if (pwWords.contains(aWord[0].toLowerCase())){
			if (aWord[1] == null) {
				this.write("no-pwpw, ");
			}
			else if (pwWords.contains(aWord[1].toLowerCase())) {
				this.write("yes-pwpw, ");
			}
			else {
				this.write("no-pwpw, ");
			}
		} 
		else {
			this.write("no-pwpw, ");
		}
	}
}
