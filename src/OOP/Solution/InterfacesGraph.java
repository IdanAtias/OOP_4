package OOP.Solution;

import java.util.HashMap;
//import java.util.HashSet;
import java.util.LinkedList;
import java.lang.reflect.Method;

import OOP.Provided.OOPInherentAmbiguity;
import OOP.Provided.OOPMultipleException;



interface B
{
	   void fa();
	   void fb();
	   void fc();

}

interface C2 extends B
{

}

interface C1 extends B
{

}

interface D extends C1, C2
{
   void fa();
   void fb();
}

interface Base extends D
{
	void fc();
}

public class InterfacesGraph {
	//graph of interfaces:
	//vertices are interfaces
	//edge u->v == u extends v
	
	/*TODO - CHANGE TO PRIVATE*/
	public HashMap<Node, Class<?>[] > interfaces = new HashMap<Node, Class<?>[] >();
	public Node base;
	/*****************/
	
	InterfacesGraph(Class<?> baseInterClass) throws OOPMultipleException{
		//TODO: throw compatible exception
		base = new Node(baseInterClass,null);
		buildGraph(base);
	}
	
	private class Node {
		public Class<?> inter;
		public Node father;
		public Node(Class<?> inter, Node father) {
			this.inter = inter;
			this.father = father;
		}
		
		@Override
		public boolean equals(Object obj) {

//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj) {
//				return true;
//			}
//			if (obj == null) {
//				return false;
//			}
//			if (!(obj instanceof Node)) {
//				return false;
//			}
//			Node other = (Node) obj;
//			if (!getOuterType().equals(other.getOuterType())) {
//				return false;
//			}
//			if (inter == null) {
//				if (other.inter != null) {
//					return false;
//				}
//			} else if (inter != other.inter) {
//				return false;
//			}
//			return true;
//		}
//		private InterfacesGraph getOuterType() {
//			return InterfacesGraph.this;
//		}
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
			Node currNode = new Node(iter, interNode);
			if (this.interfaces.containsKey(currNode)) {
					checkForAmbiguity(currNode); //throws OOPinheritedAmbiguity if fails.
			}
			buildGraph(currNode);
		}
	}
	
	private void checkForAmbiguity(Node interNode) throws OOPMultipleException{
		LinkedList<Node> path = getPathToBase(interNode.father);
		Node existsNode = null;
		for (Node curr: interfaces.keySet()){
			if (curr.equals(interNode)){
				existsNode = curr;
				break;
			}
		}
		Node intersection = getIntersection(existsNode, path);
		LinkedList<Method> methods= new LinkedList<Method>();
		for (Method m: interNode.inter.getMethods()) {
			methods.add(m);
		}
		checkForAmbiguityAux(intersection, methods,interNode);
	}

	private void checkForAmbiguityAux(Node intersection, LinkedList<Method> methods, Node faulty) throws OOPMultipleException {
		LinkedList<Node> path = getPathToBase(intersection);
		for (Node n: path) {
			Method[] nMethods = n.inter.getMethods();
			for (Method m : nMethods) {
				methods.remove(m);
			}
		}
		if (methods.isEmpty()) return;
		throw new OOPInherentAmbiguity(base.inter, faulty.inter, methods.getFirst());
	}

	private Node getIntersection(Node existsNode, LinkedList<Node> path) {
		LinkedList<Node> existNodePath=getPathToBase(existsNode.father);
		for (Node n: existNodePath) {
			if (path.contains(n)) return n;
		}
		return base;//shouldn't get to here.
	}

	private LinkedList<Node> getPathToBase(Node interNode) {
		LinkedList<Node> path = new LinkedList<Node>();
		while (interNode != null) {
			path.add(interNode);
			interNode=interNode.father;
		}
		return path;
	}
	
	
	public void printGraph()
	{
		for (Node i: interfaces.keySet()) {
			System.out.println("The current interface: "+i.inter);
			if (i.father != null){
				System.out.println("The current interface father : "+i.father.inter);
			}
			System.out.print("and he extends: ");
			for (Class<?> v: interfaces.get(i)) {
				System.out.print(" "+v);
			}
			System.out.println();
			System.out.println();
		}
}
	
	
	public static void main (String[] args) throws java.lang.Exception
	{
		try {
		InterfacesGraph g = new InterfacesGraph(Base.class);
		g.printGraph();
		} catch (OOPInherentAmbiguity e){
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("SUCCESS");
	}
	
}

