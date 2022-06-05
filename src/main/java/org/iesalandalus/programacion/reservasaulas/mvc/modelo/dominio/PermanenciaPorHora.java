package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PermanenciaPorHora extends Permanencia{
	private final static int PUNTOS = 3;
	private final static LocalTime HORA_INICIO = LocalTime.of(8, 0);
	private final static LocalTime HORA_FIN = LocalTime.of(22, 0);
	protected final static DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
	private LocalTime hora;

//	Getters y setters
	public LocalTime getHora() {
		return hora;
	}

	private void setHora(LocalTime hora) {
		if (hora == null) {
			throw new NullPointerException("ERROR: La hora de una permanencia no puede ser nula.");
		} else if (hora.isBefore(HORA_INICIO) || hora.isAfter(HORA_FIN)) {
			throw new IllegalArgumentException("ERROR: La hora de una permanencia no es válida.");
		} else if (hora.getMinute() != 0) {
			throw new IllegalArgumentException("ERROR: La hora de una permanencia debe ser una hora en punto.");
		}
		this.hora = hora;
	}

//	Constructor que utiliza super para asignar el dia con la clase padre
	public PermanenciaPorHora(LocalDate dia, LocalTime hora) {
		super(dia);
		setHora(hora);
	}

//	Constructor copia que utiliza super para asignar el día con la clase padre (validando null)
	public PermanenciaPorHora(PermanenciaPorHora p) {
		super(p);
		setHora(p.getHora());
	}

	@Override
	public int getPuntos() {
		return PUNTOS;
	}

//	Métodos hashChode y equals
	@Override
	public int hashCode() {
		return Objects.hash(getDia(), hora);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermanenciaPorHora other = (PermanenciaPorHora) obj;
		return Objects.equals(getDia(), other.getDia()) && hora == other.hora;
	}

	// Método toString
	@Override
	public String toString() {
		return "día=" + getDia().format(FORMATO_DIA) + ", hora=" + hora.format(FORMATO_HORA);
	}

	@Override
	public int compareTo(Permanencia o) {
		int resultado = 0;
		if (this.getDia().isAfter(o.getDia())) {
			resultado = 1;
		} else if (this.getDia().isBefore(o.getDia())) {
			resultado = -1;
		} else if (this.getDia().isEqual(o.getDia())) {
			if (o instanceof PermanenciaPorHora) {
				PermanenciaPorHora p = new PermanenciaPorHora((PermanenciaPorHora) o);
				if (this.getHora().isAfter(p.getHora())) {
					resultado = 1;
				} else if (this.getHora().isBefore(p.getHora())) {
					resultado = -1;
				} else {
					resultado = 0;
				}
			}
		}

		return resultado;
	}

}
