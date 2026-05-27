package om.logitrack.api.controller;


import jakarta.validation.Path;
import jakarta.validation.Valid;
import om.logitrack.api.dto.dtoRequest.VeiculoCadastroDTO;
import om.logitrack.api.dto.VeiculoDTO;
import om.logitrack.api.model.Usuario;
import om.logitrack.api.service.VeiculoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;
    public VeiculoController(VeiculoService veiculoService){
        this.veiculoService = veiculoService;
    }


    @PostMapping
    public ResponseEntity<VeiculoDTO> cadastrar(@RequestBody @Valid VeiculoCadastroDTO dados,@AuthenticationPrincipal Usuario usuarioLogado, UriComponentsBuilder uriBuilder) {
        var dto = veiculoService.cadastrar(dados, usuarioLogado);
        var uri = uriBuilder.path("/veiculos/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
}
    @GetMapping()
    public ResponseEntity<Page<VeiculoDTO>> listar(
            Pageable pageable, @AuthenticationPrincipal Usuario usuarioLogado){
        return ResponseEntity.ok(veiculoService.listar(pageable,usuarioLogado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoDTO> atualizacao(@PathVariable Long id,
                                                  @RequestBody @Valid
                                                  VeiculoCadastroDTO data,
                                                  @AuthenticationPrincipal Usuario usuarioLogado){
        var dto = veiculoService.atualizar(id, data, usuarioLogado);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado){
        veiculoService.deletar(id, usuarioLogado);
        return  ResponseEntity.noContent().build();
    }
}
