package om.logitrack.api.service;


import jakarta.transaction.Transactional;
import om.logitrack.api.dto.EmpresaDetalhamentoDTO;
import om.logitrack.api.dto.dtoRequest.EmpresaRequestDTO;
import om.logitrack.api.model.Empresa;
import om.logitrack.api.model.Usuario;
import om.logitrack.api.repository.EmpresaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    public EmpresaService(EmpresaRepository empresaRepository){
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public EmpresaDetalhamentoDTO cadastrar(EmpresaRequestDTO data){
        Empresa empresa = new Empresa();
        empresa.setNomeFantasia(data.nomeFantasia());
        empresa.setCnpj(data.cnpj());

        Empresa empresaSalva = empresaRepository.save(empresa);
        return new EmpresaDetalhamentoDTO(empresaSalva);
    }

    public Page<EmpresaDetalhamentoDTO> listar(Pageable paginacao,
                                               @AuthenticationPrincipal Usuario usuarioLogado){
        Long idEmpresaLogada = usuarioLogado.getEmpresa().getId();
        return empresaRepository.findAllByAtivoTrueAndId(paginacao, idEmpresaLogada)
                .map(EmpresaDetalhamentoDTO::new);
    }
}
