CREATE TABLE IF NOT EXISTS public.config_tributaria (
    cst_icms VARCHAR(10) PRIMARY KEY,
    aliquota_estimada DECIMAL(5,2) NOT NULL
);

-- Inserir dados padrão para CSTs comuns
INSERT INTO public.config_tributaria (cst_icms, aliquota_estimada) VALUES
('00', 18.00),
('10', 18.00),
('20', 18.00),
('30', 0.00),
('40', 0.00),
('41', 0.00),
('50', 0.00),
('51', 18.00),
('60', 18.00),
('70', 18.00),
('90', 18.00)
ON CONFLICT (cst_icms) DO NOTHING;

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
