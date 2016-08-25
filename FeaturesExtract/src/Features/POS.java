package Features;

import Main.Feature;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * what is the part-of-speech of the word?
 */
public class POS extends Feature{
	private MaxentTagger tagger;
	
	public POS() {
		tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
	}
	
	@Override
	public void execute(String[] aWord) {
		// isolate the PoS tag
		String outputString = tagger.tagString(aWord[0]);
		String toRemove = aWord[0] + "_";
		String tag = outputString.replace(toRemove, "");
		this.write(tag);
	}
}
