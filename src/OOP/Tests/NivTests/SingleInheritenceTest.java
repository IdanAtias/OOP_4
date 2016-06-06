package OOP.Tests.NivTests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.ClassRule;

import OOP.Provided.OOPInaccessibleMethod;
import OOP.Provided.OOPMultipleClassGenerator;
import OOP.Provided.OOPMultipleException;
import OOP.Tests.NivTests.Interfaces.C1;
import OOP.Tests.NivTests.Interfaces.I3;

/*
 * A test using a single inheritance with OOPMultiple. Checks that a single inheritance still
 * works, including the modifiers defines using OOPMethod.
 * 
 * Uses Interfaces Ix.
 * 
 * Uses Junit's ClassRule to create the OOPMultiple object once before the tests
 * start and remove it after every test finished running.
 * If ClassRule does not compile, try installing a newer version of Junit.
 * This test was written and tested with Junit 4.12
 */

public class SingleInheritenceTest {
	
	public static OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();
	public static I3 obj;
	
	@ClassRule
	public static ExternalResource resouce = new ExternalResource() {
		@Override
		protected void before() throws Throwable{
			obj = (I3) generator.generateMultipleClass(I3.class);
		};
		
		@Override
		protected void after(){
			generator.removeSourceFile();
		};
	};
	
	@Test
	public void workingFunctionsTest(){
		try{
			//f() tests basic inheritance of a public method
			assertTrue("f() returned wrong value",obj.f().equals(new C1().f()));
			//g() tests overriding of a public method
			assertTrue("g() returned wrong value. Overriding failed",obj.g());
			//h() tests parameter passing
			assertTrue("h() returned a wrong value.",obj.h(5).equals(5));
		}catch (OOPMultipleException e){
			fail("Unexpected exception");
		}
	}
	
	@Test
	public void inaccessibleFunctionTest(){
		Boolean caught = false;
		//f1() tests inaccessible method of Private level
		try{
			assertTrue("f1() should be inaccessible but returned a wrong value.",obj.f1().equals(new C1().f1()));
		}catch (OOPInaccessibleMethod e){
			caught = true;
		}catch (OOPMultipleException e){
			fail("f1() should be inaccessible but threw a diffrent exception.");
		}
		assertTrue("f1() should be inaccessible",caught);
	}
}
