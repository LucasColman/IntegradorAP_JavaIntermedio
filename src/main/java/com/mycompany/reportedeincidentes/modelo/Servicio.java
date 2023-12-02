package com.mycompany.reportedeincidentes.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
    @JoinTable(
        name = "Cliente_has_Servicio",
        joinColumns = @JoinColumn(name = "Servicio_idServicio"),
        inverseJoinColumns = @JoinColumn(name = "Cliente_idCliente"))
    private Set<Cliente> clientes = new HashSet<>();

     @ManyToMany
     @JoinTable(
        name = "Incidencia_has_Servicio",
        joinColumns = @JoinColumn(name = "Servicio_idServicio"),
        inverseJoinColumns = @JoinColumn(name = "Incidencia_idIncidencia"))
    private Set<Incidencia> incidencias = new HashSet<>();

    @OneToMany(mappedBy = "servicio")
    private Set<TipoIncidencia> tiposIncidencias = new HashSet<>();

    public Servicio(String nombreServicio) {
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
