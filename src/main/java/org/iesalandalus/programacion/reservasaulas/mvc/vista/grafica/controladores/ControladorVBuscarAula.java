package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
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

public class ControladorVBuscarAula {

	private IControlador controladorMVC;

	public void setControladorMVC(IControlador controlador) {
		controladorMVC = controlador;
	}
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
	
    @FXML private TableView<Aula> tabAulas;
    	@FXML private TableColumn<Aula, String> colNombreAula;
        @FXML private TableColumn<Aula, String> colPuestosAula;
        
    @FXML private TableView<Reserva> tabReservas;
    	@FXML private TableColumn<Reserva, String> colProfReservas;
    	@FXML private TableColumn<Reserva, String> colAulaReservas;
    	@FXML private TableColumn<Reserva, String> colPermReservas;
    	@FXML private TableColumn<Reserva, String> colPuntosReservas;

    public void initialize() {
		tabAulas.setItems(aulas);
		colNombreAula.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getNombre()));
		colPuestosAula.setCellValueFactory(aula -> new SimpleStringProperty(String.valueOf(aula.getValue().getPuestos())));
		
		tabReservas.setItems(reservas);
		colProfReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		colAulaReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		colPermReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
		colPuntosReservas.setCellValueFactory(reserva -> new SimpleStringProperty(String.valueOf(reserva.getValue().getPuntos())));
    }

    @FXML
    void acBuscarAula(ActionEvent event) {
    	aulas.clear();
    	reservas.clear();
    	lbError.setText("");
    	Aula aulaEncontrada=null;
    	try {
	    	String nombreAula=inputBuscarAula.getText();
	    	Aula aulaABuscar=new Aula(nombreAula,20);
	    	aulaEncontrada=controladorMVC.buscarAula(aulaABuscar);
    	} catch(Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    	if (aulaEncontrada==null) {
    		lbError.setText("No existe ningún aula con ese nombre");
    	} else {
    		aulas.add(aulaEncontrada);
    		List<Reserva> reservasAula=controladorMVC.getReservasAula(aulaEncontrada);
    		if (reservasAula!=null) {
    			reservas.setAll(controladorMVC.getReservasAula(aulaEncontrada));
    		}
    	}
    }

    @FXML private Label lbError;
    
    @FXML
    private Button btBuscarAula;
    
    @FXML
    private TextField inputBuscarAula;
    
    @FXML
    private TitledPane vBuscarAula;
    
}
