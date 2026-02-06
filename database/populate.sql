INSERT INTO MOTORISTA (id_motorista, nome, cpf, telefone, chave_pix, email) VALUES
(1, 'João Batista da Silva', '12345678901', '81999990001', 'joao.silva@email.com', 'joao.silva@email.com'),
(2, 'Maria Aparecida Souza', '12345678902', '81999990002', 'maria.souza@email.com', 'maria.souza@email.com'),
(3, 'Carlos Eduardo Lima', '12345678903', '81999990003', 'carlos.lima@email.com', 'carlos.lima@email.com'),
(4, 'Ana Paula Rodrigues', '12345678904', '81999990004', 'ana.rodrigues@email.com', 'ana.rodrigues@email.com'),
(5, 'Roberto Alves Pereira', '12345678905', '81999990005', 'roberto.pereira@email.com', 'roberto.pereira@email.com');

INSERT INTO VEICULO (placa, modelo, qtd_assentos, id_motorista) VALUES
('ABC1D23', 'Mercedes Sprinter', 15, 1),
('DEF4G56', 'Renault Master', 16, 2),
('GHI7J89', 'Fiat Ducato', 17, 3),
('JKL0M12', 'Volkswagen Crafter', 16, 4),
('NOP3Q45', 'Iveco Daily', 15, 5);

INSERT INTO ROTA (id_rota, nome) VALUES
(1, 'Garanhuns - Caruaru'),
(2, 'Caruaru - Garanhuns'),
(3, 'Garanhuns - Belo Jardim'),
(4, 'Belo Jardim - Garanhuns'),
(5, 'Garanhuns - Caetés'),
(6, 'Caetés - Garanhuns');

INSERT INTO PONTO (id_ponto, cidade, local_parada, ordem_na_rota, id_rota) VALUES
-- ROTA 1: Garanhuns → Caruaru
(1, 'Garanhuns', 'Rodoviária', 1, 1),
(2, 'São Caetano', 'Posto BR', 2, 1),
(3, 'Caruaru', 'Rodoviária', 3, 1),

-- ROTA 2: Caruaru → Garanhuns
(4, 'Caruaru', 'Rodoviária', 1, 2),
(5, 'São Caetano', 'Posto BR', 2, 2),
(6, 'Garanhuns', 'Rodoviária', 3, 2),

-- ROTA 3: Garanhuns → Belo Jardim
(7, 'Garanhuns', 'Rodoviária', 1, 3),
(8, 'Lajedo', 'Praça da Prefeitura', 2, 3),
(9, 'Belo Jardim', 'Centro', 3, 3),

-- ROTA 4: Belo Jardim → Garanhuns
(10, 'Belo Jardim', 'Centro', 1, 4),
(11, 'Lajedo', 'Praça da Prefeitura', 2, 4),
(12, 'Garanhuns', 'Rodoviária', 3, 4),

-- ROTA 5: Garanhuns → Caetés
(13, 'Garanhuns', 'Rodoviária', 1, 5),
(14, 'Caetés', 'Praça Central', 2, 5),

-- ROTA 6: Caetés → Garanhuns
(15, 'Caetés', 'Praça Central', 1, 6),
(16, 'Garanhuns', 'Rodoviária', 2, 6);


INSERT INTO VIAGEM (id_viagem, horario_saida, status, placa, id_motorista, id_rota) VALUES
-- Garanhuns → Caruaru
(1, '2026-02-05 06:30:00', 'CONCLUIDA', 'ABC1D23', 1, 1),

-- Caruaru → Garanhuns
(2, '2026-02-05 13:30:00', 'CONCLUIDA', 'ABC1D23', 1, 2),

-- Garanhuns → Belo Jardim
(3, '2026-02-15 07:00:00', 'DISPONIVEL', 'DEF4G56', 2, 3),

-- Belo Jardim → Garanhuns
(4, '2026-02-15 12:00:00', 'DISPONIVEL', 'DEF4G56', 2, 4),

-- Garanhuns → Caetés
(5, '2026-02-16 08:00:00', 'DISPONIVEL', 'GHI7J89', 3, 5),

-- Caetés → Garanhuns
(6, '2026-02-16 11:30:00', 'DISPONIVEL', 'GHI7J89', 3, 6);


INSERT INTO PRECOS_TRECHOS (id_viagem, id_ponto_subida, id_ponto_descida, valor_trecho) VALUES
-- VIAGEM 1: Garanhuns → Caruaru (Rota 1)
-- Pontos: 1 -> 2 -> 3
(1, 1, 2, 25.00),  -- Garanhuns -> São Caetano
(1, 2, 3, 20.00),  -- São Caetano -> Caruaru
(1, 1, 3, 45.00),  -- Garanhuns -> Caruaru

-- VIAGEM 2: Caruaru → Garanhuns (Rota 2)
-- Pontos: 4 -> 5 -> 6
(2, 4, 5, 20.00),  -- Caruaru -> São Caetano
(2, 5, 6, 25.00),  -- São Caetano -> Garanhuns
(2, 4, 6, 45.00),  -- Caruaru -> Garanhuns

-- VIAGEM 3: Garanhuns → Belo Jardim (Rota 3)
-- Pontos: 7 -> 8 -> 9
(3, 7, 8, 15.00),  -- Garanhuns -> Lajedo
(3, 8, 9, 15.00),  -- Lajedo -> Belo Jardim
(3, 7, 9, 30.00),  -- Garanhuns -> Belo Jardim

-- VIAGEM 4: Belo Jardim → Garanhuns (Rota 4)
-- Pontos: 10 -> 11 -> 12
(4, 10, 11, 15.00), -- Belo Jardim -> Lajedo
(4, 11, 12, 15.00), -- Lajedo -> Garanhuns
(4, 10, 12, 30.00), -- Belo Jardim -> Garanhuns

-- VIAGEM 5: Garanhuns → Caetés (Rota 5)
-- Pontos: 13 -> 14
(5, 13, 14, 10.00), -- Garanhuns -> Caetés

-- VIAGEM 6: Caetés → Garanhuns (Rota 6)
-- Pontos: 15 -> 16
(6, 15, 16, 10.00); -- Caetés -> Garanhuns


INSERT INTO CLIENTE (id_cliente, nome, cpf, telefone, email) VALUES
(1, 'Lucas Andrade', '98765432101', '81988887701', 'lucas.andrade@email.com'),
(2, 'Fernanda Oliveira', '98765432102', '81988887702', 'fernanda.oliveira@email.com'),
(3, 'Pedro Henrique Santos', '98765432103', '81988887703', 'pedro.santos@email.com'),
(4, 'Juliana Costa', '98765432104', '81988887704', 'juliana.costa@email.com'),
(5, 'Marcos Vinícius Almeida', '98765432105', '81988887705', 'marcos.almeida@email.com');


INSERT INTO RESERVA 
(id_cliente, id_viagem, id_ponto_subida, id_ponto_descida, qtd_passageiros, valor_total_reserva, status_reserva) 
VALUES
-- Viagem 1: Garanhuns → Caruaru
(1, 1, 1, 3, 1, 45.00, 'CONCLUIDA'),
(2, 1, 1, 2, 2, 50.00, 'CONCLUIDA'), -- 25 × 2

-- Viagem 2: Caruaru → Garanhuns
(3, 2, 4, 6, 1, 45.00, 'CONCLUIDA'),

-- Viagem 3: Garanhuns → Belo Jardim
(4, 3, 7, 9, 1, 30.00, 'CONFIRMADA'),
(5, 3, 7, 8, 2, 30.00, 'CONFIRMADA'), -- 15 × 2

-- Viagem 4: Belo Jardim → Garanhuns
(1, 4, 10, 12, 1, 30.00, 'CONFIRMADA'),

-- Viagem 5: Garanhuns → Caetés
(2, 5, 13, 14, 3, 30.00, 'CONFIRMADA'), -- 10 × 3

-- Viagem 6: Caetés → Garanhuns
(3, 6, 15, 16, 1, 10.00, 'CONFIRMADA');


INSERT INTO AVALIACAO (id_avaliacao, pontuacao, comentario, id_motorista, id_cliente) VALUES
-- Avaliações da Viagem 1 (Motorista João Batista)
(1, 5, 'Viagem tranquila, motorista muito educado e pontual.', 1, 1),
(2, 4, 'Bom atendimento, apenas um pequeno atraso na saída.', 1, 2),

-- Avaliação da Viagem 2 (Motorista João Batista)
(3, 5, 'Excelente viagem, direção segura e confortável.', 1, 3);
