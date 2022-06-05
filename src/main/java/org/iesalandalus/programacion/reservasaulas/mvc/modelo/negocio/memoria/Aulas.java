package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas{
	private List<Aula> coleccionAulas;

//Este getter devuelve una copia profunda mediante un método específico para ello, para evitar aliasing
	@Override
	public List<Aula> getAulas() {
		return copiaProfundaAulas(coleccionAulas);
	}

//Constructor genérico
	public Aulas() {
		coleccionAulas = new ArrayList<>();
	}

//Constructor copia validando null
	public Aulas(IAulas a) {
		if (a == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		setAulas(a);
	}

//Méto setAulas, que recibe un objeto tipo Aulas, lo convierte en un arraylist mediante getAulas y lo asigna a coleccionAulas
	private void setAulas(IAulas aulas) {
		if (aulas == null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		}
		this.coleccionAulas = aulas.getAulas();
	}

//	Método que crea una copia profunda del arraylist y lo devuelve para así evitar aliasing.
	private List<Aula> copiaProfundaAulas(List<Aula> listaAulas) {
		List<Aula> copiaProfunda = new ArrayList<>();
		
		Comparator<Aula> comparador=Comparator.comparing(Aula::getNombre);
		Collections.sort(coleccionAulas, comparador);
		
		Iterator<Aula> iterador = listaAulas.iterator();
		while (iterador.hasNext()) {
			copiaProfunda.add(new Aula(iterador.next()));
		}
		return copiaProfunda;
	}

//Método getNumAulas que nos devuelve el tamaño de coleccionAulas
	@Override
	public int getNumAulas() {
		return coleccionAulas.size();
	}

//Método insertar, que a un Aula dada comprueba si es nullo y si no, recorre coleccionAulas buscando alguna coincidencia. De haberla, nos
//retorna una excepción avisándonos del aula duplicada y si no, inserta una copia del Aula pasada como parámetro
	@Override
	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}
		else if (buscar(aula) == null) {
			coleccionAulas.add(new Aula(aula));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
		}
	}

	//Método buscar, que crea un Aula y ante un Aula dada como parámetro busca su índice con .indexOf. Si no existe, nos devolverá -1 y
	//devolvemos nulo. Si devuelve otro número, será el índice de la coincidencia así que guardamos una copia de ese objeto mediante el índice
	//obtenido y lo devolvemos
	@Override
	public Aula buscar(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}
		Aula aulaEncontrada = null;
		int indice=coleccionAulas.indexOf(aula);
		if(indice==-1) {
			aulaEncontrada=null;
		} else {
			aulaEncontrada=new Aula(coleccionAulas.get(indice));
		}
		return aulaEncontrada;
	}

//Método borrar, que comprueba nulo, comprueba que el aula exista mediante buscar y después borra el aula accediendo a su índice
	@Override
	public void borrar (Aula aula) throws OperationNotSupportedException {
		if(aula==null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}
		else if(buscar(aula)==null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		} else {
			coleccionAulas.remove(coleccionAulas.indexOf(aula));
		}
	}

//Creamos el método representar que creará un ArrayList de tipo String en el que guardaremos los .toString de las aulas para su posterior uso.
	@Override
	public List<String> representar() {
		List<String> representacion=new ArrayList<>();

//		Comparator<Aula> comparador=Comparator.comparing(Aula::getNombre,
//		(s1,s2) -> {
//			return s1.compareTo(s2);
//		});

		Iterator<Aula> iterador=coleccionAulas.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}
	
	public void comenzar() {
		
	}
	
	public void terminar() {
		
	}
}
