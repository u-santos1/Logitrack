CREATE TABLE tb_manutencoes (
    id BIGSERIAL PRIMARY KEY,
    descricao TEXT NOT NULL,
    data_entrada DATE NOT NULL,
    data_saida DATE,
    valor_total DECIMAL(10, 2) NOT NULL,
    veiculo_id BIGINT NOT NULL,
    empresa_id BIGINT NOT NULL,

    CONSTRAINT fk_manutencao_veiculo FOREIGN KEY (veiculo_id) REFERENCES tb_veiculo(id),
    CONSTRAINT fk_manutencao_empresa FOREIGN KEY (empresa_id) REFERENCES tb_empresas(id)
);

-- Índice para acelerar relatórios financeiros
CREATE INDEX idx_manutencao_data ON tb_manutencoes(data_entrada);