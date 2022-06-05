package org.iesalandalus.programacion.reservasaulas.mvc.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;

public class Controlador implements IControlador {
	private IModelo Imodelo;
	private IVista Ivista;
	
	public List<Profesor> getProfesores() {
		return Imodelo.getProfesores();
	}
	public List<Aula> getAulas(){
		return Imodelo.getAulas();
	}
	public List<Reserva> getReservas() {
		return Imodelo.getReservas();
	}
	
//Constructor del controlador, que toma como parámetros el Modelo y la Vista, comprueba nulos, y luego se asigna a sí mismo a la Vista
//para que ésta sepa quién es el Controlador.
	public Controlador(IModelo modelo, IVista vista) 
	{
		if (modelo == null) {
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new NullPointerException("ERROR: La vista no puede ser nula.");
		}
		this.Imodelo = modelo;
		this.Ivista = vista;
		this.Ivista.setControlador(this);
	}
	
//Método comenzar, que corre el método homónimo de la Vista
	@Override
	public void comenzar() {
		Imodelo.comenzar();
		Ivista.comenzar();

	}
	
//Método terminar, que simplemente corre el método exit y cierra la ejecución
	@Override
	public void terminar() {
		Imodelo.terminar();
		System.exit(0);
	}
	
//Los siguientes métodos simplemente corren los métodos homónimos del modelo, recogen los parámetros que se les pasa desde la Vista
//(que es desde donde estos métodos serán llamados) y le devuelve a la Vista los datos que retorna el modelo. También propaga las 
//excepciones para que sean tratadas más arriba
	@Override
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		Imodelo.insertarAula(aula);
	}
	
	@Override
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		Imodelo.insertarProfesor(profesor);
	}
	
	@Override
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		Imodelo.borrarAula(aula);
	}
	
	@Override
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		Imodelo.borrarProfesor(profesor);
	}
	
	@Override
	public Aula buscarAula(Aula aula) {
		Aula aulaBuscada=Imodelo.buscarAula(aula);
		return aulaBuscada;
	}
	
	@Override
	public Profesor buscarProfesor(Profesor profesor) {
		Profesor profesorBuscado=Imodelo.buscarProfesor(profesor);
		return profesorBuscado;
	}
	
	@Override
	public List<String> representarAulas() {
		List<String> listaAulas=Imodelo.representarAulas();
		return listaAulas;
	}
	
	@Override
	public List<String> representarProfesores() {
		List<String> listaProfesores=Imodelo.representarProfesores();
		return listaProfesores;
	}
	
	@Override
	public List<String> representarReservas() {
		List<String> listaReservas=Imodelo.representarReservas();
		return listaReservas;
	}
	
	@Override
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		Imodelo.realizarReserva(reserva);
	}
	
	@Override
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		Imodelo.anularReserva(reserva);
	}
	
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		List<Reserva> reservasAula=Imodelo.getReservasAula(aula);
		return reservasAula;
	}
	
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		List<Reserva> reservasProfesor=Imodelo.getReservasProfesor(profesor);
		return reservasProfesor;
	}
	
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		List<Reserva> reservasPermanencia=Imodelo.getReservasPermanencia(permanencia);
		return reservasPermanencia;
	}
	
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		boolean disponibilidad=Imodelo.consultarDisponibilidad(aula, permanencia);
		return disponibilidad;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
