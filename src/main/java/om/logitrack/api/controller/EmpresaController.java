package om.logitrack.api.controller;

import jakarta.validation.Valid;
import om.logitrack.api.dto.EmpresaDetalhamentoDTO;
import om.logitrack.api.dto.dtoRequest.EmpresaRequestDTO;
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

    private final EmpresaService empresaService;
    public EmpresaController(EmpresaService empresaService){
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<EmpresaDetalhamentoDTO> cadastrar(@RequestBody @Valid EmpresaRequestDTO data, UriComponentsBuilder uriComponentsBuilder){

        var dto = empresaService.cadastrar(data);
        var uri = uriComponentsBuilder.path("/empresas/{id}")
                .buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDetalhamentoDTO>> listar() {
        var lista = empresaService.lista();

        return ResponseEntity.ok(lista);
    }

}
