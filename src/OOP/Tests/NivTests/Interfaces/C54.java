package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;

public class C54 extends C53 implements I54 {

	@Override
	public String f5(I51 input) throws OOPMultipleException {
		return input.getClass().getName();
	}

}
