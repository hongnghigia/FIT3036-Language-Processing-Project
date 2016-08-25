package Features;

import Main.Feature;

/**
 * is the word the final word in the sentence or not? 
 */
public class IS_FINAL extends Feature{

	public IS_FINAL() {
		return;
	}
	
	@Override
	public void execute(String[] aWord){
		// if the following word doesn't exist
		if(aWord[1] == null){
			this.write("yes-final ");
		} else {
			this.write("no-final ");
		}
	}
}
