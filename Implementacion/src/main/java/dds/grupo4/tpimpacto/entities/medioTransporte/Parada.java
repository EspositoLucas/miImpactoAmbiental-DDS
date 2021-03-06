package dds.grupo4.tpimpacto.entities.medioTransporte;

import dds.grupo4.tpimpacto.entities.trayecto.Direccion;
import dds.grupo4.tpimpacto.entities.trayecto.Lugar;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Parada")
@Table(name = "paradas")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parada extends Lugar {

    @ManyToOne
    @JoinColumn(name = "transporte_publico", nullable = false)
    private TransportePublico transportePublico;

    private Double distanciaParadaSiguiente;
    private Double distanciaParadaAnterior;

    @OneToOne
    @JoinColumn(
            name = "parada_siguiente",
            unique = true,
            nullable = true,
            foreignKey = @ForeignKey(name = "FK_Paradas_ParadaSiguiente")
    )
    private Parada paradaSiguiente;

    @OneToOne
    @JoinColumn(
            name = "parada_anterior",
            unique = true,
            nullable = true,
            foreignKey = @ForeignKey(name = "FK_Paradas_ParadaAnterior")
    )
    private Parada paradaAnterior;

    public Parada(Direccion direccion, Double distanciaParadaSiguiente) {
        super(direccion);
        this.distanciaParadaSiguiente = distanciaParadaSiguiente;
    }

}
