CREATE INDEX IF NOT EXISTS idx_funcionario_empresa ON tb_funcionario(empresa_id);
CREATE INDEX IF NOT EXISTS idx_veiculo_empresa ON tb_veiculo(empresa_id);
CREATE INDEX IF NOT EXISTS idx_manutencao_empresa ON tb_manutencoes(empresa_id);


