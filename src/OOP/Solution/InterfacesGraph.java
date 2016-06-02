package OOP.Solution;

import java.util.HashMap;
import java.util.HashSet;

interface A
{
   // void test();
}

interface B extends A
{
   // void test();
}

interface C extends A, B
{

}

public class InterfacesGraph {
	//graph of interfaces:
	//vertices are interfaces
	//edge u->v == u extends v
	public HashMap<Class<?>, Class<?>[] > interfaces = new HashMap<Class<?>, Class<?>[] >();
	
	InterfacesGraph(Class<?> baseInterClass){
		//TODO: throw compatible exception
		buildGraph(baseInterClass);
	}
	
	private void buildGraph(Class<?> interClass){
		//getting the interfaces baseInter extends:
		Class<?>[] interfaces = interClass.getInterfaces();
		//base cond: no interfaces extended then return
		this.interfaces.put(interClass,interfaces);
		if (interfaces.length == 0){	
			return;
		}
		for (Class<?> iter: interfaces) {
			if (this.interfaces.containsKey(iter)) {
				continue;
			}
			buildGraph(iter);
		}
	}
	
	
	public boolean containsCircle(){
		//creating a copy of interfaces
		HashMap<Class<?>, Class<?>[] > graph = new HashMap<Class<?>, Class<?>[] >(interfaces);
		HashSet<Class<?>> sources = getSources(graph);
		
		while(graph.size() > 0){
			Class<?> src = getOneSource(sources);
			//no more sources => we got a cycle.
			if (src == null){
				return false;
			}
			//getting over on all edges from src and removing them
			//in addition we also check if we gain more sources.
			for (Class<?> inter: graph.get(src)){
				if (isSource(inter)){
					sources.add(inter);
				}
			}
			removeVertex(graph, Class<?> src);
		}
		
		return true;
		
		
	}
	private HashSet<Class<?>> getSources( HashMap<Class<?>, Class<?>[] > graph) {
		HashSet<Class<?>> sources = new HashSet<Class<?>>();
		for (Class<?> v: graph.keySet()) {
			if (isSource(v)){
				sources.add(v);
			}
		}
		return sources;
	}
	
	private Class<?> getOneSource(HashSet<Class<?>> sources) {
		for (Class<?> )
		return null;
	}
	
	
	public static void main (String[] args) throws java.lang.Exception
	{
		InterfacesGraph g= new InterfacesGraph(C.class);
		for (Class<?> i: g.interfaces.keySet()) {
			System.out.println("The current interface: "+i);
			System.out.print("and his values: ");
			for (Class<?> v: g.interfaces.get(i)) {
				System.out.print(" "+v);
			}
			System.out.println();
		}
	}
	
}

