package om.logitrack.api.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import om.logitrack.api.dto.VeiculoCadastroDTO;
import om.logitrack.api.dto.VeiculoDTO;
import om.logitrack.api.model.Veiculo;
import om.logitrack.api.repository.EmpresaRepository;
import om.logitrack.api.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final EmpresaRepository empresaRepository;

    @Transactional
    public VeiculoDTO cadastrar(VeiculoCadastroDTO data){
        var empresa = empresaRepository.findById(data.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa nao encontrada com id " + data.empresaId()));

        var veiculo = new Veiculo();
        veiculo.setPlaca(data.placa());
        veiculo.setModelo(data.modelo());
        veiculo.setTipo(data.tipo());
        veiculo.setStatus(data.status());
        veiculo.setEmpresa(empresa);
        veiculoRepository.save(veiculo);
        return new VeiculoDTO(veiculo);
    }
}
