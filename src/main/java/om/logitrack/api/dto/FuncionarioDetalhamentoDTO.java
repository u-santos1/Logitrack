package om.logitrack.api.dto;

import om.logitrack.api.model.Funcionario;

public record FuncionarioDetalhamentoDTO(Long id,
                                         String nome,
                                         String funcao,
                                         String matricula) {
    public static FuncionarioDetalhamentoDTO dto(Funcionario operador){
        return new FuncionarioDetalhamentoDTO(
                operador.getId(),
                operador.getNome(),
                operador.getFuncao(),
                operador.getMatricula()
        );
    }
}
