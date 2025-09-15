package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class CalculadoraTemporadaAlta extends CalculadoraTarifas {
   
	
	public CalculadoraTemporadaAlta(Cliente cliente, Vuelo vuelo) {
		super(cliente, vuelo);
	}

	private static final int COSTO_POR_KM = 1000;

   

    @Override
    protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        int km = calcularDistanciaVuelo(vuelo.getRuta());
        return km * COSTO_POR_KM;
    }

    @Override
    protected double calcularPorcentajeDescuento(Cliente cliente) {
        return 0.0;
    }
}