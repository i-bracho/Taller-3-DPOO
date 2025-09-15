package uniandes.dpoo.aerolinea.modelo;

import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;

/**
 * Esta clase encapsula la información sobre los aeropuertos e implementa algunas operaciones relacionadas con la ubicación geográfica de los aeropuertos.
 * 
 * No puede haber dos aeropuertos con el mismo código.
 */
public class Aeropuerto
{
    private static final int RADIO_TERRESTRE = 6371; // Radio de la Tierra en km
    private static Set<String> codigosRegistrados = new HashSet<>();

    private String nombre;
    private String codigo;
    private String ciudad;
    private String pais;
    private double latitud;
    private double longitud;

    public Aeropuerto(String nombre, String codigo, String ciudad, String pais, double latitud, double longitud) throws AeropuertoDuplicadoException {
        if (codigosRegistrados.contains(codigo)) {
            throw new AeropuertoDuplicadoException("Ya existe un aeropuerto con el código " + codigo);
        }
        this.nombre = nombre;
        this.codigo = codigo;
        this.ciudad = ciudad;
        this.pais = pais;
        this.latitud = latitud;
        this.longitud = longitud;
        codigosRegistrados.add(codigo);
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public static int calcularDistancia(Aeropuerto aeropuerto1, Aeropuerto aeropuerto2) {
        double latAeropuerto1 = Math.toRadians(aeropuerto1.getLatitud());
        double lonAeropuerto1 = Math.toRadians(aeropuerto1.getLongitud());
        double latAeropuerto2 = Math.toRadians(aeropuerto2.getLatitud());
        double lonAeropuerto2 = Math.toRadians(aeropuerto2.getLongitud());

        double deltaX = (lonAeropuerto2 - lonAeropuerto1) * Math.cos((latAeropuerto1 + latAeropuerto2) / 2);
        double deltaY = (latAeropuerto2 - latAeropuerto1);

        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY) * RADIO_TERRESTRE;
        return (int) Math.round(distancia);
    }
}
