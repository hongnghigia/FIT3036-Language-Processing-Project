package Features;
import Main.Feature;

public class IS_OF extends Feature{
	
	public IS_OF(){
		return;
	}
	
	@Override
	public void execute(String aWord){
		if(aWord.equals("of")){
			// writes to textfile "yes-of"
			System.out.println("HUE");
		} else {
			// writes to textfile "no-of"
			System.out.println("NO");
		}
	}
}
