package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControladorVAnadirReserva {

	ControladorVPrincipal cVPrincipal=null;
	private IControlador controladorMVC;
	private ToggleGroup grupo;
	
	public void setControladorVPrincipal(ControladorVPrincipal controladorVPrincipal) {
		cVPrincipal=controladorVPrincipal;
	}
	
	public void setControladorMVC(IControlador controlador) {
		controladorMVC=controlador;
	}

	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	
	@FXML private TableView<Profesor> tabProfesores;
		@FXML private TableColumn<Profesor, String> colNombreProf;
		@FXML private TableColumn<Profesor, String> colTelfProf;
		@FXML private TableColumn<Profesor, String> colMailProf;
		@FXML private TableColumn<Profesor, String> colPuntosProf;
		
	@FXML private TableView<Aula> tabAulas;
		@FXML private TableColumn<Aula, String> colNombreAula;
		@FXML private TableColumn<Aula, String> colPuestosAula;
		
	@FXML private void initialize() {
		tabProfesores.setItems(profesores);
		colNombreProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
		colTelfProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getTelefono()));
		colMailProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
		colPuntosProf.setCellValueFactory(profesor -> new SimpleStringProperty(String.valueOf(cVPrincipal.puntosDisponibles(profesor.getValue()))));
			
		tabAulas.setItems(aulas);
		colNombreAula.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getNombre()));
		colPuestosAula.setCellValueFactory(aula -> new SimpleStringProperty(String.valueOf(aula.getValue().getPuestos())));
		
		grupo = new ToggleGroup();
		rbPorHoras.setToggleGroup(grupo);
		rbPorTramo.setToggleGroup(grupo);
		inputHoras.setVisibleRowCount(5);
		inputHoras.setItems(FXCollections.observableArrayList("08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00"));
		inputTramo.setItems(FXCollections.observableArrayList("Mañana","Tarde"));
		inputFecha.setValue(LocalDate.now().plusMonths(1));
	}
	
	public void actualizaTablasReserva() {
		profesores.setAll(controladorMVC.getProfesores());
		aulas.setAll(controladorMVC.getAulas());
	}
	
	private void rbSeleccionado() {
		  RadioButton seleccionado = (RadioButton)grupo.getSelectedToggle();
		  if (seleccionado==rbPorHoras) {
			  inputHoras.setDisable(false);
			  inputTramo.setDisable(true);
		  }
		  else if (seleccionado==rbPorTramo) {
			  inputHoras.setDisable(true);
			  inputTramo.setDisable(false);
		  }
	}

    @FXML
    private DatePicker inputFecha;

    @FXML
    private ComboBox<String> inputHoras;

    @FXML
    private ComboBox<String> inputTramo;


    @FXML
    void acAnadirReserva(ActionEvent event) {
    	try {
        	RadioButton seleccionado = (RadioButton)grupo.getSelectedToggle();
        	Profesor profesor=null;
        	Aula aula=null;
        	Permanencia permanencia=null;
        	Tramo tramo=null;
        	profesor=tabProfesores.getSelectionModel().getSelectedItem();
        	aula=tabAulas.getSelectionModel().getSelectedItem();
        	if(seleccionado==rbPorHoras) {
        		permanencia=new PermanenciaPorHora(inputFecha.getValue(),LocalTime.parse(inputHoras.getSelectionModel().getSelectedItem()));
        	}
        	else if(seleccionado==rbPorTramo) {
        		if (inputTramo.getSelectionModel().getSelectedItem()=="Mañana") {
        			tramo=Tramo.MANANA;
        		}
        		else if(inputTramo.getSelectionModel().getSelectedItem()=="Tarde") {
        			tramo=Tramo.TARDE;
        		}
        		permanencia=new PermanenciaPorTramo(inputFecha.getValue(),tramo);
        	}
        	controladorMVC.realizarReserva(new Reserva(profesor,aula,permanencia));
			cVPrincipal.actualizaTablas();
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Reserva", "Reserva añadida correctamente", propietario);
    	} catch (Exception e) {
			Dialogos.mostrarDialogoError("Error", e.getMessage());
			}
    }

    @FXML
    void acCancelar(ActionEvent event) {
    	((Stage) btCancelar.getScene().getWindow()).close();
    }
    
    @FXML
    void acPorHoras(ActionEvent event) {
  	  rbSeleccionado();
    }

    @FXML
    void acPorTramo(ActionEvent event) {
  	  rbSeleccionado();
    }
    
    @FXML
    private Button btAceptar;

    @FXML
    private Button btCancelar;
    
    @FXML
    private TitledPane vAnadirReserva;
    
    @FXML
    private RadioButton rbPorHoras;

    @FXML
    private RadioButton rbPorTramo;
    
  @FXML
  void acFecha(ActionEvent event) {

  }

  @FXML
  void acHora(ActionEvent event) {

  }

  @FXML
  void acTramo(ActionEvent event) {

  }

}
