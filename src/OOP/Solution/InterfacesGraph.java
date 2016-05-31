package OOP.Solution;

import java.util.HashMap;

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

