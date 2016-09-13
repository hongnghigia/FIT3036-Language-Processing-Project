package UCG;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Node {
	
	String label;
	String kind;
	String key;
	String value;
	
	public String getLabel(){
		return label;
	}
	
	@XmlAttribute
	public void setLabel(String label){
		this.label = label;
	}
	
	public String getKind(){
		return kind;
	}
	
	@XmlElement
	public void setKind(String kind){
		this.kind = kind;
	}
	
	public String getKey(){
		return key;
	}
	
	@XmlElement
	public void setKey(String key){
		this.key = key;
	}
	
	public String getValue(){
		return value;
	}
	
	@XmlElement
	public void setValue(String value){
		this.value = value;
	}
	
}
