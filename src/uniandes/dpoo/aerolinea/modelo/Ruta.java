package uniandes.dpoo.aerolinea.modelo;

/**
 * Esta clase tiene la información de una ruta entre dos aeropuertos que cubre una aerolínea.
 */
public class Ruta {
    private String horaSalida;
    private String horaLlegada;
    private String codigoRuta;
    private Aeropuerto origen;
    private Aeropuerto destino;

    public Ruta(String horaSalida, String horaLlegada, String codigoRuta, Aeropuerto origen, Aeropuerto destino) {
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.codigoRuta = codigoRuta;
        this.origen = origen;
        this.destino = destino;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public String getCodigoRuta() {
        return codigoRuta;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public static int getMinutos(String horaCompleta) {
        return Integer.parseInt(horaCompleta) % 100;
    }

    public static int getHoras(String horaCompleta) {
        return Integer.parseInt(horaCompleta) / 100;
    }

    public int getDuracion() {
        int horasSalida = getHoras(horaSalida);
        int minutosSalida = getMinutos(horaSalida);
        int totalSalida = horasSalida * 60 + minutosSalida;
        int horasLlegada = getHoras(horaLlegada);
        int minutosLlegada = getMinutos(horaLlegada);
        int totalLlegada = horasLlegada * 60 + minutosLlegada;
        int duracion = totalLlegada - totalSalida;
        if (duracion < 0) {
            duracion += 24 * 60;
        }
        return duracion;
    }
}
