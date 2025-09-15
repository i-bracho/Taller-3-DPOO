package uniandes.dpoo.aerolinea.modelo;

public class Avion {
	private String nombre;
	private Integer capacidad;
	
	public Avion(String nombre, Integer capacidad) {
		this.nombre = nombre;
		this.capacidad = capacidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	

}
