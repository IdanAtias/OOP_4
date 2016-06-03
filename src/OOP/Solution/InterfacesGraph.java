package OOP.Solution;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.lang.reflect.Method;

import OOP.Provided.OOPInherentAmbiguity;
import OOP.Provided.OOPMultipleException;

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
	
	/*TODO - CHANGE TO PRIVATE*/
	public HashMap<Node, Class<?>[] > interfaces = new HashMap<Node, Class<?>[] >();
	public Class<?> base;
	/*****************/
	
	InterfacesGraph(Class<?> baseInterClass) throws OOPMultipleException{
		//TODO: throw compatible exception
		base = baseInterClass;
		buildGraph(baseInterClass);
	}
	
	private class Node {
		public Class<?> inter;
		public Class<?> father;
		public Node(Class<?> inter, Class<?> father) {
			this.inter = inter;
			this.father = father;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Node)) {
				return false;
			}
			Node other = (Node) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (father == null) {
				if (other.father != null) {
					return false;
				}
			} else if (father != other.father) {
				return false;
			}
			if (inter == null) {
				if (other.inter != null) {
					return false;
				}
			} else if (inter != other.inter) {
				return false;
			}
			return true;
		}
		private InterfacesGraph getOuterType() {
			return InterfacesGraph.this;
		}
		
		
	}
	
	private void buildGraph(Node interNode) throws OOPMultipleException {
		//getting the interfaces baseInter extends:
		Class<?>[] interfaces = interNode.inter.getInterfaces();
		
		//base cond: no interfaces extended then return
		this.interfaces.put(interNode,interfaces);
		if (interfaces.length == 0){	
			return;
		}
		for (Class<?> iter: interfaces) {
			Node currNode = new Node(iter, interNode.inter);
			if (this.interfaces.containsKey(currNode)) {
					checkForAmbiguity(currNode); //throws OOPinheritedAmbiguity if fails.
			}
			buildGraph(currNode);
		}
	}
	
	private void checkForAmbiguity(Node interNode) throws OOPMultipleException{
		LinkedList<Node> path = getPathToBase(interNode);
		Node existsNode;
		for (Node curr: interfaces.keySet()){
			if (curr.equals(interNode)){
				existsNode = curr;
				break;
			}
		}
		Node intersection = getIntersection(existsNode, path));
		HashSet<Method> methods= new HashSet<Method>();
		for (Method m: interNode.inter.getMethods()) {
			methods.add(m);
		}
		checkForAmbiguityAux(intersection, methods);
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

