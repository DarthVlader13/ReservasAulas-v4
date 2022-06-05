package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {
	private List<Profesor> coleccionProfesores;

//Este getter devuelve una copia profunda mediante un método específico para ello, para evitar aliasing
	@Override
	public List<Profesor> getProfesores() {
		return copiaProfundaProfesores(coleccionProfesores);
	}

//Constructor genérico
	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}

//Constructor copia validando null
	public Profesores(IProfesores p) {
		if (p == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		}
		setProfesores(p);
	}

//Méto setProfesores, que recibe un objeto tipo Profesores, lo convierte en un arraylist mediante getProfesores y lo asigna a coleccionProfesores
	private void setProfesores(IProfesores profesores) {
		if (profesores == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		}
		this.coleccionProfesores = profesores.getProfesores();
	}

//	Método que crea una copia profunda del arraylist y lo devuelve para así evitar aliasing.
	private List<Profesor> copiaProfundaProfesores(List<Profesor> listaProfesores) {
		List<Profesor> copiaProfunda = new ArrayList<>();
		
		Comparator<Profesor> comparador=Comparator.comparing(Profesor::getCorreo);
		Collections.sort(coleccionProfesores, comparador);
		
		Iterator<Profesor> iterador = listaProfesores.iterator();
		while (iterador.hasNext()) {
			copiaProfunda.add(new Profesor(iterador.next()));
		}
		return copiaProfunda;
	}

//Método getNumProfesores que nos devuelve el tamaño de coleccionProfesores
	@Override
	public int getNumProfesores() {
		return coleccionProfesores.size();
	}

//Método insertar, que a un Profesor dado comprueba si es nullo y si no, recorre coleccionProfesores buscando alguna coincidencia. De haberla, nos
//retorna una excepción avisándonos del profesor duplicado y si no, inserta una copia del Profesor pasado como parámetro
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		} else if (buscar(profesor) == null) {
			coleccionProfesores.add(new Profesor(profesor));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		}
	}

//Método buscar, que crea un Profesor y ante un Profesor dado como parámetro busca su índice con .indexOf. Si no existe, nos devolverá -1 y
//devolvemos nulo. Si devuelve otro número, será el índice de la coincidencia así que guardamos una copia de ese objeto mediante el índice
//obtenido y lo devolvemos
	@Override
	public Profesor buscar(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}
		Profesor profesorEncontrado = null;
		int indice = coleccionProfesores.indexOf(profesor);
		if (indice == -1) {
			profesorEncontrado = null;
		} else {
			profesorEncontrado = new Profesor(coleccionProfesores.get(indice));
		}
		return profesorEncontrado;
	}

//Método borrar, que comprueba nulo, comprueba que el profesor exista mediante buscar y después borra el profesor accediendo a su índice
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		} else if (buscar(profesor) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese correo.");
		} else {
			coleccionProfesores.remove(coleccionProfesores.indexOf(profesor));
		}
	}

//Creamos el método representar que creará un ArrayList de tipo String en el que guardaremos los .toString de los profesores para su posterior uso.
	@Override
	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		Iterator<Profesor> iterador = coleccionProfesores.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}
	
	@Override
	public void comenzar() {
		
	}
	
	@Override
	public void terminar() {
		
	}
	
	
}
