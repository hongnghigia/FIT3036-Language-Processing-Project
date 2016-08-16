package Features;
import Main.Feature;

public class IS_THAT extends Feature{

	public IS_THAT(){
		return;
	}
	
	@Override
	public void execute(String aWord){
		if(aWord.equals("that")){
			// writes to textfile "yes-that"
			this.write("yes-that ");
		} else {
			// writes to textfile "no-that"
			this.write("no-that ");
		}
	}
}
