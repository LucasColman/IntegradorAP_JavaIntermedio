package com.mycompany.reportedeincidentes.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Especialidad implements Serializable {

   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecialidad;

    private String nombreEspecialidad;

    @ManyToOne
    @JoinColumn(name = "incidencia")
    private Incidencia incidencia;

    @ManyToMany
    private Set<Tecnico> tecnicos = new HashSet<>();

    //Metodos
    public Especialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public void agregarTecnico(Tecnico tecnico) {
        this.tecnicos.add(tecnico);

    }

    public void quitarTecnico(Tecnico tecnico) {
        this.tecnicos.remove(tecnico);

    }
    
    public void agregarIncidencia(Incidencia incidencia){
        this.incidencia = incidencia;
    }
    
    public void quitarIncidencia(){
        this.incidencia = null;
    }

}
