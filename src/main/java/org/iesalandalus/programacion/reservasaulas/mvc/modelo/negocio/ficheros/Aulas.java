package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas {

	// DECLARACIÓN DE ATRIBUTOS
	private static final String NOMBRE_FICHERO_AULAS = "datos/aulas.dat";
	private List<Aula> coleccionAulas;

	// CONSTRUCTOR VACIO
	public Aulas() {
		coleccionAulas = new ArrayList<>();
	}

	// CREAMOS MÉTODO AULA GET, CREANDO COPIA PROFUNDA DEL ARRAY LIST PARA EVITAR EL
	// ALIASING
	public List<Aula> getAulas() {
		return copiaProfundaAulas(coleccionAulas);
	}

	// CREAMOS CONSTRUCTOR COPIA
	public Aulas(IAulas a) {
		if (a == null) {
			throw new NullPointerException("ERROR: No se pueden copiar aulas nulas.");
		}
		setAulas(a);
	}

	// CREAMOS MÉTODO SETAULAS
	private void setAulas(IAulas aulas) {
		if (aulas == null) {
			throw new NullPointerException("ERROR: No se puede copiar un aula nula.");
		}
		this.coleccionAulas = aulas.getAulas();
	}

	// CREAMOS MÉTODO COPIAPROFUNDAAULAS
	private List<Aula> copiaProfundaAulas(List<Aula> listaAulas) {
		List<Aula> copiaProfunda = new ArrayList<>();

		Comparator<Aula> comparador = Comparator.comparing(Aula::getNombre);
		Collections.sort(coleccionAulas, comparador);

		Iterator<Aula> iterador = listaAulas.iterator();
		while (iterador.hasNext()) {
			copiaProfunda.add(new Aula(iterador.next()));
		}
		return copiaProfunda;
	}

	// CREAMOS MÉTODO NUMAULAS
	@Override
	public int getNumAulas() {
		return coleccionAulas.size();
	}

	// CREAMOS MÉTODO INSERTAR
	@Override
	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		} else if (buscar(aula) == null) {
			coleccionAulas.add(new Aula(aula));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
		}
	}

	// CREAMOS MÉTODO BUSCAR
	@Override
	public Aula buscar(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}
		Aula aulaEncontrada = null;
		int indice = coleccionAulas.indexOf(aula);
		if (indice == -1) {
			aulaEncontrada = null;
		} else {
			aulaEncontrada = new Aula(coleccionAulas.get(indice));
		}
		return aulaEncontrada;
	}

	// CREAMOS MÉTODO BORRAR
	@Override
	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		} else if (buscar(aula) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		} else {
			coleccionAulas.remove(coleccionAulas.indexOf(aula));
		}
	}

	// CREAMOS MÉTODO REPRESENTAR
	@Override
	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		Iterator<Aula> iterador = coleccionAulas.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}

	// CREAMOS MÉTODO LEER Y ESCRIBIR
	private void leer() {
		Aula aula = null;
		File archivoAulas = new File(NOMBRE_FICHERO_AULAS);
		try {
			if (!archivoAulas.exists()) {
				archivoAulas.createNewFile();
			} else {
				FileInputStream fileIn = new FileInputStream(archivoAulas);
				ObjectInputStream dataIS = new ObjectInputStream(fileIn);
				do {
					aula = (Aula) dataIS.readObject();
					if (aula != null) {
						insertar(aula);
					}
				} while (aula != null);
				dataIS.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se pudo abrir el fichero de aulas");
		} catch (IOException e) {
//			System.out.println("ERROR inesperado de Entrada/Salida en lectura");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No se pudo encontrar la clase a leer");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	private void escribir() {
		File archivoAulas = new File(NOMBRE_FICHERO_AULAS);
		try {
			FileOutputStream fileOut = new FileOutputStream(archivoAulas);
			ObjectOutputStream dataOS = new ObjectOutputStream(fileOut);
			for (Aula a : coleccionAulas) {
				dataOS.writeObject(a);
			}
			dataOS.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se pudo encontrar el fichero de aulas");
		} catch (IOException e) {
			System.out.println("ERROR inesperado de Entrada/Salida");
		}
	}

	@Override
	public void comenzar() {
		leer();
	}

	@Override
	public void terminar() {
		escribir();
	}
}
