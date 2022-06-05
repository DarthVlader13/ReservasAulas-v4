package org.iesalandalus.programacion.reservasaulas.mvc.vista.texto;

import java.time.LocalDate; 
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	// DECLARACIÓN DE ATRIBUTOS
	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static Opcion[] opciones = Opcion.values();
	static Tramo[] tramos = Tramo.values();

	// CREAMOS CONSTRUCTOR CONSOLA (UTILIDAD, NO SE INSTANCIAN OBJETOS).
	private Consola() {

	}

	// CREAMOS MÉTODO MOSTRARMENU
	public static void mostrarMenu() {
		mostrarCabecera(
				"Bienvenido al sistema de reservas del IES Al-Ándalus. Entra libremente y deja parte de la felicidad que traes contigo.");
		for (Opcion o : opciones) {
			System.out.println(o);
		}
	}

	// CREAMOS MÉTODO MOSTRARCABECERA
	public static void mostrarCabecera(String cabecera) {
		LocalDate presente = LocalDate.now();
		String salida = " Hoy es " + presente.format(FORMATO_DIA).toString();
		System.out.println(cabecera + salida);
	}

	// CREAMOS MÉTODO ELEGIR OPCION
	public static int elegirOpcion() {
		System.out.println("Por favor, elija una opción del menú");
		int eleccion = Entrada.entero();
		return eleccion;
	}

	// CREAMOS MÉTODO LEERAULA
	public static Aula leerAula() {
		Aula aula = new Aula(leerNombreAula(), leerNumeroPuestos());
		return new Aula(aula);
	}

	// CREAMOS MÉTODO LEERNOMBREAULA
	public static String leerNombreAula() {
		System.out.println("Introduzca el nombre del aula");
		String nombreAula = Entrada.cadena();
		return nombreAula;
	}

	// CREAMOS MÉTODO LEERPROFESOR
	public static Profesor leerProfesor() {
		String nombreProfesor = leerNombreProfesor();
		System.out.println("Introduzca el correo del profesor");
		String correoProfesor = Entrada.cadena();
		System.out.println("Introduzca el teléfono del profesor");
		String telefonoProfesor = Entrada.cadena();
		Profesor profesor = new Profesor(nombreProfesor, correoProfesor, telefonoProfesor);
		return new Profesor(profesor);
	}

	// CREAMOS MÉTODO LEERNOMBREPROFESOR
	public static String leerNombreProfesor() {
		System.out.println("Introduzca el nombre del profesor");
		String nombreProfesor = Entrada.cadena();
		return nombreProfesor;
	}

	// CREAMOS MÉTODO LEERTRAMO
	public static Tramo leerTramo() {
		Tramo tramoFinal = null;
		boolean problema = false;
		do {
			System.out.println("Elija un tramo horario:");
			System.out.println("1- Mañana");
			System.out.println("2- Tarde");
			int tramoElegido = Entrada.entero();
			if (tramoElegido == 1) {
				tramoFinal = Tramo.MANANA;
				problema = false;
			} else if (tramoElegido == 2) {
				tramoFinal = Tramo.TARDE;
				problema = false;
			} else {
				System.out.println("ERROR: El tramo introducido no es válido");
				problema = true;
			}
		} while (problema == true);
		return tramoFinal;
	}

	// CREAMOS MÉTODO LEERDIA
	public static LocalDate leerDia() {
		LocalDate fechaFinal = null;
		boolean problema = false;
		do {
			try {
				System.out.println("Introduzca una fecha(formato dd/mm/aaaa):");
				String fechaIntroducida = Entrada.cadena();
				fechaFinal = LocalDate.parse(fechaIntroducida, FORMATO_DIA);
				problema = false;

			} catch (DateTimeParseException e) {
				System.out.println("ERROR: Formato incorrecto");
				problema = true;
			}

		} while (problema == true);
		return fechaFinal;
	}

	// CREAMOS MÉTODO LEERPROFESORFICTICIO
	public static Profesor leerProfesorFicticio() {
		System.out.println("Introduzca el correo del profesor");
		String correoProfesor = Entrada.cadena();
		Profesor profesor = new Profesor("pepe", correoProfesor, "600121212");
		return new Profesor(profesor);
	}

	// CREAMOS MÉTODO LEERLEERNUMEROPUESTOS
	public static int leerNumeroPuestos() {
		System.out.println("Introduzca el número de puestos del aula");
		int puestosAula = Entrada.entero();
		return puestosAula;
	}

	// CREAMOS MÉTODO LEERAULAFICTICIA
	public static Aula leerAulaFicticia() {
		Aula aula = new Aula(leerNombreAula(), 20);
		return new Aula(aula);
	}

	// CREAMOS MÉTODO LEERHORA
	private static LocalTime leerHora() {
		LocalTime horaFinal = null;
		boolean problema = false;
		do {
			try {
				System.out.println("Introduzca una hora (formato hh:00)");
				String horaIntroducida = Entrada.cadena();
				horaFinal = LocalTime.parse(horaIntroducida);
				problema = false;
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: Formato incorrecto");
				problema = true;
			}
		} while (problema == true);
		return horaFinal;
	}

	// CREAMOS MÉTODO ELEGIRPERMANENCIA
	public static int elegirPermanencia() {
		int permanenciaElegida = 0;
		do {
			System.out.println("Seleccione una permanencia:");
			System.out.println("1- Por tramo (mañana o tarde)");
			System.out.println("2- Por horas");
			permanenciaElegida = Entrada.entero();
		} while (permanenciaElegida < 1 || permanenciaElegida > 2);
		return permanenciaElegida;
	}

	// CREAMOS MÉTODO LEERPERMANENCIA
	public static Permanencia leerPermanencia() {
		Permanencia permanenciaFinal = null;
		int permanenciaElegida = elegirPermanencia();
		if (permanenciaElegida == 1) {
			permanenciaFinal = new PermanenciaPorTramo(leerDia(), leerTramo());
		} else {
			permanenciaFinal = new PermanenciaPorHora(leerDia(), leerHora());
		}
		if (permanenciaFinal instanceof PermanenciaPorTramo)
			return new PermanenciaPorTramo((PermanenciaPorTramo) permanenciaFinal);
		else {
			return new PermanenciaPorHora((PermanenciaPorHora) permanenciaFinal);
		}
	}

	// CREAMOS MÉTODO LEERRESERVA
	public static Reserva leerReserva() {
		Reserva reservaFinal = new Reserva(leerProfesorFicticio(), leerAulaFicticia(), leerPermanencia());
		return new Reserva(reservaFinal);
	}

	// CREAMOS MÉTODO LEERRESERVAFICTICIA
	public static Reserva leerReservaFicticia() {
		Profesor profesor = new Profesor("pepe", "pepe@gmail.com", "600121212");
		Reserva reservaFinal = new Reserva(profesor, leerAulaFicticia(), leerPermanencia());
		return new Reserva(reservaFinal);
	}

}
