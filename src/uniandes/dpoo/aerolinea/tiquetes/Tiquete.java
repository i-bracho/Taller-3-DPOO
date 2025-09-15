package uniandes.dpoo.aerolinea.tiquetes;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class Tiquete {
	private String codigo;
	private Integer tarifa;
	private boolean usado;
	private Cliente cliente;
	private Vuelo vuelo;
	public Tiquete(String codigo, Integer tarifa, Cliente cliente, Vuelo vuelo) {
		super();
		this.codigo = codigo;
		this.tarifa = tarifa;
		this.usado = false;
		this.cliente = cliente;
		this.vuelo = vuelo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Integer getTarifa() {
		return tarifa;
	}
	public void setTarifa(Integer tarifa) {
		this.tarifa = tarifa;
	}
	public boolean isUsado() {
		return usado;
	}
	public void setUsado(boolean usado) {
		this.usado = usado;
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
	
	public void marcarComoUsado() {
		this.usado = true;
	}
	public boolean esUsado(){
		return this.usado;
	}
	
	
	

}
