package om.logitrack.api.dto.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaRequestDTO(@NotBlank String nomeFantasia,
                                @CNPJ String cnpj) {
}
