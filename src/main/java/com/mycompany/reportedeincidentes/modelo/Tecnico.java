package com.mycompany.reportedeincidentes.modelo;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Tecnico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTecnico;
    private String dni;
    private String nombre;
    private String apellido;

    @ManyToMany
    private Set<Especialidad> especialidades = new HashSet<>();

    @OneToMany(mappedBy = "tecnico")
    private Set<Incidencia> incidencias = new HashSet<>();

    //Metodos
    public Tecnico(String dni, String nombre, String apellido) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public void agregarEspecialidad(Especialidad especialidad) {
        this.especialidades.add(especialidad);
        especialidad.agregarTecnico(this);
    }

    public void quitarEspecialidad(Especialidad especialidad) {
        this.especialidades.remove(especialidad);
    }

    public void agregarIncidencia(Incidencia incidencia) {
        this.incidencias.add(incidencia);
        incidencia.setTecnico(this);
    }

    public void quitarIncidencia(Incidencia incidencia) {
        this.incidencias.remove(incidencia);
    }

    public Duration calcularTiempoPromedioResolucion() {
        long tiempoTotalResolucion = 0;
    
        for (Incidencia incidencia : getIncidencias()) {
            if (incidencia.getFechaApertura() != null && incidencia.getFechaResolucion() != null) {
                tiempoTotalResolucion += Duration.between(incidencia.getFechaApertura(), incidencia.getFechaResolucion()).toMillis();
                return Duration.ofMillis(tiempoTotalResolucion/ getIncidencias().size());
            } else {
                return Duration.ZERO;
            }
        }
        return Duration.ZERO;
    }

}
