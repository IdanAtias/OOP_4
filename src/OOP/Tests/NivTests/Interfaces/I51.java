package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I51 {
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public String f1() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public Integer f2() throws OOPMultipleException;

}
