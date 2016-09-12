package Features;

import Main.Feature;

/**
 * the class label of the word
 */
public class CLASS extends Feature{
	
	public CLASS(){
		return;
	}
	
	@Override
	public void execute(String[] aWord){
		this.write("?");
	}
}
