package Main;



/**
 * abstract class for all features
 */
public abstract class Feature {
	Writer w = new Writer();
	
	public abstract void execute(String[] aWord);
	
	// write to file the result of the check
	public void write(String answer){
		w.write(answer);
	}
}