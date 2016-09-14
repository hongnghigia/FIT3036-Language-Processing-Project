package Main;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import UCG.*;

public class XmlGen {
	private String object;
	private ArrayList<String> preps;
	private ArrayList<String> landmarks;
	
	public XmlGen(String object, ArrayList<String> preps, ArrayList<String> landmarks){
		this.object = object;
		this.preps = preps;
		this.landmarks = landmarks;
	}
	
	public void run(){
		ConceptGraph cg = new ConceptGraph();
		
		// node for object noun
		Node objNode = new Node();
		objNode.setLabel("node1");
		Node.Concept obConcept = new Node.Concept();
		Node.Feature obCalled = new Node.Feature();
		Node.Feature obRole = new Node.Feature();
		obCalled.setKey("called");
		obCalled.setValue(this.object);
		obRole.setKey("cg_role");
		obRole.setValue("node");
		
		obConcept.getFeatures().add(obCalled);
		obConcept.getFeatures().add(obRole);
		
		objNode.setConcept(obConcept);
		
		Node.Arc ppArc = new Node.Arc();
		ppArc.setLabel("Arc0");
		Node.Concept arc = new Node.Concept();
		Node.Feature called = new Node.Feature();
		Node.Feature role = new Node.Feature();
		called.setKey("called");
		called.setValue(preps.get(0));
		role.setKey("cg_role");
		role.setValue("arc");
		arc.getFeatures().add(called);
		arc.getFeatures().add(role);
		
		
		// node for landmark
		Node LandMarkNode = new Node();
		LandMarkNode.setLabel("node2");
		Node.Concept conceptL = new Node.Concept();
		Node.Feature Lcalled = new Node.Feature();
		Node.Feature Lrole = new Node.Feature();
		Lcalled.setKey("called");
		Lrole.setKey("cg_role");
		Lcalled.setValue(landmarks.get(0));
		Lrole.setValue("node");
		conceptL.getFeatures().add(Lcalled);
		conceptL.getFeatures().add(Lrole);
		
		LandMarkNode.setConcept(conceptL);
	
		ppArc.setConcept(arc);
		ppArc.setChild(LandMarkNode.getLabel());
		
		objNode.getArcs().add(ppArc);
		cg.getNodes().add(objNode);
		cg.getNodes().add(LandMarkNode);
		if(preps.size() > 1){
			Node.Arc ppArc2 = new Node.Arc();
			ppArc2.setLabel("Arc1");
			Node.Concept arc2 = new Node.Concept();
			Node.Feature called2 = new Node.Feature();
			Node.Feature role2 = new Node.Feature();
			called2.setKey("called");
			called2.setValue(preps.get(1));
			role2.setKey("cg_role");
			role2.setValue("arc");
			arc2.getFeatures().add(called2);
			arc2.getFeatures().add(role2);
			
			// node for landmark
			Node LandMarkNode2 = new Node();
			LandMarkNode2.setLabel("node3");
			Node.Concept conceptL2 = new Node.Concept();
			Node.Feature Lcalled2 = new Node.Feature();
			Node.Feature Lrole2 = new Node.Feature();
			Lcalled2.setKey("called");
			Lrole2.setKey("cg_role");
			Lcalled2.setValue(landmarks.get(1));
			Lrole2.setValue("node");
			conceptL2.getFeatures().add(Lcalled2);
			conceptL2.getFeatures().add(Lrole2);
			
			LandMarkNode2.setConcept(conceptL2);

			ppArc2.setConcept(arc2);
			ppArc2.setChild(LandMarkNode2.getLabel());
			
			objNode.getArcs().add(ppArc2);
			
			cg.getNodes().add(LandMarkNode2);
		}

		
		try{
			File file = new File("ucg.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(cg, file);
			jaxbMarshaller.marshal(cg, System.out);
		} catch (JAXBException e){
			e.printStackTrace();
		}
	}
	
}
