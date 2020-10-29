package ud.prog3.pr01;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.*;

public class ListaDeReproduccionTest {
	private ListaDeReproduccion lr1;
	private ListaDeReproduccion lr2;
	private final File FIC_TEST1 = new File("test/res/video1.mp4");
	private final File FIC_TEST2 = new File("test/res/video2.mp4");
	private final File FIC_TEST3 = new File("test/res/video3.mp4");
	private final File FIC_TEST4 = new File("test/res/video4.mp4");

	@Before
	public void setUp() throws Exception {
		lr1 = new ListaDeReproduccion();
		lr2 = new ListaDeReproduccion();
		lr2.add(FIC_TEST1, FIC_TEST2, FIC_TEST3, FIC_TEST4);
	}

	@After
	public void tearDown() {
		lr2.clear();
	}

	// Chequeo de error por getFic(índice) por encima de final
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_Exc1() {
		lr1.getFic(0); // Debe dar error porque aún no existe la posición 0
	}

	// Chequeo de error por get(índice) por debajo de 0
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_Exc2() {
		lr2.getFic(-1); // Debe dar error porque aún no existe la posición -1
	}

	// Chequeo de funcionamiento correcto de get(índice)
	@Test
	public void testGet() {
		assertEquals(FIC_TEST1, lr2.getFic(0)); // El único dato es el fic-test1
	}

	// Chequeo de funcionamiento correcto de intercambia(pos1,pos2) cuando las
	// posiciones son correctas
	@Test
	public void testIntercambia() {
		lr2.intercambia(0, 3);
		assertEquals(FIC_TEST4, lr2.getFic(0)); // se ha cambiado y fic4 esta en pos 0
	}

	// Chequeo de funcionamiento correcto de intercambia(pos1,pos2) cuando las
	// posiciones no son correctas
	@Test
	public void testIntercambia2() {
		lr2.intercambia(0, 66);
		assertEquals(FIC_TEST2, lr2.getFic(1)); // No se ha cambiado y fic2 esta en pos 1
	}

	// Chequeo de funcionamiento correcto de add(File)
	@Test
	public void testAnyadir() {
		lr1.add(FIC_TEST1);
		assertEquals(FIC_TEST1, lr1.getFic(0));
	}

	// Chequeo de funcionamiento correcto de removeFic(indice) cuando el indice es
	// correcto
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemove() {
		lr2.removeFic(3);
		lr2.getFic(3);
	}

	// Chequeo de funcionamiento correcto de removeFic(indice) cuando indice no es
	// correcto
	@Test
	public void testRemove2() {
		lr2.removeFic(8475);
		assertEquals(FIC_TEST4, lr2.getFic(3));
	}

	// Chequeo de funcionamiento correcto de size()
	@Test
	public void testSize() {
		assertEquals(4, lr2.size());
	}

	@Test
	public void addCarpeta() {
		String carpetaTest = "test/res/";
		String filtroTest = "*Pentatonix*.mp4";

		ListaDeReproduccion lr = new ListaDeReproduccion();
		lr.add(carpetaTest, filtroTest);
		fail("Método sin acabar");
	}

}
