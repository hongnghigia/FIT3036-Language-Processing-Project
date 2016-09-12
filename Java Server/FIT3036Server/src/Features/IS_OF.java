package Features;

import Main.Feature;

/**
 * is the word "of"?
 */
public class IS_OF extends Feature{
	
	public IS_OF(){
		return;
	}
	
	@Override
	public void execute(String[] aWord){
		if(aWord[0].toLowerCase().equals("of")){
			this.write("yes-of, ");
		} else {
			this.write("no-of, ");
		}
	}
}
