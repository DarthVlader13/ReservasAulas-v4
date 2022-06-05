package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Reserva implements Serializable{
	private Profesor profesor;
	private Aula aula;
	private Permanencia permanencia;
	
//	Getters y setters
	public Profesor getProfesor() {
		return new Profesor(profesor);
	}
	private void setProfesor(Profesor profesor) {
		if (profesor==null) {
			throw new NullPointerException("ERROR: La reserva debe estar a nombre de un profesor.");
		}
		this.profesor = new Profesor(profesor);
	}
	public Aula getAula() {
		return new Aula(aula);
	}
	private void setAula(Aula aula) {
		if(aula==null) {
			throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
		}
		this.aula = new Aula(aula);
	}
	
	//Nos devuelve una copia de la permanencia discriminando si ésta es por hora o por tramo
	public Permanencia getPermanencia() {
		Permanencia permanenciaCopia=null;
		if(permanencia instanceof PermanenciaPorHora) {
			permanenciaCopia=new PermanenciaPorHora((PermanenciaPorHora)permanencia);
		}
		else if (permanencia instanceof PermanenciaPorTramo) {
			permanenciaCopia=new PermanenciaPorTramo((PermanenciaPorTramo)permanencia);
		}
		return permanenciaCopia;
	}
	
	//Valida null y luego asigna una copia de la permanencia discriminando si ésta es por hora o por tramo
	private void setPermanencia(Permanencia permanencia) {
		if(permanencia==null) {
			throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
		}
		else if(permanencia instanceof PermanenciaPorHora) {
			this.permanencia=new PermanenciaPorHora((PermanenciaPorHora) permanencia);
		}
		else if(permanencia instanceof PermanenciaPorTramo) {
			this.permanencia=new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
		}
	}
	
//	Creamos el constructor con tres parámetros
	public Reserva (Profesor profesor, Aula aula, Permanencia permanencia) {
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);
	}
	
//	Creamos el constructor copia validando null
	public Reserva (Reserva r) {
		if(r==null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		}
		setProfesor(r.getProfesor());
		setAula(r.getAula());
		setPermanencia(r.getPermanencia());
	}
	
//Método que recibe un aula y una permanencia como parámetros, obtiene un profesor mediante otro método y devuelve una reserva
	public static Reserva getReservaFicticia(Aula aula,Permanencia permanencia) {
		Reserva reserva=new Reserva(Profesor.getProfesorFicticio("pepe@gmail.com"),aula,permanencia);
		return new Reserva(reserva);
	}
	
//	Método que devuelve la suma de los puntos de la permanencia y del aula
	public float getPuntos() {
		return permanencia.getPuntos()+aula.getPuntos();
	}
	
//	Equals y hashCode
	@Override
	public int hashCode() {
		return Objects.hash(aula, permanencia);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(aula, other.aula) && Objects.equals(permanencia, other.permanencia);
	}
	
//	Método toString
	@Override
	public String toString() {
		return profesor.toString() + ", " + aula.toString() + ", " + permanencia.toString() + ", " + String.format("puntos=%.1f",getPuntos());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
