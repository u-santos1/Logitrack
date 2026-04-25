package om.logitrack.api.dto;

import om.logitrack.api.model.Empresa;
import om.logitrack.api.model.Veiculo;

import java.util.List;

public record EmpresaDetalhamentoDTO(Long id,
                                     String nomeFantasia,
                                     String cnpj,
                                     List<VeiculoDTO> veiculos) {
    public EmpresaDetalhamentoDTO(Empresa empresa){
        this(
                empresa.getId(),
                empresa.getNomeFantasia(),
                empresa.getCnpj(),
                empresa.getVeiculos() != null ?
                        empresa.getVeiculos().stream().map(VeiculoDTO::new).toList() :
                        java.util.Collections.emptyList()
        );
    }
}
