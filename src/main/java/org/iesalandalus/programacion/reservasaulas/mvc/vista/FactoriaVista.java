package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import org.iesalandalus.programacion.reservasaulas.mvc.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.texto.VistaTexto;

public enum FactoriaVista {
	TEXTO {
		@Override
		public IVista crear() {
			IVista texto=new VistaTexto();
			return texto;
		}
	},

	GRAFICA {
		@Override
		public IVista crear() {
			IVista grafica=new VistaGrafica();
			return grafica;
		}
	};
	
	public abstract IVista crear();
	
	
}
