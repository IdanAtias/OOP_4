package OOP.Tests.NivTests;

import static org.junit.Assert.*;

import org.junit.Test;

import OOP.Provided.OOPCoincidentalAmbiguity;
import OOP.Provided.OOPInaccessibleMethod;
import OOP.Provided.OOPMultipleClassGenerator;
import OOP.Provided.OOPMultipleException;
import OOP.Tests.NivTests.Interfaces.C51;
import OOP.Tests.NivTests.Interfaces.C54;
import OOP.Tests.NivTests.Interfaces.C55;
import OOP.Tests.NivTests.Interfaces.C56;
import OOP.Tests.NivTests.Interfaces.C58;
import OOP.Tests.NivTests.Interfaces.I50;
import OOP.Tests.NivTests.Interfaces.NivsException;

/*
 * A test that creates a somewhat complex, legal inheritance tree with a lot of
 * methods with different arguments and return values, and ensure they call
 * the right method.
 * 
 * Uses interfaces I5x, with I50 being the Interface for the OOPMultiple class.
 */
public class CallingFunctionsTest {
	
	@Test
	public void MainTest(){
		OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();
		try{
			I50 obj = (I50)generator.generateMultipleClass(I50.class);
			assertEquals(obj.f1(),new C55().f1());
			assertEquals(obj.f2(),new C55().f2());
			assertEquals(obj.f3(),new C55().f3());
			try{
				assertEquals(obj.f5(new C51()),new C54().f5(new C51()));
			}catch (OOPInaccessibleMethod e){
				fail("You probably don't check parameters agreeing correctly. You should use isAssingableFrom");
			}
			assertEquals(obj.f4(new Integer(1), new Integer(2),new Integer(3), new Boolean(true), new C51()),new Integer(1));
			assertEquals(obj.f6(9),new C56().f6(9));
			try{
				obj.callTheWrongOneAndExceptionsWillBeThrown();
			}catch(NivsException e){
				fail("You called C57's function rather than C58's function.");
			}
			Boolean caught = false;
			try{
				/*
				 * Apparently c++ doesn't allow function overloading in multiple inheritance, and
				 * treats it as ambiguity as well. So this test was updated to test that.
				 * This probably fits CoincidentalAmbiguityTest better but I just don't care
				 * any more.
				 */
				assertEquals(obj.f7(),new C58().f7());
				assertEquals(obj.f7('t'),new Character('t'));
			}catch(OOPCoincidentalAmbiguity | OOPInaccessibleMethod e){
				caught = true;
			}
			assertTrue("This should throw OOPCoincidentalAmbiguity, idk why",caught);
		}catch(OOPMultipleException e){
			fail("No exceptions should be thrown during this test");
		}finally{
			generator.removeSourceFile();
		}
	}
}
