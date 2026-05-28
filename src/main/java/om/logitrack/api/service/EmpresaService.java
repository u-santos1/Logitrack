package om.logitrack.api.service;

import om.logitrack.api.infra.RegraDeNegocio;
import org.springframework.transaction.annotation.Transactional;
import om.logitrack.api.dto.EmpresaDetalhamentoDTO;
import om.logitrack.api.dto.dtoRequest.EmpresaRequestDTO;
import om.logitrack.api.model.Empresa;
import om.logitrack.api.model.Usuario;
import om.logitrack.api.repository.EmpresaRepository;
import org.springframework.data.domain.Page;

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
        if (empresaRepository.findByCnpjAndTrue(data.cnpj()).isPresent()){
            throw new RegraDeNegocio("Ja existe uma empresa com esse cnpj");
        }
        Empresa empresa = new Empresa();
        empresa.setNomeFantasia(data.nomeFantasia());
        empresa.setCnpj(data.cnpj());

        Empresa empresaSalva = empresaRepository.save(empresa);
        return new EmpresaDetalhamentoDTO(empresaSalva);
    }

    @Transactional(readOnly = true)
    public Page<EmpresaDetalhamentoDTO> listar(Pageable paginacao,
                                                Usuario usuarioLogado){
        Long idEmpresaLogada = usuarioLogado.getEmpresa().getId();
        return empresaRepository.findAllByAtivoTrueAndId(paginacao, idEmpresaLogada)
                .map(EmpresaDetalhamentoDTO::new);
    }
    @Transactional
    public EmpresaDetalhamentoDTO atualizar(Long id,
                                            EmpresaRequestDTO data,
                                            Usuario usuarioLogado){
        Empresa busca = empresaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocio("Empresa nao encontrada"));

        if(!busca.getId().equals(usuarioLogado.getEmpresa().getId())){
            throw new RegraDeNegocio("Acesso negado. Você não pode alterar os dados de outra empresa.");
        }

        if (!busca.getCnpj().equals(data.cnpj())){
            boolean cnpjExiste = empresaRepository.findByCnpjAndTrue(data.cnpj()).isPresent();
                if(cnpjExiste){
                    throw new RegraDeNegocio("Ja existe outra empresa cadastrada com esse cnpj");
                }
            }
        busca.setNomeFantasia(data.nomeFantasia());
        busca.setCnpj(data.cnpj());
        Empresa salvar = empresaRepository.save(busca);
        return new EmpresaDetalhamentoDTO(salvar);

    }

    @Transactional
    public void deletar(Long id, Usuario usuarioLogado){
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocio("Empresa nao encontrada"));
        if (!empresa.getId().equals(usuarioLogado.getEmpresa().getId())){
            throw new RegraDeNegocio("Acesso negado. Você não pode excluir outra empresa.");
        }
        empresa.setAtivo(false);
        empresaRepository.save(empresa);
    }

}
