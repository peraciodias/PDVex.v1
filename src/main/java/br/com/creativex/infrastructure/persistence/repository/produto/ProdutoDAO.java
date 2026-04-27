// Peracio Dias
//creativex sistemas

package br.com.creativex.infrastructure.persistence.repository.produto;

import br.com.creativex.domain.entity.produto.Produto;
import br.com.creativex.domain.transaction.Transaction;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO {
    private final Transaction tx;

    public ProdutoDAO(Transaction tx) {
        this.tx = tx;
    }

    // ...existing code...

    // ==============================
    // BUSCAR ÚLTIMO
    // ==============================
    public Produto buscarUltimo() {
        return tx.execute(conn -> {
            String sql = "SELECT * FROM tabela_produtos ORDER BY id DESC LIMIT 1";

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapear(rs);
                }
            }
            return null;
        });
    }

    // ==============================
    // INSERIR
    // ==============================
    // ==============================

    public void inserir(Produto p) {
        tx.execute(conn -> {
            String sql = """
                INSERT INTO tabela_produtos (
                    codigo_barra, descricao, marca, atributos, unidade_medida, categoria,
                    cod_grupo, grupo, tipo_balanca, quantidade_estoque, preco_custo, preco_venda,
                    ncm, cest, cfop_padrao, unidade_tributavel, cean_tributavel,
                    cst_icms, aliquota_icms, cst_pis, ppis, cst_cofins, pcofins,
                    loja
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, p.getCodigoBarra());
                stmt.setString(2, p.getDescricao());
                stmt.setString(3, p.getMarca());
                stmt.setString(4, p.getAtributos());
                stmt.setString(5, p.getUnidadeMedida());
                stmt.setString(6, p.getCategoria());
                stmt.setInt(7, p.getCodGrupo());
                stmt.setString(8, p.getGrupo());
                stmt.setString(9, p.getTipoBalanca() == 0 ? null : String.valueOf(p.getTipoBalanca()));
                stmt.setBigDecimal(10, p.getQuantidadeEstoque());
                stmt.setBigDecimal(11, p.getPrecoCusto());
                stmt.setBigDecimal(12, p.getPrecoVenda());
                stmt.setString(13, p.getNcm());
                stmt.setString(14, p.getCest());
                stmt.setString(15, p.getCfopPadrao());
                stmt.setString(16, p.getUnidadeTributavel());
                stmt.setString(17, p.getCeanTributavel());
                stmt.setString(18, p.getCstIcms());
                stmt.setBigDecimal(19, p.getAliquotaIcms());
                stmt.setString(20, p.getCstPis());
                stmt.setBigDecimal(21, p.getPpis());
                stmt.setString(22, p.getCstCofins());
                stmt.setBigDecimal(23, p.getPcofins());
                stmt.setString(24, p.getLoja());
                stmt.executeUpdate();
            }

            return null;
        });
    }

    // ==============================
    // EXCLUIR
    // ==============================
    public void excluir(long id) {
        tx.execute(conn -> {
            String sql = "DELETE FROM tabela_produtos WHERE id = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }

            return null;
        });
    }
    // ==============================
    // ATUALIZAR
    // ==============================
    public void atualizar(Produto p) {
        tx.execute(conn -> {
            String sql = """
                UPDATE tabela_produtos SET
                    codigo_barra = ?, descricao = ?, marca = ?, atributos = ?, unidade_medida = ?,
                    categoria = ?, cod_grupo = ?, grupo = ?, tipo_balanca = ?,
                    quantidade_estoque = ?, preco_custo = ?, preco_venda = ?,
                    ncm = ?, cest = ?, cfop_padrao = ?, unidade_tributavel = ?, cean_tributavel = ?,
                    cst_icms = ?, aliquota_icms = ?, cst_pis = ?, ppis = ?,
                    cst_cofins = ?, pcofins = ?, loja = ?
                WHERE id = ?
                """;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, p.getCodigoBarra());
                stmt.setString(2, p.getDescricao());
                stmt.setString(3, p.getMarca());
                stmt.setString(4, p.getAtributos());
                stmt.setString(5, p.getUnidadeMedida());
                stmt.setString(6, p.getCategoria());
                stmt.setInt(7, p.getCodGrupo());
                stmt.setString(8, p.getGrupo());
                stmt.setString(9, p.getTipoBalanca() == 0 ? null : String.valueOf(p.getTipoBalanca()));
                stmt.setBigDecimal(10, p.getQuantidadeEstoque());
                stmt.setBigDecimal(11, p.getPrecoCusto());
                stmt.setBigDecimal(12, p.getPrecoVenda());
                stmt.setString(13, p.getNcm());
                stmt.setString(14, p.getCest());
                stmt.setString(15, p.getCfopPadrao());
                stmt.setString(16, p.getUnidadeTributavel());
                stmt.setString(17, p.getCeanTributavel());
                stmt.setString(18, p.getCstIcms());
                stmt.setBigDecimal(19, p.getAliquotaIcms());
                stmt.setString(20, p.getCstPis());
                stmt.setBigDecimal(21, p.getPpis());
                stmt.setString(22, p.getCstCofins());
                stmt.setBigDecimal(23, p.getPcofins());
                stmt.setString(24, p.getLoja());
                stmt.setLong(25, p.getId());
                stmt.executeUpdate();
            }

            return null;
        });
    }


    // ==============================   
    // REGISTRAR VENDA (SEM throws)
    // ==============================
    public void registrarVenda(long idProduto, BigDecimal qtd, long idUsuario) {
        tx.execute(conn -> {
            String sqlUpdate = "UPDATE tabela_produtos SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?";
            String sqlInsert = "INSERT INTO TABELA_MOVIMENTACOES_ESTOQUE (id_produto, tipo, quantidade, motivo, id_usuario) VALUES (?, 'SAIDA', ?, 'Venda PDV', ?)";

            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                stmtUpdate.setBigDecimal(1, qtd);
                stmtUpdate.setLong(2, idProduto);
                stmtUpdate.executeUpdate();
            }

            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setLong(1, idProduto);
                stmtInsert.setBigDecimal(2, qtd);
                stmtInsert.setLong(3, idUsuario);
                stmtInsert.executeUpdate();
            }

            return null;
        });
    }

    // ==============================
    // BUSCAS E LISTAGENS
    // ==============================
    public Produto buscarPorId(long id) {
        return tx.execute(conn -> {
            String sql = "SELECT * FROM tabela_produtos WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return mapear(rs);
                }
            }
            return null;
        });
    }

    public Produto buscarPorNome(String nome) {
        return tx.execute(conn -> {
            String sql = "SELECT * FROM tabela_produtos WHERE descricao ILIKE ? ORDER BY id LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + nome + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return mapear(rs);
                }
            }
            return null;
        });
    }

    public Produto buscarPorCodigoBarra(String codigo) {
        return tx.execute(conn -> {
            String sql = "SELECT * FROM vw_pdv_bipagem WHERE codigo_barra = ? LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, codigo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return mapearFromView(rs);
                }
            }
            return null;
        });
    }

    public List<Produto> listarPorIdLimite(long idInicial, int limite) {
        return tx.execute(conn -> {
            String sql = "SELECT * FROM tabela_produtos WHERE id >= ? ORDER BY id LIMIT ?";
            List<Produto> lista = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, idInicial);
                ps.setInt(2, limite);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) lista.add(mapear(rs));
                }
            }
            return lista;
        });
    }

    // Compatibilidade: busca por código de barras ou por id/descrição
    public Produto buscarPorCodigoOuId(String filtro) {
        if (filtro == null || filtro.isBlank()) return null;

        // tenta por código de barras primeiro (muitos códigos são numéricos)
        Produto p = buscarPorCodigoBarra(filtro);
        if (p != null) return p;

        // se for numérico, tenta por id
        if (filtro.matches("\\d+")) {
            long id = Long.parseLong(filtro);
            Produto byId = buscarPorId(id);
            if (byId != null) return byId;
        }

        // por fim tenta por nome/descrição
        return buscarPorNome(filtro);
    }

    // ==============================
    // MAPEAR
    // ==============================
    private Produto mapear(ResultSet rs) throws SQLException {

        Produto p = new Produto();
        p.setId(rs.getLong("id"));
        p.setCodigoBarra(rs.getString("codigo_barra"));
        p.setDescricao(rs.getString("descricao"));
        p.setMarca(rs.getString("marca"));
        p.setPrecoCusto(rs.getBigDecimal("preco_custo"));
        p.setPrecoVenda(rs.getBigDecimal("preco_venda"));
        p.setQuantidadeEstoque(rs.getBigDecimal("quantidade_estoque"));
        p.setCstIcms(rs.getString("cst_icms"));

        // Novos campos da View para cálculo de impostos
        p.setAliquotaAplicada(rs.getBigDecimal("aliquota_aplicada"));
        p.setValorImpostoItem(rs.getBigDecimal("valor_imposto_item"));

        return p;
    }

    // ==============================
    // MAPEAR DA VIEW (PARA CAIXAS)
    // ==============================
    private Produto mapearFromView(ResultSet rs) throws SQLException {

        Produto p = new Produto();
        p.setId(rs.getLong("id"));
        p.setCodigoBarra(rs.getString("codigo_barra"));
        p.setDescricao(rs.getString("descricao"));
        p.setMarca(rs.getString("marca"));
        p.setPrecoCusto(rs.getBigDecimal("preco_custo"));
        p.setPrecoVenda(rs.getBigDecimal("preco_venda"));
        p.setQuantidadeEstoque(rs.getBigDecimal("quantidade_estoque"));
        p.setCstIcms(rs.getString("cst_icms"));

        // Novos campos da View para cálculo de impostos
        p.setAliquotaAplicada(rs.getBigDecimal("aliquota_aplicada"));
        p.setValorImpostoItem(rs.getBigDecimal("valor_imposto_item"));

        return p;
    }
}
