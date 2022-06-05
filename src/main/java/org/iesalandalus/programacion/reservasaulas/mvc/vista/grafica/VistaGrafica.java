package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores.ControladorVPrincipal;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaGrafica extends Application implements IVista {
	
	private static IControlador controladorMVC;

	@Override
	public void setControlador(IControlador controlador) {
		controladorMVC=controlador;
	}

	@Override
	public void comenzar() {
		launch(this.getClass());
	}

	@Override
	public void salir() {
		controladorMVC.terminar();
	}

	// Creamos el escenario principal, cargando el FMXL, pasándole el controladorMVC al controlador correspondiente y actualizando las tablas de dicho
	// controlador de paso. Creamos un closeRequest setteamos la escena y la mostramos. Capturamos cualquier error con try/catch
	@Override
	public void start(Stage escenarioPrincipal){
		try {
			FXMLLoader cargadorVentanaPrincipal=new FXMLLoader(LocalizadorRecursos.class.getResource("/vistas/vPrincipal.fxml"));
			VBox raiz=cargadorVentanaPrincipal.load();
			ControladorVPrincipal cVentanaPrincipal=cargadorVentanaPrincipal.getController();
			cVentanaPrincipal.setControladorMVC(controladorMVC);
			cVentanaPrincipal.actualizaTablas();
			
			Scene escena=new Scene(raiz);
			escenarioPrincipal.setOnCloseRequest(e -> confirmarSalida(escenarioPrincipal, e));
			escenarioPrincipal.setTitle("Gestión reservas");
			escenarioPrincipal.setScene(escena);
			escenarioPrincipal.setResizable(false);
			escenarioPrincipal.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Método para confirmar la salida que muestra un diálogo de confirmación y si se acepta, corre el método salir y cierra la ventana. De lo contrario,
	// consume el evento.
	private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", escenarioPrincipal)) {
			salir();
			escenarioPrincipal.close();
		}
		else {
			e.consume();
		}
	}

}
