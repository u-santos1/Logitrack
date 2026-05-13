ALTER TABLE tb_manutencoes ADD COLUMN funcionario_id BIGINT;
ALTER TABLE tb_manutencoes ADD CONSTRAINT fk_manuntecao_funcionario FOREIGN KEY (funcionario_id) REFERENCES tb_funcionario(id);