package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

public interface I40 {
	@OOPMethod(modifier = OOPModifier.PRIVATE)
	public void fPrivate() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.PROTECTED)
	public void fProtected() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.DEFAULT)
	public void fdefault() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.PUBLIC)
	public void fPublic() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.PRIVATE)
	public void fOverridenPrivate() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.PROTECTED)
	public void fOverridenProtected() throws OOPMultipleException;
	@OOPMethod(modifier = OOPModifier.DEFAULT)
	public void fOverridendefault() throws OOPMultipleException;

}
