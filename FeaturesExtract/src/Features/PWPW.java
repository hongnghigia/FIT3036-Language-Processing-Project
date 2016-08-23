package Features;

import java.util.ArrayList;
import Main.Feature;

public class PWPW extends Feature{
	ArrayList<String> pwWords = new ArrayList<String>();
	
	public PWPW() {
		for(String word: "back, center, corner, edge, end, front, right, left, middle, side, top ".split("\\P{Alpha}+")){
			pwWords.add(word);
		}
	}
	
	@Override
	public void execute(String aWord) {
		if (pwWords.contains(aWord)){
			//writes to textfile yes-pwpw
			this.write("yes-pwpw ");
		} else {
			//writes to textfile no-pwpw
			this.write("no-pwpw ");
		}
		
	}
}
