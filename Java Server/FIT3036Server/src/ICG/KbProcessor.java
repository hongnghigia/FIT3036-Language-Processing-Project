package ICG;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;


public class KbProcessor {
	private ArrayList<String> names = new ArrayList<String>();
	private HashMap<String, String> properties;
	private String filename;
	public KbProcessor(String filename){
		this.filename = filename;
	}
	
	public ArrayList<String> getNames(String target){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.filename));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	
		try{
			this.names.clear();
			String line = br.readLine();
			while(line != null){
//				System.out.println(line);
				String[] tmp = line.split(" ");
				if (tmp[0].equalsIgnoreCase("define")){
					String[] parts = line.split(" ");
					String[] obj = parts[2].split("\\:");
					
					if (obj[1].toLowerCase().contains(target)){
						this.names.add(obj[0]);
						line = br.readLine();
					} else {
						line = br.readLine();
					}
				} else {
					line = br.readLine();
				}
			}
			br.close();
			return this.names;
		} catch (IOException e){
			e.printStackTrace();
		}
		return this.names;
		
	}
	
	public HashMap<String, String> getProperties(String name){
		properties = new HashMap<String, String>();
		properties.clear();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(this.filename));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {
			String propertyLines = br.readLine().trim();
			while (propertyLines != null){
				String[] tmp = propertyLines.split(" ");
				if (tmp[0].equalsIgnoreCase("define")){
					String[] parts = propertyLines.split(" ");
					String[] obj = parts[2].split("\\:");
					if (obj[0].equalsIgnoreCase(name)){
						// starting at the line after the define line, add in every properties until
						// the next define line.
						//System.out.println(propertyLines);
						propertyLines = br.readLine().trim();
						while (!propertyLines.contains("define")){
							if (propertyLines.isEmpty()){
								return properties;
							} else {
								String[] splits = propertyLines.split(" = ");
								properties.put(splits[0], splits[1]);	
								propertyLines = br.readLine().trim();
								}
						}
					}
				}
				propertyLines = br.readLine();
			}
			return properties;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return properties;
	}
}
