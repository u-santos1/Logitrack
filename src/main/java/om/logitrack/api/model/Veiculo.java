package om.logitrack.api.model;




import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import om.logitrack.api.model.enums.StatusVeiculo;
import om.logitrack.api.model.enums.TipoVeiculo;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_veiculo")
@Getter
@Setter
@SQLRestriction("ativo = true")public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipo;

    @Enumerated(EnumType.STRING)
    private StatusVeiculo status = StatusVeiculo.DISPONIVEL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @Column(name = "criado_id")
    private LocalDateTime criadoEm = LocalDateTime.now();

    private boolean ativo = true;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private List<Manutencao> manutencaos = new ArrayList<>();
}
