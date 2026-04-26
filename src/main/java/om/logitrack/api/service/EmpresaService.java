package om.logitrack.api.service;


import om.logitrack.api.dto.EmpresaDetalhamentoDTO;
import om.logitrack.api.dto.dtoRequest.EmpresaRequestDTO;
import om.logitrack.api.model.Empresa;
import om.logitrack.api.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<EmpresaDetalhamentoDTO> lista(){
        return empresaRepository.findAllComVeiculos()
                .stream()
                .map(EmpresaDetalhamentoDTO:: new)
                .toList();

    }
}
