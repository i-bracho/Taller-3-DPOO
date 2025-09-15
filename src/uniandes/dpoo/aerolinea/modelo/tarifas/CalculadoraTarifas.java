package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public abstract class CalculadoraTarifas {
	public static final double IMPUESTO = 0.28;
	protected Cliente cliente;
	protected Vuelo vuelo;
	
	
	public CalculadoraTarifas(Cliente cliente, Vuelo vuelo) {
		this.cliente = cliente;
		this.vuelo = vuelo;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vuelo getVuelo() {
		return vuelo;
	}

	public void setVuelo(Vuelo vuelo) {
		this.vuelo = vuelo;
	}

	public int calcularTarifa(Vuelo vuelo, Cliente cliente) {
	        int costoBase = calcularCostoBase(vuelo, cliente);
	        double descuento = calcularPorcentajeDescuento(cliente);
	        int costoConDescuento = (int) Math.round(costoBase * (1 - descuento));
	        int valorImpuestos = calcularValorImpuestos(costoConDescuento);
	        return costoConDescuento + valorImpuestos;
	    }
	
	protected abstract int calcularCostoBase(Vuelo vuelo, Cliente cliente);
	
	protected abstract double calcularPorcentajeDescuento(Cliente cliente);
	
	protected int calcularDistanciaVuelo(Ruta ruta) {
		return Aeropuerto.calcularDistancia(ruta.getOrigen(), ruta.getDestino());
	}
	
	protected int calcularValorImpuestos(int costoBase) {
		    return (int) Math.round(costoBase * IMPUESTO);
		}
	}


