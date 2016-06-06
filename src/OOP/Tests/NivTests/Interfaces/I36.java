package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I36 {
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void f36() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.PRIVATE)
	public void f() throws OOPMultipleException;

}
