package OOP.Tests.NivTests;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Method;

import OOP.Provided.OOPInherentAmbiguity;
import OOP.Provided.OOPMultipleClassGenerator;
import OOP.Provided.OOPMultipleException;
import OOP.Tests.NivTests.Interfaces.I10;
import OOP.Tests.NivTests.Interfaces.I11;
import OOP.Tests.NivTests.Interfaces.I14;
import OOP.Tests.NivTests.Interfaces.I20;
import OOP.Tests.NivTests.Interfaces.I23;
import OOP.Tests.NivTests.Interfaces.I24;
import OOP.Tests.NivTests.Interfaces.I27;

/*
 * A test for cases of inherent ambiguity cases.
 * @Test:
 * @DiamondInherentAmbiguityTest tests a basic case of 4 interfaces causing inherent ambiguity.
 * @NoInherentAmbiguityTest tests creates a diamond inheritance where the ambiguous function is overridden
 * right away resulting in no inherent ambiguity.
 * @SubTreeInherentAmbiguityTest is similar to NoInherentAmbiguityTest, however here one of the sub-trees has an
 * inherent ambiguity that is later overridden. It uses the same tree from DiamondInherentAmbiguityTest
 * and adds another interface at the bottom that overrides f1(), resulting in no ambiguity for I22
 * but I14 still has an inherent ambiguity
 * @CompexInherentAmbiguityTest creates a more complex graph with multiple inheritance paths.
 * Uses Interfaces I1x and I2x.
 * @PrivateAmbiguityTest creates a tree where the ambiguous method is private.
 * TODO make test's results fit the clarification from Oren when he publishes it 
 * 
 * According to the TA, in case of inherent ambiguity the OOPMultiple class should no be created.
 * 
 * This test was written and tested with Junit 4.12
 */

public class InherentAmbiguityTest {
	public static OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();
	
	@Test
	public void DiamondInherentAmbiguityTest(){
		Boolean caught = false;
		String expected = null;
		try{
			Method badMethod = I11.class.getMethod("f1", null);
			expected = new OOPInherentAmbiguity(I14.class,I11.class,badMethod).getMessage();
		}catch (Throwable e){
			e.printStackTrace();
			fail("Unexpected excpetion in reflection. This would probably never happen.");
		}
		try{
			generator.generateMultipleClass(I14.class);
		}catch (OOPInherentAmbiguity e){
			caught = true;
			assertTrue("Wrong Message",expected.equals(e.getMessage()));
		}catch (OOPMultipleException e){
			caught = true;
			fail("threw wrong exception");
		}finally{			
			generator.removeSourceFile();
			assertTrue("Didn't throw OOPInherentAmbiguity while validating the inheritance tree",caught);
		}
	}
	
	@Test
	public void NoInherentAmbiguityTest(){
		try{
			generator.generateMultipleClass(I20.class);
		}catch(OOPMultipleException e){
			fail("The inheratence tree is valid here and so it should work properly");
		}finally{
			generator.removeSourceFile();
		}
	}
	
	@Test
	public void SubTreeInherentAmbiguityTest(){
		Boolean caught = false;
		String expected = null;
		try{
			Method badMethod = I11.class.getMethod("f1", null);
			expected = new OOPInherentAmbiguity(I23.class,I11.class,badMethod).getMessage();
		}catch (Throwable e){
			e.printStackTrace();
			fail("Unexpected excpetion in reflection. This would probably never happen.");
		}
		try{
			generator.generateMultipleClass(I23.class);
		}catch (OOPInherentAmbiguity e){
			caught = true;
			assertTrue("Wrong Message",expected.equals(e.getMessage()));
		}catch (OOPMultipleException e){
			caught = true;
			fail("threw wrong exception");
		}finally{
			generator.removeSourceFile();
			assertTrue("Didn't throw OOPInherentAmbiguity while validating the inheritance tree",caught);
		}
	}
	
	@Test
	public void CompexInherentAmbiguityTest(){
		Boolean caught = false;
		String expected = null;
		try{
			Method badMethod = I11.class.getMethod("f1", null);
			expected = new OOPInherentAmbiguity(I10.class,I11.class,badMethod).getMessage();
		}catch (Throwable e){
			e.printStackTrace();
			fail("Unexpected excpetion in reflection. This would probably never happen.");
		}
		try{
			generator.generateMultipleClass(I10.class);
		}catch (OOPInherentAmbiguity e){
			caught = true;
			assertTrue("Wrong Message",expected.equals(e.getMessage()));
		}catch (OOPMultipleException e){
			caught = true;
			fail("threw wrong exception");
		}finally{
			generator.removeSourceFile();
			assertTrue("Didn't throw OOPInherentAmbiguity while validating the inheritance tree",caught);
		}
	}
	
	@Test
	public void PrivateAmbiguityTest(){
		Boolean caught = false;
		String expected = null;
		try{
			Method badMethod = I24.class.getMethod("f1", null);
			expected = new OOPInherentAmbiguity(I27.class,I24.class,badMethod).getMessage();
		}catch (Throwable e){
			e.printStackTrace();
			fail("Unexpected excpetion in reflection. This would probably never happen.");
		}
		try{
			generator.generateMultipleClass(I27.class);
		}catch (OOPInherentAmbiguity e){
			caught = true;
			assertTrue("Wrong Message",expected.equals(e.getMessage()));
		}catch (OOPMultipleException e){
			fail("threw wrong exception");
		}finally{
			generator.removeSourceFile();
			assertTrue("Didn't throw OOPInherentAmbiguity while validating the inheritance tree",caught);
		}
	}
}
