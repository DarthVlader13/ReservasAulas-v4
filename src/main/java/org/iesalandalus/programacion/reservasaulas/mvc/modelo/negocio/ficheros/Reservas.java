package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Reservas implements IReservas {

	// DECLARACIÓN DE ATRIBUTOS
	private final static float MAX_PUNTOS_PROFESOR_MES = 200;
	private static final String NOMBRE_FICHERO_RESERVAS = "datos/reservas.dat";
	private List<Reserva> coleccionReservas;

	// CREAMOS MÉTODO GETRESERVAS
	@Override
	public List<Reserva> getReservas() {
		return copiaProfundaReservas(coleccionReservas);
	}

	// CREAMOS CONSTRUCTOR VACIO
	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}

	// CREAMOS CONSTRUCTOR COPIA
	public Reservas(IReservas r) {
		if (r == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		}
		setReservas(r);
	}

	// CREAMOS MÉTODO SETAULAS
	private void setReservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		}
		this.coleccionReservas = reservas.getReservas();
	}

	// CREAMOS MÉTODO GETNUMRESERVAS
	@Override
	public int getNumReservas() {
		return coleccionReservas.size();
	}

	// CREAMOS MÉTODO COPIAPROFUNDA DEL ARRAYLIST
	private List<Reserva> copiaProfundaReservas(List<Reserva> listaReservas) {
		List<Reserva> copiaProfunda = new ArrayList<>();
		Iterator<Reserva> iterador = listaReservas.iterator();
		while (iterador.hasNext()) {
			copiaProfunda.add(new Reserva(iterador.next()));
		}
		Comparator<Reserva> comparador = Comparator.comparing(Reserva::getAula, (s1, s2) -> {
			return s1.compareTo(s2);
		}).thenComparing(Reserva::getPermanencia, (s1, s2) -> {
			return s1.compareTo(s2);
		});
		Collections.sort(copiaProfunda, comparador);

		return copiaProfunda;
	}

	// CREAMOS MÉTODO GETPUNTOSGASTADOSRESERVA
	private float getPuntosGastadosReserva(Reserva reserva) {
		return reserva.getPuntos();
	}

	// CREAMOS MÉTODO INSERTAR
	@Override
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
		}
		Reserva reservaDia = getReservaAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		List<Reserva> reservasProfesor = getReservasProfesorMes(reserva.getProfesor(),
				reserva.getPermanencia().getDia());
		float puntosGastados = 0;
		for (Reserva r : reservasProfesor) {
			puntosGastados = puntosGastados + r.getPuntos();
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException(
					"ERROR: Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		} else if (puntosGastados + getPuntosGastadosReserva(reserva) > MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException(
					"ERROR: Esta reserva excede los puntos máximos por mes para dicho profesor.");
		} else if (reservaDia != null) {
			if ((reservaDia.getPermanencia() instanceof PermanenciaPorTramo
					&& reserva.getPermanencia() instanceof PermanenciaPorHora)
					|| (reservaDia.getPermanencia() instanceof PermanenciaPorHora
							&& reserva.getPermanencia() instanceof PermanenciaPorTramo)) {
				throw new OperationNotSupportedException(
						"ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este día.");
			}
		}
		if (buscar(reserva) == null) {
			coleccionReservas.add(new Reserva(reserva));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
		}
	}

	// CREAMOS MÉTODO BUSCAR
	@Override
	public Reserva buscar(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
		}
		Reserva reservaEncontrada = null;
		int indice = coleccionReservas.indexOf(reserva);
		if (indice == -1) {
			reservaEncontrada = null;
		} else {
			reservaEncontrada = new Reserva(coleccionReservas.get(indice));
		}
		return reservaEncontrada;
	}

	// CREAMOS MÉTODO BORRAR
	@Override
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
		} else if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException(
					"ERROR: Sólo se pueden anular reservas para el mes que viene o posteriores.");
		} else if (buscar(reserva) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ninguna reserva igual.");
		} else {
			coleccionReservas.remove(coleccionReservas.indexOf(reserva));
		}
	}

	// CREAMOS MÉTODO REPRESENTAR
	@Override
	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		Iterator<Reserva> iterador = getReservas().iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}

	// CREAMOS MÉTODO GETRESERVASPROFESOR
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		List<Reserva> listaProfesor = new ArrayList<>();		
		Iterator<Reserva> iterador = getReservas().iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar = iterador.next();
			if (profesor.equals(auxiliar.getProfesor())) {
				listaProfesor.add(new Reserva(auxiliar));
			}
		}
		return listaProfesor;
	}

	// CREAMOS MÉTODO GETRESERVASAULA
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		if (aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula.");
		}
		List<Reserva> listaAula = new ArrayList<>();
		
//		Comparator<Reserva> comparador=Comparator.comparing(Reserva::getPermanencia,
//		(s1,s2) -> {
//			return s1.compareTo(s2);
//		});
//		Collections.sort(coleccionReservas, comparador);
		
		Iterator<Reserva> iterador = getReservas().iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar = iterador.next();
			if (aula.equals(auxiliar.getAula())) {
				listaAula.add(new Reserva(auxiliar));
			}
		}
		return listaAula;
	}

	// CREAMOS MÉTODO GETRESERVASPERMANENCIA
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
		List<Reserva> listaPermanencia = new ArrayList<>();
		Iterator<Reserva> iterador = getReservas().iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar = iterador.next();
			if (permanencia.getDia().isEqual(auxiliar.getPermanencia().getDia())) {
				listaPermanencia.add(new Reserva(auxiliar));
			}
		}
		return listaPermanencia;
	}

	// CREAMOS MÉTODO CONSULTARDISPONIBILIDAD
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		} else if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
		boolean disponible = true;
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar = iterador.next();
			if (!esMesSiguienteOPosterior(Reserva.getReservaFicticia(aula, permanencia))) {
				disponible = false;
			} else if (aula.equals(auxiliar.getAula()) && permanencia.getDia().equals(auxiliar.getPermanencia().getDia())) {
				if ((permanencia instanceof PermanenciaPorHora && auxiliar.getPermanencia() instanceof PermanenciaPorTramo)
						|| (permanencia instanceof PermanenciaPorTramo && auxiliar.getPermanencia() instanceof PermanenciaPorHora)) {
					disponible = false;
				} else if (permanencia instanceof PermanenciaPorHora && auxiliar.getPermanencia() instanceof PermanenciaPorHora) {
					if (((PermanenciaPorHora) permanencia).getHora().equals(((PermanenciaPorHora) auxiliar.getPermanencia()).getHora())) {
						disponible = false;
					}
				} else if (permanencia instanceof PermanenciaPorTramo&& auxiliar.getPermanencia() instanceof PermanenciaPorTramo) {
					if (((PermanenciaPorTramo) permanencia).getTramo().equals(((PermanenciaPorTramo) auxiliar.getPermanencia()).getTramo())) {
						disponible = false;
					}
				}
			}
		}
		return disponible;
	}

	// CREAMOS MÉTODO ESMESSIGUIENTEOPOSTERIOR
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		if (reserva == null) {
			throw new NullPointerException("ERROR: La reserva no puede ser nula");
		}
		boolean mesSiguiente = false;
		Month mes = reserva.getPermanencia().getDia().getMonth();
		Month mesActual = LocalDate.now().getMonth();
		if (mes.getValue() > mesActual.getValue() && reserva.getPermanencia().getDia().isAfter(LocalDate.now())) {
			mesSiguiente = true;
		}
		return mesSiguiente;
	}

	// CREAMOS MÉTODO GETRESERVASPROFESORMES
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate fecha) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo");
		}
		List<Reserva> reservasMes = new ArrayList<>();
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar = iterador.next();
			Month mesLista = auxiliar.getPermanencia().getDia().getMonth();
			Month mesFecha = fecha.getMonth();
			if (profesor.equals(auxiliar.getProfesor()) && mesLista.getValue() == mesFecha.getValue()) {
				reservasMes.add(new Reserva(auxiliar));
			}
		}
		return reservasMes;
	}

	// CREAMOS MÉTODO GETRESERVAAULADIA
	@Override
	public Reserva getReservaAulaDia(Aula aula, LocalDate fecha) {
		if (aula == null) {
			throw new NullPointerException("ERROR: El aula no puede ser nula");
		}
		Reserva reservaDia = null;
		Iterator<Reserva> iterador = coleccionReservas.iterator();
		while (iterador.hasNext()) {
			Reserva auxiliar = iterador.next();
			if (aula.equals(auxiliar.getAula()) && fecha.equals(auxiliar.getPermanencia().getDia())) {
				reservaDia = new Reserva(auxiliar);
			}
		}
		return reservaDia;
	}

	// CREAMOS MÉTODO LEER Y ESCRIBIR
	private void leer() {
		Reserva reserva=null;
		File archivoReservas=new File(NOMBRE_FICHERO_RESERVAS);
		try {
			if (!archivoReservas.exists()) {
				archivoReservas.createNewFile();
			} else {
				FileInputStream fileIn = new FileInputStream(archivoReservas);
				ObjectInputStream dataIS=new ObjectInputStream(fileIn);
				do {
					reserva=(Reserva)dataIS.readObject();
					if (reserva!=null) {
						insertar(reserva);
					}
				} while (reserva!=null);
				dataIS.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se pudo abrir el fichero de reservas");
		} catch (IOException e) {
//			System.out.println("ERROR inesperado de Entrada/Salida en lectura");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No se pudo encontrar la clase a leer");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	private void escribir() {
		File archivoReservas=new File(NOMBRE_FICHERO_RESERVAS);
		try {
			FileOutputStream fileOut=new FileOutputStream(archivoReservas);
			ObjectOutputStream dataOS=new ObjectOutputStream(fileOut);
			for(Reserva r:coleccionReservas) {
				dataOS.writeObject(r);
			}
			dataOS.close();
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No se pudo encontrar el fichero de reservas");
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
