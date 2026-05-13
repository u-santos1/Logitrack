package om.logitrack.api.dto.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FuncionarioDTO(@NotBlank String nome,
                             @NotBlank String funcao,
                             @NotBlank String matricula,
                             @NotNull Long empresaId) {
}
