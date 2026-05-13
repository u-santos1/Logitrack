package om.logitrack.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import om.logitrack.api.dto.ManutencaoDetalhadamenteDTO;
import om.logitrack.api.dto.dtoRequest.ManutencaoDTO;
import om.logitrack.api.infra.RegraDeNegocio;
import om.logitrack.api.model.Funcionario;
import om.logitrack.api.model.Manutencao;
import om.logitrack.api.model.Veiculo;
import om.logitrack.api.model.enums.StatusVeiculo;
import om.logitrack.api.repository.EmpresaRepository;
import om.logitrack.api.repository.FuncionarioRepository;
import om.logitrack.api.repository.ManutencaoRepository;
import om.logitrack.api.repository.VeiculoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ManutencaoService {

    private final EmpresaRepository empresaRepository;
    private final VeiculoRepository veiculoRepository;
    private final ManutencaoRepository manutencaoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Transactional
    public ManutencaoDetalhadamenteDTO criar(ManutencaoDTO data){
        boolean jaEstaNaOficina = manutencaoRepository.existsByVeiculoPlacaAndDataSaidaIsNull(data.placa());
        if(jaEstaNaOficina){
            throw new RegraDeNegocio("Este veículo já possui uma manutenção em andamento na oficina.");
        }
        Veiculo veiculo = veiculoRepository.findByPlaca(data.placa())
                .orElseThrow(()-> new RegraDeNegocio("Nao foi encontrado o veiculo no banco de dados da empresa"));

        Funcionario funcionario = funcionarioRepository.findById(data.funcionarioId())
                .orElseThrow(()-> new RegraDeNegocio("Nao foi encontrado o funcionario"));


        Manutencao manutencao = new Manutencao();
        manutencao.setDescricao(data.descricao());
        manutencao.setDataEntrada(data.dataEntrada());
        manutencao.setDataSaida(data.dataSaida());
        manutencao.setValorTotal(data.valorTotal());
        manutencao.setAtivo(true);
        manutencao.setVeiculo(veiculo);
        manutencao.setEmpresa(veiculo.getEmpresa());
        manutencao.setFuncionario(funcionario);
        manutencao.setStatus(StatusVeiculo.MANUTENCAO);

        var salvar = manutencaoRepository.save(manutencao);
        return ManutencaoDetalhadamenteDTO.dto(salvar);}


    @Transactional(readOnly = true)
    public Page<ManutencaoDetalhadamenteDTO> listar(Pageable paginacao){
       return manutencaoRepository.findAll(paginacao)
               .map(ManutencaoDetalhadamenteDTO::dto);

    }
    @Transactional
    public ManutencaoDetalhadamenteDTO atualizacao( Long id, ManutencaoDTO data){
        Manutencao buscar = manutencaoRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocio("veiculo em manutencao nao encontrado"));

        Funcionario novoFuncionario = funcionarioRepository.findById(data.funcionarioId())
                        .orElseThrow(()-> new RegraDeNegocio("Funcionario nao encontrado"));

        if(!novoFuncionario.isAtivo()){
            throw new RegraDeNegocio("Este funcionário está inativo/desligado e não pode assumir manutenções.");
        }


        buscar.setDescricao(data.descricao());
        buscar.setDataEntrada(data.dataEntrada());
        buscar.setDataSaida(data.dataSaida());
        buscar.setValorTotal(data.valorTotal());

        buscar.setFuncionario(novoFuncionario);

        if (data.dataSaida() != null){
            buscar.setStatus(StatusVeiculo.DISPONIVEL);
        }

        var salvarNovo = manutencaoRepository.save(buscar);
        return ManutencaoDetalhadamenteDTO.dto(salvarNovo);

    }
    @Transactional
    public void deletar(Long id){
        Manutencao manutencao = manutencaoRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocio("manutencao nao encontrado para esse id"));
        manutencao.setAtivo(false);
        manutencaoRepository.save(manutencao);
    }

    @Transactional(readOnly = true)
    public Page<ManutencaoDetalhadamenteDTO> listarPorPlaca(String placa, Pageable pageable){
        var buscarPorPlaca = manutencaoRepository.findByVeiculoPlaca(placa, pageable);
        return buscarPorPlaca.map(ManutencaoDetalhadamenteDTO::dto);
    }
}

