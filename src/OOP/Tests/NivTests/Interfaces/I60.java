package OOP.Tests.NivTests.Interfaces;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMethod;
import OOP.Solution.OOPModifier;

/**
 * Created by Daniel Lyubin on 6/6/2016.
 */
public interface I60 {
    @OOPMethod(modifier = OOPModifier.PUBLIC)
    void f() throws OOPMultipleException;
}
