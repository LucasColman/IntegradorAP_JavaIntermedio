
package com.mycompany.reportedeincidentes.modelo;

import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor @ToString @AllArgsConstructor
public class TipoIncidencia implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoIncidenciaLong;
    
    private String nombreIncidencia;
    
    private String complejidad;
    
    @Temporal(value = TemporalType.TIME)
    private LocalTime tiempoEstimadoResolucion;
    
    @Temporal(value = TemporalType.TIME)
    private LocalTime tiempoMaximoResolucion;
    
    @ManyToOne @JoinColumn(name = "incidencia") 
    private Incidencia incidencia;
    
    @ManyToOne @JoinColumn(name = "servicio")
    private Servicio servicio;
    
}
