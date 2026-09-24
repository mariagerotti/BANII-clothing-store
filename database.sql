-- DROP TABLES in reverse dependency order
DROP TABLE IF EXISTS item_compra CASCADE;
DROP TABLE IF EXISTS item_venda CASCADE;
DROP TABLE IF EXISTS compra CASCADE;
DROP TABLE IF EXISTS venda CASCADE;
DROP TABLE IF EXISTS produto CASCADE;
DROP TABLE IF EXISTS fornecedor CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS categoria CASCADE;
DROP TABLE IF EXISTS marca CASCADE;

-- 1. marca
CREATE TABLE marca (
    id_marca SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

-- 2. categoria
CREATE TABLE categoria (
    id_categoria SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);

-- 3. cliente
CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(150),
    endereco VARCHAR(255)
);

-- 4. fornecedor
CREATE TABLE fornecedor (
    id_fornecedor SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(150),
    endereco VARCHAR(255)
);

-- 5. produto
CREATE TABLE produto (
    id_produto SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    descricao VARCHAR(255),
    preco NUMERIC(10,2) NOT NULL,
    tamanho VARCHAR(10),
    quantidade_estoque INTEGER NOT NULL DEFAULT 0,
    cor VARCHAR(50),
    id_marca INTEGER NOT NULL REFERENCES marca(id_marca),
    id_categoria INTEGER NOT NULL REFERENCES categoria(id_categoria)
);

-- 6. venda
CREATE TABLE venda (
    id_venda SERIAL PRIMARY KEY,
    data_venda DATE NOT NULL,
    valor_total NUMERIC(10,2) NOT NULL DEFAULT 0,
    id_cliente INTEGER NOT NULL REFERENCES cliente(id_cliente)
);

-- 7. compra
CREATE TABLE compra (
    id_compra SERIAL PRIMARY KEY,
    data_compra DATE NOT NULL,
    valor_total NUMERIC(10,2) NOT NULL DEFAULT 0,
    id_fornecedor INTEGER NOT NULL REFERENCES fornecedor(id_fornecedor)
);

-- 8. item_venda
CREATE TABLE item_venda (
    id_item_venda SERIAL PRIMARY KEY,
    id_venda INTEGER NOT NULL REFERENCES venda(id_venda) ON DELETE CASCADE,
    id_produto INTEGER NOT NULL REFERENCES produto(id_produto),
    qtd_vendida INTEGER NOT NULL,
    preco_unitario NUMERIC(10,2) NOT NULL,
    UNIQUE(id_venda, id_produto)
);

-- 9. item_compra
CREATE TABLE item_compra (
    id_item_compra SERIAL PRIMARY KEY,
    id_compra INTEGER NOT NULL REFERENCES compra(id_compra) ON DELETE CASCADE,
    id_produto INTEGER NOT NULL REFERENCES produto(id_produto),
    qtd_comprada INTEGER NOT NULL,
    preco_unitario NUMERIC(10,2) NOT NULL,
    UNIQUE(id_compra, id_produto)
);

-- ==========================================
-- INSERT DATA
-- ==========================================

-- Categorias
INSERT INTO categoria (nome, descricao) VALUES
('Vestidos', 'Vestidos femininos para diversas ocasiões'),
('Blusas', 'Blusas e camisetas femininas'),
('Calças', 'Calças femininas de diversos modelos'),
('Saias', 'Saias femininas'),
('Jaquetas', 'Jaquetas e casacos femininos'),
('Acessórios', 'Acessórios femininos como bolsas, cintos e lenços');

-- Marcas
INSERT INTO marca (nome) VALUES
('Bella Donna'),
('Rosa Chic'),
('Flor de Lis'),
('Elegance'),
('Donna Moda');

-- Clientes
INSERT INTO cliente (nome, cpf, telefone, email, endereco) VALUES
('Maria Silva', '123.456.789-00', '(11) 98765-4321', 'maria.silva@email.com', 'Rua das Flores, 123, São Paulo, SP'),
('Ana Oliveira', '234.567.890-11', '(11) 97654-3210', 'ana.oli@email.com', 'Av. Paulista, 456, São Paulo, SP'),
('Juliana Santos', '345.678.901-22', '(21) 96543-2109', 'juh.santos@email.com', 'Rua Copacabana, 789, Rio de Janeiro, RJ'),
('Camila Ferreira', '456.789.012-33', '(31) 95432-1098', 'camila.fer@email.com', 'Av. Afonso Pena, 321, Belo Horizonte, MG'),
('Beatriz Costa', '567.890.123-44', '(41) 94321-0987', 'bia.costa@email.com', 'Rua XV de Novembro, 654, Curitiba, PR');

-- Fornecedores
INSERT INTO fornecedor (nome, cnpj, telefone, email, endereco) VALUES
('Distribuidora Fashion Ltda', '12.345.678/0001-90', '(11) 3333-4444', 'contato@distfashion.com.br', 'Rua do Brás, 100, São Paulo, SP'),
('Tecidos & Confecções Brasil', '98.765.432/0001-10', '(47) 3222-1111', 'vendas@tecbrasil.com.br', 'Av. Industrial, 500, Blumenau, SC'),
('Moda Atacado S.A.', '45.678.901/0001-55', '(85) 3111-2222', 'comercial@modaatacado.com.br', 'Rua das Confecções, 800, Fortaleza, CE');

-- Produtos
INSERT INTO produto (nome, descricao, preco, tamanho, quantidade_estoque, cor, id_marca, id_categoria) VALUES
('Vestido Floral Longo', 'Vestido longo com estampa floral de verão', 149.90, 'M', 20, 'Azul', 3, 1),
('Vestido de Festa Preto', 'Vestido de festa clássico', 299.90, 'P', 10, 'Preto', 4, 1),
('Vestido Midi Canelado', 'Vestido midi ajustado ao corpo', 89.90, 'G', 30, 'Vermelho', 1, 1),
('Blusa de Seda Básica', 'Blusa manga curta em seda sintética', 79.90, 'M', 45, 'Branco', 2, 2),
('T-shirt Algodão Estampada', 'Camiseta de algodão com estampa frontal', 49.90, 'P', 50, 'Amarelo', 5, 2),
('Blusa Gola Alta Inverno', 'Blusa manga longa com gola alta', 119.90, 'GG', 15, 'Preto', 4, 2),
('Calça Jeans Skinny', 'Calça jeans modelo skinny com elastano', 159.90, 'M', 25, 'Jeans Azul', 1, 3),
('Calça Pantalona Alfaiataria', 'Calça elegante de corte amplo', 189.90, 'G', 12, 'Bege', 4, 3),
('Calça Legging Esportiva', 'Calça legging para atividades diárias', 69.90, 'M', 40, 'Preto', 5, 3),
('Saia Plissada Midi', 'Saia midi com tecido plissado', 129.90, 'P', 18, 'Rosa', 2, 4),
('Saia Jeans Curta', 'Saia jeans básica para o dia a dia', 89.90, 'M', 22, 'Jeans Claro', 3, 4),
('Jaqueta de Couro Fake', 'Jaqueta feminina estilo biker', 259.90, 'M', 8, 'Preto', 4, 5),
('Casaco de Lã Alongado', 'Casaco de inverno elegante', 399.90, 'G', 5, 'Vinho', 4, 5),
('Jaqueta Jeans Oversized', 'Jaqueta ampla de lavagem média', 179.90, 'P', 14, 'Jeans Azul', 1, 5),
('Bolsa Tiracolo Pequena', 'Bolsa de ombro em couro sintético', 119.90, 'Único', 30, 'Caramelo', 2, 6),
('Cinto Fino de Couro', 'Cinto fino com fivela dourada', 39.90, 'Único', 45, 'Preto', 5, 6),
('Lenço de Seda Estampado', 'Lenço leve para o pescoço ou cabelo', 29.90, 'Único', 25, 'Multicolor', 3, 6);

-- Vendas
-- Venda 1: 1 Vestido Floral Longo (149.90) + 1 Bolsa Tiracolo Pequena (119.90) = 269.80
INSERT INTO venda (data_venda, valor_total, id_cliente) VALUES ('2024-05-10', 269.80, 1);
-- Venda 2: 2 T-shirt Algodão Estampada (2 * 49.90) = 99.80
INSERT INTO venda (data_venda, valor_total, id_cliente) VALUES ('2024-05-12', 99.80, 2);
-- Venda 3: 1 Calça Pantalona Alfaiataria (189.90) + 1 Blusa de Seda Básica (79.90) = 269.80
INSERT INTO venda (data_venda, valor_total, id_cliente) VALUES ('2024-06-01', 269.80, 3);
-- Venda 4: 1 Casaco de Lã Alongado (399.90) = 399.90
INSERT INTO venda (data_venda, valor_total, id_cliente) VALUES ('2025-01-15', 399.90, 4);

-- Itens das Vendas
-- Itens Venda 1
INSERT INTO item_venda (id_venda, id_produto, qtd_vendida, preco_unitario) VALUES
(1, 1, 1, 149.90),
(1, 15, 1, 119.90);
-- Itens Venda 2
INSERT INTO item_venda (id_venda, id_produto, qtd_vendida, preco_unitario) VALUES
(2, 5, 2, 49.90);
-- Itens Venda 3
INSERT INTO item_venda (id_venda, id_produto, qtd_vendida, preco_unitario) VALUES
(3, 8, 1, 189.90),
(3, 4, 1, 79.90);
-- Itens Venda 4
INSERT INTO item_venda (id_venda, id_produto, qtd_vendida, preco_unitario) VALUES
(4, 13, 1, 399.90);

-- Compras
-- Compra 1 (Distribuidora Fashion Ltda): 10 Vestido Floral Longo (10 * 80.00 = 800.00) + 10 Bolsa Tiracolo (10 * 60.00 = 600.00) = 1400.00
INSERT INTO compra (data_compra, valor_total, id_fornecedor) VALUES ('2024-04-20', 1400.00, 1);
-- Compra 2 (Tecidos & Confecções): 20 T-shirt (20 * 25.00 = 500.00) + 15 Calça Jeans (15 * 80.00 = 1200.00) = 1700.00
INSERT INTO compra (data_compra, valor_total, id_fornecedor) VALUES ('2024-05-05', 1700.00, 2);
-- Compra 3 (Moda Atacado S.A.): 5 Casaco de Lã (5 * 200.00 = 1000.00) = 1000.00
INSERT INTO compra (data_compra, valor_total, id_fornecedor) VALUES ('2024-12-10', 1000.00, 3);

-- Itens das Compras
-- Itens Compra 1
INSERT INTO item_compra (id_compra, id_produto, qtd_comprada, preco_unitario) VALUES
(1, 1, 10, 80.00),
(1, 15, 10, 60.00);
-- Itens Compra 2
INSERT INTO item_compra (id_compra, id_produto, qtd_comprada, preco_unitario) VALUES
(2, 5, 20, 25.00),
(2, 7, 15, 80.00);
-- Itens Compra 3
INSERT INTO item_compra (id_compra, id_produto, qtd_comprada, preco_unitario) VALUES
(3, 13, 5, 200.00);
