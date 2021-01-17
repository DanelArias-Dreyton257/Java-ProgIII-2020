package examen.parc202012;

// ATENCIÓN!  Enlaza JUnit 4 para poder compilar y ejecutar este test
import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.*;

public class TestIntegracion {

	// T2
	
	@Before
	public void setUp() {
		ProcesoCSVMeet.preparaVentana();
		ProcesoCSVMeet.cargaCSVMeet( "src/examen/parc202012/meet2020-10-15.csv" );
		ProcesoCSVMeet.cargaCSVMeet( "src/examen/parc202012/meet2020-10-22.csv" );
		ProcesoCSVMeet.cargaCSVMeet( "src/examen/parc202012/meet2020-10-29.csv" );
		ProcesoCSVMeet.cargaCSVMeet( "src/examen/parc202012/meet2020-11-05.csv" );
		
	}
	
	@Test
	public void test() {
		assertTrue(ProcesoCSVMeet.actualizaTablaIntegracion());
		TreeMap<String, UsuarioMeet> mapa = ProcesoCSVMeet.mUsuarios;
		//Compruebo el primero y el ultimo
		assertEquals(DatosDePrueba.listaPrueba2.get(0), mapa.firstEntry().getValue());
		assertEquals(DatosDePrueba.listaPrueba2.get(DatosDePrueba.listaPrueba2.size()-1), mapa.lastEntry().getValue());
		
		//Fechas (solo compruebo un par)
		assertTrue(ProcesoCSVMeet.sFechas.contains("15/10/2020"));
		assertTrue(ProcesoCSVMeet.sFechas.contains("22/10/2020"));
	}

}
