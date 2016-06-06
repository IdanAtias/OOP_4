package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I54 extends I53 {
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public String f5(I51 input) throws OOPMultipleException;

}
