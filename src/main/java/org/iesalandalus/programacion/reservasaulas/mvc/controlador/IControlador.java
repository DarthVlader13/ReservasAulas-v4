package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public interface IControlador {

	//Método comenzar, que corre el método homónimo de la Vista
	void comenzar();

	//Método terminar, que simplemente corre el método exit y cierra la ejecución
	void terminar();

	//Los siguientes métodos simplemente corren los métodos homónimos del modelo, recogen los parámetros que se les pasa desde la Vista
	//(que es desde donde estos métodos serán llamados) y le devuelve a la Vista los datos que retorna el modelo. También propaga las 
	//excepciones para que sean tratadas más arriba
	void insertarAula(Aula aula) throws OperationNotSupportedException;

	void insertarProfesor(Profesor profesor) throws OperationNotSupportedException;

	void borrarAula(Aula aula) throws OperationNotSupportedException;

	void borrarProfesor(Profesor profesor) throws OperationNotSupportedException;

	Aula buscarAula(Aula aula);

	Profesor buscarProfesor(Profesor profesor);

	List<String> representarAulas();

	List<String> representarProfesores();

	List<String> representarReservas();
	
	
	List<Profesor> getProfesores();
	
	List<Aula> getAulas();
	
	List<Reserva> getReservas();
	
	

	void realizarReserva(Reserva reserva) throws OperationNotSupportedException;

	void anularReserva(Reserva reserva) throws OperationNotSupportedException;

	List<Reserva> getReservasAula(Aula aula);

	List<Reserva> getReservasProfesor(Profesor profesor);

	List<Reserva> getReservasPermanencia(Permanencia permanencia);

	boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);

}