package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I43 {
	@OOPMethod(modifier = OOPModifier.DEFAULT)
	public void fDefaultInSamePackage() throws OOPMultipleException;

}
