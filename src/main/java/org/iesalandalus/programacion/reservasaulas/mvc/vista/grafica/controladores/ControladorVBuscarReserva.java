package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;

public class ControladorVBuscarReserva {
	ControladorVPrincipal cVPrincipal=null;
	private IControlador controladorMVC;
	
	public void setControladorVPrincipal(ControladorVPrincipal controladorVPrincipal) {
		cVPrincipal=controladorVPrincipal;
	}
	
	public void setControladorMVC(IControlador controlador) {
		controladorMVC=controlador;
	}
	
	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
	
	@FXML private TableView<Reserva> tabReservas;
	@FXML private TableColumn<Reserva, String> colProfReservas;
	@FXML private TableColumn<Reserva, String> colAulaReservas;
	@FXML private TableColumn<Reserva, String> colPermReservas;
	@FXML private TableColumn<Reserva, String> colPuntosReservas;
	
	@FXML private void initialize() {
		tabReservas.setItems(reservas);
		colProfReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		colAulaReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		colPermReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
		colPuntosReservas.setCellValueFactory(reserva -> new SimpleStringProperty(String.valueOf(reserva.getValue().getPuntos())));
	}

    
    @FXML
    void acBuscarReserva(ActionEvent event) {
    	List<Reserva> coleccionReservas=null;
    	try {
    		lbError.setText("");
        	reservas.clear();
        	Tramo tramo=Tramo.MANANA;
        	Permanencia permanencia=new PermanenciaPorTramo(inputFecha.getValue(),tramo);
        	coleccionReservas=controladorMVC.getReservasPermanencia(permanencia);
        	if (coleccionReservas==null) {
        		lbError.setText("No existe ninguna reserva para ese dia");
        	}
        	else {
        		reservas.setAll(coleccionReservas);
        	}
    	}catch(Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    }
    
    @FXML
    private Label lbError;
    
    @FXML
    private DatePicker inputFecha;
    
    @FXML
    private Button btBuscarReserva;

    @FXML
    private TitledPane vBuscarReserva;

    @FXML
    void acFecha(ActionEvent event) {

    }


}
