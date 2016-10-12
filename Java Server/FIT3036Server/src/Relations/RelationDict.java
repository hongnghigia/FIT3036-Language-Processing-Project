package Relations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class RelationDict {

	private HashMap<String, String> relationsDict = new  HashMap<String, String>();
	
	public RelationDict() {
		setup();
	}
	
	private void setup() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("spatialrelations.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try{
			String line = br.readLine().trim();
			while(line != null){
				String[] tmp = line.split(" ");
				if (tmp[0].equalsIgnoreCase("define")){
					String[] parts = tmp[2].split(":");
					String result = parts[0];
					
					line = br.readLine().trim();
					while (!line.isEmpty()) {
						if (line.contains("description") || line.contains("implementation")) {
							line = br.readLine().trim();
						}
						else if (line.contains("called")) {
							String[] called = line.split("\\+=");
							called[1] = called[1].trim();
							relationsDict.put(called[1], result);
							line = br.readLine();
							if (line == null) {
								return;
							}
						}
					}
				} 
				line = br.readLine();
			}
//		try {
//			String line = br.readLine().trim();
//			
//			br.close();
		} 
		catch (IOException e){
			e.printStackTrace();
		}
	}
		
	public String getSynonym(String old) {
		String tmp = old.trim().replace(" ", "_");
		return relationsDict.get(tmp);
	}
}
