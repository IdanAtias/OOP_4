package OOP.Solution;

import OOP.Provided.*;
import OOP.Solution.InterfacesGraph.Node;
import javafx.util.Pair;

import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedList;

public class OOPMultipleControl {

	// TODO: DO NOT CHANGE !!!!!!
	private Class<?> interfaceClass;
	private File sourceFile;
	public InterfacesGraph graph = null;

	// TODO: DO NOT CHANGE !!!!!!
	public OOPMultipleControl(Class<?> interfaceClass, File sourceFile) {
		this.interfaceClass = interfaceClass;
		this.sourceFile = sourceFile;
	}

	// TODO: fill in here :
	public void validateInheritanceTree() throws OOPMultipleException {
		graph = new InterfacesGraph(interfaceClass, false /* isSubTree */);
		for (InterfacesGraph.Node vertex : graph.interfaces.keySet()) {
			new InterfacesGraph(vertex.inter, true /* isSubTree */);
		}
		LinkedList<Method> baseMethods = new LinkedList<Method>();
		for (Method m: graph.base.inter.getDeclaredMethods()){
			baseMethods.add(m);
		}
		/*this throws if hiding or overloading*/
		graph.checkVisibilityAndOverloading(graph.base.inter, baseMethods);
	}

	// TODO: fill in here :
	public Object invoke(String methodName, Object[] args) throws OOPMultipleException {
		HashSet<Pair<Class<?>, Method>> candidates = new HashSet<Pair<Class<?>, Method>>();
		getMethod(graph.base, methodName, args, candidates, true);
		if (candidates.size() == 0)
			throw new OOPInaccessibleMethod();
		if (candidates.size() > 1)
			throw new OOPCoincidentalAmbiguity(candidates);
		try {
			Class<?> inter = candidates.iterator().next().getKey();
			Method method = candidates.iterator().next().getValue();
			if (method.getAnnotation(OOPMethod.class).modifier() == OOPModifier.PRIVATE) {
				throw new OOPInaccessibleMethod();
			}
			Class<?> interClass = getClassFromInter(inter, method);
			Method methodInClass = interClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
			return methodInClass.invoke(interClass.newInstance(), args);
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
			throw new OOPInaccessibleMethod();
		} catch (InvocationTargetException e) {
			throw (OOPMultipleException) e.getTargetException();
		} catch (NoSuchMethodException e) {
			/* CANT BE ACCORDING TO PDF */
			/*
			 * SEE PDF IN ASSUMPTIONS SECTION IN NUMBER 5 - CLASS A MUST
			 * IMPLEMENT ALL METHOD OF INTERFACE A
			 */
			return null;
		}
	}

	private void getMethod(InterfacesGraph.Node node, String methodName, Object[] args,
			HashSet<Pair<Class<?>, Method>> candidates, boolean isPathKeepPackage) throws OOPMultipleException {

		if (!node.equals(graph.base)) {
			isPathKeepPackage = isPathKeepPackage && (node.inter.getPackage() == graph.base.inter.getPackage());
			/*null if cant find*/
			Method m = findMethod(node, methodName, args, isPathKeepPackage); 
			if (m != null) {
				if (!isTypesEqual(args, m.getParameterTypes())){
					/*found overloading - throw inaccessible*/
					throw new OOPInaccessibleMethod(); /*could found what else to throw*/
				}
					candidates.add(new Pair<Class<?>, Method>(node.inter, m));
				return;
			}
		}
		for (Class<?> inter : graph.interfaces.get(node)) {
			InterfacesGraph.Node interNode = new InterfacesGraph.Node(inter, node);
			getMethod(interNode, methodName, args, candidates, isPathKeepPackage);
		}
	}

	private Class<?> getClassFromInter(Class<?> inter, Method corruptedMethod) throws OOPMultipleException {
		if (inter == null) {
			return null;
		}
		String interName = inter.getName();
		int indexOfI = interName.lastIndexOf('I');
		if (indexOfI < 0) {
			throw new OOPBadClass(corruptedMethod);
		}
		String className = interName.substring(0, indexOfI) + "C" + interName.substring(indexOfI + 1);
		try {
			return Class.forName(className);
		} catch (Exception e) {
			throw new OOPBadClass(corruptedMethod);
		}

	}

	private Method findMethod(Node node, String methodName, Object[] args, boolean isPathKeepPackage) throws OOPMultipleException{
		if (node != null && methodName != null) {
			Class<?> interClass = node.inter;
			Method[] interMethods = interClass.getDeclaredMethods();
			for (Method m : interMethods) {
				OOPModifier mod = m.getAnnotation(OOPMethod.class).modifier();
				if (m.getName() == methodName) {
					if (mod == OOPModifier.DEFAULT) {
						if (node.inter.getPackage() != graph.base.inter.getPackage() || !isPathKeepPackage) {
							throw new OOPInaccessibleMethod();
						}
					}
					return m;
				}
			}
		}
		return null;
	}

	private boolean isTypesEqual(Object[] args, Class<?>[] parameterTypes) {
		if (args == null && (parameterTypes == null || parameterTypes.length == 0)) {
			return true;
		}
		if (args == null) return false;
		if (args.length != parameterTypes.length) {
			return false;
		}
		for (int i = 0; i < args.length; i++) {
			if (!parameterTypes[i].isAssignableFrom(args[i].getClass())) {
				return false;
			}
		}
		return true;
	}

	// TODO: DO NOT CHANGE !!!!!!
	public void removeSourceFile() {
		if (sourceFile.exists()) {
			sourceFile.delete();
		}
	}
}
