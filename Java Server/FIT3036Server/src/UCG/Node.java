package UCG;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

/*
 * @Description: Node tag for xml
 */
@XmlRootElement(name = "Node")
@XmlSeeAlso(ConceptGraph.class)
public class Node {
	
	private String label;
	private Concept concept;
	private List<Arc> arcs;
	
	@XmlElementRef()
	public List<Arc> getArcs(){
		return arcs;
	}
	
	public Node(){
		arcs = new ArrayList<Arc>();
	}
	
	@XmlAttribute(name = "label")
	public void setLabel(String label){
		this.label = label;
	}
	public String getLabel(){
		return label;
	}
	
	@XmlElement(name = "concept")
	
	public void setConcept(Concept concept){
		this.concept = concept;
	}	
	public Concept getConcept(){
		return concept;
	}
	
	@XmlElement(name = "arc")
	private Arc arc;
	
	public void setArc(Arc arc){
		this.arc = arc;
	}
	
/*
 * @Description: Concept tag for xml
 */
	@XmlRootElement(name = "concept")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Concept{
		private List<Feature> features;

		// list of features for a concept
		public List<Feature> getFeatures(){
			return features;
		}
		public Concept(){
			features = new ArrayList<Feature>();
		}
		
		@XmlAttribute(name = "kind")
		private String kind = "base";
		
		public String getKind(){
			return this.kind;
		}
		
		public void setKind(String kind){
			this.kind = kind;
		}
		@XmlElement(name = "feature")
		private Feature feature;
		
		public void setFeature(Feature feature){
			this.feature = feature;
		}
		public Feature getFeature(){
			return this.feature;
		}
 	}
/*
 * @Description: Feature tag for xml	
 */
	
	@XmlRootElement(name = "feature")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Feature{
		@XmlAttribute(name = "key")
		private String key;
		
		public String getKey(){
			return key;
		}
		
		public void setKey(String key){
			this.key = key;
		}
		
		@XmlAttribute(name = "value")
		private String value;
		
		public String getValue(){
			return value;
		}
		
		public void setValue(String value){
			this.value = value;
		}
	}
	
/*
 * @Description: Arc tag for xml
 */
	@XmlRootElement(name = "arc")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Arc{
		@XmlAttribute(name = "label")
		private String label;
		
		public String getLabel(){
			return label;
		}
		public void setLabel(String label){
			this.label = label;
		}
		
		@XmlElement(name = "concept")
		private Concept concept;
		
		public void setConcept(Concept concept){
			this.concept = concept;
		}
		public Concept getConcept(){
			return concept;
		}
		
		@XmlElement(name = "child")
		private String node;
		
		public void setChild(String node){
			this.node = node;
		}
		public String getChild(){
			return node;
		}
	}
}
