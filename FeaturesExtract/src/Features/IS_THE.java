package Features;
import java.util.ArrayList;

import Main.Feature;

/*
 * IS_THE feature
 */
public class IS_THE extends Feature{

	public 	IS_THE(){
		return;
	}
	
	@Override
	public void execute(String aWord){
		if(aWord.equals("the")){
			// write to textfile "yes-the"
			this.write("yes-the ");
		} else {
			//write to textfile "no-the"
			this.write("no-the ");
		}
	}
	
}
