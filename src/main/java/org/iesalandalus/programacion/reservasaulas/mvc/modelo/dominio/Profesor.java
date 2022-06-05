package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profesor implements Serializable{
	private static final String ER_TELEFONO = "([0-9]{9})";
	private static final String ER_CORREO = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private String nombre;
	private String correo;
	private String telefono;

//	Getters y setters validando null, blank y formato
	public String getNombre() {
		return nombre;
	}

//Este setter hace uso del método formateaNombre para asignar el nombre con el formato adecuado
	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre del profesor no puede ser nulo.");
		} else if (nombre.isBlank()) {
			throw new IllegalArgumentException("ERROR: El nombre del profesor no puede estar vacío.");
		}
		this.nombre = formateaNombre(nombre);
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {

		if (correo == null) {
			throw new NullPointerException("ERROR: El correo del profesor no puede ser nulo.");
		}

		Pattern pat = Pattern.compile(ER_CORREO);
		Matcher mat = pat.matcher(correo);
		if (mat.matches() == false) {
			throw new IllegalArgumentException("ERROR: El correo del profesor no es válido.");
		}
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		if (telefono == null) {
			this.telefono = telefono;
		} else {
			Pattern pat = Pattern.compile(ER_TELEFONO);
			Matcher mat = pat.matcher(telefono);
			if (mat.matches() == false) {
				throw new IllegalArgumentException("ERROR: El teléfono del profesor no es válido.");
			}
		}
		this.telefono = telefono;
	}

//	Contructor con dos parámetros
	public Profesor(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
	}

//	Constructor con tres parámetros
	public Profesor(String nombre, String correo, String telefono) {
		setNombre(nombre);
		setCorreo(correo);
		setTelefono(telefono);
	}

//	Constructor copia validando null
	public Profesor(Profesor p) {
		if (p == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		}
		setNombre(p.getNombre());
		setCorreo(p.getCorreo());
		setTelefono(p.getTelefono());
	}

//Método formateaNombre, que borra todos los espacios dobles y todos aquellos al principio y al final del nombre. Luego crea un array de tipo
//Char donde se copia el nombre y después lo recorre, poniendo en mayúsculas todo Char que venga después de un espacio. Por último, pone la
//primera letra del nombre en mayúscula, lo convierte en String y lo devuelve.
	private String formateaNombre(String nombreSinFormato) {
		String nombre = nombreSinFormato.trim().replaceAll("\\s{2,}", " ").toLowerCase();
		char cadenaChar[] = new char[nombre.length()];
		cadenaChar=nombre.toCharArray();
		for (int i = 0; i < cadenaChar.length; ++i) {
			if (cadenaChar[i] == ' ') {
				cadenaChar[i + 1] = Character.toUpperCase(cadenaChar[i + 1]);
			}
		}
		cadenaChar[0] = Character.toUpperCase(cadenaChar[0]);
		nombre = String.valueOf(cadenaChar);
		return nombre;
	}
	
//Método que devuelve un profesor ficticio ante un correo dado
	public static Profesor getProfesorFicticio (String correo) {
		Profesor profesor=new Profesor("Pepe",correo,"600121212");
		return new Profesor(profesor);
	}
	
//	Equals y hashCode
	@Override
	public int hashCode() {
		return Objects.hash(correo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(correo, other.correo);
	}
	
	@Override
	public String toString() {
		if (telefono != null) {
			return "nombre=" + nombre + ", correo=" + correo + ", teléfono=" + telefono;
		}
		return "nombre=" + nombre + ", correo=" + correo;
	}



}
