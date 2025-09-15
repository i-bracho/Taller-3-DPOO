package uniandes.dpoo.aerolinea.modelo;

import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;

public class Vuelo {

    private String fecha;
    private Ruta ruta;
    private Avion avion;
    private Set<Tiquete> tiquetes;

    public Vuelo(String fecha, Ruta ruta, Avion avion) {
        this.fecha = fecha;
        this.ruta = ruta;
        this.avion = avion;
        this.tiquetes = new HashSet<>();
    }

    public String getFecha() {
        return fecha;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public Avion getAvion() {
        return avion;
    }

    public Set<Tiquete> getTiquetes() {
        return tiquetes;
    }

    public Tiquete venderTiquete(Cliente cliente, CalculadoraTarifas calculadora) throws VueloSobrevendidoException {
        if (!tieneSillasDisponibles()) {
            throw new VueloSobrevendidoException(this);
        }
        int tarifa = calculadora.calcularTarifa(this, cliente);
        Tiquete tiquete = GeneradorTiquetes.generarTiquete(this, cliente, tarifa);
        tiquetes.add(tiquete);
        GeneradorTiquetes.registrarTiquete(tiquete);
        cliente.agregarTiquete(tiquete);
        return tiquete;
    }

    public int getTarifa(Cliente cliente, CalculadoraTarifas calculadora) {
        return calculadora.calcularTarifa(this, cliente);
    }

    public int getNumTiquetes() {
        return tiquetes.size();
    }

    public boolean tieneSillasDisponibles() {
        return getNumTiquetes() < avion.getCapacidad();
    }

    public int getCapacidadDisponible() {
        return avion.getCapacidad() - getNumTiquetes();
    }
}