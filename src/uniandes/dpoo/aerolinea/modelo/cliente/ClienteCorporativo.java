package uniandes.dpoo.aerolinea.modelo.cliente;

import org.json.JSONObject;

/**
 * Esta clase se usa para representar a los clientes de la aerolínea que son empresas.
 */
public class ClienteCorporativo extends Cliente {

    public static final String CORPORATIVO = "Corporativo";

    public static final int GRANDE = 3;
    public static final int MEDIANA = 2;
    public static final int PEQUENA = 1;

    private int tamanoEmpresa;
    private String nombreEmpresa;

    public ClienteCorporativo(String nombreEmpresa, int tamano) {
        super(nombreEmpresa, nombreEmpresa); 
        this.nombreEmpresa = nombreEmpresa;
        this.tamanoEmpresa = tamano;
    }

    public int getTamanoEmpresa() {
        return tamanoEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public static ClienteCorporativo cargarDesdeJSON(JSONObject cliente) {
        String nombreEmpresa = cliente.getString("nombreEmpresa");
        int tam = cliente.getInt("tamanoEmpresa");
        return new ClienteCorporativo(nombreEmpresa, tam);
    }

    public JSONObject salvarEnJSON() {
        JSONObject jobject = new JSONObject();
        jobject.put("nombreEmpresa", this.nombreEmpresa);
        jobject.put("tamanoEmpresa", this.tamanoEmpresa);
        jobject.put("tipo", CORPORATIVO);
        return jobject;
    }

    @Override
    public String getTipoCliente() {
        return CORPORATIVO;
    }
}
