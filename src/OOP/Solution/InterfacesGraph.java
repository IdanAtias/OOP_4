package OOP.Solution;

import java.util.HashMap;
import java.util.HashSet;

public class InterfacesGraph {
	//graph of interfaces:
	//vertices are interfaces
	//edge u->v == u extends v
	HashMap<Class<?>, Class[] > interfaces = new HashMap<Class<?>, Class[] >();
	
	InterfacesGraph(Class<?> interClass){
		
	}
	
	private void buildGraph(Class<?> baseInterClass){
		//getting the interfaces baseInter extends:
		Class[] interfaces = baseInterClass.getInterfaces();
		
	}
}
