package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.*;

public interface I2 extends I1 {
	@OOPMethod(modifier=OOPModifier.PUBLIC)
	public Integer h(Integer num) throws OOPMultipleException;
	
	@Override
	@OOPMethod(modifier=OOPModifier.PUBLIC)
	public Boolean g()  throws OOPMultipleException;
}
