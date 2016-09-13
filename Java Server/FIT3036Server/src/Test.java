import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import UCG.*;

public class Test {
	
	public void runTest(){

		ConceptGraph cg = new ConceptGraph();
		
		Node node = new Node();
		node.setLabel("node1");
		
		Node node2 = new Node();
		node2.setLabel("node2");
		
		Node.Concept _concept = new Node.Concept();
		Node.Concept _conceptArc = new Node.Concept();
		
		Node.Feature called = new Node.Feature();
		Node.Feature role = new Node.Feature();
		
		Node.Arc arc = new Node.Arc();
		arc.setLabel("Arc24");
		Node.Feature arcRole = new Node.Feature();
		Node.Feature arcCalled = new Node.Feature();
		
		// setting up for arc
		arcRole.setKey("cg_role");
		arcRole.setValue("arc");
		arcCalled.setKey("called");
		arcCalled.setValue("on");
		_conceptArc.getFeatures().add(arcCalled);
		_conceptArc.getFeatures().add(arcRole);
		
		// setting up for concept for node
		called.setKey("called");
		called.setValue("mug");
		_concept.getFeatures().add(called);
		role.setKey("cg_role");
		role.setValue("node");
		_concept.getFeatures().add(role);
		
		// adding the elements together
		// the structure is as follow
		// <CG>
		// 	<node>
		//		<concept>
		//			<feature>
		// 		<arc>
		//			<component>
		//				<feature>
		// 	<node2>
		//		...
		
		arc.setConcept(_conceptArc);
		node.setArc(arc);
		node.setConcept(_concept);
		cg.getNodes().add(node);
		cg.getNodes().add(node2);
		
		try{
			File file = new File("ucg.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					
			// output
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(cg, file);
			jaxbMarshaller.marshal(cg, System.out);
		} catch (JAXBException e){
			e.printStackTrace();
		}
	}
}
