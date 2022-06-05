package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Modelo implements IModelo {
	private IAulas aulas;
	private IProfesores profesores;
	private IReservas reservas;
	
//Constructor que crea los objetos anteriormente mencionados
//	public Modelo(){
//		aulas=FactoriaFuenteDatos.FICHEROS.crear().crearAulas();
//		profesores=FactoriaFuenteDatos.FICHEROS.crear().crearProfesores();
//		reservas=FactoriaFuenteDatos.FICHEROS.crear().crearReservas();;
//	}
	
	public Modelo(IFuenteDatos fuenteDatos) {
		aulas=fuenteDatos.crearAulas();
		profesores=fuenteDatos.crearProfesores();
		reservas=fuenteDatos.crearReservas();
	}
	
	@Override
	public List<Aula> getAulas(){
		return aulas.getAulas();
	}
		
	@Override
	public int getNumAulas() {
		return aulas.getNumAulas();
	}
		
//Método representarAulas, que obtiene un array String de las aulas. Este método comprobará si dicho array solo contiene nulos y, de ser
//así, devolverá nulo para que dicho resultado se trate más arriba
	@Override
	public List<String> representarAulas() {
		List<String> listaAulas=aulas.representar();
		boolean vacio=true;
		Iterator<String> iterador=listaAulas.iterator();
		while (iterador.hasNext()) {
			String auxiliar=iterador.next();
			if(auxiliar!=null) {
				vacio=false;
			}		
		}
		if(vacio==true) {
			return null;
		}
		return listaAulas;
	}

//Método buscarAula, que busca un aula dada como parámetro y comprueba si es nula. Si lo es, significa que el aula no se encontró y 
//retorna nulo y si no, retorna una copia del aula encontrada.
	@Override
	public Aula buscarAula(Aula aula){
		Aula aulaEncontrada=aulas.buscar(aula);
		if(aulaEncontrada==null) {
			return null;
		}
		else {
			return new Aula(aulaEncontrada);
		}
	}
	
//Método que inserta un aula pasada como parámetro propagando excepciones
	@Override
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		aulas.insertar(aula);
	}
	
//Método que borra un aula pasada como parámetro propagando excepciones
	@Override
	public void borrarAula(Aula aula) throws OperationNotSupportedException{
		aulas.borrar(aula);
	}
	
	@Override
	public List<Profesor> getProfesores() {
		return profesores.getProfesores();
	}
	
	@Override
	public int getNumProfesores() {
		return profesores.getNumProfesores();
	}
	
//Método que hace el equivalente de representarAulas pero con profesores
	@Override
	public List<String> representarProfesores() {
		List<String> listaProfesores=profesores.representar();
		boolean vacio=true;
		Iterator<String> iterador=listaProfesores.iterator();
		while (iterador.hasNext()) {
			String auxiliar=iterador.next();
			if(auxiliar!=null) {
				vacio=false;
			}		
		}
		if(vacio==true) {
			return null;
		}
		return listaProfesores;
	}

//Método que hace el equivalente de buscarAulas pero con profesores
	@Override
	public Profesor buscarProfesor(Profesor profesor){
		Profesor profesorEncontrado=profesores.buscar(profesor);
		if(profesorEncontrado==null) {
			return null;
		}
		return new Profesor(profesorEncontrado);
	}
	
//Método que inserta un profesor pasado como parámetro propagando excepciones
	@Override
	public void insertarProfesor (Profesor profesor) throws OperationNotSupportedException {
		profesores.insertar(profesor);
	}
	
//Método que borra un prpfesor pasado como parámetro propagando excepciones
	@Override
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		profesores.borrar(profesor);
	}
	
	@Override
	public List<Reserva> getReservas() {
		return reservas.getReservas();
	}
	
	@Override
	public int getNumReservas() {
		return reservas.getNumReservas();
	}
	
//Método que hace el equivalente de representarAulas pero con reservas
	@Override
	public List<String> representarReservas() {
		List<String> listaReservas=reservas.representar();
		boolean vacio=true;
		Iterator<String> iterador=listaReservas.iterator();
		while (iterador.hasNext()) {
			String auxiliar=iterador.next();
			if(auxiliar!=null) {
				vacio=false;
			}		
		}
		if(vacio==true) {
			return null;
		}
		return listaReservas;
	}

//Método que hace el equivalente de buscarAulas pero con reservas
	@Override
	public Reserva buscarReserva(Reserva reserva){
		Reserva reservaEncontrada=reservas.buscar(reserva);
		if(reservaEncontrada==null) {
			return null;
		}
		return new Reserva(reservaEncontrada);
	}
	
	@Override
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		reservas.insertar(reserva);
	}
	
	@Override
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		reservas.borrar(reserva);
	}
	
//Método que hace el equivalente de representarAulas pero con un array de tipo Reserva
	@Override
	public List<Reserva> getReservasAula(Aula aula){
		List<Reserva> reservasAula=reservas.getReservasAula(aula);
		boolean vacio=true;
		Iterator<Reserva> iterador=reservasAula.iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar=iterador.next();
			if(auxiliar!=null) {
				vacio=false;
			}		
		}
		if(vacio==true) {
			return null;
		}
		return reservasAula;
	}
	
//Método que hace el equivalente de representarAulas pero con un array de tipo Reserva y para profesores
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor){
		List<Reserva> reservasProfesor=reservas.getReservasProfesor(profesor);
		boolean vacio=true;
		Iterator<Reserva> iterador=reservasProfesor.iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar=iterador.next();
			if(auxiliar!=null) {
				vacio=false;
			}		
		}
		if(vacio==true) {
			return null;
		}
		return reservasProfesor;
	}
	
//Método que hace el equivalente de representarAulas pero con un array de tipo Reserva y para permamencias
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		List<Reserva> reservasPermanencia=reservas.getReservasPermanencia(permanencia);
		boolean vacio=true;
		Iterator<Reserva> iterador=reservasPermanencia.iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar=iterador.next();
			if(auxiliar!=null) {
				vacio=false;
			}		
		}
		if(vacio==true) {
			return null;
		}
		return reservasPermanencia;
	}
	
//Método que correrá el método homónimo de Reservas y devolverá true si está disponible y false de lo contrario
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		return reservas.consultarDisponibilidad(aula, permanencia);
	}
	
	
	@Override
	public void comenzar() {
		aulas.comenzar();
		profesores.comenzar();
		reservas.comenzar();
	}
	
	@Override
	public void terminar() {
		aulas.terminar();
		profesores.terminar();
		reservas.terminar();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
