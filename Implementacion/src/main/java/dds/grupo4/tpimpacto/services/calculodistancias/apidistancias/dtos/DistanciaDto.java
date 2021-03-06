package dds.grupo4.tpimpacto.services.calculodistancias.apidistancias.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DistanciaDto {

    private float valor;
    private String unidad;

}
