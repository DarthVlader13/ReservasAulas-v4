package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

public class PermanenciaPorTramo extends Permanencia {
	private static final int PUNTOS = 10;
	private Tramo tramo;

//	Constructor usando super para utilizar el constructor del padre para el día
	public PermanenciaPorTramo(LocalDate dia, Tramo tramo) {
		super(dia);
		setTramo(tramo);
	}

//	Constructor copia usando super para utilizar el constructor copia del padre para el día (que además valida el null de p)
	public PermanenciaPorTramo(PermanenciaPorTramo p) {
		super(p);
		setTramo(p.getTramo());
	}

//	Getters y setters
	public Tramo getTramo() {
		return tramo;
	}

	private void setTramo(Tramo tramo) {
		if (tramo == null) {
			throw new NullPointerException("ERROR: El tramo de una permanencia no puede ser nulo.");
		}
		this.tramo = tramo;
	}

	@Override
	public int getPuntos() {
		return PUNTOS;
	}

	// hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(getDia(), tramo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
		return Objects.equals(getDia(), other.getDia()) && tramo == other.tramo;
	}

//	Método toString
	@Override
	public String toString() {
		return "día=" + getDia().format(FORMATO_DIA) + ", tramo=" + tramo;
	}

	@Override
	public int compareTo(Permanencia o) {
		int resultado = 0;
		if (this.getDia().isAfter(o.getDia())) {
			resultado = 1;
		} else if (this.getDia().isBefore(o.getDia())) {
			resultado = -1;
		} else if (this.getDia().isEqual(o.getDia())) {
			if (o instanceof PermanenciaPorTramo) {
				PermanenciaPorTramo p = new PermanenciaPorTramo((PermanenciaPorTramo) o);
				if (this.getTramo().equals(Tramo.MANANA) && p.getTramo().equals(Tramo.TARDE)) {
					resultado = -1;
				} else if (this.getTramo().equals(Tramo.TARDE) && p.getTramo().equals(Tramo.MANANA)) {
					resultado = 1;
				} else {
					resultado = 0;
				}
			}
		}
		return resultado;
	}

}
