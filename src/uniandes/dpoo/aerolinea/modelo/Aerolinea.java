package uniandes.dpoo.aerolinea.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;
import uniandes.dpoo.aerolinea.persistencia.IPersistenciaAerolinea;
import uniandes.dpoo.aerolinea.persistencia.IPersistenciaTiquetes;
import uniandes.dpoo.aerolinea.persistencia.TipoInvalidoException;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTemporadaAlta;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifasTemporadaBaja;

public class Aerolinea
{
    private List<Avion> aviones;
    private Map<String, Ruta> rutas;
    private List<Vuelo> vuelos;
    private Map<String, Cliente> clientes;

    public Aerolinea( )
    {
        aviones = new LinkedList<>();
        rutas = new HashMap<>();
        vuelos = new LinkedList<>();
        clientes = new HashMap<>();
    }

    public void agregarRuta( Ruta ruta )
    {
        this.rutas.put( ruta.getCodigoRuta( ), ruta );
    }

    public void agregarAvion( Avion avion )
    {
        this.aviones.add( avion );
    }

    public void agregarCliente( Cliente cliente )
    {
        this.clientes.put( cliente.getIdentificador( ), cliente );
    }

    public boolean existeCliente( String identificadorCliente )
    {
        return this.clientes.containsKey( identificadorCliente );
    }

    public Cliente getCliente( String identificadorCliente )
    {
        return this.clientes.get( identificadorCliente );
    }

    public Collection<Avion> getAviones( )
    {
        return aviones;
    }

    public Collection<Ruta> getRutas( )
    {
        return rutas.values( );
    }

    public Ruta getRuta( String codigoRuta )
    {
        return rutas.get( codigoRuta );
    }

    public Collection<Vuelo> getVuelos( )
    {
        return vuelos;
    }

    public Vuelo getVuelo( String codigoRuta, String fechaVuelo )
    {
        for (Vuelo v : vuelos) {
            if (v.getRuta().getCodigoRuta().equals(codigoRuta) && v.getFecha().equals(fechaVuelo)) {
                return v;
            }
        }
        return null;
    }

    public Collection<Cliente> getClientes( )
    {
        return clientes.values( );
    }

    public Collection<Tiquete> getTiquetes( )
    {
        List<Tiquete> todos = new ArrayList<>();
        for (Vuelo v : vuelos) {
            todos.addAll(v.getTiquetes());
        }
        return todos;
    }

    public void cargarAerolinea( String archivo, String tipoArchivo ) throws TipoInvalidoException, IOException, InformacionInconsistenteException
    {
        IPersistenciaAerolinea cargador = CentralPersistencia.getPersistenciaAerolinea(tipoArchivo);
        cargador.cargarAerolinea(archivo, this);
    }

    public void salvarAerolinea( String archivo, String tipoArchivo ) throws TipoInvalidoException, IOException
    {
        IPersistenciaAerolinea salvador = CentralPersistencia.getPersistenciaAerolinea(tipoArchivo);
        salvador.salvarAerolinea(archivo, this);
    }

    public void cargarTiquetes( String archivo, String tipoArchivo ) throws TipoInvalidoException, IOException, InformacionInconsistenteException
    {
        IPersistenciaTiquetes cargador = CentralPersistencia.getPersistenciaTiquetes( tipoArchivo );
        cargador.cargarTiquetes( archivo, this );
    }

    public void salvarTiquetes( String archivo, String tipoArchivo ) throws TipoInvalidoException, IOException
    {
        IPersistenciaTiquetes cargador = CentralPersistencia.getPersistenciaTiquetes( tipoArchivo );
        cargador.salvarTiquetes( archivo, this );
    }

    public void programarVuelo( String fecha, String codigoRuta, String nombreAvion ) throws Exception
    {
        Ruta ruta = getRuta(codigoRuta);
        if (ruta == null) throw new Exception("Ruta no encontrada: " + codigoRuta);

        Avion avion = null;
        for (Avion a : aviones) {
            if (a.getNombre().equals(nombreAvion)) {
                avion = a;
                break;
            }
        }
        if (avion == null) throw new Exception("Avion no encontrado: " + nombreAvion);

        for (Vuelo v : vuelos) {
            if (v.getAvion().equals(avion) && v.getFecha().equals(fecha)) {
                throw new Exception("El avión ya tiene un vuelo programado en esa fecha.");
            }
        }

        Vuelo nuevo = new Vuelo(fecha, ruta, avion);
        vuelos.add(nuevo);
    }

    public int venderTiquetes( String identificadorCliente, String fecha, String codigoRuta, int cantidad ) throws VueloSobrevendidoException, Exception
    {
        Cliente cliente = getCliente(identificadorCliente);
        if (cliente == null) throw new Exception("Cliente no encontrado: " + identificadorCliente);

        Vuelo vuelo = getVuelo(codigoRuta, fecha);
        if (vuelo == null) throw new Exception("Vuelo no encontrado: " + codigoRuta + " en " + fecha);

        int mes = Integer.parseInt(fecha.substring(4, 6));
        CalculadoraTarifas calculadora = esTemporadaAlta(mes) ? new CalculadoraTemporadaAlta(cliente, vuelo) : new CalculadoraTarifasTemporadaBaja(cliente, vuelo);

        int total = 0;
        for (int i = 0; i < cantidad; i++) {
            total += vuelo.venderTiquete(cliente, calculadora).getTarifa();
        }
        return total;
    }

    private boolean esTemporadaAlta(int mes) {
        return (mes >= 6 && mes <= 8) || mes == 12;
    }

    public void registrarVueloRealizado( String fecha, String codigoRuta )
    {
        Vuelo vuelo = getVuelo(codigoRuta, fecha);
        if (vuelo != null) {
            for (Cliente c : clientes.values()) {
                c.usarTiquetes(vuelo);
            }
        }
    }

    public String consultarSaldoPendienteCliente( String identificadorCliente )
    {
        Cliente cliente = getCliente(identificadorCliente);
        if (cliente == null) return "0";
        int total = 0;
        for (Tiquete t : cliente.getTiquetes()) {
            if (!t.esUsado()) {
                total += t.getTarifa();
            }
        }
        return String.valueOf(total);
    }
}