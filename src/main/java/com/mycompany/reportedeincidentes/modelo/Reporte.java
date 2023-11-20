
package com.mycompany.reportedeincidentes.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Getter @Setter
@NoArgsConstructor @ToString @AllArgsConstructor
public class Reporte implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReporte;
    
    @Temporal(TemporalType.DATE)
    private LocalDate fecha;
    
    @ManyToOne @JoinColumn(name = "incidencia")
    private Incidencia incidencia;
    
    
    
}
