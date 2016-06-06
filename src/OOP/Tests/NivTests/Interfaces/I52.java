package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I52 extends I51 {
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public Boolean f3() throws OOPMultipleException;

}
