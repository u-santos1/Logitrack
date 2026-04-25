package om.logitrack.api.dto;

import jakarta.validation.constraints.NotBlank;

public record EmpresaCadastroDTO(@NotBlank String nomeFantasia,
                                 @NotBlank String cnpj) {
}
