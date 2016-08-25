package Features;

import java.util.ArrayList;
import Main.Feature;

/**
 * Is this word in the list of position words, while the
 * next word (if exists) is "of"?
 */
public class PWOF extends Feature{
	ArrayList<String> pwWords = new ArrayList<String>();

	public PWOF() {
		for(String word: "back, center, corner, edge, end, front, right, left, middle, side, top ".split("\\P{Alpha}+")){
			pwWords.add(word);
		}
	}

	@Override
	public void execute(String[] aWord) {
		if (pwWords.contains(aWord[0].toLowerCase())){
			if (aWord[1] == null) {
				this.write("no-pwof");
			}
			else if (aWord[1].toLowerCase().equals("of")) {
				this.write("yes-pwof ");
			}
			else {
				this.write("no-pwof ");
			}
		} 
		else {
			this.write("no-pwof ");
		}
	}
}
