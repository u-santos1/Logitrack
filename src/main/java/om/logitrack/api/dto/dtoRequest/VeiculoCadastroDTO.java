package om.logitrack.api.dto.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import om.logitrack.api.model.enums.StatusVeiculo;
import om.logitrack.api.model.enums.TipoVeiculo;

public record VeiculoCadastroDTO(
        @NotBlank
        String placa,
        @NotBlank
        String modelo,
        @NotNull
        TipoVeiculo tipo,
        @NotNull
        StatusVeiculo status,
        @NotNull
        Long empresaId
) {
}
