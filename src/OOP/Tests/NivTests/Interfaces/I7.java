package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I7 extends I1 {
	@Override
	@OOPMethod(modifier=OOPModifier.DEFAULT)
	public Integer f1() throws OOPMultipleException;

}
