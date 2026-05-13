CREATE TABLE tb_funcionario(

id BIGSERIAL PRIMARY KEY,
nome VARCHAR(50) NOT NULL,
funcao VARCHAR(100) NOT NULL,
matricula VARCHAR(255) NOT NULL UNIQUE,
ativo BOOLEAN DEFAULT TRUE,
empresa_id BIGINT NOT NULL,
CONSTRAINT fk_operador_empresa FOREIGN KEY (empresa_id) REFERENCES tb_empresas(id)
);
