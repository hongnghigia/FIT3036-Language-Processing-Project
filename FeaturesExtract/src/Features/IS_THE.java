package Features;

import Main.Feature;

/**
 * is the word "the"?
 */
public class IS_THE extends Feature{

	public 	IS_THE(){
		return;
	}
	
	@Override
	public void execute(String[] aWord){
		if(aWord[0].toLowerCase().equals("the")){
			this.write("yes-the,");
		} else {
			this.write("no-the,");
		}
	}
}
