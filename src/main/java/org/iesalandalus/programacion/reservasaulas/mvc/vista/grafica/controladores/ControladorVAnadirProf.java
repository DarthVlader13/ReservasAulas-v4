package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

public class ControladorVAnadirProf {
	
    @FXML private TextField inputNombreProf;
    @FXML private TextField inputMailProf;
    @FXML private TextField inputTelfProf;
	ControladorVPrincipal cVPrincipal=null;
	private IControlador controladorMVC;
	
	public void setControladorVPrincipal(ControladorVPrincipal controladorVPrincipal) {
		cVPrincipal=controladorVPrincipal;
	}
	
	public void setControladorMVC(IControlador controlador) {
		controladorMVC=controlador;
	}

	public void initialize() {
		inputNombreProf.textProperty().addListener((ob, ov, nv) -> compruebaNombre(ov, nv));
		inputMailProf.textProperty().addListener((ob, ov, nv) -> compruebaMail(ov, nv));
		inputTelfProf.textProperty().addListener((ob, ov, nv) -> compruebaTelf(ov, nv));
	}

    @FXML
    void acAnadirProfesor(ActionEvent event) {
    	Profesor profesor=null;
    	try {
    		profesor=crearProfesor();
    		controladorMVC.insertarProfesor(profesor);
			cVPrincipal.actualizaTablas();
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Profesor", "Profesor añadido correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Error", e.getMessage());
			inputNombreProf.clear();
			inputMailProf.clear();
			inputTelfProf.clear();
		}
    
    }

    @FXML
    void acCancelar(ActionEvent event) {
    	((Stage) btCancelar.getScene().getWindow()).close();
    }

    private void compruebaNombre (String ov, String nv) {
		if (nv.matches("[^0-9]*")) {
			inputNombreProf.setStyle("-fx-border-color: green");
		} else{
			inputNombreProf.setText(ov);
			inputNombreProf.setStyle("-fx-border-color: red");
		}
    }
    
    private void compruebaMail (String ov, String nv) {
		if (nv.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")) {
			inputMailProf.setStyle("-fx-border-color: green");
		} else{
			inputMailProf.setStyle("-fx-border-color: red");
		}
    }
    
    private void compruebaTelf (String ov, String nv) {
		if (nv.matches("([0-9]{9})")) {
			inputTelfProf.setStyle("-fx-border-color: green");
		} else{
			// inputTelfProf.setText(ov);
			inputTelfProf.setStyle("-fx-border-color: red");
		}
    }
    
    private Profesor crearProfesor() {
    	String nombre=inputNombreProf.getText();
    	String mail=inputMailProf.getText();
    	String telf=inputTelfProf.getText();
    	Profesor profesor= new Profesor(nombre,mail,telf);
    	return new Profesor(profesor);
    }
    
    
    @FXML private Button btAceptar;
    @FXML private Button btCancelar;
    // @FXML private TitledPane vAnadirProf;
}
