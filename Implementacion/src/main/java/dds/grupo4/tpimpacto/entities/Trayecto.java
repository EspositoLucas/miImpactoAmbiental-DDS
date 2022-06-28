package dds.grupo4.tpimpacto.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Trayecto")
@Table(name = "trayectos")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trayecto extends BaseEntity {

    // TODO: ver si estas Direcciones no deberian ser Lugares
    private Direccion partida;
    private Direccion destino;
    private List<Tramo> tramos = new ArrayList<>();

    public Trayecto(Direccion partida, Direccion destino, List<Tramo> tramos) {
        this.partida = partida;
        this.destino = destino;
        this.tramos = tramos;
    }

    public double distanciaTotalRecorrida() {
        return this.tramos.stream()
                .mapToDouble(t -> t.distancia())
                .sum();
    }

}
