package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class ControladorVBuscarProf {

	private IControlador controladorMVC;
	ControladorVPrincipal cVPrincipal=null;

	public void setControladorMVC(IControlador controlador) {
		controladorMVC = controlador;
	}
	public void setControladorVPrincipal(ControladorVPrincipal controladorVPrincipal) {
		cVPrincipal=controladorVPrincipal;
	}
	
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
	
    @FXML private TableView<Profesor> tabProfesores;
    	@FXML private TableColumn<Profesor, String> colNombreProf;
        @FXML private TableColumn<Profesor, String> colTelfProf;
        @FXML private TableColumn<Profesor, String> colMailProf;
        @FXML private TableColumn<Profesor, String> colPuntosProf;

    @FXML private TableView<Reserva> tabReservas;
    	@FXML private TableColumn<Reserva, String> colProfReservas;
        @FXML private TableColumn<Reserva, String> colAulaReservas;
        @FXML private TableColumn<Reserva, String> colPermReservas;
        @FXML private TableColumn<Reserva, String> colPuntosReservas;

        public void initialize() {
    		tabProfesores.setItems(profesores);
    		colNombreProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
    		colTelfProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getTelefono()));
    		colMailProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
    		colPuntosProf.setCellValueFactory(profesor -> new SimpleStringProperty(String.valueOf(cVPrincipal.puntosDisponibles(profesor.getValue()))));
    		
    		tabReservas.setItems(reservas);
    		colProfReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
    		colAulaReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
    		colPermReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
    		colPuntosReservas.setCellValueFactory(reserva -> new SimpleStringProperty(String.valueOf(reserva.getValue().getPuntos())));
        }

    @FXML void acBuscarProf(ActionEvent event) {
    	profesores.clear();
    	reservas.clear();
    	lbError.setText("");
    	Profesor profesorEncontrado=null;
    	try {
	    	String correoProfesor=inputBuscarProf.getText();
	    	Profesor profesorABuscar=new Profesor("pepe", correoProfesor, "600121212");
	    	profesorEncontrado=controladorMVC.buscarProfesor(profesorABuscar);
    	} catch(Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    	if (profesorEncontrado==null) {
    		lbError.setText("No existe ningún profesor con ese correo");
    	} else {
    		profesores.add(profesorEncontrado);
    		List<Reserva> reservasProfesor=controladorMVC.getReservasProfesor(profesorEncontrado);
    		if (reservasProfesor!=null) {
    			reservas.setAll(controladorMVC.getReservasProfesor(profesorEncontrado));
    		}
    	}
    }
    
    @FXML
    private Label lbError;
    
    @FXML
    private Button btBuscarProf;
    
    @FXML
    private TitledPane vBuscarProf;
    
    @FXML
    private TextField inputBuscarProf;

}
