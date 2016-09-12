package Features;

import Main.Feature;
import java.util.ArrayList;

/**
 * is the word in the list of prepositions?
 */
public class IS_PP extends Feature{
	ArrayList<String> ppWords = new ArrayList<String>();
	
	public IS_PP(){
		for(String word: "on, above, next, to, further, away, from, in, close, closer, closest, at, inside, behind, under, near, against, underneath, over, nearest, nearer, toward, towards, below, about, between".split("\\P{Alpha}+")){
			ppWords.add(word);
		}
	}
	
	@Override
	public void execute(String[] aWord){
		if(ppWords.contains(aWord[0].toLowerCase())){
			this.write("yes-pp, ");
		} else {
			this.write("no-pp, ");
		}
	}
}
