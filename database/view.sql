--1. Detalhes das Viagens (Une Motorista, Veículo e Rota)
CREATE VIEW v_detalhes_viagem AS
SELECT 
    v.id_viagem,
    m.nome AS motorista,
    ve.modelo AS veiculo,
    ve.placa,
    r.origem,
    r.destino,
    v.data_hora
FROM VIAGEM v
JOIN VEICULO ve ON v.id_veiculo = ve.id_veiculo
JOIN MOTORISTA m ON ve.id_motorista = m.id_motorista
JOIN ROTA r ON v.id_rota = r.id_rota;

-- 2. Relatório de Ocupação e Receita
DROP VIEW IF EXISTS v_relatorio_financeiro_viagem;
CREATE VIEW v_relatorio_financeiro_viagem AS
SELECT 
    v.id_viagem,
    r.origem,
    r.destino,
    COUNT(res.id_reserva) AS total_passageiros,
    SUM(pt.valor) AS receita_total
FROM VIAGEM v
JOIN ROTA r ON v.id_rota = r.id_rota
LEFT JOIN RESERVA res ON v.id_viagem = res.id_viagem
LEFT JOIN PRECOS_TRECHOS pt ON r.id_rota = pt.id_rota
GROUP BY v.id_viagem, r.origem, r.destino;

-- 3. Ranking de Avaliações por Motorista
DROP VIEW IF EXISTS v_ranking_motoristas;
CREATE VIEW v_ranking_motoristas AS
SELECT 
    m.nome AS motorista,
    AVG(a.nota) AS media_avaliacao,
    COUNT(a.id_avaliacao) AS total_feedbacks
FROM MOTORISTA m
JOIN VEICULO ve ON m.id_motorista = ve.id_motorista
JOIN VIAGEM vi ON ve.id_veiculo = vi.id_veiculo
JOIN AVALIACAO a ON vi.id_viagem = a.id_viagem
GROUP BY m.id_motorista, m.nome
HAVING COUNT(a.id_avaliacao) > 0;