package Features;
import java.util.ArrayList;

import Main.Feature;

public class IS_PW extends Feature{
	ArrayList<String> pwWords = new ArrayList<String>();

	public IS_PW(){

	}

	@Override
	public void execute(String aWord) {
		if(pwWords.contains(aWord)){
			//writes to textfile yes-pw
			this.write("yes-pw ");
		} else {
			//writes to textfile no-pw
			this.write("no-pw ");
		}
		
	}
}
