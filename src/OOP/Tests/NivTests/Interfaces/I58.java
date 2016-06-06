package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I58 extends I57 {
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public Character f7() throws OOPMultipleException;
	@Override
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void callTheWrongOneAndExceptionsWillBeThrown() throws OOPMultipleException;

}
