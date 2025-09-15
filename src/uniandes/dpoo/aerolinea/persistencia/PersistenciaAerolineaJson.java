package uniandes.dpoo.aerolinea.persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;

public class PersistenciaAerolineaJson implements IPersistenciaAerolinea {

    private static final String AVIONES = "aviones";
    private static final String RUTAS = "rutas";
    private static final String VUELOS = "vuelos";

    @Override
    public void cargarAerolinea(String archivo, Aerolinea aerolinea)
            throws IOException, InformacionInconsistenteException {

        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONObject raiz = new JSONObject(jsonCompleto);

        JSONArray jAviones = raiz.getJSONArray(AVIONES);
        for (int i = 0; i < jAviones.length(); i++) {
            JSONObject jAvion = jAviones.getJSONObject(i);
            Avion avion = new Avion(jAvion.getString("nombre"), jAvion.getInt("capacidad"));
            aerolinea.agregarAvion(avion);
        }

        JSONArray jRutas = raiz.getJSONArray(RUTAS);
        for (int i = 0; i < jRutas.length(); i++) {
            JSONObject jRuta = jRutas.getJSONObject(i);
            Ruta ruta = new Ruta(
                jRuta.getString("horaSalida"),
                jRuta.getString("horaLlegada"),
                jRuta.getString("codigoRuta"),
                null,
                null
            );
            aerolinea.agregarRuta(ruta);
        }

        JSONArray jVuelos = raiz.getJSONArray(VUELOS);
        for (int i = 0; i < jVuelos.length(); i++) {
            JSONObject jVuelo = jVuelos.getJSONObject(i);
            String fecha = jVuelo.getString("fecha");
            String codigoRuta = jVuelo.getString("codigoRuta");
            String nombreAvion = jVuelo.getString("nombreAvion");
            try {
                aerolinea.programarVuelo(fecha, codigoRuta, nombreAvion);
            } catch (Exception e) {
                throw new InformacionInconsistenteException("Error cargando vuelo: " + e.getMessage());
            }
        }
    }

    @Override
    public void salvarAerolinea(String archivo, Aerolinea aerolinea) throws IOException {
        JSONObject raiz = new JSONObject();

        JSONArray jAviones = new JSONArray();
        for (Avion a : aerolinea.getAviones()) {
            JSONObject jAvion = new JSONObject();
            jAvion.put("nombre", a.getNombre());
            jAvion.put("capacidad", a.getCapacidad());
            jAviones.put(jAvion);
        }
        raiz.put(AVIONES, jAviones);

        JSONArray jRutas = new JSONArray();
        for (Ruta r : aerolinea.getRutas()) {
            JSONObject jRuta = new JSONObject();
            jRuta.put("horaSalida", r.getHoraSalida());
            jRuta.put("horaLlegada", r.getHoraLlegada());
            jRuta.put("codigoRuta", r.getCodigoRuta());
            jRuta.put("origen", r.getOrigen() != null ? r.getOrigen().getCodigo() : JSONObject.NULL);
            jRuta.put("destino", r.getDestino() != null ? r.getDestino().getCodigo() : JSONObject.NULL);
            jRutas.put(jRuta);
        }
        raiz.put(RUTAS, jRutas);

        JSONArray jVuelos = new JSONArray();
        for (Vuelo v : aerolinea.getVuelos()) {
            JSONObject jVuelo = new JSONObject();
            jVuelo.put("fecha", v.getFecha());
            jVuelo.put("codigoRuta", v.getRuta().getCodigoRuta());
            jVuelo.put("nombreAvion", v.getAvion().getNombre());
            jVuelos.put(jVuelo);
        }
        raiz.put(VUELOS, jVuelos);

        try (PrintWriter pw = new PrintWriter(archivo)) {
            raiz.write(pw, 2, 0);
        }
    }
}
