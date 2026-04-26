package om.logitrack.api.dto.dtoRequest;

import jakarta.validation.constraints.NotBlank;

public record EmpresaRequestDTO(@NotBlank String nomeFantasia,
                                @NotBlank String cnpj) {
}
