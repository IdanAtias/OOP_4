package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I34 extends I31, I32 {
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void f34() throws OOPMultipleException;
	@Override
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void f() throws OOPMultipleException;

}
