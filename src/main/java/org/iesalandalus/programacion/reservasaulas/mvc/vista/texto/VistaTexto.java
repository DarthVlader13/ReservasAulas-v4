package org.iesalandalus.programacion.reservasaulas.mvc.vista.texto;

import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class VistaTexto implements IVista {
	private IControlador Icontrolador;

	private final static String ERROR = "No existen reservas para el parámetro proporcionado";
	private final static String NOMBRE_VALIDO = "Manolo";
	private final static String CORREO_VALIDO = "manolo@eldelbombo.com";

//Constructor de la vista, que corre el método setVista del enum Opcion para que éste sepa quién es la Vista y ejecute las opciones en
//consecuencia. 
	public VistaTexto() {
		Opcion.setVista(this);
	}

//Método que setea el controlador una vez se instancie
	@Override
	public void setControlador(IControlador controlador) {
		this.Icontrolador = controlador;
	}

//Método comenzar, que muestra el menú, nos da a elegir una opción, pasa dicha opción por el método getOpcionSegunOrdinal para validar que el ordenal
//sea correcto, lo transforma en una Opcion y luego la ejecuta. Todo ello se repetirá mientras la Opcion elegida no sea SALIR
	@Override
	public void comenzar() {
		int ordinalOpcion = 0;
		do {
			try {
				Consola.mostrarMenu();
				ordinalOpcion = Consola.elegirOpcion();
				Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
				opcion.ejecutar();
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}

//Método que llama al método terminar del controlador y da un mensaje de despedida
	@Override
	public void salir() {
		System.out.println("¡Hasta otra!");
		Icontrolador.terminar();
	}

//Método que intenta llamar al método homónimo del controlador pasándo como parámetro el método adecuado de la consola. Captura todos los errores posibles,
//los muestra y se queda dentro del bucle hasta que no existen. Luego nos da un mensaje de realización correcta.
	public void insertarAula() {
		try {
			Icontrolador.insertarAula(Consola.leerAula());
			System.out.println("Aula insertada correctamente.");
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}

	}

//Método que intenta llamar al método homónimo del controlador pasándo como parámetro el método adecuado de la consola. Captura todos los errores posibles,
//los muestra y se queda dentro del bucle hasta que no existen. Luego nos da un mensaje de realización correcta.
	public void borrarAula() {
		List<String> listaAulas = Icontrolador.representarAulas();
		if (listaAulas == null) {
			System.out.println("No existen aulas que borrar");
		} else {
			try {
				Icontrolador.borrarAula(Consola.leerAulaFicticia());
				System.out.println("Aula borrada correctamente.");
			} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

//Método que crea un Aula y luego ejecuta el método homónimo del controlador pasando como parámetro el método adecuado de la consola. Captura todos los
//errores posibles, los muestra y queda dentro del bucle hasta que no existen. Luego comprueba si el retorno es nulo (no existe) y muestra un mensaje en
//consecuencia. Si el aula existe, la muestra con su .toString
	public void buscarAula() {
		Aula aula = null;
		List<String> listaAulas = Icontrolador.representarAulas();
		if (listaAulas == null) {
			System.out.println("No existen aulas que buscar");
		} else {
			try {
				aula = Icontrolador.buscarAula(Consola.leerAulaFicticia());
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			if (aula == null) {
				System.out.println("El aula buscada no existe");
			} else {
				System.out.println(aula.toString());
			}
		}
	}

//Método que crea un array String y ejecuta el método homónimo del controlador. Captura todos los errores posibles y los muestra de existir. Si el retorno es
//nulo (no existe), muestra un mensaje en consecuencia. Si no, recorre el array mostrando los resultados con el .toString de la clase
	public void listarAulas() {
		List<String> listaAulas = null;
		try {
			listaAulas = Icontrolador.representarAulas();
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (listaAulas == null) {
			System.out.println("No hay aulas que mostrar");
		} else {
			Iterator<String> iterador = listaAulas.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

//Método que intenta llamar al método homónimo del controlador pasándo como parámetro el método adecuado de la consola. Captura todos los errores posibles,
//los muestra y se queda dentro del bucle hasta que no existen. Luego nos da un mensaje de realización correcta.
	public void insertarProfesor() {
		try {
			Icontrolador.insertarProfesor(Consola.leerProfesor());
			System.out.println("Profesor insertado correctamente.");
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

//Método que intenta llamar al método homónimo del controlador pasándo como parámetro el método adecuado de la consola. Captura todos los errores posibles,
//los muestra y se queda dentro del bucle hasta que no existen. Luego nos da un mensaje de realización correcta.
	public void borrarProfesor() {
		List<String> listaProfesores = Icontrolador.representarProfesores();
		if (listaProfesores == null) {
			System.out.println("No existen profesores que borrar");
		} else {
			try {
				Profesor profesorABorrar = new Profesor(Consola.leerProfesorFicticio());
				Icontrolador.borrarProfesor(profesorABorrar);
				System.out.println("Profesor borrado correctamente.");
			} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

//Método que crea un Aula y luego ejecuta el método homónimo del controlador pasando como parámetro el método adecuado de la consola. Captura todos los
//errores posibles, los muestra y queda dentro del bucle hasta que no existen. Luego comprueba si el retorno es nulo (no existe) y muestra un mensaje en
//consecuencia. Si el aula existe, la muestra con su .toString
	public void buscarProfesor() {
		Profesor profesor = null;
		List<String> listaProfesores = Icontrolador.representarProfesores();
		if (listaProfesores == null) {
			System.out.println("No existen profesores que buscar");
		} else {
			try {
				Profesor profesorABuscar = new Profesor(Consola.leerProfesorFicticio());
				profesor = Icontrolador.buscarProfesor(profesorABuscar);
			} catch (NullPointerException | IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
			if (profesor == null) {
				System.out.println("El profesor buscado no existe");
			} else {
				System.out.println(profesor.toString());
			}
		}
	}

//Método que crea un array String y ejecuta el método homónimo del controlador. Captura todos los errores posibles y los muestra de existir. Si el retorno es
//nulo (no existe), muestra un mensaje en consecuencia. Si no, recorre el array mostrando los resultados con el .toString de la clase
	public void listarProfesores() {
		List<String> listaProfesores = null;
		try {
			listaProfesores = Icontrolador.representarProfesores();
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (listaProfesores == null) {
			System.out.println("No hay profesores que mostrar");
		} else {
			Iterator<String> iterador = listaProfesores.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

//Método que llama al método homónimo del controlador y le pasa como parámetro el método leerReserva (explicado a continuación), que a su vez recibe como
//parámetro el método adecuado de la consola. Captura todos los posibles errores y continúa en el bucle hasta que no existan, y luego nos da un mensaje de
//realización correcta.
	public void realizarReserva() {
		Reserva reservaARealizar = null;
		try {
			reservaARealizar = Consola.leerReserva();
			if (Icontrolador.buscarProfesor(reservaARealizar.getProfesor()) == null) {
				System.out.println("ERROR: El profesor introducido no existe. Por favor, creélo antes de intentar realizar una reserva con él.");
			} 
			else if (Icontrolador.buscarAula(reservaARealizar.getAula()) == null) {
				System.out.println("ERROR: El aula introducida no existe. Por favor, creéla antes de intentar realizar una reserva con ella.");
			} 
			else {
				Profesor profesor= new Profesor(Icontrolador.buscarProfesor(reservaARealizar.getProfesor()));
				Aula aula= new Aula(Icontrolador.buscarAula(reservaARealizar.getAula()));
				Reserva reservaFinal=new Reserva (profesor,aula,reservaARealizar.getPermanencia());
				Icontrolador.realizarReserva(reservaFinal);
				System.out.println("Reserva realizada correctamente.");

			}
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

//Método que también hace uso del leerReserva anterior para generar una reserva que pasaremos como parámetro al método homónimo del controlador. Dado que
//solo nos interesa la Permanencia y el Aula para encontrar coincidencias entre las reservas, el profesor lo creamos haciendo uso de su constructor de dos
//parámetros y con los atributos estáticos de la clase Vista. Capturamos los errores y continuamos dentro del bucle hasta que no existen y finalmente
//devolvemos un mensaje de realización correcta.
	public void anularReserva() {
		Reserva reserva = null;
		List<String> listaReservas = Icontrolador.representarReservas();
		if (listaReservas == null) {
			System.out.println("No existen reservas que borrar");
		} else {
			try {
				reserva = Consola.leerReservaFicticia();
				Icontrolador.anularReserva(reserva);
				System.out.println("Reserva anulada correctamente.");
			} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
				System.out.println(e.getMessage());
			}
		}
	}

//Método que crea un array String y ejecuta el método homónimo del controlador. Captura todos los errores posibles y los muestra de existir. Si el retorno es
//nulo (no existe), muestra un mensaje en consecuencia. Si no, recorre el array mostrando los resultados con el .toString de la clase. Al haber desaparecido
//el método listarReservasPermanencia() y apuntarse a que ese resultado se podía obtener en este método, se ha modificado para que a una fecha dada nos dé la
//lista de todas las reservas para dicha fecha. El método anterior ha quedado comentado.
	public void listarReservas() {
//		List<String> listaReservas = null;
//		try {
//			listaReservas = Icontrolador.representarReservas();
//		} catch (NullPointerException | IllegalArgumentException e) {
//			System.out.println(e.getMessage());
//		}
//		if (listaReservas == null) {
//			System.out.println("No hay reservas que mostrar");
//		} else {
//			Iterator<String> iterador = listaReservas.iterator();
//			while (iterador.hasNext()) {
//				System.out.println(iterador.next().toString());
//			}
//		}
		List<Reserva> listaReservas = null;
		try {
			Permanencia permanencia=null;
			permanencia=new PermanenciaPorTramo(Consola.leerDia(),Tramo.TARDE);
			listaReservas = Icontrolador.getReservasPermanencia(permanencia);	
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (listaReservas == null) {
			System.out.println(ERROR);
		} else {
			Iterator<Reserva> iterador = listaReservas.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

//Método similar a listaReservas pero solo para un parámetro dado, que obtendremos mediante el método adecuado de la consola
	public void listarReservasAula() {
		List<Reserva> listaReservasAula = null;
		try {
			listaReservasAula = Icontrolador.getReservasAula(Consola.leerAulaFicticia());
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (listaReservasAula == null) {
			System.out.println(ERROR);
		} else {
			Iterator<Reserva> iterador = listaReservasAula.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

//Método similar a listaReservas pero solo para un parámetro dado, que obtendremos mediante el método adecuado de la consola
	public void listarReservasProfesor() {
		List<Reserva> listaReservasProfesor = null;
		try {
			listaReservasProfesor = Icontrolador.getReservasProfesor(Consola.leerProfesorFicticio());
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (listaReservasProfesor == null) {
			System.out.println(ERROR);
		} else {
			Iterator<Reserva> iterador = listaReservasProfesor.iterator();
			while (iterador.hasNext()) {
				System.out.println(iterador.next().toString());
			}
		}
	}

//Método que recibe una permanencia y un aula, y verifica si dicha aula está disponible para ese tipo de permamencia y esa fecha, discriminando por
//tramos u horas según convenga. Verifica el null del aula y que la permanencia introducida sea posterior al mes presente
	public void consultarDisponibilidad() {
		boolean disponible = true;
		Permanencia permanencia = null;
		Aula aula = null;
		try {
			permanencia = Consola.leerPermanencia();
			aula = Consola.leerAulaFicticia();
			disponible = Icontrolador.consultarDisponibilidad(aula, permanencia);
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		if (Icontrolador.buscarAula(aula) == null) {
			System.out.println("El aula introducida no existe");
		} 
		else if (disponible == true) {
			System.out.println("El aula se encuentra disponible para la permanencia y día introducidos.");
		} else {
			System.out.println("El aula NO se encuentra disponible para la permanencia y día introducidos.");
		}
	}

}
