package om.logitrack.api.repository;

import om.logitrack.api.model.Manutencao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    @Query(
            "SELECT m FROM Manutencao m JOIN FETCH m.veiculo WHERE m.empresa.id = :empresaId"
    )
    List<Manutencao> listarComVeiculo(Long empresaId);

    boolean existsByVeiculoPlacaAndDataSaidaIsNull(String placa);

    Page<Manutencao> findByVeiculoPlaca(String placa, Pageable pageable);

    @Query(
            "SELECT SUM(m.valorTotal) FROM Manutencao m WHERE m.veiculo.placa = :placa AND m.ativo = true"
    )
    BigDecimal somarCustosPorPlacas(String placa);

    @Query(
            "SELECT SUM(m.valorTotal) FROM Manutencao m" +
                    " WHERE m.dataEntrada BETWEEN :inicio AND :fim" +
                    " AND m.ativo = true"
    )
    BigDecimal somarCustoTotal(@Param("inicio") LocalDate inicio,
                               @Param("fim") LocalDate fim);

    Page<Manutencao> findAllByAtivoTrueAndEmpresaId(Pageable pageable, Long id);
}
