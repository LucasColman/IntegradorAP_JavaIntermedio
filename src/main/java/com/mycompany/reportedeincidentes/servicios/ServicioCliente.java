
package com.mycompany.reportedeincidentes.servicios;

import com.mycompany.reportedeincidentes.modelo.Cliente;
import com.mycompany.reportedeincidentes.repositorio.ClienteJpaController;
import com.mycompany.reportedeincidentes.repositorio.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioCliente {
    ClienteJpaController clienteJPA = new ClienteJpaController();
    
    public void crearCliente(Cliente cliente){
        clienteJPA.create(cliente);
        
    }
    public void eliminarCliente(Long idCliente){
        try {
            clienteJPA.destroy(idCliente);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServicioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void editarCliente(Cliente cliente){
        try {
            clienteJPA.edit(cliente);
        } catch (Exception ex) {
            Logger.getLogger(ServicioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Cliente buscarCliente(Long idCliente){
        return clienteJPA.findCliente(idCliente);
        
    }
    
    public Set<Cliente> listarClientes(){
        List<Cliente> lista = clienteJPA.findClienteEntities();
        Set<Cliente> listaClientes = new HashSet<Cliente> (lista);
        return listaClientes;
    }
    
    
}
