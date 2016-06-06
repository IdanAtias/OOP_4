package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I31 {
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void f31() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void f() throws OOPMultipleException;
}
