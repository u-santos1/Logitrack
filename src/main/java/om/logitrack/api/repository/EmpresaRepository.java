package om.logitrack.api.repository;


import om.logitrack.api.model.Empresa;

import om.logitrack.api.model.Funcionario;
import om.logitrack.api.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    @Query("SELECT DISTINCT e FROM Empresa e LEFT JOIN FETCH e.veiculos")
    List<Empresa> findAllComVeiculos();


    Page<Empresa> findAllByAtivoTrueAndId(Pageable pageable, Long id);

    Optional<Empresa> findByCnpjAndTrue(String cnpj);
}
