package om.logitrack.api.repository;

import om.logitrack.api.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    @Query(
            "SELECT m FROM Manutencao m JOIN FETCH m.veiculo WHERE m.empresa.id = :empresaId"
    )
    List<Manutencao> listarComVeiculo(Long empresaId);
}
