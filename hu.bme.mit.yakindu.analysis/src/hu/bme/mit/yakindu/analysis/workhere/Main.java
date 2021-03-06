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
		
		System.out.println("package hu.bme.mit.yakindu.analysis.workhere;\r\n" + 
				"\r\n" + 
				"import java.io.IOException;\r\n" + 
				"import java.util.Scanner;\r\n" + 
				"\r\n" + 
				"import hu.bme.mit.yakindu.analysis.RuntimeService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.TimerService;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;\r\n" + 
				"import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"public class RunStatechart {\r\n" + 
				"	\r\n" + 
				"	public static void main(String[] args) throws IOException {\r\n" + 
				"		ExampleStatemachine s = new ExampleStatemachine();\r\n" + 
				"		s.setTimer(new TimerService());\r\n" + 
				"		RuntimeService.getInstance().registerStatemachine(s, 200);\r\n" + 
				"		\r\n" + 
				"		s.init();\r\n" + 
				"		s.enter();\r\n" + 
				"		\r\n" +
				"		boolean end = false;\r\n" +
				"		Scanner getInput = new Scanner(System.in);\r\n" + 
				"		\r\n" + 
				"		while(!end) {\r\n" + 
				"			String in = getInput.nextLine();\r\n" + 
				"			switch(in) {");
		
		while(iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof EventDefinition) {
				EventDefinition eventDef = (EventDefinition) content;
				System.out.println(
				"			case " + '"' + eventDef.getName() + '"' + ":\n" + 
				"				s.raise" + eventDef.getName().substring(0,1).toUpperCase() + eventDef.getName().substring(1) +"();"+ "\n"+
				"				break;"
				);
			}
		}
		
		System.out.println(
				"			case \"exit\":\r\n" + 
				"				end = true;\r\n" +  
				"				break;\r\n" +
				"			default:\r\n" +   
				"				break;\r\n" + 
				"			}\r\n" + 
				"			print(s);\n"+
				"			s.runCycle();\n"+		
				"		}\n"+	
				"		System.exit(0);\n"+
				"	}\n" + 
				"	public static void print(IExampleStatemachine s) {");
		
		iterator = s.eAllContents();
		
		while(iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof VariableDefinition) {
				VariableDefinition varDef = (VariableDefinition) content;
				System.out.println( "		System.out.println(" + '"' + varDef.getName().toUpperCase().charAt(0) + " = " + '"' + " + s.getSCInterface()." +
				"get" + varDef.getName().substring(0,1).toUpperCase() + varDef.getName().substring(1) + "());");
			}
		}
	
		System.out.println(
				"	}\n"
				+ "}");
		
//		int i = 0;
//		System.out.println("public static void print(IExampleStatemachine s) {");
//		while (iterator.hasNext()) {
//			EObject content = iterator.next();
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
//			if(content instanceof VariableDefinition) {
//				VariableDefinition varDef = (VariableDefinition) content;
//				System.out.println(varDef.getName());
//				System.out.println("\tSystem.out.println(\"" + varDef.getName().toUpperCase().charAt(0) + " =\" + s.getSCInterface.get" +
//						varDef.getName().substring(0,1).toUpperCase() + varDef.getName().substring(1) + "());");
//			}
//			
//			if(content instanceof EventDefinition) {
//				EventDefinition eventDef = (EventDefinition) content;
//				System.out.println(evenDef.getName);
//			}
//			System.out.println("}");
//		}
		
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
