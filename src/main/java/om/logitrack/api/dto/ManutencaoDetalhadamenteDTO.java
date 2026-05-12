package om.logitrack.api.dto;
import om.logitrack.api.model.Empresa;
import om.logitrack.api.model.Manutencao;
import om.logitrack.api.model.Veiculo;

import java.math.BigDecimal;
import java.time.LocalDate;


public record ManutencaoDetalhadamenteDTO(Long id,
                                          String descricao,
                                          LocalDate dataEntrada,
                                          LocalDate dataSaida,
                                          BigDecimal valorTotal,
                                          String placa) {
    public static ManutencaoDetalhadamenteDTO dto(Manutencao manutencao){
        return new ManutencaoDetalhadamenteDTO(
                manutencao.getId(),
                manutencao.getDescricao(),
                manutencao.getDataEntrada(),
                manutencao.getDataSaida(),
                manutencao.getValorTotal(),
                manutencao.getVeiculo().getPlaca()
        );
    }
}
