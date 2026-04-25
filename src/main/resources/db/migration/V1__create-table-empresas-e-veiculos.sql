CREATE TABLE tb_empresas (
id BIGSERIAL PRIMARY KEY,
nome_fantasia VARCHAR(255) NOT NULL,
cnpj VARCHAR(18) NOT NULL UNIQUE,
ativo BOOLEAN DEFAULT TRUE);

CREATE TABLE tb_veiculo (
id BIGSERIAL PRIMARY KEY,
placa VARCHAR(10) NOT NULL UNIQUE,
modelo VARCHAR(100) NOT NULL,
tipo VARCHAR(50) NOT NULL,
status VARCHAR(50) NOT NULL,
ativo BOOLEAN DEFAULT TRUE,
criado_id TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
empresa_id BIGINT NOT NULL,
CONSTRAINT fk_veiculo_empresa FOREIGN KEY (empresa_id) REFERENCES tb_empresas(id));

CREATE INDEX idx_veiculo_empresa ON tb_veiculo(empresa_id);
CREATE INDEX idx_veiculo_placa ON tb_veiculo(placa);

