package Features;

import Main.Feature;

public class FIRST_PP extends Feature{

	public FIRST_PP(){
		return;
	}
	@Override
	public void execute(String[] aWord) {
		this.write(aWord[6]);
	}

}
