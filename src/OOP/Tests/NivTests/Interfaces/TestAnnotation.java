package OOP.Tests.NivTests.Interfaces;

import OOP.Solution.*;

/*
 * Simply checks the annotation compiles properly. Will probably be removed very soon, as other tests will do that anyways
 * This test is passed simply by compiling this file, and it will not be used in any other test
 */

public interface TestAnnotation {
	@OOPMethod(modifier = OOPModifier.PRIVATE)
	public void f();

	@OOPMethod(modifier = OOPModifier.DEFAULT)
	public int g();
	
	@OOPMethod(modifier = OOPModifier.PROTECTED)
	public int h();
	
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public int i();
	
	@OOPMethod()
	public int j();
}
