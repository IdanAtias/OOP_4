package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I53 extends I51{
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public Integer f4(Integer i1,Integer i2,Integer i3, Boolean i4, I51 somethingElse) throws OOPMultipleException;
}
