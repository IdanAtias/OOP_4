package OOP.Solution;

import OOP.Provided.*;
import OOP.Solution.InterfacesGraph.Node;
import javafx.util.Pair;

import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;

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
		graph = new InterfacesGraph(interfaceClass);
	}

	// TODO: fill in here :
	public Object invoke(String methodName, Object[] args) throws OOPMultipleException {
		HashSet<Pair<Class<?>, Method>> candidates = new HashSet<Pair<Class<?>, Method>>();
		getMethod(graph.base, methodName, args, candidates);
		if (candidates.size() == 0)
			throw new OOPInaccessibleMethod();
		if (candidates.size() > 1)
			throw new OOPCoincidentalAmbiguity(candidates);
		try {
			Class<?> _class = candidates.iterator().next().getKey();
			Method methodInClass = candidates.iterator().next().getValue();
			return methodInClass.invoke(_class.newInstance(), args);
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
			throw new OOPInaccessibleMethod();
		} catch (InvocationTargetException e) {
			throw (OOPMultipleException) e.getTargetException();
		}
	}

	private void getMethod(InterfacesGraph.Node node, String methodName, Object[] args,
			HashSet<Pair<Class<?>, Method>> candidates) throws OOPMultipleException {
		if (!node.equals(graph.base)) {
			Method m = findMethod(node, methodName, args); // null if cant find
			if (m != null) {
				Class<?> interClass = getClassFromInter(node.inter, m);
				try {
					Method methodInClass = interClass.getDeclaredMethod(m.getName(), m.getParameterTypes());
					candidates.add(new Pair<Class<?>, Method>(interClass, methodInClass));
					return;
				} catch (NoSuchMethodException e) {
					throw new OOPBadClass(m);
				}
			}
		}
		for (Class<?> inter : graph.interfaces.get(node)) {
			InterfacesGraph.Node interNode = new InterfacesGraph.Node(inter, node);
			getMethod(interNode, methodName, args, candidates);
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

	private Method findMethod(Node node, String methodName, Object[] args) {
		if (node != null && methodName != null) {
			Class<?> interClass = node.inter;
			Method[] interMethods = interClass.getDeclaredMethods();
			for (Method m : interMethods) {
				OOPModifier mod = m.getAnnotation(OOPMethod.class).modifier();
				if (m.getName() == methodName && isTypesEqual(args, m.getParameterTypes())
						&& mod != OOPModifier.PRIVATE) {
					if (mod == OOPModifier.DEFAULT) {
						if (node.inter.getPackage() != graph.base.inter.getPackage()) {
							continue;
						}
					}
					return m;
				}
			}
		}
		return null;
	}

	/* TODO - DEAL WITH INHERITENCE */
	private boolean isTypesEqual(Object[] args, Class<?>[] parameterTypes) {
		if (args == null && (parameterTypes == null || parameterTypes.length == 0)) {
			return true;
		}
		if (args.length != parameterTypes.length) {
			return false;
		}
		for (int i = 0; i < args.length; i++) {
			if (!args[i].getClass().equals(parameterTypes[i])) {
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
