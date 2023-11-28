
package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Cliente;
import java.util.Set;


public interface ServicioCliente {
   public void crearCliente(Cliente cliente);
   public void eliminarCliente(Long idCliente);
   public void editarCliente(Cliente cliente);
   public Cliente buscarCliente(Long idCliente);
   public Set<Cliente> listarClientes();
}
