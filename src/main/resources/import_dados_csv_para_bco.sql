--psql -U pera -d bco_dados_mercado -f import_dados_csv_para_bco.sql
-- executar na pasta onde estiver o import_dados_csv_para_bco.sql
--usuários

\copy tabela_usuarios (
    nome, login, senha, perfil, ativo
)
FROM '/tmp/dados_usuarios.csv'
WITH (
    FORMAT CSV,
    HEADER TRUE,
    DELIMITER ';',
    NULL '',
    ENCODING 'UTF8'
);
--produtos
\copy tabela_produtos (
    codigo_barra,
    descricao,
    marca,
    atributos,
    unidade_medida,
    categoria,
    cod_grupo,
    grupo,
    tipo_balanca,
    quantidade_estoque,
    preco_custo,
    preco_venda,
    ncm,
    cest,
    cfop_padrao,
    unidade_tributavel,
    cean_tributavel,
    cst_icms,
    aliquota_icms,
    cst_pis,
    ppis,
    cst_cofins,
    pcofins,
    data_cadastro,
    loja
)
FROM '/tmp/dados_produtos.csv'
WITH (
    FORMAT CSV,
    HEADER TRUE,
    DELIMITER ';',
    NULL '',
    ENCODING 'UTF8'
);

--clientes
\copy tabela_clientes (
    nome,
    cpf,
    rg,
    telefone,
    email,
    endereco,
    numero,
    bairro,
    cidade,
    uf,
    cep,
    limite_credito,
    data_cadastro
)
FROM '/tmp/dados_clientes.csv'
WITH (
    FORMAT CSV,
    HEADER TRUE,
    DELIMITER ';',
    NULL '',
    ENCODING 'UTF8'
);
--clientes_pj

\copy tabela_clientes_pj (
    razao_social,
    nome_fantasia,
    cnpj,
    ie,
    telefone,
    email,
    endereco,
    numero,
    complemento,
    bairro,
    cidade,
    uf,
    cep,
    limite_credito,
    data_cadastro
)
FROM '/tmp/dados_clientes_pj.csv'
WITH (
    FORMAT CSV,
    HEADER TRUE,
    DELIMITER ';',
    NULL '',
    ENCODING 'UTF8'
);

--fornecedores

\copy tabela_fornecedores (
    razao_social,
    nome_fantasia,
    cnpj,
    ie,
    contato,
    telefone,
    email,
    endereco,
    numero,
    complemento,
    bairro,
    cidade,
    uf,
    cep,
    limite_credito,
    data_cadastro
)
FROM '/tmp/dados_fornecedores.csv'
WITH (FORMAT CSV, HEADER TRUE, DELIMITER ';',  NULL '',  ENCODING 'UTF8' );
--atenção 
--Após rodar os COPY acima, execute isto
--para "entregar as chaves" de volta para o usuário 'pera'do sistema:
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO pera;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO pera;
