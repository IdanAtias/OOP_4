package OOP.Tests.NivTests;

import static org.junit.Assert.*;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;

import OOP.Provided.OOPInaccessibleMethod;
import OOP.Provided.OOPMultipleClassGenerator;
import OOP.Provided.OOPMultipleException;
import OOP.Tests.NivTests.Interfaces.I42;

/*
 * A test for cases of inaccessible methods. Uses a single tree for all tests
 * @Test:
 * @PublicTest - a basic test that calls a few public functions and makes sure they work.
 * Also test an overridden method of each modifier other than PUBLIC that was made PUBLIC.
 * @PrivateTest - tests that calls to private methods always fail
 * @DefaultTest - tests that calls to default methods only work if the entire inheritance tree
 * is in the same file.
 * @ProtectedTest - tests that calls to protected methods always works, as according to the F.A.Q we
 * can assume a protected method won't be called from a different package (although they are here, just ignore that).
 * 
 * Uses interfaces I4x.
 * 
 * Uses Junit's ClassRule to create the OOPMultiple object once before the tests
 * start and remove it after every test finished running.
 * If ClassRule does not compile, try installing a newer version of Junit.
 * This test was written and tested with Junit 4.12
 */
public class InaccessibleMethodTest {
	public static OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();
	public static I42 obj;
	
	@ClassRule
	public static ExternalResource resouce = new ExternalResource() {
		@Override
		protected void before() throws Throwable{
			obj = (I42) generator.generateMultipleClass(I42.class);
		};
		
		@Override
		protected void after(){
			generator.removeSourceFile();
		};
	};
	
//	@Test
//	public void PublicTest(){
//		try{
//			obj.fPublic();
//			obj.fOverridenProtected();
//			obj.fOverridendefault();
//			obj.fOverridenPrivate();
//		}catch (OOPInaccessibleMethod e){
//			fail("The methods should be accessible here");
//		}catch (OOPMultipleException e){
//			fail("unexpected exception");
//		}	
//	}
	
	@Test
	public void PrivateTest(){
		Boolean caught = false;
		try{
			obj.fPrivate();
		}catch (OOPInaccessibleMethod e){
			caught = true;
		}catch (OOPMultipleException e){
			fail("unexpected exception");
		}
		assertTrue("didn't throw OOPInaccessibleMethod",caught);
	}
	@Test
	public void DefaultTest(){
		try{
			obj.fDefaultInSamePackage();
		}catch (OOPInaccessibleMethod e){
			fail("The methods should be accessible here");
		}catch (OOPMultipleException e){
			fail("unexpected exception");
		}
		Boolean caught = false;
		try{
			//I41 is in a different package, so this call should fail
			obj.fdefault();
		}catch (OOPInaccessibleMethod e){
			caught = true;
		}catch (OOPMultipleException e){
			fail("unexpected exception");
		}
		assertTrue("didn't throw OOPInaccessibleMethod",caught);
	}
	
	@Test
	public void ProtectedTest(){
		try{
			obj.fProtected();
		}catch (OOPInaccessibleMethod e){
			fail("The methods should be accessible here");
		}catch (OOPMultipleException e){
			fail("unexpected exception");
		}
	}
}
