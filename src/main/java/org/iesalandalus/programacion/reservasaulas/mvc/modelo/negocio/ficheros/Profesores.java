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
	private static final String NOMBRE_FICHERO_PROFESORES = "datos/profesores.dat";
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

		Comparator<Profesor> comparador = Comparator.comparing(Profesor::getCorreo);
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

	// Crea un archivo con la ruta pasada, luego abre un stream binario y después otro para objetos y copia toda coleccionAulas en el archivo. Captura
	// excepciones de haberlas
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

	// Crea un archivo con la ruta pasada, comprueba si existe y si no lo crea, y después crea un stream binario y otro de objetos y lo recorre, insertando
	// cada uno de los objetos que se encuentra. Cuando llega a
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

	@Override
	public void comenzar() {
		leer();
	}

	@Override
	public void terminar() {
		escribir();
	}

}
