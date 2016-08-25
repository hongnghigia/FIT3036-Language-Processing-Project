package Features;

import Main.Feature;

/**
 * is the word "that"?
 */
public class IS_THAT extends Feature{

	public IS_THAT(){
		return;
	}
	
	@Override
	public void execute(String[] aWord){
		if(aWord[0].toLowerCase().equals("that")){
			this.write("yes-that ");
		} else {
			this.write("no-that ");
		}
	}
}
