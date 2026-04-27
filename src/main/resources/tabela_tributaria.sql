CREATE OR REPLACE VIEW public.vw_pdv_bipagem AS
SELECT 
    p.id,
    p.codigo_barra,
    p.descricao,
    p.marca,
    p.preco_venda,
    p.quantidade_estoque,
    p.cst_icms,
    p.preco_custo,
    COALESCE(t.aliquota_estimada, 13.45) as aliquota_aplicada,
    ROUND((p.preco_venda * COALESCE(t.aliquota_estimada, 13.45) / 100), 2) as valor_imposto_item
FROM 
    public.tabela_produtos p
LEFT JOIN 
    public.config_tributaria t ON p.cst_icms = t.cst_icms;


--2. Criar a View vw_pdv_bipagem

--Esta View é o que o teu Java vai "enxergar".
--Ela junta o produto com o imposto e já entrega o valor calculado.

CREATE OR REPLACE VIEW public.vw_pdv_bipagem AS
SELECT 
    p.id,
    p.codigo_barra,
    p.descricao,
    p.marca,
    p.preco_venda,
    p.quantidade_estoque,
    p.cst_icms,
    p.preco_custo,
    COALESCE(t.aliquota_estimada, 13.45) as aliquota_aplicada,
    ROUND((p.preco_venda * COALESCE(t.aliquota_estimada, 13.45) / 100), 2) as valor_imposto_item
FROM 
    public.tabela_produtos p
LEFT JOIN 
    public.config_tributaria t ON p.cst_icms = t.cst_icms;
