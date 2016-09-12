package Features;

import java.text.DecimalFormat;
import Main.Feature;

/**
 * what is the relative position of the word in the
 * sentence, i.e. current position over the sentence
 * length?
 */
public class RELATIVE_POS extends Feature{
	
	public RELATIVE_POS(){
		return;
	}
	
	@Override
	public void execute(String[] aWord){
		DecimalFormat df = new DecimalFormat("#.###");
		double a = Double.parseDouble(aWord[2]);
		double b = Double.parseDouble(aWord[3]);
		double result = a/b;
		this.write(df.format(result) + ",");
	}
}
