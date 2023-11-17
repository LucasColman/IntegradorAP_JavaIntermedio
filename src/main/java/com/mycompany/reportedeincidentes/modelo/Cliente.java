
package com.mycompany.reportedeincidentes.modelo;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @ToString
public class Cliente {
    private String idCliente;
    private String nombre;
    private String apellido;
    private String razonSocialString;
    private String cuil;
    private Set<Servicio> servicios = new HashSet<>();
    private Set<Incidencia> incidencias = new HashSet<>();


}
