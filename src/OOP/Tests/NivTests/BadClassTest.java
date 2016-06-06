package OOP.Tests.NivTests;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.Test;

import OOP.Provided.OOPBadClass;
import OOP.Provided.OOPMultipleClassGenerator;
import OOP.Provided.OOPMultipleException;
import OOP.Tests.NivTests.Interfaces.I4;
import OOP.Tests.NivTests.Interfaces.I5;
import OOP.Tests.NivTests.Interfaces.I6;
import OOP.Tests.NivTests.Interfaces.I8;

/*
 * A test for various cases where the annotations are wrong. Names of functions are self explanatory.
 * Uses Interfaces Ix (some shared with SingleInheritenceTest).
 * 
 * This test was written and tested with Junit 4.12
 */

public class BadClassTest {
	
	public static OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();
	
	@Test
	public void noAnnotationTest(){
		Boolean caught = false;
		String expected = null;
		try{
			Method badMethod = I4.class.getMethod("t", null);
			expected = new OOPBadClass(badMethod).getMessage();
		}catch (Throwable e){
			e.printStackTrace();
			fail("Unexpected excpetion in reflection. This would probably never happen.");
		}
		try{
			generator.generateMultipleClass(I5.class);
		}catch (OOPBadClass e){
			caught = true;
			assertTrue("Wrong Message",expected.equals(e.getMessage()));
		}catch (OOPMultipleException e){
			caught = true;
			fail("Should have thrown OOPBadClass, threw something else");
		}finally{
			generator.removeSourceFile();
			assertTrue("Should have thrown OOPBadClass, threw nothing",caught);
		}
	}
	
	@Test
	public void weakerAccessAnnotationTest(){
		Boolean caught = false;
		String expected = null;
		try{
			Method badMethod = I6.class.getMethod("h", Integer.class);
			expected = new OOPBadClass(badMethod).getMessage();
		}catch (Throwable e){
			e.printStackTrace();
			fail("Unexpected excpetion in reflection. This would probably never happen.");
		}
		try{
			generator.generateMultipleClass(I6.class);
		}catch (OOPBadClass e){
			caught = true;
			assertTrue("Wrong Message",expected.equals(e.getMessage()));
		}catch (OOPMultipleException e){
			caught = true;
			fail("Should have thrown OOPBadClass, threw something else");
		}finally{
			generator.removeSourceFile();
			assertTrue("Should have thrown OOPBadClass, threw nothing",caught);
		}
	}
	
	@Test
	public void strongerAccessAnnotationTest(){
		try{
			generator.generateMultipleClass(I8.class);
		}catch (OOPMultipleException e){
			fail("The inheritance tree is legal here, should compile successfully");
		}
	}
}
