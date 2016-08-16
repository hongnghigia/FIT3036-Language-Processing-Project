package Main;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Feature {
	Writer w = new Writer();
	
	public abstract void execute(String aWord);
	
	public void write(String answer){
		w.write(answer);
	}
}