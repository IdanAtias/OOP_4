package OOP.Tests.NivTests;

import org.junit.Test;

import OOP.Provided.OOPCoincidentalAmbiguity;
import OOP.Provided.OOPInaccessibleMethod;
import OOP.Provided.OOPMultipleClassGenerator;
import OOP.Provided.OOPMultipleException;
import OOP.Tests.NivTests.Interfaces.I33;
import OOP.Tests.NivTests.Interfaces.I35;
import OOP.Tests.NivTests.Interfaces.I37;

import static org.junit.Assert.*;

// TODO check that e.getMessage() returns a legal message when an exception is thrown.
/*
 * A test for cases of inherent ambiguity cases.
 * @Tests:
 * @NotCallingAmbiguityTest - creates a tree with inherent coincidental and doesn't call the ambiguous method.
 * No exceptions should be thrown at any time during this function, since all invocations are not ambiguous.
 * @BasicCoincidentalAmbiguityTest - creates the same tree from NotCallingAmbiguity and calls the ambiguous method
 * this time. Should only fail when the method is called.
 * @OverridenCoincidentalAmbiguityTest - creates a tree with a coincidental ambiguity that is than overridden, so
 * that the call isn't ambiguous and should succeed.
 * @OneMethodIsPrivate - Since one method is private and there is an illegal
 * 
 * Uses interfaces I3x.
 * 
 * According to the TA, in case of coincidental ambiguity the OOPMultiple class should be created, and throw
 * an exception when an ambiguous method is invoked.
 * 
 * This test was written and tested with Junit 4.12
 */

public class CoincidentalAmbiguityTest {
	
	public static OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();
	
	@Test
	public void NotCallingAmbiguityTest(){
		try{
			I33 obj = (I33)generator.generateMultipleClass(I33.class);
			obj.f31();
			obj.f32();
		}catch(OOPCoincidentalAmbiguity e){
			fail("There is no ambiguous method invocation");
		}catch(OOPMultipleException e){
			fail("unxepceted exception");
		}finally{
			generator.removeSourceFile();
		}
	}
	
	@Test
	public void BasicCoincidentalAmbiguityTest(){
		try{
			I33 obj = (I33)generator.generateMultipleClass(I33.class);
			Boolean caught = false;
			try{
				obj.f();
			}catch(OOPCoincidentalAmbiguity e){
				caught = true;
			}catch(OOPMultipleException e){
				fail("wrong exception when calling f()");
			}
			assertTrue("Didn't throw exception when calling f()",caught);
		}catch(OOPMultipleException e){
			fail("unxepceted exception");
		}finally{
			generator.removeSourceFile();
		}
	}
	
	@Test
	public void OverridenCoincidentalAmbiguityTest(){
		try{
			I35 obj = (I35)generator.generateMultipleClass(I35.class);
			obj.f31();
			obj.f32();
			obj.f34();
			obj.f();
		}catch(OOPCoincidentalAmbiguity e){
			fail("There is no ambiguous method invocation");
		}catch(OOPMultipleException e){
			fail("unxepceted exception");
		}finally{
			generator.removeSourceFile();
		}
	}
	
	@Test
	public void OneMethodIsPrivate(){
		Boolean caught = false;
		try{
			I37 obj = (I37)generator.generateMultipleClass(I37.class);
			obj.f(); //Should call I31.f() since I36.f() is private
		}catch(OOPCoincidentalAmbiguity e){
			caught = true;
		}catch(OOPInaccessibleMethod e){
			caught = true;
		}catch(OOPMultipleException e){
			fail("unxepceted exception");
		}finally{
			generator.removeSourceFile();
			assertTrue("according to the FAQ some exception should be thrown here",caught);
		}
	}
}
