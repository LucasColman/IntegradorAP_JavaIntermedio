package com.mycompany.reportedeincidentes.modelo;

import com.mycompany.reportedeincidentes.Enums.Estado;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
public class Incidencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIncidencia;
    private String descripcion;
    private Estado estado;
    private LocalDateTime fechaApertura;
    private LocalDateTime fechaResolucion;

    @ManyToOne
    @JoinColumn(name = "tecnico")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    @ManyToMany
    private Set<Servicio> servicios = new HashSet<>();

    @OneToMany(mappedBy = "incidencia")
    private Set<Reporte> reportes = new HashSet<>();

    @OneToMany(mappedBy = "incidencia")
    private Set<TipoIncidencia> tiposIncidencias = new HashSet<>();

    @OneToMany(mappedBy = "incidencia")
    private Set<Especialidad> especialidades = new HashSet<>();

    public Incidencia(String descripcion, Estado estado, LocalDateTime fechaApertura, LocalDateTime fechaResolucion, Tecnico tecnico, Cliente cliente) {
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaApertura = fechaApertura;
        this.fechaResolucion = fechaResolucion;
        this.tecnico = tecnico;
        this.cliente = cliente;
    }

    public void agregarServicio(Servicio servicio) {
        this.servicios.add(servicio);
        servicio.agregarIncidencia(this);
    }

    public void quitarServicio(Servicio servicio) {
        this.servicios.remove(servicio);
    }

    public void agregarReporte(Reporte reporte) {
        this.reportes.add(reporte);
    }

    public void quitarReporte(Reporte reporte) {
        this.reportes.remove(reporte);
    }

    public void agregarTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tiposIncidencias.add(tipoIncidencia);
        tipoIncidencia.setIncidencia(this);
    }

    public void quitarTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tiposIncidencias.remove(tipoIncidencia);
    }

    public void agregarEspecialidad(Especialidad especialidad) {
        this.especialidades.add(especialidad);
        especialidad.agregarIncidencia(this);
    }

    public void quitarEspecialidad(Especialidad especialidad) {
        this.especialidades.remove(especialidad);
        especialidad.quitarIncidencia();
    }

    
    
 }
    
