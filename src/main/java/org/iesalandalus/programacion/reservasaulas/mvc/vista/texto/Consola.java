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
	private final static DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	static Opcion[] opciones = Opcion.values();
	static Tramo[] tramos = Tramo.values();

	private Consola() {

	}

//Método que recorre el array de opciones y las va mostrando con el toString de Opcion
	public static void mostrarMenu() {
		mostrarCabecera(
				"Bienvenido al sistema de reservas del IES Al-Ándalus. Entra libremente y deja parte de la felicidad que traes contigo.");
		for (Opcion o : opciones) {
			System.out.println(o);
		}
	}

//Muestra un mensaje de cabecera
	public static void mostrarCabecera(String cabecera) {
		LocalDate presente = LocalDate.now();
		String salida = " Hoy es " + presente.format(FORMATO_DIA).toString();
		System.out.println(cabecera + salida);
	}

//Método que pide que se elija una opción y la devuelve. Se validará después con el método esOrdinalValido del método getOpcionSegunOrdinal de Opcion
	public static int elegirOpcion() {
		System.out.println("Por favor, elija una opción del menú");
		int eleccion = Entrada.entero();
		return eleccion;
	}

//Método que ejecuta leerNombreAula() y leerNumeroPuestos(). Luego crea un aula con dichos parámetros y retorna una copia
	public static Aula leerAula() {
		Aula aula = new Aula(leerNombreAula(), leerNumeroPuestos());
		return new Aula(aula);
	}

//Método que pide el nombre de un aula y lo devuelve
	public static String leerNombreAula() {
		System.out.println("Introduzca el nombre del aula");
		String nombreAula = Entrada.cadena();
		return nombreAula;
	}

	// Método que pide un número de puestos para el aula y lo devuelve
	public static int leerNumeroPuestos() {
		System.out.println("Introduzca el número de puestos del aula");
		int puestosAula = Entrada.entero();
		return puestosAula;
	}

	// Método que pide un nombre para el aula y crea un aula ficticia con dicho
	// nombre
	public static Aula leerAulaFicticia() {
		Aula aula = new Aula(leerNombreAula(), 20);
		return new Aula(aula);
	}

//Método que ejecuta leerNombreProfesor(), que es un método que pide un nombre para un profesor. Luego pide el correo y el teléfono y crea un profesor
//con dichos datos, para después retornar una copia
	public static Profesor leerProfesor() {
		String nombreProfesor = leerNombreProfesor();
		System.out.println("Introduzca el correo del profesor");
		String correoProfesor = Entrada.cadena();
		System.out.println("Introduzca el teléfono del profesor");
		String telefonoProfesor = Entrada.cadena();
		Profesor profesor = new Profesor(nombreProfesor, correoProfesor, telefonoProfesor);
		return new Profesor(profesor);
	}

//Método que pide el nombre de un profesor y lo devuelve
	public static String leerNombreProfesor() {
		System.out.println("Introduzca el nombre del profesor");
		String nombreProfesor = Entrada.cadena();
		return nombreProfesor;
	}

	// Método que pide el correo de un profesor y crea un profesor ficticio con
	// dicho correo
	public static Profesor leerProfesorFicticio() {
		System.out.println("Introduzca el correo del profesor");
		String correoProfesor = Entrada.cadena();
		Profesor profesor = new Profesor("pepe", correoProfesor, "600121212");
		return new Profesor(profesor);
	}

//Método que pide que se elija un tramo de entre los posibles tramos, que se muestran mediante un forEach y el .toString del enum Tramo. Luego compara,
//ignorando mayúsculas, si el tramo introducido coincide con alguno de los del array de tramos. Si es así, devuelve el Tramo correspondiente, y si no
//nos seguirá pidiendo que introduzcamos un tramo válido mediante la bandera problema
	public static Tramo leerTramo() {
		Tramo tramoFinal = null;
		boolean problema = false;
		do {
			System.out.println("Elija un tramo horario:");
			System.out.println("1- Mañana");
			System.out.println("2- Tarde");
			int tramoElegido = Entrada.entero();
			if (tramoElegido==1) {
				tramoFinal = Tramo.MANANA;
				problema = false;
			} else if (tramoElegido==2) {
				tramoFinal = Tramo.TARDE;
				problema = false;
			} else {
				System.out.println("ERROR: El tramo introducido no es válido");
				problema = true;
			}
		} while (problema == true);
		return tramoFinal;
	}

//Método que nos pide introducir un día con un formato dado. Seguirá pidiendo el día mientras no se introduzca con el formato correcto y, una vez esté
//o la fecha sea anterior a la presente, nos devuelve el LocalDate del día.
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

	//Método que pide una hora mientras no se introduzca en el formato correcto y después la devuelve
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

	// Método que pide al usuario que elija un tipo de permanencia
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

	//Método que se apoya en elegirPermanencia() para, una vez elegido el tipo, pedir los datos para crear una permanencia de un tipo u otro
	//y devolver una copia
	public static Permanencia leerPermanencia() {
		Permanencia permanenciaFinal=null;
		int permanenciaElegida = elegirPermanencia();
		if(permanenciaElegida==1) {
			permanenciaFinal=new PermanenciaPorTramo(leerDia(),leerTramo());
		}
		else {
			permanenciaFinal=new PermanenciaPorHora(leerDia(),leerHora());
		}
		if (permanenciaFinal instanceof PermanenciaPorTramo)
			return new PermanenciaPorTramo((PermanenciaPorTramo) permanenciaFinal);
		else {
			return new PermanenciaPorHora((PermanenciaPorHora) permanenciaFinal);
		}
	}

	//Método que crea una reserva a partir de un profesor ficticio, un aula ficticia y una permanencia dada
	public static Reserva leerReserva() {
		Reserva reservaFinal=new Reserva(leerProfesorFicticio(),leerAulaFicticia(),leerPermanencia());
		return new Reserva (reservaFinal);
	}
	
	//Método que crea una reserva ficticia a partir de un aula ficticia, una permanencia y un profesor inventado
	public static Reserva leerReservaFicticia() {
		Profesor profesor=new Profesor ("pepe", "pepe@gmail.com", "600121212");
		Reserva reservaFinal=new Reserva (profesor,leerAulaFicticia(),leerPermanencia());
		return new Reserva (reservaFinal);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
