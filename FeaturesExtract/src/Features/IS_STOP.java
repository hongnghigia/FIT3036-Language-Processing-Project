package Features;
import java.util.ArrayList;

import Main.Feature;

/*
 * IS_STOP feature
 */
public class IS_STOP extends Feature{
	ArrayList<String> stopWords = new ArrayList<String>();
	
	public IS_STOP(){
		
	}
	
	@Override
	public void execute(String aWord){
		if(stopWords.contains(aWord)){
			//writes to textfile "yes-stop"
			this.write("yes-stop ");
		} else {
			//writes to textfile "no-stop"
			this.write("no-stop ");
		}
	}
}
