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

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {

	// DECLARACIÓN DE ATRIBUTOS
	private static final String NOMBRE_FICHERO_PROFESORES = "datos/profesores.dat";
	private List<Profesor> coleccionProfesores;

	// CREAMOS MÉTODO GETPROFESORES
	public List<Profesor> getProfesores() {
		return copiaProfundaProfesores(coleccionProfesores);
	}

	// CREAMOS MÉTODO SETPROFESORES
	private void setProfesores(Profesores profesores) {
		if (profesores == null) {
			throw new NullPointerException("ERROR: No se puede copiar un profesor nulo.");
		}
		this.coleccionProfesores = profesores.getProfesores();
	}

	// CONSTRUCTOR VACIO
	public Profesores() {
		coleccionProfesores = new ArrayList<>();
	}

	// CREAMOS CONSTRUCTOR COPIA
	public Profesores(Profesores p) {
		if (p == null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		}
		setProfesores(p);
	}

	// CREAMOS MÉTODO GETNUMPROFESORES
	public int getNumProfesores() {
		return coleccionProfesores.size();
	}

	// CREAMOS MÉTODO COPIAPROFUNDAPROFESORES
	private List<Profesor> copiaProfundaProfesores(List<Profesor> listaProfesores) {
		List<Profesor> copiaProfunda = new ArrayList<>();
		Iterator<Profesor> iterador = listaProfesores.iterator();
		while (iterador.hasNext()) {
			copiaProfunda.add(new Profesor(iterador.next()));
		}
		return copiaProfunda;
	}

	// CREAMOS MÉTODO INSERTAR
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		} else if (buscar(profesor) == null) {
			coleccionProfesores.add(new Profesor(profesor));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese nombre.");
		}
	}

	// CREAMOS MÉTODO BUSCAR
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

	// CREAMOS MÉTODO BORRAR
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		} else if (buscar(profesor) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese nombre.");
		} else {
			coleccionProfesores.remove(coleccionProfesores.indexOf(profesor));
		}
	}

	// CREAMOS MÉTODO REPRESENTAR
	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		Iterator<Profesor> iterador = coleccionProfesores.iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}
	
	// CREAMOS MÉTODO LEER Y ESCRIBIR
	private void leer() {
		Profesor profesor = null;
		File archivoProfesores = new File(NOMBRE_FICHERO_PROFESORES);
		try {
			if (!archivoProfesores.exists()) {
				archivoProfesores.createNewFile();
			} else {
				FileInputStream fileIn = new FileInputStream(archivoProfesores);
				ObjectInputStream dataIS = new ObjectInputStream(fileIn);
				do {
					profesor = (Profesor) dataIS.readObject();
					if (profesor != null) {
						insertar(profesor);
					}
				} while (profesor != null);
				dataIS.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se pudo abrir el fichero de profesores");
		} catch (IOException e) {
//				System.out.println("ERROR inesperado de Entrada/Salida en lectura");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No se pudo encontrar la clase a leer");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void escribir() {
		File archivoProfesores = new File(NOMBRE_FICHERO_PROFESORES);
		try {
			FileOutputStream fileOut = new FileOutputStream(archivoProfesores);
			ObjectOutputStream dataOS = new ObjectOutputStream(fileOut);
			for (Profesor p : coleccionProfesores) {
				dataOS.writeObject(p);
			}
			dataOS.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se pudo encontrar el fichero de profesores");
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
