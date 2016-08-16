package Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	private String[] attributes = {"@RELATION CHUNKER",
			 "@ATTRIBUTE\tIS_OF\t{no-of,yes-of}",
			 "@ATTRIBUTE\tIS_THE\t{yes-the,no-the}",
			 "@ATTRIBUTE\tIS_PP\t{no-pp,yes-pp}",
			 "@ATTRIBUTE\tIS_PW\t{no-pw,yes-pw}",
			 "@ATTRIBUTE\tIS_STOP\t{no-stop,yes-stop}",
			 "@ATTRIBUTE\tIS_THAT\t{yes-that,no-that}",
			 "@DATA"};
	
	public void writeAttributes() throws IOException {
		FileWriter fw = new FileWriter("output.txt");
		BufferedWriter out = new BufferedWriter(fw);
		out.append(attributes[0]);
		out.newLine();
		out.newLine();
		out.append(attributes[1]);
		out.newLine();
		out.append(attributes[2]);
		out.newLine();
		out.append(attributes[3]);
		out.newLine();
		out.append(attributes[4]);
		out.newLine();
		out.append(attributes[5]);
		out.newLine();
		out.append(attributes[6]);
		out.newLine();
		out.newLine();
		out.newLine();
		out.append(attributes[7]);
		out.newLine();
		out.close();
	}
}
