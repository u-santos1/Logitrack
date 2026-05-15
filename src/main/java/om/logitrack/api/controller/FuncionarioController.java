package om.logitrack.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import om.logitrack.api.dto.FuncionarioDetalhamentoDTO;
import om.logitrack.api.dto.dtoRequest.FuncionarioDTO;
import om.logitrack.api.model.Usuario;
import om.logitrack.api.service.FuncionarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioDetalhamentoDTO> criar(@RequestBody @Valid FuncionarioDTO data, UriComponentsBuilder uriComponentsBuilder){
        var dto = funcionarioService.criar(data);
        var uri = uriComponentsBuilder.path("/funcionarios/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<FuncionarioDetalhamentoDTO>> listar(
            @PageableDefault(size = 10, sort = {"matricula"}, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Usuario usuarioLogado){
        var dto = funcionarioService.listar(pageable, usuarioLogado);
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDetalhamentoDTO> atualizacao(@PathVariable Long id, @RequestBody @Valid FuncionarioDTO data){
        var dto = funcionarioService.atualizacao(id, data);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        funcionarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
