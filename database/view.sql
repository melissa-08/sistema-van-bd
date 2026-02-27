--1. Detalhes das Viagens (Une Motorista, Veículo e Rota)
DROP VIEW IF EXISTS v_detalhes_viagem;
CREATE VIEW v_detalhes_viagem AS
SELECT 
    t.id AS travel_id,
    d.name AS driver_name,
    v.model AS vehicle_model,
    v.plate AS vehicle_plate,
    r.name AS route_name,
    t.departure_time,
    t.status
FROM travels t
JOIN vehicles v ON t.vehicle_id = v.id
JOIN drivers d ON v.driver_id = d.id
JOIN routes r ON t.route_id = r.id;

-- 2. Relatório de Ocupação e Receita
DROP VIEW IF EXISTS v_relatorio_financeiro_viagem;
CREATE VIEW v_relatorio_financeiro_viagem AS
SELECT 
    t.id AS travel_id,
    r.name AS route_name,
    t.departure_time,
    COALESCE(SUM(res.passenger_count), 0) AS total_passengers,
    COALESCE(SUM(res.total_value), 0) AS total_revenue
FROM travels t
JOIN routes r ON t.route_id = r.id
LEFT JOIN reservations res ON t.id = res.travel_id
GROUP BY t.id, r.name, t.departure_time;

-- 3. Ranking de Avaliações por Motorista
DROP VIEW IF EXISTS v_ranking_motoristas;
CREATE VIEW v_ranking_motoristas AS
SELECT 
    d.id AS driver_id,
    d.name AS driver_name,
    ROUND(AVG(ra.score), 2) AS average_rating,
    COUNT(ra.id) AS total_feedbacks
FROM drivers d
JOIN vehicles v ON d.id = v.driver_id
JOIN travels t ON v.id = t.vehicle_id
JOIN ratings ra ON t.id = ra.travel_id
GROUP BY d.id, d.name
HAVING COUNT(ra.id) > 0;