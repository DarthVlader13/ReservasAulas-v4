package org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.controladores;





import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.recursos.LocalizadorRecursos;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;



public class ControladorVPrincipal {

	// Instanciamos el controlador, que se pasará desde VistaGrafica mediante el setControladorMVC
	private IControlador controladorMVC;
	
	public void setControladorMVC(IControlador controlador) {
		controladorMVC=controlador;
	}

	// Creamos los ObservableList, que será lo que se vea en la tabla
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();

	// Creamos las tablas con el tipo de objeto que contendrán, así como sus columnas, que contendrán Strings
	@FXML private TableView<Profesor> tabProfesores;
		@FXML private TableColumn<Profesor, String> colNombreProf;
		@FXML private TableColumn<Profesor, String> colTelfProf;
		@FXML private TableColumn<Profesor, String> colMailProf;
		@FXML private TableColumn<Profesor, ProgressBar> colPuntosProf;
		
	@FXML private TableView<Aula> tabAulas;
    	@FXML private TableColumn<Aula, String> colNombreAula;
    	@FXML private TableColumn<Aula, String> colPuestosAula;

	@FXML private TableView<Reserva> tabReservas;
		@FXML private TableColumn<Reserva, String> colProfReservas;
		@FXML private TableColumn<Reserva, String> colAulaReservas;
		@FXML private TableColumn<Reserva, String> colPermReservas;
		@FXML private TableColumn<Reserva, String> colPuntosReservas;
		
	//Declaramos los distintos escenarios y sus controladores para luego utilizarlos en sus respectivas ventanas emergentes
	private Stage anadirProfesor;
	private ControladorVAnadirProf cAnadirProf;
	private Stage buscarProfesor;
	private ControladorVBuscarProf cBuscarProf;
	private Stage anadirAula;
	private ControladorVAnadirAula cAnadirAula;
	private Stage buscarAula;
	private ControladorVBuscarAula cBuscarAula;
	private Stage anadirReserva;
	private ControladorVAnadirReserva cAnadirReserva;
	private Stage buscarReserva;
	private ControladorVBuscarReserva cBuscarReserva;
	
	//Inicializamos las tablas, indicando en setItems la ObservableList que contendrá y mediante funciones lambda los strings que irán
	// a cada columna. 
	@FXML private void initialize() {
		tabProfesores.setItems(profesores);
		colNombreProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getNombre()));
		colTelfProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getTelefono()));
		colMailProf.setCellValueFactory(profesor -> new SimpleStringProperty(profesor.getValue().getCorreo()));
		colPuntosProf.setCellValueFactory(profesor -> new SimpleObjectProperty<ProgressBar>(crearBarra(profesor.getValue())));

		
		tabAulas.setItems(aulas);
		colNombreAula.setCellValueFactory(aula -> new SimpleStringProperty(aula.getValue().getNombre()));
		colPuestosAula.setCellValueFactory(aula -> new SimpleStringProperty(String.valueOf(aula.getValue().getPuestos())));
		
		tabReservas.setItems(reservas);
		colProfReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		colAulaReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		colPermReservas.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().toString()));
		colPuntosReservas.setCellValueFactory(reserva -> new SimpleStringProperty(String.valueOf(reserva.getValue().getPuntos())));
	}
    
	//Método que actualiza las tablas, volcando el contenido de los getters en sus respectivas ObservableList. Inicialmente se corre
	// desde la propia VistaGrafica y luego se irá corriendo en distintos métodos
	public void actualizaTablas() {
		profesores.setAll(controladorMVC.getProfesores());
		aulas.setAll(controladorMVC.getAulas());
		reservas.setAll(controladorMVC.getReservas());
	}
	
	// Métodos para borrar, que guardan el objeto seleccionado en su tipo correspondiente y luego comprueban que dicho objeto no sea null,
	// y después pregunta mediante un diálogo si está seguro. Si se acepta, se pasa el objeto al controlador para que lo borre y se actualizan
	// las tablas. Si no, no pasa nada. Captura mediante un try/catch cualquier error y lo muestra en forma de diálogo
    @FXML void acBorrarAula(ActionEvent event) {
    	Aula aula=null;
    	try {
    		aula=tabAulas.getSelectionModel().getSelectedItem();
    		if (aula!=null && Dialogos.mostrarDialogoConfirmacion("Borrar Aula", "¿Estás seguro de que quieres borrar el aula?", null)) {
    			controladorMVC.borrarAula(aula);
    			actualizaTablas();
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    }
    @FXML void acBorrarProfesor(ActionEvent event) {
    	Profesor profesor=null;
    	try {
    		profesor=tabProfesores.getSelectionModel().getSelectedItem();
    		if (profesor!=null && Dialogos.mostrarDialogoConfirmacion("Borrar Profesor", "¿Estás seguro de que quieres borrar el profesor?", null)) {
    			controladorMVC.borrarProfesor(profesor);
    			actualizaTablas();
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    }
    @FXML void acBorrarReserva(ActionEvent event) {
    	Reserva reserva=null;
    	try {
    		reserva=tabReservas.getSelectionModel().getSelectedItem();
    		if (reserva!=null && Dialogos.mostrarDialogoConfirmacion("Borrar Reserva", "¿Estás seguro de que quieres borrar la reserva?", null)) {
    			controladorMVC.anularReserva(reserva);;
    			actualizaTablas();
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Error", e.getMessage());
    	}
    }

    // Métodos que abren otras ventanas. La acción corre el método correspondiente y muestra dicha ventana. El método crea un nuevo escenario,
    // carga el FMXL, le pasa el controlador, le pone título, la hace unresizable y crea como aplicación modal. 
    @FXML
    void acAbrirAnadirAula(ActionEvent event) throws IOException {
    	abrirAnadirAula();
    	anadirAula.showAndWait();
    }
    private void abrirAnadirAula() throws IOException {
    	anadirAula = new Stage();
		FXMLLoader cargadorVAnadirAula=new FXMLLoader(LocalizadorRecursos.class.getResource("/vistas/vAnadirAula.fxml"));
		TitledPane raizAnadirAula=cargadorVAnadirAula.load();
		cAnadirAula=cargadorVAnadirAula.getController();
		cAnadirAula.setControladorMVC(controladorMVC);
		cAnadirAula.setControladorVPrincipal(this);
		
		Scene escenaAnadirAula=new Scene(raizAnadirAula);
		anadirAula.setTitle("Añadir Aula");
		anadirAula.setScene(escenaAnadirAula);
		anadirAula.setResizable(false);
		anadirAula.initModality(Modality.APPLICATION_MODAL);
    }
    
    @FXML
    void acAbrirAnadirProf(ActionEvent event) throws IOException {
    	abrirAnadirProf();
    	anadirProfesor.showAndWait();
    }
    private void abrirAnadirProf() throws IOException {
    	anadirProfesor = new Stage();
		FXMLLoader cargadorVAnadirProf=new FXMLLoader(LocalizadorRecursos.class.getResource("/vistas/vAnadirProf.fxml"));
		TitledPane raizAnadirProf=cargadorVAnadirProf.load();
		cAnadirProf=cargadorVAnadirProf.getController();
		cAnadirProf.setControladorMVC(controladorMVC);
		cAnadirProf.setControladorVPrincipal(this);
		
		Scene escenaAnadirProf=new Scene(raizAnadirProf);
		anadirProfesor.setTitle("Añadir Profesor");
		anadirProfesor.setScene(escenaAnadirProf);
		anadirProfesor.setResizable(false);
		anadirProfesor.initModality(Modality.APPLICATION_MODAL);
    }
    
    @FXML
    void acAbrirAnadirReserva(ActionEvent event) throws IOException {
    	abrirAnadirReserva();
    	anadirReserva.showAndWait();
    }
    private void abrirAnadirReserva() throws IOException {
    	anadirReserva = new Stage();
		FXMLLoader cargadorVAnadirReserva=new FXMLLoader(LocalizadorRecursos.class.getResource("/vistas/vAnadirReserva.fxml"));
		VBox raizAnadirReserva=cargadorVAnadirReserva.load();
		cAnadirReserva=cargadorVAnadirReserva.getController();
		cAnadirReserva.setControladorMVC(controladorMVC);
		cAnadirReserva.setControladorVPrincipal(this);
		cAnadirReserva.actualizaTablasReserva();
		
		Scene escenaAnadirReserva=new Scene(raizAnadirReserva);
		anadirReserva.setTitle("Añadir Reserva");
		anadirReserva.setScene(escenaAnadirReserva);
		anadirReserva.setResizable(false);
		anadirReserva.initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    void acAbrirBuscarAula(ActionEvent event) throws IOException {
    	abrirBuscarAula();
    	buscarAula.showAndWait();
    }
    
    private void abrirBuscarAula() throws IOException {
    	buscarAula = new Stage();
		FXMLLoader cargadorVBuscarAula=new FXMLLoader(LocalizadorRecursos.class.getResource("/vistas/vBuscarAula.fxml"));
		VBox raizBuscarAula=cargadorVBuscarAula.load();
		cBuscarAula=cargadorVBuscarAula.getController();
		cBuscarAula.setControladorMVC(controladorMVC);
		// cBuscarAula.setControladorVPrincipal(this);
		
		Scene escenaBuscarAula=new Scene(raizBuscarAula);
		buscarAula.setTitle("Buscar Aula");
		buscarAula.setScene(escenaBuscarAula);
		buscarAula.setResizable(false);
		buscarAula.initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    void acAbrirBuscarProf(ActionEvent event) throws IOException {
    	abrirBuscarProf();
    	buscarProfesor.showAndWait();
    }
    
    private void abrirBuscarProf() throws IOException{
    	buscarProfesor=new Stage();
		FXMLLoader cargadorVBuscarProf=new FXMLLoader(LocalizadorRecursos.class.getResource("/vistas/vBuscarProf.fxml"));
		VBox raizBuscarProf=cargadorVBuscarProf.load();
		cBuscarProf=cargadorVBuscarProf.getController();
		cBuscarProf.setControladorMVC(controladorMVC);
		cBuscarProf.setControladorVPrincipal(this);
		
		Scene escenaBuscarProf=new Scene(raizBuscarProf);
		buscarProfesor.setTitle("Buscar Profesor");
		buscarProfesor.setScene(escenaBuscarProf);
		buscarProfesor.setResizable(false);
		buscarProfesor.initModality(Modality.APPLICATION_MODAL);
    }
    @FXML
    void acAbrirBuscarReserva(ActionEvent event) throws IOException {
    	abrirBuscarReserva();
    	buscarReserva.showAndWait();
    }
    
    private void abrirBuscarReserva() throws IOException {
    	buscarReserva=new Stage();
		FXMLLoader cargadorVBuscarReserva=new FXMLLoader(LocalizadorRecursos.class.getResource("/vistas/vBuscarReserva.fxml"));
		VBox raizBuscarReserva=cargadorVBuscarReserva.load();
		cBuscarReserva=cargadorVBuscarReserva.getController();
		cBuscarReserva.setControladorMVC(controladorMVC);
		cBuscarReserva.setControladorVPrincipal(this);
		
		Scene escenaBuscarReserva=new Scene(raizBuscarReserva);
		buscarReserva.setTitle("Buscar Reserva");
		buscarReserva.setScene(escenaBuscarReserva);
		buscarReserva.setResizable(false);
		buscarReserva.initModality(Modality.APPLICATION_MODAL);
    }

    // Método que calcula los puntos que ha gastado un profesor y devuelve cuántos le quedan disponibles
    public float puntosDisponibles (Profesor profesor) {
    	float puntosGastados=0;
    	float puntosDisponibles=200;
    	float puntosFinales=0;
    	List<Reserva> reservasProf=controladorMVC.getReservasProfesor(profesor);
    	if (reservasProf==null) {
    		puntosFinales=200;
    	}
    	else {
        	for (Reserva r : reservasProf) {
        		Month mes = r.getPermanencia().getDia().getMonth();
        		Month mesActual = LocalDate.now().getMonth();
        		if (mes.getValue() > mesActual.getValue() && r.getPermanencia().getDia().isAfter(LocalDate.now())) {
        			puntosGastados=puntosGastados+r.getPuntos();
        		}
        	}
        	puntosFinales=puntosDisponibles-puntosGastados;
    	}
    	return puntosFinales;
    }
    
    public ProgressBar crearBarra(Profesor profesor) {
    	float puntuacion=puntosDisponibles(profesor)/200;
    	ProgressBar barra = new ProgressBar(puntuacion);
		Tooltip puntosDispo = new Tooltip ("Puntos disponibles: "+puntosDisponibles(profesor));
		barra.setTooltip(puntosDispo);
    	barra.setMinWidth(190);
    	if (puntuacion<0.25) {
    		barra.setStyle("-fx-accent: red;");
    	}
    	else if (puntuacion>0.5) {
    		barra.setStyle("-fx-accent: green;");
    	}
    	else {
    		barra.setStyle("-fx-accent: orange;");
    	}
    	return barra;
    }
    

    

    @FXML
    void acSalir(ActionEvent event) {
    	if(Dialogos.mostrarDialogoConfirmacion("Salir", "¿Seguro que desea cerrar la aplicación?", null)) {
    		controladorMVC.terminar();
    		System.exit(0);
    	}
    }
    
    @FXML
    void acAcercaDe(ActionEvent event) throws IOException {
		VBox contenido = FXMLLoader.load(LocalizadorRecursos.class.getResource("/vistas/AcercaDe.fxml"));
		Dialogos.mostrarDialogoInformacionPersonalizado("Gestión de reservas", contenido);
    }
    
    @FXML
    private MenuItem miAyuda;

    @FXML
    private MenuItem miSalir;
    /*
    @FXML
    private MenuItem menuBorrarAula;

    @FXML
    private MenuItem menuBorrarProfesor;

    @FXML
    private MenuItem menuBorrarReserva;
    */
    
    /*
    @FXML
    private Button btAbrirAnadirAula;

    @FXML
    private Button btAbrirAnadirProfesor;

    @FXML
    private Button btAbrirAnadirReserva;

    @FXML
    private Button btAbrirBuscarAula;

    @FXML
    private Button btAbrirBuscarProfesor;

    @FXML
    private Button btAbrirBuscarReserva;

    */
}
