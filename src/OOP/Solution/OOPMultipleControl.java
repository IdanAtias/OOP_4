package OOP.Solution;

import OOP.Provided.*;
import OOP.Solution.InterfacesGraph.Node;
import javafx.util.Pair;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;
import com.sun.org.glassfish.gmbal.ParameterNames;

public class OOPMultipleControl {

    //TODO: DO NOT CHANGE !!!!!!
    private Class<?> interfaceClass;
    private File sourceFile;
    private InterfacesGraph graph=null;
    

    //TODO: DO NOT CHANGE !!!!!!
    public OOPMultipleControl(Class<?> interfaceClass, File sourceFile) {
        this.interfaceClass = interfaceClass;
        this.sourceFile = sourceFile;
    }

    //TODO: fill in here :
    public void validateInheritanceTree() throws OOPMultipleException {
    	graph = new InterfacesGraph(interfaceClass);
    }

    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPMultipleException {
    	HashSet<Pair<Class<?>,Method>> candidates = new HashSet<Pair<Class<?>,Method>>();
    	getMethod(graph.base, methodName, args, candidates);
    	if (candidates.size()==0) throw new OOPInaccessibleMethod(); 
        if (candidates.size()>1) throw new OOPCoincidentalAmbiguity(candidates);
    	try {
    		return candidates.iterator().next().getValue().invoke(this, args);
    	} catch (Exception e){
    		throw new OOPInaccessibleMethod();
    	}
    	
    }

	private void getMethod(InterfacesGraph.Node node, String methodName, Object[] args, HashSet<Pair<Class<?>,Method>> candidates) {
		if (!node.equals(graph.base)){
			Method m = findMethod(node, methodName, args); //null if cant find
			if (m != null){
				Class<?> interClass = getClassFromInter(node.inter);
				candidates.add(new Pair<Class<?>, Method>(interClass, m));
				return;	
			}	
		}
		int method_count = 0;
		Method correctMethod = null;
		for (Class<?> inter: graph.interfaces.get(node)){
			InterfacesGraph.Node interNode = new InterfacesGraph.Node(inter, node);
			getMethod(interNode, methodName, args, candidates);
		}
	}

	private Method findMethod(Node node, String methodName, Object[] args) {
		if (node != null && methodName != null && args != null){
			Class<?> interClass = node.inter;
			Method[] interMethods = interClass.getDeclaredMethods();
			for (Method m: interMethods){
				if (m.getName() == methodName && isTypesEqual(args,m.getParameterTypes())){
					return m;
				}
			}
		}
		return null;
	}

	private boolean isTypesEqual(Object[] args, Class<?>[] parameterTypes) {
		if (args != null && parameterTypes != null){
			if (args.length != parameterTypes.length){
				return false;
			}
			for (int i=0; i < args.length; i++){
				//TODO - continue from here
			}
		}
		return false;
	}

	//TODO: add more of your code :
    public enum OOPModifier{
    	DEFAULT, PRIVATE, PROTECTED, PUBLIC
    }
    
    @Target(ElementType.METHOD)	// to be used just for methods.
    @Retention(RetentionPolicy.RUNTIME) //make it valid on runtime.
    public @interface OOPMethod {
    	OOPModifier modifier() default OOPModifier.DEFAULT;
    }
    


    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
