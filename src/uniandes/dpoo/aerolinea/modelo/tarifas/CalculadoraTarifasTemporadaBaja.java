package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas {
	
	protected static final int COSTO_POR_KM_NATURAL = 600;
    protected static final int COSTO_POR_KM_CORPORATIVO = 900;
    protected static final double DESCUENTO_PEQ = 0.02;
    protected static final double DESCUENTO_MEDIANAS = 0.1;
    protected static final double DESCUENTO_GRANDES = 0.2;

	public CalculadoraTarifasTemporadaBaja(Cliente cliente, Vuelo vuelo) {
		super(cliente, vuelo);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int calcularCostoBase(Vuelo vuelo, Cliente cliente) {
        int distancia = calcularDistanciaVuelo(vuelo.getRuta());
        if (cliente.esCorporativo()) {
            return distancia * COSTO_POR_KM_CORPORATIVO;
        } else {
            return distancia * COSTO_POR_KM_NATURAL;
        }
    }

	@Override
	protected double calcularPorcentajeDescuento(Cliente cliente) {
		 protected double calcularPorcentajeDescuento(Cliente cliente) {
		        if (cliente.esCorporativo()) {
		            int numEmpleados = cliente.getNumeroEmpleados();
		            if (numEmpleados < 10) return DESCUENTO_PEQ;
		            else if (numEmpleados < 50) return DESCUENTO_MEDIANAS;
		            else return DESCUENTO_GRANDES;
		        }
		        return 0.0;
		    }

	@Override
	protected int calcularValorImpuestos(int costoBase) {
		// TODO Auto-generated method stub
		return 0;
	}

}
