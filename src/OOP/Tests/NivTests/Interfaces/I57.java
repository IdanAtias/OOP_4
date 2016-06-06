package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I57 extends I56 {
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void callTheWrongOneAndExceptionsWillBeThrown() throws OOPMultipleException;

}
