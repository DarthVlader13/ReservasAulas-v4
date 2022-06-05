package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

public enum Tramo {

	MANANA("MaĆ±ana"), TARDE("Tarde");

	private String cadenaAMostrar;

	private Tramo(String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}

	public String toString() {
		return cadenaAMostrar;
	}
}
