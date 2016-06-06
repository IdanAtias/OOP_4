package OOP.Solution;

import java.util.HashMap;
import java.util.LinkedList;
import java.lang.reflect.Method;

import OOP.Provided.OOPInherentAmbiguity;
import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

//interface I5 {
//    @OOPMethod(modifier = OOPModifier.PUBLIC)
//	void fa();
//    @OOPMethod(modifier = OOPModifier.PUBLIC)
//	void fb();
//    @OOPMethod(modifier = OOPModifier.PUBLIC)
//	void fc();
//}
//
//interface I4 extends I5 {
//}
//
//interface I3 extends I5 {
//}
//
//interface I2 extends I3, I4 {
//    @OOPMethod(modifier = OOPModifier.PUBLIC)
//	void fa();
//    @OOPMethod(modifier = OOPModifier.PUBLIC)
//	void fb();
//  @OOPMethod(modifier = OOPModifier.PUBLIC)
//	void fc();
//}
//
//interface I1 extends I2 {
//    @OOPMethod(modifier = OOPModifier.PUBLIC)
//	void fc();
//}

public class InterfacesGraph {
	// graph of interfaces:
	// vertices are interfaces
	// edge u->v == u extends v

	public HashMap<Node, Class<?>[]> interfaces = new HashMap<Node, Class<?>[]>();
	public Node base;
	private static Node orgBase = null;

	/*****************/

	InterfacesGraph(Class<?> baseInterClass, boolean isSubTree) throws OOPMultipleException {
		base = new Node(baseInterClass, null);
		if (!isSubTree){
			orgBase = base;
		}
		if (isSubTree) checkAnnotations(baseInterClass);
		buildGraph(base,isSubTree);
	}
	
	private boolean isEqualParamTypes(Class<?>[] arg0, Class<?>[] arg1) {
		if (arg0.length == arg1.length) {
			for (int arg2 = 0; arg2 < arg0.length; ++arg2) {
				if (arg0[arg2] != arg1[arg2]) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}
	/*
	 * Params:
	 * @mToOverride - the "problematic" method in the faulty class that should be overriden
	 * @m - method to check if overrides mToOverride
	 * 
	 * @return
	 * True if overrides, else False.
	 */
	private boolean checkForOverride(Method mToOverride, Method m) {
		if (mToOverride != null && m != null && m instanceof Method && mToOverride instanceof Method) {
			if (mToOverride.getName() == m.getName() && mToOverride.getReturnType().equals(m.getReturnType())
					&& isEqualParamTypes(mToOverride.getParameterTypes(), m.getParameterTypes())) {
					OOPModifier mod_m = (m.getAnnotation(OOPMethod.class)).modifier();
					OOPModifier mod_mToOverride = (mToOverride.getAnnotation(OOPMethod.class)).modifier();
					Package m_pack =  m.getDeclaringClass().getPackage();
					Package mToOverride_pack = mToOverride.getDeclaringClass().getPackage();
					if (mod_m == OOPModifier.PRIVATE){
						/*3*/
						return false;
					}
					if (mod_mToOverride==OOPModifier.DEFAULT){
						if (mToOverride_pack != base.inter.getPackage()){
							/*2*/
							return true;
						}
						if (mod_m != OOPModifier.PRIVATE){
							/*1*/
							return mToOverride_pack == m_pack;
						}
					}
					if (mod_mToOverride != OOPModifier.DEFAULT && mod_m == OOPModifier.DEFAULT){
						/*4*/
						return m_pack == base.inter.getPackage();
					}
					return true;
			}
		}
		return false;
	}

	private void checkAnnotations(Class<?> inter) throws OOPMultipleException {
		Method[] methods = inter.getDeclaredMethods();
		for (Method m : methods) {
			if (!m.isAnnotationPresent(OOPMethod.class)){
				throw new OOPInherentAmbiguity(orgBase.inter, inter, m);	
			}
		}
	}

	public static class Node {
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
			// result = prime * result + getOuterType().hashCode();
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
			if (inter == null) {
				if (other.inter != null) {
					return false;
				}
			} else if (!(inter.getSimpleName()).equals(other.inter.getSimpleName())) {
				return false;
			}
			return true;
		}
	}

	// buildGraph -> on every node: checkForAmbiguitiy -> getPathToBase,
	// getIntersection, checkForAmbiguityAux
	public void buildGraph(Node interNode, boolean isSubTree) throws OOPMultipleException {
		// getting the interfaces baseInter extends:
		Class<?>[] interfaces = interNode.inter.getInterfaces();

		// base cond: no interfaces extended then return
		this.interfaces.put(interNode, interfaces);
		if (interfaces.length == 0) {
			return;
		}
		for (Class<?> iter : interfaces) {
			if (isSubTree){
				checkAnnotations(iter);
			}
			Node currNode = new Node(iter, interNode);
			if (isSubTree && this.interfaces.containsKey(currNode)) {
				checkForAmbiguity(currNode, isSubTree); // throws OOPinheritedAmbiguity if
												// fails.
			}
			buildGraph(currNode, isSubTree);
		}
	}

	private void checkForAmbiguity(Node interNode, boolean isSubTree) throws OOPMultipleException {
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
		checkForAmbiguityAux(intersection, methods, interNode, isSubTree);
	}

	private void checkForAmbiguityAux(Node intersection, LinkedList<Method> methods, Node faulty, boolean isSubTree)
			throws OOPMultipleException {
		LinkedList<Node> path = getPathToBase(intersection);
		for (Node node : path) {
			if (!isSubTree && node.equals(base)){ /*base funcs cant override*/
				continue;
			}
			Method[] nMethods = node.inter.getDeclaredMethods();
			for (Method n_method : nMethods) {
				LinkedList<Method> methods_cpy = new LinkedList<Method>(methods);
				for (Method method : methods_cpy) {
					if (checkForOverride(method, n_method)) methods.remove(method);
				}
			}
		}
		if (methods.isEmpty())
			return;
		throw new OOPInherentAmbiguity(orgBase.inter, faulty.inter, methods.getFirst());
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

//	public static void main(String[] args) throws java.lang.Exception {
//		 try {
//		 InterfacesGraph g = new InterfacesGraph(I1.class);
//		 g.printGraph();
//		 } catch (OOPInherentAmbiguity e) {
//		 System.out.println(e.getMessage());
//		 return;
//		 }
//		 System.out.println("SUCCESS");
//	}
}
