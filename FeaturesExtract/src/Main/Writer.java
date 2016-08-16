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
		file = new File("sample.txt");
	}
	
	public void writeAttributes(){
//		FileWriter fw = new FileWriter("output.txt");
//		BufferedWriter out = new BufferedWriter(fw);
		try{
		PrintWriter out = new PrintWriter(new FileWriter(file,true));
		out.append(attributes[0]);
		out.write("\n");
		out.write("\n");
		out.append(attributes[1]);
		out.write("\n");
		out.append(attributes[2]);
		out.write("\n");
		out.append(attributes[3]);
		out.write("\n");
		out.append(attributes[4]);
		out.write("\n");
		out.append(attributes[5]);
		out.write("\n");
		out.append(attributes[6]);
		out.write("\n");
		out.write("\n");
		out.write("\n");
		out.append(attributes[7]);
		out.write("\n");
		out.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void write(String answer) {
		// TODO Auto-generated method stub
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
}
