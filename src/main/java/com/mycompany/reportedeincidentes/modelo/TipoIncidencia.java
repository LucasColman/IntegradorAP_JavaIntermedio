
package com.mycompany.reportedeincidentes.modelo;

import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor @ToString 
public class TipoIncidencia implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoIncidenciaLong;
    
    private String nombreIncidencia;
    private String complejidad;
    private LocalTime tiempoEstimadoResolucion;
    private LocalTime tiempoMaximoResolucion;
    
    @ManyToOne @JoinColumn(name = "incidencia") 
    private Incidencia incidencia;
    
    @ManyToOne @JoinColumn(name = "servicio")
    private Servicio servicio;

    public TipoIncidencia(String nombreIncidencia, String complejidad, LocalTime tiempoEstimadoResolucion, LocalTime tiempoMaximoResolucion, Incidencia incidencia, Servicio servicio) {
        this.nombreIncidencia = nombreIncidencia;
        this.complejidad = complejidad;
        this.tiempoEstimadoResolucion = tiempoEstimadoResolucion;
        this.tiempoMaximoResolucion = tiempoMaximoResolucion;
        this.incidencia = incidencia;
        incidencia.getTiposIncidencias().add(this);
        this.servicio = servicio;
    }
    
    
    
    
}
