package om.logitrack.api.repository;


import om.logitrack.api.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Optional<Veiculo> findByPlaca(String placa);

    @Query(
            "SELECT v FROM Veiculo v WHERE v.empresa.id = :empresaId"
    )
    List<Veiculo> listarPorEmpresa(Long empresaId);

    @Query(
            "SELECT v FROM Veiculo v " +
                    "WHERE v.empresa.id = :empresaId " +
                    "AND v.status = om.logitrack.api.model.enums.StatusVeiculo.DISPONIVEL"
    )List<Veiculo> buscarDisponiveis(Long empresaId);

    List<Veiculo> findByEmpresa_Id(Long empresaId);
}
