package Features;

import Main.Feature;

public class IS_FIRST extends Feature{

	@Override
	public void execute(String[] aWord) {
		if (aWord[4].equals("B-O") || aWord[4].equals("I-O")){
			this.write("yes-first,");
		} else {
			this.write("no-first,");
		}
		
	}

}
