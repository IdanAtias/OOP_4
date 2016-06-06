package OOP.Tests.NivTests.Interfaces;

public class C2 extends C1 implements I2{
	//overrides C1's g() and should be called
	@Override
	public Boolean g(){
		return true;
	}
	//Tests parameter passing by invoke
	@Override
	public Integer h(Integer num){
		return num;
	}
}
