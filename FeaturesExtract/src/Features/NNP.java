package Features;

import java.util.ArrayList;
import Main.Feature;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * if the current PoS is NN, while the next word (if
 * exists) is in the list of prepositions? 
 */
public class NNP extends Feature{
	private MaxentTagger tagger;
	ArrayList<String> ppWords = new ArrayList<String>();

	public NNP() {
		tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
		for(String word: "on, above, next, to, further, away, from, in, close, closer, closest, at, inside, behind, under, near, against, underneath, over, nearest, nearer, toward, towards, below, about, between".split("\\P{Alpha}+")){
			ppWords.add(word);
		}
	}
	
	@Override
	public void execute(String[] aWord) {
		// isolate the PoS tag
		String outputString = tagger.tagString(aWord[0]);
		String toRemove = aWord[0] + "_";
		String tag = outputString.replace(toRemove, "");
		tag = tag.replace(" ", "");
		
		if (tag.equals("NN")){
			if (aWord[1] == null) {
				this.write("no-NNP ");
			}
			else if (ppWords.contains(aWord[1].toLowerCase())) {
				this.write("yes-NNP ");
			}
			else {
				this.write("no-NNP ");
			}
		} 
		else {
			this.write("no-NNP ");
		}
	}
}
