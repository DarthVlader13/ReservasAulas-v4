package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public interface IModelo {

	List<Aula> getAulas();

	int getNumAulas();

	//Método representarAulas, que obtiene un array String de las aulas. Este método comprobará si dicho array solo contiene nulos y, de ser
	//así, devolverá nulo para que dicho resultado se trate más arriba
	List<String> representarAulas();

	//Método buscarAula, que busca un aula dada como parámetro y comprueba si es nula. Si lo es, significa que el aula no se encontró y 
	//retorna nulo y si no, retorna una copia del aula encontrada.
	Aula buscarAula(Aula aula);

	//Método que inserta un aula pasada como parámetro propagando excepciones
	void insertarAula(Aula aula) throws OperationNotSupportedException;

	//Método que borra un aula pasada como parámetro propagando excepciones
	void borrarAula(Aula aula) throws OperationNotSupportedException;

	List<Profesor> getProfesores();

	int getNumProfesores();

	//Método que hace el equivalente de representarAulas pero con profesores
	List<String> representarProfesores();

	//Método que hace el equivalente de buscarAulas pero con profesores
	Profesor buscarProfesor(Profesor profesor);

	//Método que inserta un profesor pasado como parámetro propagando excepciones
	void insertarProfesor(Profesor profesor) throws OperationNotSupportedException;

	//Método que borra un prpfesor pasado como parámetro propagando excepciones
	void borrarProfesor(Profesor profesor) throws OperationNotSupportedException;

	List<Reserva> getReservas();

	int getNumReservas();

	//Método que hace el equivalente de representarAulas pero con reservas
	List<String> representarReservas();

	//Método que hace el equivalente de buscarAulas pero con reservas
	Reserva buscarReserva(Reserva reserva);

	void realizarReserva(Reserva reserva) throws OperationNotSupportedException;

	void anularReserva(Reserva reserva) throws OperationNotSupportedException;

	//Método que hace el equivalente de representarAulas pero con un array de tipo Reserva
	List<Reserva> getReservasAula(Aula aula);

	//Método que hace el equivalente de representarAulas pero con un array de tipo Reserva y para profesores
	List<Reserva> getReservasProfesor(Profesor profesor);

	//Método que hace el equivalente de representarAulas pero con un array de tipo Reserva y para permamencias
	List<Reserva> getReservasPermanencia(Permanencia permanencia);

	//Método que correrá el método homónimo de Reservas y devolverá true si está disponible y false de lo contrario
	boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);
	
	public void comenzar();
	
	public void terminar();

}