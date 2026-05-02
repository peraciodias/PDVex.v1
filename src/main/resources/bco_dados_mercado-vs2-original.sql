--Para executar via linha de comando:
--psql -h localhost -U seu_usuario -d seu_banco -f schema_banco.sql.
--Ou simplesmente copie e cole no Query Tool do pgAdmin 4 ou DBeaver.

-- SCRIPT DE CRIAÇÃO - POSTGRESQL
-- Data: 05/04/2026

-- Limpeza opcional (CUIDADO: remove os dados se já existirem)
-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public;
-- Transfere a propriedade de todas as tabelas no esquema public para o pera
-- Isso garante que ele possa fazer SELECT, INSERT, UPDATE e DELETE sem restrições

--executar essa linha após criar as tabelas -> GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO pera;

-- MUITO IMPORTANTE: Garante permissão nas Sequências (os IDs automáticos)
-- Sem isso, o Java falha ao tentar gerar um novo ID de venda

--executar essa linha após criar as tabelas -> GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO pera;

-- Define que todas as tabelas criadas no futuro também pertencerão ao pera


--------------------------------------------------------------------------------
-- 1. TABELAS SEM DEPENDÊNCIAS (TABELAS PAI)
--------------------------------------------------------------------------------
CREATE TABLE tabela_estabelecimento (
    id SERIAL PRIMARY KEY,
    razao_social VARCHAR(150) NOT NULL,
    nome_fantasia VARCHAR(150),
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    inscricao_estadual VARCHAR(20),
    logradouro VARCHAR(150),
    numero VARCHAR(20),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado CHAR(2),
    regime_tributario INT DEFAULT 1, -- 1: Simples Nacional, 3: Regime Normal
    aliq_ibpt DECIMAL(5,2) DEFAULT 13.45 -- Alíquota média para Lei da Transparência
);

CREATE TABLE public.tabela_usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(20) DEFAULT 'OPERADOR',
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE public.tabela_clientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    rg VARCHAR(20),
    telefone VARCHAR(15),
    email VARCHAR(100),
    endereco VARCHAR(150),
    numero VARCHAR(10),
    bairro VARCHAR(50),
    cidade VARCHAR(50) NOT NULL,
    uf CHAR(2),
    cep VARCHAR(8),
    limite_credito NUMERIC(10,2) DEFAULT 0.00,
    data_cadastro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.tabela_clientes_pj (
    id BIGSERIAL PRIMARY KEY,
    razao_social VARCHAR(150) NOT NULL,
    nome_fantasia VARCHAR(150),
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    ie VARCHAR(30),
    telefone VARCHAR(11),
    email VARCHAR(150),
    endereco VARCHAR(150),
    numero VARCHAR(20),
    complemento VARCHAR(20),
    bairro VARCHAR(100),
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2),
    cep VARCHAR(8),
    limite_credito NUMERIC(12,2) DEFAULT 0.00,
    data_cadastro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.tabela_fornecedores (
    id BIGSERIAL PRIMARY KEY,
    razao_social VARCHAR(150) NOT NULL,
    nome_fantasia VARCHAR(150),
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    ie VARCHAR(30),
    contato VARCHAR(120) NOT NULL,
    telefone VARCHAR(11),
    email VARCHAR(150),
    endereco VARCHAR(150),
    numero VARCHAR(20),
    complemento VARCHAR(20),
    bairro VARCHAR(100),
    cidade VARCHAR(100) NOT NULL,
    uf CHAR(2),
    cep VARCHAR(8),
    limite_credito NUMERIC(12,2) DEFAULT 0.00,
    data_cadastro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.tabela_produtos (
    id BIGSERIAL PRIMARY KEY,
    codigo_barra VARCHAR(20) NOT NULL UNIQUE,
    origem VARCHAR(1) NOT NULL,
    descricao VARCHAR(50) NOT NULL,
    marca VARCHAR(50),
    atributos VARCHAR(50),
    unidade_medida VARCHAR(30),
    categoria VARCHAR(50),
    cod_grupo INTEGER,
    grupo VARCHAR(80),
    tipo_balanca CHAR(1) CHECK (tipo_balanca IN ('B', 'C', 'N')),
    quantidade_estoque NUMERIC(10,3) NOT NULL DEFAULT 0.000,
    preco_custo NUMERIC(10,2),
    preco_venda NUMERIC(10,2) NOT NULL,
    ncm VARCHAR(10),
    cest VARCHAR(7),
    cfop_padrao VARCHAR(4),
    unidade_tributavel VARCHAR(10),
    cean_tributavel VARCHAR(20),
    cst_icms VARCHAR(3),
    aliquota_icms NUMERIC(5,2) DEFAULT 0.00,
    cst_pis VARCHAR(2),
    ppis NUMERIC(5,2) DEFAULT 0.00,
    cst_cofins VARCHAR(2),
    pcofins NUMERIC(5,2) DEFAULT 0.00,
    data_cadastro TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    loja VARCHAR(50) DEFAULT 'MATRIZ'
);

-- Tabela de Apoio necessária para as chaves estrangeiras de Venda
CREATE TABLE IF NOT EXISTS public.tabela_caixas (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(50),
    id_usuario BIGINT REFERENCES public.tabela_usuarios(id)
);

--------------------------------------------------------------------------------
-- 2. TABELAS COM DEPENDÊNCIAS (CHAVES ESTRANGEIRAS)
--------------------------------------------------------------------------------

CREATE TABLE public.tabela_vendas (
    id_venda BIGSERIAL PRIMARY KEY,
    data_venda TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    
    id_usuario BIGINT NOT NULL,
    id_cliente BIGINT,
    nome_cliente_avulso VARCHAR(150),
    cpf_avulso VARCHAR(20),
    
    total_bruto NUMERIC(10,2) NOT NULL,
    total_desconto NUMERIC(10,2) DEFAULT 0.00,
	total_liquido NUMERIC(10,2) NOT NULL,
    total_tributos NUMERIC(10,2) DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'CONCLUIDA',
    metodo_pagamento VARCHAR(50) NOT NULL,
    
    valor_pago NUMERIC(10,2),
    troco NUMERIC(10,2),
    
    chave_nfe VARCHAR(44),
    id_caixa BIGINT,

    -- CONSTRAINTS
    CONSTRAINT fk_cliente_venda 
        FOREIGN KEY (id_cliente) 
        REFERENCES public.tabela_clientes(id),

    CONSTRAINT fk_usuario_venda 
        FOREIGN KEY (id_usuario) 
        REFERENCES public.tabela_usuarios(id),

    CONSTRAINT fk_venda_caixa 
        FOREIGN KEY (id_caixa) 
        REFERENCES public.tabela_caixas(id) 
        ON DELETE RESTRICT,

    CONSTRAINT chk_valores_venda 
        CHECK (
            total_bruto >= 0 AND 
            total_liquido >= 0 AND 
            (valor_pago IS NULL OR valor_pago >= 0)
        )
);
CREATE TABLE public.tabela_itens_venda (
    id_item BIGSERIAL PRIMARY KEY,
    id_venda BIGINT NOT NULL,
    id_produto BIGINT NOT NULL,
    quantidade NUMERIC(10,3) NOT NULL,
    preco_unitario NUMERIC(10,2) NOT NULL,
    desconto_item NUMERIC(10,2) DEFAULT 0.00,
    subtotal NUMERIC(10,2) NOT NULL,
    preco_custo_momento NUMERIC(10,2),
    cst_fiscal_momento VARCHAR(3),
    CONSTRAINT fk_venda_item FOREIGN KEY (id_venda) REFERENCES public.tabela_vendas(id_venda) ON DELETE CASCADE,
    CONSTRAINT fk_produto_item FOREIGN KEY (id_produto) REFERENCES public.tabela_produtos(id)
);

CREATE TABLE public.tabela_movimentacoes_estoque (
    id BIGSERIAL PRIMARY KEY,
    id_produto BIGINT NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    quantidade NUMERIC(10,3) NOT NULL,
    saldo_anterior NUMERIC(10,3),
    saldo_posterior NUMERIC(10,3),
    motivo VARCHAR(255),
    id_usuario BIGINT REFERENCES public.tabela_usuarios(id),
    id_venda_origem BIGINT REFERENCES public.tabela_vendas(id_venda),
    data_movimentacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);


--------------------------------------------------------------------------------
-- 3. ÍNDICES ADICIONAIS
--------------------------------------------------------------------------------

CREATE INDEX idx_fornecedor_cidade ON public.tabela_fornecedores (cidade);
CREATE INDEX idx_fornecedor_contato ON public.tabela_fornecedores (contato);
CREATE INDEX idx_fornecedor_razao ON public.tabela_fornecedores (razao_social);
CREATE INDEX idx_produto_cod_barra ON public.tabela_produtos (codigo_barra);
CREATE INDEX idx_movimentacao_produto ON public.tabela_movimentacoes_estoque (id_produto);

-- Índices para performance de vendas (PDV)
CREATE INDEX idx_vendas_data ON public.tabela_vendas (data_venda);
CREATE INDEX idx_itens_venda_id_venda ON public.tabela_itens_venda (id_venda);
CREATE INDEX idx_movimentacao_venda ON public.tabela_movimentacoes_estoque (id_venda_origem);

--------------------------------------------------------------------------------
-- 4. CONSTRAINTS DE INTEGRIDADE
--------------------------------------------------------------------------------

-- Garante que valores de venda não sejam negativos
ALTER TABLE public.tabela_vendas
ADD CONSTRAINT chk_total_valido CHECK (total_liquido >= 0);
