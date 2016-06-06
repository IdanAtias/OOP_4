package OOP.Tests.NivTests.Interfaces;

public class C1 implements I1{
	//The interface's modifier is private so this should not be called for c3
	@Override
	public Integer f(){
		return 7;
	}
	@Override
	public Integer f1(){
		return f();
	}
	@Override
	public Boolean g(){
		return false;
	}
}
