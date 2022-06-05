package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Permanencia implements Serializable{
	private LocalDate dia;
	protected final static DateTimeFormatter FORMATO_DIA=DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
//	Getters y setters, validando posibles null
	public LocalDate getDia() {
		return dia;
	}
	private void setDia(LocalDate dia) {
		if (dia==null) {
			throw new NullPointerException("ERROR: El día de una permanencia no puede ser nulo.");
		}
		this.dia = dia;
	}

//	Constructor con parámetros
	public Permanencia (LocalDate dia) {
		setDia(dia);
	}
	
//	Constructor copia, validando posible null
	public Permanencia (Permanencia p) {
		if (p==null) {
			throw new NullPointerException("ERROR: No se puede copiar una permanencia nula.");
		}
		setDia(p.getDia());
	}
	
	
//	Método getPuntos abstract
	public abstract int getPuntos();
	
//	Métodos equals y hashcode en abstract
	@Override
	public abstract int hashCode();
	@Override
	public abstract boolean equals(Object obj);
	
//	Creamos el método toString
	@Override
	public String toString() {
		return "dia=" + dia.format(FORMATO_DIA);
	}
	
	
	public abstract int compareTo(Permanencia p);
	
	
	
	
	
	
	
	
	
	
	
	
	
}
