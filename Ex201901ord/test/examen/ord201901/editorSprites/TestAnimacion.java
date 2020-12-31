package examen.ord201901.editorSprites;

import static org.junit.Assert.assertEquals;

import javax.swing.UIManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAnimacion {

	VentanaEdicionSprites ven;

	@Before
	public void setUp() {
		try { // Cambiamos el look and feel (se tiene que hacer antes de crear la GUI
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		} // Si Nimbus no está disponible, se usa el l&f por defecto
		ven = new VentanaEdicionSprites();

		// Estas tres líneas inicializan la secuencia con tres gráficos de ejemplos
		// (sustituir los paths por los gráficos que se deseen)
		// Con los ficheros suministrados comprobar que están en la carpeta
		// correspondiente a las imágenes de las carpetas
		ven.getController()
				.anyadirSpriteASecuencia(new java.io.File("src/examen/ord201901/editorSprites/img/Attack__000.png"));
		ven.getController()
				.anyadirSpriteASecuencia(new java.io.File("src/examen/ord201901/editorSprites/img/Attack__001.png"));
		ven.getController()
				.anyadirSpriteASecuencia(new java.io.File("src/examen/ord201901/editorSprites/img/Attack__002.png"));
		ven.getController()
				.anyadirSpriteASecuencia(new java.io.File("src/examen/ord201901/editorSprites/img/Attack__003.png"));
		ven.getController()
				.anyadirSpriteASecuencia(new java.io.File("src/examen/ord201901/editorSprites/img/Attack__004.png"));
		ven.setVisible(true);
	}

	@After
	public void tearDown() {
		ven.dispose();
	}

	@Test
	public void testVelYAngulo() {
		int vel = 100;
		ven.slVelocidad.setValue(vel);
		ven.getController().sliderStateChanged(ven.slVelocidad, ven.tfVelocidad);
		int angulo = 25;
		ven.slAngulo.setValue(angulo);
		ven.getController().sliderStateChanged(ven.slAngulo, ven.tfAngulo);
		assertEquals(vel+"", ven.tfVelocidad.getText());
		assertEquals(angulo+"", ven.tfAngulo.getText());
	}
}
