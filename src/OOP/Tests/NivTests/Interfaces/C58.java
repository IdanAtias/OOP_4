package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public class C58 extends C57 implements I58 {

	@Override
	public Character f7() throws OOPMultipleException {
		return 'C';
	}
	
	@Override
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void callTheWrongOneAndExceptionsWillBeThrown() throws OOPMultipleException{
		
	}

}
