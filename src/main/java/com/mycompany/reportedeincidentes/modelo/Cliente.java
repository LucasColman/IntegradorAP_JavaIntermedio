package com.mycompany.reportedeincidentes.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*@Join column: Indica un atributo que actúa como llave foránea en otra entidad.
    name: Identifica a la columna que guarda la clave foránea. Es el único campo obligatorio.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    private String apellido;
    private String nombre;
    private String razonSocial;
    private String cuil;

    @ManyToMany
    private Set<Servicio> servicios = new HashSet<>();
    @OneToMany(mappedBy = "cliente")
    private Set<Incidencia> incidencias = new HashSet<>();

    public void agregarServicios(Servicio servicio) {
        this.servicios.add(servicio);
    }

    public void quitarServicios(Servicio servicio) {
        this.servicios.remove(servicio);
    }

    public void agregarIncidencia(Incidencia incidencia) {
        this.incidencias.add(incidencia);
    }

    public void quitarIncidencia(Incidencia incidencia) {
        this.incidencias.remove(incidencia);
    }

}
