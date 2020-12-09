package examen.ext201702;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/** Clase de test unitario de la clase FarmaciaGuardia
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class FarmaciaGuardiaTest {
	private MapaFarmacias mapaTest;
	private FarmaciaGuardia f1;
	private FarmaciaGuardia f2;
	private FarmaciaGuardia f3;
	private FarmaciaGuardia f4;
	private FarmaciaGuardia f5;
	
	@Before
	public void setUp() {
		mapaTest = new MapaFarmacias();
		f1 = new FarmaciaGuardia( "Lekeitio", "00:00-23:00", "Avda. MecawenDios 12 | 966666666" );
		f2 = new FarmaciaGuardia( "Bilbao", "00:00-22:00", "Calle sePorFavor 00 | 900000000" );
		f3 = new FarmaciaGuardia( "Bilbao", "00:00-20:00", "Calle sePorFavor 03 | 944139000" );
		f4 = new FarmaciaGuardia( "Bilbao", "00:00-19:00", "Avda. EnElTejado 23 | 943539000" );
		f5 = new FarmaciaGuardia( "Bilbao", "00:00-22:00", "Calle sePorFavor 13 | 944139450" );
		mapaTest.anyadir(f1,f2,f3,f4,f5);
	}

	@Test
	public void testConstructor3Strings() {
		FarmaciaGuardia f = new FarmaciaGuardia( "Bilbao", "00:00-22:00", "(Deusto) Avda. Universidades 24 | 944139000" );
		FarmaciaGuardia f1 = new FarmaciaGuardia( "Bilbao", "00:01-22:00", "Test" );
		FarmaciaGuardia f2 = new FarmaciaGuardia( "Bilbao", "23:59-22:00", "Test" );
		assertTrue( f.getHoraDesde() < f1.getHoraDesde() );
		assertTrue( f.getHoraDesde() < f2.getHoraDesde() );
		assertEquals( f.getLocalidad(), "Bilbao" );
		assertEquals( f.getZona(), "Deusto" );
		assertEquals( f.getDireccion(), "Avda. Universidades 24" );
		assertEquals( f.getTelefono(), "944139000" );
		FarmaciaGuardia f3 = new FarmaciaGuardia( "Bilbao", "00:00-22:00", "Deusto Avda. Universidades 24 944139000" );
		assertEquals( f3.getZona(), "" );
		assertEquals( f3.getTelefono(), "" );
	}
	
	@Test
	public void testCapicua() {
		FarmaciaGuardia f1 = new FarmaciaGuardia( "Bilbao", "09:00-22:00", "(Deusto) Avda. Universidades 24 | 94 4139000" );
		FarmaciaGuardia f2 = new FarmaciaGuardia( "Bilbao", "09:00-22:00", "(Deusto) Avda. Universidades 24 | 94 413 14 49" );
		FarmaciaGuardia f3 = new FarmaciaGuardia( "Bilbao", "09:00-22:00", "(Deusto) Avda. Universidades 24 | 944 132 439" );
		assertTrue( f1.calcCapicua() > 5 );
		assertTrue( f2.calcCapicua() == 0 );
		assertTrue( f3.calcCapicua() == 2 );
	}
	@Test
	public void testMapaOrdenado1() {
		MapaFarmaciasOrdenadas mapaOrd = new MapaFarmaciasOrdenadas(mapaTest);
		
		assertEquals("Bilbao", mapaOrd.getMapaOrd().ceilingKey("Bilbao"));
		assertEquals("Lekeitio", mapaOrd.getMapaOrd().floorKey("Lekeitio"));
	}
	@Test
	public void testMapaOrdenado2() {
		MapaFarmaciasOrdenadas mapaOrd = new MapaFarmaciasOrdenadas(mapaTest);
		assertEquals(f2,mapaOrd.getMapaOrd().get("Bilbao").lower(f5));
		//mas comprobaciones faltan
	}



}
