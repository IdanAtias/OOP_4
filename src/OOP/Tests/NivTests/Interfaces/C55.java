package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;

public class C55 implements I55 {

	@Override
	public String f5(I51 input) throws OOPMultipleException {
		return ((I54)this).f5(input);
	}

	@Override
	public Integer f4(Integer i1, Integer i2, Integer i3, Boolean i4, I51 somethingElse)
			throws OOPMultipleException {
		return ((I54)this).f4(i1, i2, i3, i4, somethingElse);
	}

	@Override
	public Boolean f3() throws OOPMultipleException {
		return true;
	}

	@Override
	public String f1() throws OOPMultipleException {
		return "C55";
	}

	@Override
	public Integer f2() throws OOPMultipleException {
		return 55;
	}

	@Override
	public Character f7(Character input) throws OOPMultipleException {
		return input;
	}

}
