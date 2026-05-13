package om.logitrack.api.service;

import lombok.RequiredArgsConstructor;
import om.logitrack.api.dto.FuncionarioDetalhamentoDTO;
import om.logitrack.api.dto.dtoRequest.FuncionarioDTO;
import om.logitrack.api.infra.RegraDeNegocio;
import om.logitrack.api.model.Empresa;
import om.logitrack.api.model.Funcionario;
import om.logitrack.api.repository.EmpresaRepository;
import om.logitrack.api.repository.FuncionarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    private final EmpresaRepository empresaRepository;

    @Transactional

    public FuncionarioDetalhamentoDTO criar(FuncionarioDTO data){
        if (funcionarioRepository.existsByMatricula(data.matricula())){
            throw new RegraDeNegocio("Erro, ja existe funcionario cadastrado");
        }
        Empresa empresaEncontrada = empresaRepository.findById(data.empresaId())

                .orElseThrow(()-> new RegraDeNegocio("Empresa nao encontrada"));

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(data.nome());
        funcionario.setFuncao(data.funcao());
        funcionario.setMatricula(data.matricula());
        funcionario.setAtivo(true);
        funcionario.setEmpresa(empresaEncontrada);

        var salvar = funcionarioRepository.save(funcionario);
        return FuncionarioDetalhamentoDTO.dto(salvar);
    }
    @Transactional(readOnly = true)
    public Page<FuncionarioDetalhamentoDTO> listar(Pageable pageable){
        return funcionarioRepository.findAll(pageable)
                .map(FuncionarioDetalhamentoDTO::dto);

    }

}