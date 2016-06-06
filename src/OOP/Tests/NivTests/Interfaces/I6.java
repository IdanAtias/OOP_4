package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I6 extends I2 {
	@Override
	@OOPMethod(modifier=OOPModifier.PROTECTED)
	public Integer h(Integer num) throws OOPMultipleException;

}
