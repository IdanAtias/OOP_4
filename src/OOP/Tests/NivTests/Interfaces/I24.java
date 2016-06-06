package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I24 {
	@OOPMethod(modifier = OOPModifier.PRIVATE)
	public Boolean f1() throws OOPMultipleException;
}
