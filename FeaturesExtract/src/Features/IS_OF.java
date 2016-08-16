package Features;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import Main.Feature;

/*
 * IS_OF feature
 */
public class IS_OF extends Feature{
	
	public IS_OF(){
		return;
	}
	
	@Override
	public void execute(String aWord){
		if(aWord.equals("of")){
			// writes to textfile "yes-of"
			this.write("yes-of ");
		} else {
			// writes to textfile "no-of"
			this.write("no-of ");
		}
	}
}
