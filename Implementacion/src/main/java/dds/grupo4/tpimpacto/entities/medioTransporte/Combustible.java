package dds.grupo4.tpimpacto.entities.medioTransporte;

import dds.grupo4.tpimpacto.entities.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "Combustible")
@Table(name = "combustibles")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Combustible extends BaseEntity {

    private TipoCombustible tipoCombustible;
    private UnidadCombustible unidad;

    public Combustible(TipoCombustible tipoCombustible, UnidadCombustible unidad) {
        this.tipoCombustible = tipoCombustible;
        this.unidad = unidad;
    }
}
