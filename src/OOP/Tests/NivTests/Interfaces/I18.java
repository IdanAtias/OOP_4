package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;

public interface I18 extends I16, I17 {
	@OOPMethod()
	public Boolean f8() throws OOPMultipleException;
	
	@Override
	@OOPMethod()
	public Boolean f5() throws OOPMultipleException;
}
