package astronomy;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import astronomy.SpectralType.SpectralClass;

public class SpectralTypeTest {
	SpectralType st1;
	SpectralType st2;
	SpectralType st3;
	
	@Before
	public void setUp() {
		
	}

	@After
	public void tearDown() {
	}
	
	@Test
	public void toStringTest() {
		st1 =  new SpectralType("O9");
		st2 =  new SpectralType("B2");
		st3 =  new SpectralType("Z65");
		
		assertEquals("O9", st1.toString());
		assertEquals("B2", st2.toString());
		assertEquals("OTHER", st3.toString());
	}
	@Test
	public void constructorTest() {
		st1 =  new SpectralType("O9");
		st2 =  new SpectralType("B2");
		st3 =  new SpectralType("Z65");
		
		assertEquals(SpectralClass.O, st1.getSpectralClass());
		assertEquals(9, st1.getSpectralNumber());
		
		assertEquals(SpectralClass.B, st2.getSpectralClass());
		assertEquals(2, st2.getSpectralNumber());
		
		assertEquals(SpectralClass.OTHER, st3.getSpectralClass());
		assertEquals(-1, st3.getSpectralNumber());
		
	}
	@Test (expected= NullPointerException.class)
	public void constructorTest2() {
		st1 =  new SpectralType(null);
	}
	
}
