package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
	private File file;
	private String[] attributes = {"@RELATION CHUNKER",
			 "@ATTRIBUTE\tIS_OF\t{no-of,yes-of}",
			 "@ATTRIBUTE\tIS_THE\t{yes-the,no-the}",
			 "@ATTRIBUTE\tIS_PP\t{no-pp,yes-pp}",
			 "@ATTRIBUTE\tIS_PW\t{no-pw,yes-pw}",
			 "@ATTRIBUTE\tIS_STOP\t{no-stop,yes-stop}",
			 "@ATTRIBUTE\tIS_THAT\t{yes-that,no-that}",
			 "@DATA"};

	public Writer(){
		file = new File("outputs.arff");
	}
	
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
		out.println();
		out.println();
		out.println(attributes[7]);
		out.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void write(String answer) {
		
		try{
			if(!file.exists()){
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(file, true));
			out.write(answer);
			out.close();
			} catch (IOException e){
				e.printStackTrace();
			}
	}
	
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
