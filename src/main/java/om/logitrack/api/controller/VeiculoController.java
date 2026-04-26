package om.logitrack.api.controller;


import jakarta.validation.Valid;
import om.logitrack.api.dto.dtoRequest.VeiculoCadastroDTO;
import om.logitrack.api.dto.VeiculoDTO;
import om.logitrack.api.service.VeiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;
    public VeiculoController(VeiculoService veiculoService){
        this.veiculoService = veiculoService;
    }


    @PostMapping
    public ResponseEntity<VeiculoDTO> cadastrar(@RequestBody @Valid VeiculoCadastroDTO dados, UriComponentsBuilder uriBuilder) {
        var dto = veiculoService.cadastrar(dados);
        var uri = uriBuilder.path("/veiculos/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
}
}
