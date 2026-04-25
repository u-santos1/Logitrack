package om.logitrack.api.dto;

import om.logitrack.api.model.Veiculo;
import om.logitrack.api.model.enums.TipoVeiculo;

public record VeiculoDTO(Long id,
                         String placa,
                         String modelo,
                         TipoVeiculo tipo,
                         String status) {
    public VeiculoDTO(Veiculo veiculo){
        this(
                veiculo.getId(),
                veiculo.getPlaca(),
                veiculo.getModelo(),
                veiculo.getTipo(),
                veiculo.getStatus() != null ? veiculo.getStatus().name() : null
        );
    }
}
