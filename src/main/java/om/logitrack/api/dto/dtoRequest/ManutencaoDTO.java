package om.logitrack.api.dto.dtoRequest;


import java.math.BigDecimal;
import java.time.LocalDate;


public record ManutencaoDTO(
        String descricao,
        LocalDate dataEntrada,
        LocalDate dataSaida,
        BigDecimal valorTotal,
        String placa)
{
}
