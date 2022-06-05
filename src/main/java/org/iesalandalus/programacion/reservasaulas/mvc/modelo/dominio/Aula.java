package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Aula implements Serializable {
	private final static float PUNTOS_POR_PUESTO=0.5f;
	private final static int MIN_PUESTOS=10;
	private final static int MAX_PUESTOS=50;
	private String nombre;
	private int puestos;


	//	Getters y setters
	public String getNombre() {
		return nombre;
	}
	private void setNombre(String nombre) {
		if (nombre==null) {
			throw new NullPointerException("ERROR: El nombre del aula no puede ser nulo.");
		}
		else if (nombre.isBlank()) {
			throw new IllegalArgumentException("ERROR: El nombre del aula no puede estar vacío.");
		}
			this.nombre = nombre;
	}
	public int getPuestos() {
		return puestos;
	}
	private void setPuestos(int puestos) {
		if(puestos<MIN_PUESTOS||puestos>MAX_PUESTOS) {
			throw new IllegalArgumentException("ERROR: El número de puestos no es correcto.");
		}
		this.puestos = puestos;
	}
	
//	Constructor con parámetros
	public Aula (String nombre, int puestos){
		setNombre(nombre);
		setPuestos(puestos);
	}
	
//	Constructor copia validando null
	public Aula (Aula a) {
		if (a==null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		}
		setNombre(a.getNombre());
		setPuestos(a.getPuestos());
	}
	
	public float getPuntos() {
		float puntos=getPuestos()*PUNTOS_POR_PUESTO;
		return puntos;
	}

//Método que devuelve un aula ficticia ante un nombre dado
	public static Aula getAulaFicticia (String nombre) {
		Aula aula=new Aula (nombre,10);
		return new Aula(aula);
	}

	
//	Equals y hashCode
	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		return Objects.equals(nombre, other.nombre);
	}
	
	
//	toString
	@Override
	public String toString() {
		return "nombre=" + getNombre() + ", puestos=" + getPuestos();
	}
	
	public int compareTo(Aula aula) {
		int resultado=0;
		resultado=this.getNombre().compareTo(aula.getNombre());
		return resultado;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
