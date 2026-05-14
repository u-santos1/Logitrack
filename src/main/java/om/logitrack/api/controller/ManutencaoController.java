package om.logitrack.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import om.logitrack.api.dto.CustoTotalDTO;
import om.logitrack.api.dto.ManutencaoDetalhadamenteDTO;
import om.logitrack.api.dto.dtoRequest.ManutencaoDTO;
import om.logitrack.api.service.ManutencaoService;
import org.springframework.data.domain.DomainEvents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/manutencoes")
@RequiredArgsConstructor
public class ManutencaoController {
    private final ManutencaoService manutencaoService;

    @PostMapping
    public ResponseEntity<ManutencaoDetalhadamenteDTO> criar(@RequestBody @Valid ManutencaoDTO data, UriComponentsBuilder uriComponentsBuilder){
        var dto = manutencaoService.criar(data);
        var uri = uriComponentsBuilder.path("/manutencoes/{id}")
                .buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    @GetMapping()
    public ResponseEntity<Page<ManutencaoDetalhadamenteDTO>> listar(@PageableDefault(size = 10, sort = {"dataEntrada"}, direction = Sort.Direction.DESC) Pageable pageable){
        var pagina = manutencaoService.listar(pageable);
        return ResponseEntity.ok(pagina);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ManutencaoDetalhadamenteDTO> atualizacao(@PathVariable Long id, @RequestBody @Valid  ManutencaoDTO data){
        var dto = manutencaoService.atualizacao(id, data);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        manutencaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/veiculo/{placa}")
    public ResponseEntity<Page<ManutencaoDetalhadamenteDTO>> listarPlaca(@PathVariable String placa,
                                                                         @PageableDefault(size = 10, sort = {"dataEntrada"}
                                                                         ,direction = Sort.Direction.DESC) Pageable pageable){
        var dto = manutencaoService.listarPorPlaca(placa, pageable);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/veiculo/{placa}/custos")
    public ResponseEntity<CustoTotalDTO> consultarPlaca(@PathVariable String placa){
        var dto = manutencaoService.custoPorPlaca(placa);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/relatorio/custo")
    public ResponseEntity<CustoTotalDTO> custo(@RequestParam LocalDate inicio,
                               @RequestParam LocalDate fim){
        var dto = manutencaoService.custo(inicio, fim);
        return ResponseEntity.ok(dto);
    }

}
