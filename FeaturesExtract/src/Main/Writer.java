package Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * used to write text to the output file
 */
public class Writer {
	private File file;
	private String[] attributes = {"@RELATION CHUNKER",
			 "@ATTRIBUTE\tIS_FINAL\t{yes-final,no-final}",
			 "@ATTRIBUTE\tIS_THAT\t{yes-that,no-that}",
			 "@ATTRIBUTE\tIS_THE\t{yes-the,no-the}",
			 "@ATTRIBUTE\tPOS\t{CC,CD,DT,EX,FW,IN,JJ,JJR,JJS,LS,MD,NN,NNS,NNP,NNPS,PDT,POS,PRP,PRP$,RB,RBR,RBS,RP,SYM,TO,UH,VB,VBD,VBG,VBN,VBP,VBZ,WDT,WP,WP$,WRB,``}",
			 "@ATTRIBUTE\tIS_OF\t{no-of,yes-of}",
			 "@ATTRIBUTE\tIS_PW\t{no-pw,yes-pw}",
			 "@ATTRIBUTE\tIS_PP\t{no-pp,yes-pp}",
			 "@ATTRIBUTE\tIS_STOP\t{no-stop,yes-stop}",
			 "@ATTRIBUTE\tFIRST_THE\t{0,1}",
			 "@ATTRIBUTE\tRELATIVE_POS\tNUMERIC",
			 "@ATTRIBUTE\tPWOF\t{no-pwof,yes-pwof}",
			 "@ATTRIBUTE\tNNP\t{no-NNP,yes-NNP}",
			 "@ATTRIBUTE\tPWPW\t{no-pwpw,yes-pwpw}",
			 "@ATTRIBUTE\tCLASS\t{B-O,I-O,B-P,I-P,B-L,I-L,B-N,I-N,B-A,I-A,B-S,I-S}",
			 "@DATA"};

	public Writer(){
		file = new File("outputs.arff");
	}
	
	/**
	 * setup the template for the output file (prints attributes and headers)
	 */
	public void writeAttributes(){
		try{
		PrintWriter out = new PrintWriter(new FileWriter(file,true));
		out.println(attributes[0]);
		out.println();
		out.println(attributes[1]);
		out.println(attributes[2]);
		out.println(attributes[3]);
		out.println(attributes[4]);
		out.println(attributes[5]);
		out.println(attributes[6]);
		out.println(attributes[7]);
		out.println(attributes[8]);
		out.println(attributes[9]);
		out.println(attributes[10]);
		out.println(attributes[11]);
		out.println(attributes[12]);
		out.println(attributes[13]);
		out.println();
		out.println();
		out.println(attributes[14]);
		out.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * writes text to the output file
	 * @param answer
	 */
	public void write(String answer) {
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(file, true));
			out.write(answer);
			out.close();
		} 
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * prints new line in the output file
	 */
	public void newLine() {
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(file, true));
			out.println();
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
