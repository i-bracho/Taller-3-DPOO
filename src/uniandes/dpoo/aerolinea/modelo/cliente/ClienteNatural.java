package uniandes.dpoo.aerolinea.modelo.cliente;

import org.json.JSONObject;

public class ClienteNatural extends Cliente {
    public static final String NATURAL = "Natural";

    public ClienteNatural(String nombre) {
        super(nombre, nombre);
    }

    @Override
    public String getTipoCliente() {
        return NATURAL;
    }

    public static ClienteNatural cargarDesdeJSON(JSONObject cliente) {
        String nombre = cliente.getString("nombre");
        return new ClienteNatural(nombre);
    }

    public JSONObject salvarEnJSON() {
        JSONObject jobject = new JSONObject();
        jobject.put("nombre", this.nombre);
        jobject.put("tipo", NATURAL);
        return jobject;
    }
}