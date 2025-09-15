package uniandes.dpoo.aerolinea.modelo.cliente;
import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public abstract class Cliente {
    
    protected String identificador;
    protected String nombre;
    protected Set<Tiquete> tiquetes;

    public Cliente(String identificador, String nombre) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.tiquetes = new HashSet<>();
    }
   
    public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Tiquete> getTiquetes() {
		return tiquetes;
	}

	public void setTiquetes(Set<Tiquete> tiquetes) {
		this.tiquetes = tiquetes;
	}

	public void agregarTiquete(Tiquete tiquete) {
        tiquetes.add(tiquete);
    }

    public abstract String getTipoCliente();
    
    public int calcularValorTotalTiquetes() {
        int total = 0;
        for (Tiquete t : tiquetes) {
            total += t.getTarifa();
        }
        return total;
    }

    public void usarTiquetes(Vuelo vuelo) {
        for (Tiquete t : tiquetes) {
        	if (t.getVuelo().equals(vuelo) && !t.esUsado()) {
                t.marcarComoUsado();
            }
        }
    }
}

