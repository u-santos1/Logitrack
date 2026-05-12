ALTER TABLE tb_manutencoes AND COLUMN ativo BOOLEAN;
ALTER TABLE tb_manutencoes AND COLUMN status VARCHAR(50);

UPDATE tb_manutencoes SET ativo = TRUE;
UPDATE tb_manutencoes SET status = 'MANUTENCAO';

ALTER TABLE tb_manutencoes ALTER COLUMN ativo SET NOT NULL;
ALTER TABLE tb_manutencoes ALTER COLUMN status SET NOT NULL;