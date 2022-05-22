package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;


import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		int i = 0;
		System.out.println("public static void print(IExampleStatemachine s) {");
		while (iterator.hasNext()) {
			EObject content = iterator.next();
//			if(content instanceof State) {
//				State state = (State) content;
//				
//				if(state.getName() != null && state.getName() != "") {	
//				System.out.println(state.getName());
//				}
//				
//				else {
//					i++;
//					System.out.println("Unknown_" + i);
//				}
//				
//				if(state.getOutgoingTransitions().isEmpty()) {
//					System.out.println("Found trap: " + state.getName());
//				}
//					
//			}
//			else if(content instanceof Transition) {
//				Transition transition = (Transition) content;
//				System.out.println(transition.getSource().getName() + " -> " + transition.getTarget().getName());
//			}
			if(content instanceof VariableDefinition) {
				VariableDefinition varDef = (VariableDefinition) content;
//				System.out.println(varDef.getName());
				System.out.println("\tSystem.out.println(\"" + varDef.getName().toUpperCase().charAt(0) + " =\" + s.getSCInterface.get" +
						varDef.getName().substring(0,1).toUpperCase() + varDef.getName().substring(1) + "());");
			}
//			if(content instanceof EventDefinition) {
//				EventDefinition eventDef = (EventDefinition) content;
//				System.out.println(eventDef.getName());
//			}
		}
		System.out.println("}");
		
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
