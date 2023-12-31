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
public class Tecnico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTecnico;
    private String dni;
    private String nombre;
    private String apellido;

    @ManyToMany
    @JoinTable(
        name = "Tecnico_has_Especialidad",
        joinColumns = @JoinColumn(name = "Especialidad_idEspecialidad"),
        inverseJoinColumns = @JoinColumn(name = "Tecnico_idTecnico"))
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


}
