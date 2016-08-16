package Features;
import java.util.ArrayList;

import Main.Feature;

public class IS_PW extends Feature{
	ArrayList<String> stopWords = new ArrayList<String>();

	public IS_PW(){

	}

	@Override
	public void execute(String aWord) {
		if(stopWords.contains(aWord)){
			//writes to textfile yes-stop
		} else {
			//writes to textfile no-stop
		}
		
	}
}
