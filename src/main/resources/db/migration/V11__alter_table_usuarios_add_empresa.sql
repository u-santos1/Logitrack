    ALTER TABLE tb_usuarios ADD COLUMN empresa_id BIGINT;
    UPDATE tb_usuarios SET empresa_id = 1;
    ALTER TABLE tb_usuarios ADD CONSTRAINT fk_usuarios_empresa FOREIGN KEY (empresa_id) REFERENCES tb_empresas(id);