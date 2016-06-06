package OOP.Tests.NivTests.protectedInterfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;
import OOP.Tests.NivTests.Interfaces.I40;

public interface I41 extends I40 {
	@Override
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void fOverridenPrivate() throws OOPMultipleException;
	@Override
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void fOverridenProtected() throws OOPMultipleException;
	@Override
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void fOverridendefault() throws OOPMultipleException;

}
