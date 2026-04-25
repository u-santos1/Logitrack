package om.logitrack.api.repository;

import om.logitrack.api.dto.EmpresaDetalhamentoDTO;
import om.logitrack.api.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    @Query("SELECT DISTINCT e FROM Empresa e LEFT JOIN FETCH e.veiculos")
    List<Empresa> findAllComVeiculos();
}
