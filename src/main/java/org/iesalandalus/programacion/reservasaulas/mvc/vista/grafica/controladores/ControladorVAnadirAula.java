package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorVAnadirAula {

	@FXML private TextField inputNombreAula;
	@FXML private TextField inputPuestosAula;
	private ControladorVPrincipal cVPrincipal;
	private IControlador controladorMVC;

	public void setControladorMVC(IControlador controlador) {
		controladorMVC = controlador;
	}

	//El controlador principal usa este método para enviarse a sí mismo y así poder actualizar sus tablas y demás
	public void setControladorVPrincipal(ControladorVPrincipal cVPrincipal) {
		this.cVPrincipal = cVPrincipal;
	}

	//creamos un addlistener que nos comprabará los valores del campo puestos
	public void initialize() {
		inputPuestosAula.textProperty().addListener((ob, ov, nv) -> compruebaPuestos(ov, nv));
	}

	//La acción para añadir un aulea, toma los valores de getAula, llama al controlador para insertarla, actualiza la tabla de la ventana principal,
	//y obtiene el Stage del botón aceptar para pasarlo como parámetro al diálogo de información, que nos comunica que todo ha sido correcto y cierra.
	//Si hay cualquier error, muestra el mensaje en el diálogo correspondiente y limpia los texfields para intentarlo de nuevo.
	@FXML
	void acAnadirAula(ActionEvent event) {
		Aula aula = null;
		try {
			aula = crearAula();
			controladorMVC.insertarAula(aula);
			cVPrincipal.actualizaTablas();
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Aula", "Aula añadida correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Error", e.getMessage());
			inputNombreAula.clear();
			inputPuestosAula.clear();
		}
	}

	//Botón cancelar, toma el Stage de éste y la cierra.
	@FXML
	void acCancelar(ActionEvent event) {
		((Stage) btCancelar.getScene().getWindow()).close();
	}

	//Método que toma los valores de la ventana y crea un Aula con ellos. Si no puede convertir el String de puestos a un int, lo deja en 0
	//para que nos salte el error más conveniente.
	private Aula crearAula() {
		Aula aula = null;
		int puestos = 0;
			String nombre = inputNombreAula.getText();
			try {
				puestos = Integer.valueOf(inputPuestosAula.getText());
			} catch (Exception e){}
			aula = new Aula(nombre, puestos);
		return new Aula(aula);
	}

	//comprueba si el nuevo valor de puestos coincide con el match. Si es así, lo pone en verde y si no, lo pone en rojo.
	private void compruebaPuestos(String oldValue, String newValue) {
		if (newValue.matches("[0-9]*")) {
			inputPuestosAula.setStyle("-fx-border-color: green");
		} else{
			inputPuestosAula.setText(oldValue);
			inputPuestosAula.setStyle("-fx-border-color: red");
		}
	}

	@FXML private Button btAceptar;
	@FXML private Button btCancelar;

	/*
	 * @FXML private TitledPane vAnadirAula;
	 */
}
