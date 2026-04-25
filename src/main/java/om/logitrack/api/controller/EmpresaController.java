package om.logitrack.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import om.logitrack.api.dto.EmpresaCadastroDTO;
import om.logitrack.api.dto.EmpresaDetalhamentoDTO;
import om.logitrack.api.model.Empresa;
import om.logitrack.api.repository.EmpresaRepository;
import om.logitrack.api.service.EmpresaService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/empresas")

public class EmpresaController {

    private final EmpresaRepository empresaRepository;
    private final EmpresaService empresaService;
    public EmpresaController(EmpresaRepository empresaRepository, EmpresaService empresaService){
        this.empresaRepository = empresaRepository;
        this.empresaService = empresaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<EmpresaDetalhamentoDTO> cadastrar(@RequestBody @Valid EmpresaCadastroDTO data, UriComponentsBuilder uriComponentsBuilder){
        Empresa empresa = new Empresa();
        empresa.setNomeFantasia(data.nomeFantasia());
        empresa.setCnpj(data.cnpj());

        Empresa empresaSalva = empresaRepository.save(empresa);
        var uri = uriComponentsBuilder.path("/empresas/{id}")
                .buildAndExpand(empresaSalva.getId()).toUri();
        return ResponseEntity.created(uri).body(new EmpresaDetalhamentoDTO(empresaSalva));
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDetalhamentoDTO>> listar() {
        var lista = empresaService.lista();

        return ResponseEntity.ok(lista);
    }

}
