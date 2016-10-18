package Features;

import java.util.ArrayList;

import Main.Feature;

public class THE_PW extends Feature{

	ArrayList<String> pwWords = new ArrayList<String>();
	
	public THE_PW(){
		for(String word: "back, center, corner, edge, end, front, right, left, middle, side, top ".split("\\P{Alpha}+")){
			pwWords.add(word);
		}
	}
	@Override
	public void execute(String[] aWord) {
		if (aWord[0].toLowerCase().equals("the") && pwWords.contains(aWord[1])){
			this.write("1,");
		} else {
			this.write("0,");
		}
	}

}
