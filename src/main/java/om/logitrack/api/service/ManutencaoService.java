package om.logitrack.api.service;

import lombok.RequiredArgsConstructor;
import om.logitrack.api.dto.ManutencaoDetalhadamenteDTO;
import om.logitrack.api.dto.dtoRequest.ManutencaoDTO;
import om.logitrack.api.infra.RegraDeNegocio;
import om.logitrack.api.model.Manutencao;
import om.logitrack.api.model.Veiculo;
import om.logitrack.api.repository.EmpresaRepository;
import om.logitrack.api.repository.ManutencaoRepository;
import om.logitrack.api.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@RequiredArgsConstructor
public class ManutencaoService {

    private final EmpresaRepository empresaRepository;
    private final VeiculoRepository veiculoRepository;
    private final ManutencaoRepository manutencaoRepository;

    @Transactional
    public ManutencaoDetalhadamenteDTO criar(ManutencaoDTO data){
        Veiculo veiculo = veiculoRepository.findByPlaca(data.placa())
                .orElseThrow(()-> new RegraDeNegocio("Nao foi encontrado o veiculo no banco de dados da empresa"));


        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao(data.descricao());
        manutencao.setDataEntrada(data.dataEntrada());
        manutencao.setDataSaida(data.dataSaida());
        manutencao.setValorTotal(data.valorTotal());
        manutencao.setVeiculo(veiculo);
        manutencao.setEmpresa(veiculo.getEmpresa());

        var salvar = manutencaoRepository.save(manutencao);
        return ManutencaoDetalhadamenteDTO.dto(salvar);
}}
