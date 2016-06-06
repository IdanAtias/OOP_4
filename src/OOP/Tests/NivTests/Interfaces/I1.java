package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.*;

public interface I1{
	@OOPMethod(modifier=OOPModifier.PUBLIC)
	public Integer f() throws OOPMultipleException;
	@OOPMethod(modifier=OOPModifier.PRIVATE)
	public Integer f1() throws OOPMultipleException;
	@OOPMethod(modifier=OOPModifier.PUBLIC)
	public Boolean g() throws OOPMultipleException;
}
