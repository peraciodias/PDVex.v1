--psql -U pera -d bco_dados_mercado -f exporta_dados_csv.sql
--executar na pasta onde se encontra -> exporta_dados_csv.sql
--usuários
COPY (
    SELECT nome, login, senha, perfil, ativo
    FROM tabela_usuarios
)
TO '/tmp/dados_usuarios.csv'
WITH (FORMAT CSV, HEADER TRUE, DELIMITER ';', NULL '', ENCODING 'UTF8');

--produtos
COPY (
    SELECT 
        codigo_barra,
        descricao,
        NULLIF(marca, ''),
        NULLIF(atributos, ''),
        unidade_medida,
        NULLIF(categoria, ''),
        cod_grupo,
        grupo,
        tipo_balanca,
        quantidade_estoque,
        preco_custo,
        preco_venda,
        NULLIF(ncm, ''),
        NULLIF(cest, ''),
        NULLIF(cfop_padrao, ''),
        NULLIF(unidade_tributavel, ''),
        NULLIF(cean_tributavel, ''),
        cst_icms,
        aliquota_icms,
        NULLIF(cst_pis, ''),
        ppis,
        NULLIF(cst_cofins, ''),
        pcofins,
        data_cadastro,
        NULLIF(loja, '')
    FROM tabela_produtos
)
TO '/tmp/dados_produtos.csv'
WITH (FORMAT CSV, HEADER TRUE, DELIMITER ';', NULL '', ENCODING 'UTF8');

--clientes
COPY (
    SELECT 
        nome,
        cpf,
        NULLIF(rg, ''),
        NULLIF(telefone, ''),
        NULLIF(email, ''),
        NULLIF(endereco, ''),
        NULLIF(numero, ''),
        NULLIF(bairro, ''),
        cidade,
        uf,
        NULLIF(cep, ''),
        limite_credito,
        data_cadastro
    FROM tabela_clientes
)
TO '/tmp/dados_clientes.csv'
WITH (FORMAT CSV, HEADER TRUE, DELIMITER ';', NULL '', ENCODING 'UTF8');

--clientes_pj
COPY (
    SELECT 
        razao_social,
        nome_fantasia,
        cnpj,
        NULLIF(ie, ''),
        NULLIF(telefone, ''),
        NULLIF(email, ''),
        NULLIF(endereco, ''),
        NULLIF(numero, ''),
        NULLIF(complemento, ''),
        NULLIF(bairro, ''),
        cidade,
        uf,
        NULLIF(cep, ''),
        limite_credito,
        data_cadastro
    FROM tabela_clientes_pj
)
TO '/tmp/dados_clientes_pj.csv'
WITH (FORMAT CSV, HEADER TRUE, DELIMITER ';', NULL '', ENCODING 'UTF8');

--fornecedores
COPY (
    SELECT 
        razao_social,
        nome_fantasia,
        cnpj,
        NULLIF(ie, ''),
        contato,
        NULLIF(telefone, ''),
        NULLIF(email, ''),
        NULLIF(endereco, ''),
        NULLIF(numero, ''),
        NULLIF(complemento, ''),
        NULLIF(bairro, ''),
        cidade,
        uf,
        NULLIF(cep, ''),
        limite_credito,
        data_cadastro
    FROM tabela_fornecedores
)
TO '/tmp/dados_fornecedores.csv'
WITH (FORMAT CSV, HEADER TRUE, DELIMITER ';', NULL '', ENCODING 'UTF8');

