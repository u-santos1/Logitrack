package om.logitrack.api.dto;

import om.logitrack.api.model.Funcionario;

public record OperadorDetalhamentoDTO(Long id,
                                      String nome,
                                      String funcao,
                                      String matricula) {
    public static OperadorDetalhamentoDTO dto(Funcionario operador){
        return new OperadorDetalhamentoDTO(
                operador.getId(),
                operador.getNome(),
                operador.getFuncao(),
                operador.getMatricula()
        );
    }
}
