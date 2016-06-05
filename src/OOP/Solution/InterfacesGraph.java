package OOP.Solution;

import java.util.HashMap;
import java.util.LinkedList;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import OOP.Provided.OOPInherentAmbiguity;
import OOP.Provided.OOPMultipleException;

interface I5 {
	void fa();

	void fb();
	void fc();
}

interface I4 extends I5
{
}

interface I3 extends I5 {
}

interface I2 extends I3, I4 {
	void fa();
	void fb();
}

interface I1 extends I2 {
	void fc();
}

public class InterfacesGraph {
	// graph of interfaces:
	// vertices are interfaces
	// edge u->v == u extends v

	/* TODO - CHANGE TO PRIVATE */
	public HashMap<Node, Class<?>[]> interfaces = new HashMap<Node, Class<?>[]>();
	public Node base;

	/*****************/

	InterfacesGraph(Class<?> baseInterClass) throws OOPMultipleException {
		// TODO: throw compatible exception
		checkAnnotations (baseInterClass);
		base = new Node(baseInterClass, null);
		buildGraph(base);
	}

	private void checkAnnotations(Class<?> inter) throws OOPMultipleException {
		Method[] methods= inter.getDeclaredMethods();
		for (Method m: methods) {
			Annotation[] annotations = m.getAnnotations();
			boolean hasOOPAnottaion=false;
			for (Annotation a : annotations) {
				if (a.toString()=="OOPMethod") hasOOPAnottaion=true;
			}
			if (hasOOPAnottaion==false) throw new OOPInherentAmbiguity(base.inter, inter, m);
		}
	}

	private class Node {
		public Class<?> inter;
		public Node father;

		public Node(Class<?> inter, Node father) {
			this.inter = inter;
			this.father = father;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			/*
			 * interfaceClass doesn't have how to "hashCode" so we use its name
			 * which is String that has "hashCode" implemented.
			 */
			//result = prime * result + getOuterType().hashCode();
			result = prime * result + ((inter == null) ? 0 : inter.getSimpleName().hashCode());
			return result;
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
			if (getOuterType() != (other.getOuterType())) {
				return false;
			}
			if (inter == null) {
				if (other.inter != null) {
					return false;
				}
			} else if (!(inter.getSimpleName()).equals(other.inter.getSimpleName())) {
				return false;
			}
			return true;
		}

		private InterfacesGraph getOuterType() {
			return InterfacesGraph.this;
		}
	}

	//buildGraph -> on every node: checkForAmbiguitiy -> getPathToBase, getIntersection, checkForAmbiguityAux
	private void buildGraph(Node interNode) throws OOPMultipleException {
		// getting the interfaces baseInter extends:
		Class<?>[] interfaces = interNode.inter.getInterfaces();
		
		// base cond: no interfaces extended then return
		this.interfaces.put(interNode, interfaces);
		if (interfaces.length == 0) {
			return;
		}
		for (Class<?> iter : interfaces) {
			checkAnnotations(iter);
			Node currNode = new Node(iter, interNode);
			if (this.interfaces.containsKey(currNode)) {
				checkForAmbiguity(currNode); // throws OOPinheritedAmbiguity if fails.
			}
			buildGraph(currNode);
		}
	}

	private void checkForAmbiguity(Node interNode) throws OOPMultipleException {
		LinkedList<Node> path = getPathToBase(interNode.father);
		Node existsNode = null;
		for (Node curr : interfaces.keySet()) {
			if (curr.equals(interNode)) {
				existsNode = curr;
				break;
			}
		}
		Node intersection = getIntersection(existsNode, path);
		LinkedList<Method> methods = new LinkedList<Method>();
		for (Method m : interNode.inter.getDeclaredMethods()) {
			methods.add(m);
		}
		checkForAmbiguityAux(intersection, methods, interNode);
	}

	private void checkForAmbiguityAux(Node intersection, LinkedList<Method> methods, Node faulty)
			throws OOPMultipleException {
		LinkedList<Node> path = getPathToBase(intersection);
		for (Node n : path) {
			Method[] nMethods = n.inter.getMethods();
			for (Method m : nMethods) {
				
				methods.remove(m);
			}
		}
		if (methods.isEmpty())
			return;
		throw new OOPInherentAmbiguity(base.inter, faulty.inter, methods.getFirst());
	}

	private Node getIntersection(Node existsNode, LinkedList<Node> path) {
		LinkedList<Node> existNodePath = getPathToBase(existsNode.father);
		for (Node n : existNodePath) {
			if (path.contains(n))
				return n;
		}
		return base;// shouldn't get to here.
	}

	private LinkedList<Node> getPathToBase(Node interNode) {
		LinkedList<Node> path = new LinkedList<Node>();
		while (interNode != null) {
			path.add(interNode);
			interNode = interNode.father;
		}
		return path;
	}

	public void printGraph() {
		for (Node i : interfaces.keySet()) {
			System.out.println("The current interface: " + i.inter.getSimpleName());
			if (i.father != null) {
				System.out.println("The current interface father : " + i.father.inter.getSimpleName());
			}
			System.out.print("and he extends: ");
			for (Class<?> v : interfaces.get(i)) {
				System.out.print(" " + v.getSimpleName());
			}
			System.out.println();
			System.out.println();
		}
	}

	public static void main(String[] args) throws java.lang.Exception {
//		try {
			System.out.println((I2.class.getDeclaredMethods())[0].getName());
			return;
//			InterfacesGraph g = new InterfacesGraph(I1.class);
//			g.printGraph();
//		} catch (OOPInherentAmbiguity e) {
//			System.out.println(e.getMessage());
//			return;
//		}
//		System.out.println("SUCCESS");
	}
}
