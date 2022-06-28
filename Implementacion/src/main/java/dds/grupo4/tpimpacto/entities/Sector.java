package dds.grupo4.tpimpacto.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Sector")
@Table(name = "sectores")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sector extends BaseEntity {

    private String nombre;
    private Organizacion organizacion;
    private Espacio espacio;
    private List<Miembro> miembros = new ArrayList<>();

    public Sector(String nombre, Espacio espacio) {
        this.nombre = nombre;
        this.espacio = espacio;
    }

    public void addMiembro(Miembro miembro) {
        miembros.add(miembro);
        miembro.setSector(this);
    }

    // calculo HC promedio

//    public double calculoHCPromedio() {
//
//        return 0;
//    }

}
