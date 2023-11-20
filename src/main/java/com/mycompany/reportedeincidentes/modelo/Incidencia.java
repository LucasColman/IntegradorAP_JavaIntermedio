package com.mycompany.reportedeincidentes.modelo;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@AllArgsConstructor
public class Incidencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIncidencia;

    private String descripcion;
    private boolean estado;

    @Temporal(value = TemporalType.DATE)
    private LocalDate fechaApertura;

    @Temporal(value = TemporalType.DATE)
    private LocalDate fechaResolucion;

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

    public void agregarServicio(Servicio servicio) {
        this.servicios.add(servicio);
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
    }

    public void quitarTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tiposIncidencias.remove(tipoIncidencia);
    }

    public void agregarEspecialidad(Especialidad especialidad) {
        this.especialidades.add(especialidad);
    }

    public void quitarEspecialidad(Especialidad especialidad) {
        this.especialidades.remove(especialidad);
    }

}
