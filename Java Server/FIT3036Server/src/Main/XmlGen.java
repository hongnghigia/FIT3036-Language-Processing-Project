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
		NodeXML objNode = new NodeXML();
		objNode.setLabel("node1");
		NodeXML.Concept obConcept = new NodeXML.Concept();
		NodeXML.Feature obCalled = new NodeXML.Feature();
		NodeXML.Feature obRole = new NodeXML.Feature();
		obCalled.setKey("called");
		obCalled.setValue(this.object);
		obRole.setKey("cg_role");
		obRole.setValue("node");
		
		obConcept.getFeatures().add(obCalled);
		obConcept.getFeatures().add(obRole);
		
		objNode.setConcept(obConcept);
		
		NodeXML.Arc ppArc = new NodeXML.Arc();
		ppArc.setLabel("Arc0");
		NodeXML.Concept arc = new NodeXML.Concept();
		NodeXML.Feature called = new NodeXML.Feature();
		NodeXML.Feature role = new NodeXML.Feature();
		called.setKey("called");
		called.setValue(preps.get(0));
		role.setKey("cg_role");
		role.setValue("arc");
		arc.getFeatures().add(called);
		arc.getFeatures().add(role);
		
		
		// node for landmark
		NodeXML LandMarkNode = new NodeXML();
		LandMarkNode.setLabel("node2");
		NodeXML.Concept conceptL = new NodeXML.Concept();
		NodeXML.Feature Lcalled = new NodeXML.Feature();
		NodeXML.Feature Lrole = new NodeXML.Feature();
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
			NodeXML.Arc ppArc2 = new NodeXML.Arc();
			ppArc2.setLabel("Arc1");
			NodeXML.Concept arc2 = new NodeXML.Concept();
			NodeXML.Feature called2 = new NodeXML.Feature();
			NodeXML.Feature role2 = new NodeXML.Feature();
			called2.setKey("called");
			called2.setValue(preps.get(1));
			role2.setKey("cg_role");
			role2.setValue("arc");
			arc2.getFeatures().add(called2);
			arc2.getFeatures().add(role2);
			
			// node for landmark
			NodeXML LandMarkNode2 = new NodeXML();
			LandMarkNode2.setLabel("node3");
			NodeXML.Concept conceptL2 = new NodeXML.Concept();
			NodeXML.Feature Lcalled2 = new NodeXML.Feature();
			NodeXML.Feature Lrole2 = new NodeXML.Feature();
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
			JAXBContext jaxbContext = JAXBContext.newInstance(NodeXML.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(cg, file);
			jaxbMarshaller.marshal(cg, System.out);
		} catch (JAXBException e){
			e.printStackTrace();
		}
	}
	
}
