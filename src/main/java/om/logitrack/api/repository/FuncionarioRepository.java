package om.logitrack.api.repository;

import om.logitrack.api.model.Empresa;
import om.logitrack.api.model.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    boolean existsByMatricula(String matricula);

    Page<Funcionario> findAllByAtivoTrueAndEmpresaId(Pageable pageable, Long empresaId);
}
