package dds.grupo4.tpimpacto.entities.medicion;

import dds.grupo4.tpimpacto.entities.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "TipoConsumo")
@Table(name = "tipos_de_consumo")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TipoConsumo extends BaseEntity {

    private String nombre;
    private FactorEmision factorEmision;

    private Double peso ; // para la actividad logistica y productos

    private Double distanciaMediaRecorrida ; //para la actividad logistica y productos

    public TipoConsumo(String nombre, FactorEmision factorEmision) {
        this.nombre = nombre;
        this.factorEmision = factorEmision;
    }
}