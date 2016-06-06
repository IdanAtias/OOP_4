package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;

public class C57 extends C56 implements I57 {

	@Override
	public void callTheWrongOneAndExceptionsWillBeThrown() throws OOPMultipleException {
		throw new NivsException();
	}

}
