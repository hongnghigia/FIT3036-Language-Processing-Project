package ICG;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;


public class KbProcessor {
	private ArrayList<String> properties = new ArrayList<String>();
	private String filename;
	public KbProcessor(String filename){
		this.filename = filename;
	}
	
	public ArrayList<String> getProperties(String target){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.filename));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try{
			String line = br.readLine();
			while(line != null){
//				System.out.println(line);
				String[] tmp = line.split(" ");
				if (tmp[0].equalsIgnoreCase("define")){
					String[] parts = line.split(" ");
					String[] obj = parts[2].split("\\:");
					if (obj[1].equalsIgnoreCase(target)){
						this.properties.add(obj[0]);
						line = br.readLine();
					} else {
						line = br.readLine();
					}
				} else {
					line = br.readLine();
				}
			}
			br.close();
			return this.properties;
		} catch (IOException e){
			e.printStackTrace();
		}
		return this.properties;
		
	}
}
