package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

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
	private final static float MAX_PUNTOS_PROFESOR_MES = 200;
	private List<Reserva> coleccionReservas;

//Este getter devuelve una copia profunda mediante un método específico para ello, para evitar aliasing
	@Override
	public List<Reserva> getReservas() {
		return copiaProfundaReservas(coleccionReservas);
	}

//Constructor genérico
	public Reservas() {
		coleccionReservas = new ArrayList<>();
	}

//Constructor copia validando null
	public Reservas(IReservas r) {
		if (r == null) {
			throw new NullPointerException("ERROR: No se pueden copiar reservas nulas.");
		}
		setReservas(r);
	}

//Méto setAulas, que recibe un objeto tipo Aulas, lo convierte en un arraylist mediante getAulas y lo asigna a coleccionAulas
	private void setReservas(IReservas reservas) {
		if (reservas == null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		}
		this.coleccionReservas = reservas.getReservas();
	}

//	Método que crea una copia profunda del arraylist y lo devuelve para así evitar aliasing.
	private List<Reserva> copiaProfundaReservas(List<Reserva> listaReservas) {
		List<Reserva> copiaProfunda = new ArrayList<>();
		Iterator<Reserva> iterador = listaReservas.iterator();
		while (iterador.hasNext()) {
			copiaProfunda.add(new Reserva(iterador.next()));
		}
		Comparator<Reserva> comparador=Comparator.comparing(Reserva::getAula,
				(s1,s2) -> {
					return s1.compareTo(s2);
				}).thenComparing(Reserva::getPermanencia,
				(s1,s2) -> {
					return s1.compareTo(s2);
				});
		Collections.sort(copiaProfunda, comparador);
		
		return copiaProfunda;
	}

//Método getNumAulas que nos devuelve el tamaño de coleccionAulas
	@Override
	public int getNumReservas() {
		return coleccionReservas.size();
	}

//Método buscar, que crea un Aula y ante un Aula dada como parámetro busca su índice con .indexOf. Si no existe, nos devolverá -1 y
//devolvemos nulo. Si devuelve otro número, será el índice de la coincidencia así que guardamos una copia de ese objeto mediante el índice
//obtenido y lo devolvemos
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

//Método borrar, que comprueba nulo, comprueba que el aula exista mediante buscar y después borra el aula accediendo a su índice
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

//Creamos el método representar que creará un ArrayList de tipo String en el que guardaremos los .toString de las aulas para su posterior uso.
	@Override
	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		Iterator<Reserva> iterador = getReservas().iterator();
		while (iterador.hasNext()) {
			representacion.add(iterador.next().toString());
		}
		return representacion;
	}

//Método que recibe un Profesor, valida en null y después crea una lista de Reserva y un iterador para recorrerla. Utilizaremos una variable auxiliar
//para guardar cada Reserva para después sacar su profesor con el método apropiado y compararlo con el del parámetro. De haber coincidencia, copiamos
//dicha Reserva en la lista creada y finalmente la devolvemos.
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

//Método que recibe un Aula, valida en null y después crea una lista de Reserva y un iterador para recorrerla. Utilizaremos una variable auxiliar
//para guardar cada Reserva para después sacar su aula con el método apropiado y compararla con el del parámetro. De haber coincidencia, copiamos
//dicha Reserva en la lista creada y finalmente la devolvemos.
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

//Método que recibe una Permanencia, valida en null y después crea una lista de Reserva y un iterador para recorrerla. Utilizaremos una variable auxiliar
//para guardar cada Reserva para después sacar su permanencia con el método apropiado y compararla con el del parámetro. De haber coincidencia, copiamos
//dicha Reserva en la lista creada y finalmente la devolvemos.
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

//Método que ante un Aula y una Permanencia pasadas como parámetros, valida nulo y después crea un iterador para recorrer coleccionReservas. Usa una
//variable auxiliar para ir guardando cada Reserva de la iteración y comprueba mediante los métodos apropiados si el aula y la permanencia de dicha
//Reserva coinciden con las pasadas como parámetros. De ser así, devuelve una disponibilidad false, de lo contrario la devuelve como true
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

//Método que ante una Reserva pasada como parámetro valida null, luego extrae el mes de dicha Reserva en una variable Month y guarda en otra el mes
//extraído de la fecha presente. Luego las compara y si el mes de la reserva es mayor que el presente, retorna true
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

//Método que recibe un Profesor y un LocalDate como parámetros. Valida null y luego crea una lista y un iterador que irá guardando en una variable
//auxiliar la Reserva por la que itere. Extrae del auxiliar el mes, así como el del LocalDate del parámetro. Luego compara el profesor del auxiliar,
//obtenido con el método adecuado, y ambos meses. Si todo coincide, copia el auxiliar en la lista creada. Finalmente retorna la lista
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

	// Método que recibe un Aula y un LocalDate como parámetros. Valida null y luego
	// crea un iterador que guarda en una variable auxiliar la reserva que
	// está iterando. Si el aula y la fecha del auxiliar, extraídos con los métodos
	// adecuados, coinciden con las de los parámetros, hace una copia del
	// auxiliar y lo retorna. Si no, retorna null
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

	// Método que obtiene los puntos de una reserva pasada como parámetro
	private float getPuntosGastadosReserva(Reserva reserva) {
		return reserva.getPuntos();
	}

	// Método insertar, que a un Aula dada comprueba si es nullo y si no, recorre
	// coleccionAulas buscando alguna coincidencia. De haberla, nos
	// retorna una excepción avisándonos del aula duplicada y si no, inserta una
	// copia del Aula pasada como parámetro
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
	
	@Override
	public void comenzar() {
		
	}
	
	@Override
	public void terminar() {
		
	}

}
