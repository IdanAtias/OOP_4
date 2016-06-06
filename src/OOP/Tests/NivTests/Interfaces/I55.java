package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I55 extends I54, I52 {
	@Override
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public String f1() throws OOPMultipleException;
	@Override
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public Integer f2() throws OOPMultipleException;
	@Override
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public Boolean f3() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public Character f7(Character input) throws OOPMultipleException;
}
