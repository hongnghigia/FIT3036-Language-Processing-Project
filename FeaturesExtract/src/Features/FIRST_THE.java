package Features;

import Main.Feature;

public class FIRST_THE extends Feature{
	
	public FIRST_THE(){
		return;
	}
	
	@Override
	public void execute(String[] aWord) {
		this.write(aWord[5]);
	}
}
