package om.logitrack.api.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import om.logitrack.api.dto.dtoRequest.VeiculoCadastroDTO;
import om.logitrack.api.dto.VeiculoDTO;
import om.logitrack.api.infra.RegraDeNegocio;
import om.logitrack.api.model.Usuario;
import om.logitrack.api.model.Veiculo;
import om.logitrack.api.repository.EmpresaRepository;
import om.logitrack.api.repository.VeiculoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;


    @Transactional
    public VeiculoDTO cadastrar(VeiculoCadastroDTO data, Usuario usuarioLogado){

        if (veiculoRepository.findByPlaca(data.placa()).isPresent()){
            throw new RegraDeNegocio("Já existe um veículo cadastrado com esta placa no sistema.");
        }

        var veiculo = new Veiculo();
        veiculo.setPlaca(data.placa());
        veiculo.setModelo(data.modelo());
        veiculo.setTipo(data.tipo());
        veiculo.setStatus(data.status());
        veiculo.setEmpresa(usuarioLogado.getEmpresa());
        veiculoRepository.save(veiculo);
        return VeiculoDTO.dto(veiculo);
    }

    @Transactional(readOnly = true)
    public Page<VeiculoDTO> listar(Pageable pageable, Usuario usuarioLogado){
        Long idEmpresaLogado = usuarioLogado.getEmpresa().getId();
        return veiculoRepository.findAllByAtivoTrueAndEmpresaId(pageable, idEmpresaLogado)
                .map(VeiculoDTO::dto);
    }

    @Transactional
    public VeiculoDTO atualizar(Long id, VeiculoCadastroDTO data, Usuario usuarioLogado){
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocio("Veiculo nao encontrado"));

        if (!veiculo.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId())){
            throw new RegraDeNegocio("Acesso negado. Este veiculo nao pertence a sua empresa");
        }
        if (!veiculo.getPlaca().equals(data.placa())){
            boolean placaJaExiste = veiculoRepository.findByPlaca(data.placa()).isPresent();
            if (placaJaExiste){
                throw new RegraDeNegocio("Já existe outro veículo cadastrado com esta placa.");
            }
        }

        veiculo.setPlaca(data.placa());
        veiculo.setModelo(data.modelo());
        veiculo.setTipo(data.tipo());
        veiculo.setStatus(data.status());

        Veiculo salvar = veiculoRepository.save(veiculo);
        return VeiculoDTO.dto(salvar);
    }

    @Transactional
    public void deletar(Long id, Usuario usuarioLogado){
        Veiculo veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocio("Veiculo nao encontrado para ser deletado"));
        if(!veiculo.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId())){
            throw new RegraDeNegocio("Acesso negado. Você não tem permissão para excluir veículos de outra empresa.");
        }
        veiculo.setAtivo(false);
        veiculoRepository.save(veiculo);
    }
}
