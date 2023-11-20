package com.mycompany.reportedeincidentes.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Servicio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    private String nombreServicio;

    @ManyToMany
    private Set<Cliente> clientes = new HashSet<>();

    @ManyToMany
    private Set<Incidencia> incidencias = new HashSet<>();

    @OneToMany(mappedBy = "servicio")
    private Set<TipoIncidencia> tiposIncidencias = new HashSet<>();

    public Servicio(Long idServicio, String nombreServicio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
    }
    

    public void agregarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public void quitarCliente(Cliente cliente) {
        this.clientes.remove(cliente);
    }

    public void agregarTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tiposIncidencias.add(tipoIncidencia);
    }

    public void quitarTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tiposIncidencias.remove(tipoIncidencia);
    }

    public void agregarIncidencia(Incidencia incidencia) {
        this.incidencias.add(incidencia);
    }

    public void quitarIncidencia(Incidencia incidencia) {
        this.incidencias.remove(incidencia);
    }

}
