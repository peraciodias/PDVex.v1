package br.com.creativex.ui.caixas;

import br.com.creativex.domain.entity.usuario.Usuario;
import br.com.creativex.domain.entity.venda.ItemVenda;
import br.com.creativex.domain.entity.venda.Venda;
import br.com.creativex.domain.entity.produto.Produto;
import br.com.creativex.config.AppFactory;
import br.com.creativex.presentation.controller.CaixaController;
import br.com.creativex.presentation.controller.ProdutoController;
import br.com.creativex.ui.HomeScreen;
import br.com.creativex.ui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class CaixasForm extends JPanel {
//-> 25/04
    private JPanel painelItemAtual;
    private JLabel lblItemAtual;
//<-
    private JTextField txtCodigoBarras, txtQuantidade, txtTotalVenda;
    private JTextArea areaCupom;
    private JButton btnFinalizar, btnRemoverItem, btnVoltar;

    private Usuario usuario;
    private MainWindow mainWindow;

    private final CaixaController caixaController;
    private final ProdutoController produtoController;

    private Venda vendaAtual = new Venda();
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(java.util.Locale.of("pt", "BR"));

    public CaixasForm(Usuario usuario, MainWindow mainWindow) {
        this.usuario = usuario;
        this.mainWindow = mainWindow;

        this.caixaController = AppFactory.caixaController();
        this.produtoController = AppFactory.produtoController();

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        initComponents();
        configurarAtalhos();
    }

    private void initComponents() {
        // CAMPOS DE ENTRADA
        txtCodigoBarras = new JTextField();
        txtCodigoBarras.setFont(new Font("SansSerif", Font.BOLD, 18));

        txtQuantidade = new JTextField("1");
        txtQuantidade.setFont(new Font("SansSerif", Font.BOLD, 18));

        JPanel pnlTopo = new JPanel(new BorderLayout());
      //->
        painelItemAtual = new JPanel(new BorderLayout());
        painelItemAtual.setBackground(Color.BLACK);
        painelItemAtual.setPreferredSize(new Dimension(100, 60));

        lblItemAtual = new JLabel("AGUARDANDO PRODUTO...");
        lblItemAtual.setForeground(Color.GREEN);
        lblItemAtual.setFont(new Font("Monospaced", Font.BOLD, 26));
        lblItemAtual.setHorizontalAlignment(SwingConstants.CENTER);
        painelItemAtual.add(lblItemAtual, BorderLayout.CENTER);
      //<-

        JPanel entrada = new JPanel(new GridLayout(1, 4, 10, 10));
        entrada.add(new JLabel("CÓDIGO DE BARRAS / ID:"));
        entrada.add(txtCodigoBarras);
        entrada.add(new JLabel("QUANTIDADE:"));
        entrada.add(txtQuantidade);

        JLabel lblOperador = new JLabel("Operador: " + usuario.getNome());
        lblOperador.setFont(new Font("SansSerif", Font.BOLD, 14));

        pnlTopo.add(entrada, BorderLayout.CENTER);
        pnlTopo.add(lblOperador, BorderLayout.EAST);

        // ÁREA DE CUPOM
        areaCupom = new JTextArea();
        areaCupom.setEditable(false);
        areaCupom.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaCupom.setBackground(Color.WHITE);

        JScrollPane scrollCupom = new JScrollPane(areaCupom);

        // PAINEL DE AÇÕES
        JPanel pnlAcoes = new JPanel();
        pnlAcoes.setLayout(new BoxLayout(pnlAcoes, BoxLayout.Y_AXIS));
        pnlAcoes.setPreferredSize(new Dimension(250, 0));

        txtTotalVenda = new JTextField("R$ 0,00");
        txtTotalVenda.setEditable(false);
        txtTotalVenda.setBackground(Color.BLACK);
        txtTotalVenda.setForeground(Color.GREEN);
        txtTotalVenda.setFont(new Font("Monospaced", Font.BOLD, 35));
        txtTotalVenda.setHorizontalAlignment(JTextField.CENTER);

        btnFinalizar = new JButton("FINALIZAR (F12)");
        btnFinalizar.setBackground(new Color(0, 153, 51));
        btnFinalizar.setForeground(Color.WHITE);

        btnRemoverItem = new JButton("Remover Item (DEL)");
        btnVoltar = new JButton("Voltar");

        pnlAcoes.add(new JLabel("TOTAL DA VENDA:"));
        pnlAcoes.add(txtTotalVenda);
        pnlAcoes.add(Box.createRigidArea(new Dimension(0, 20)));
        pnlAcoes.add(btnFinalizar);
        pnlAcoes.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlAcoes.add(btnRemoverItem);
        pnlAcoes.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlAcoes.add(btnVoltar);

       // add(pnlTopo, BorderLayout.NORTH);
       //->
        JPanel topoCompleto = new JPanel();
        topoCompleto.setLayout(new BoxLayout(topoCompleto, BoxLayout.Y_AXIS));

        topoCompleto.add(pnlTopo);
        topoCompleto.add(Box.createVerticalStrut(5));
        topoCompleto.add(painelItemAtual);

        add(topoCompleto, BorderLayout.NORTH);
       //<-
        add(scrollCupom, BorderLayout.CENTER);
        add(pnlAcoes, BorderLayout.EAST);

        configurarEventos();
    }

    private void configurarEventos() {
        txtCodigoBarras.addActionListener(e -> adicionarProdutoPeloCodigo());
        btnFinalizar.addActionListener(e -> finalizarVenda());
        btnRemoverItem.addActionListener(e -> removerItemPorSeqDialog());
        btnVoltar.addActionListener(e -> voltarParaHome());
    }

    private void configurarAtalhos() {
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "finalizar");

        this.getActionMap().put("finalizar", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                finalizarVenda();
            }
        });

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "remover");

        this.getActionMap().put("remover", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                removerItemPorSeqDialog();
            }
        });
    }

    private void voltarParaHome() {
        if (mainWindow != null) {
            mainWindow.abrirModulo(new HomeScreen());
        }
    }


    private void adicionarProdutoPeloCodigo() {
        String filtro = txtCodigoBarras.getText().trim();
        BigDecimal qtd;

        try {
            qtd = new BigDecimal(txtQuantidade.getText().replace(",", "."));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida!");
            return;
        }

        if (filtro.isEmpty()) return;

        try {
            // O objeto 'p' nasce aqui dentro deste bloco 'try'
            Produto p = produtoController.buscarPorCodigoBarra(filtro);

            if (p != null) {
                if (p.getQuantidadeEstoque().compareTo(qtd) < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Estoque insuficiente!\nDisponível: " + p.getQuantidadeEstoque());
                    return;
                }

                // --- LÓGICA DE TRATAMENTO DE TEXTO (AQUI NÃO FICA VERMELHO) ---
                String descFinal = p.getDescricao().replaceAll("_x[0-9A-Fa-f]{4}_", "").trim().toUpperCase();
                String marcaFinal = (p.getMarca() != null) ? p.getMarca().trim().toUpperCase() : "";

                // Usamos os textos limpos para criar o item da venda
                ItemVenda item = new ItemVenda(
                        p.getId(),
                        descFinal, // Agora usamos o nome limpo
                        qtd,
                        p.getPrecoVenda()
                );

                item.setPrecoCustoMomento(p.getPrecoCusto());
                item.setCstFiscalMomento(p.getCstIcms());

                vendaAtual.adicionarItem(item);

                // Atualiza o visor usando o seu método (ele também precisa estar dentro do try)
                atualizarItemAtual(p);

                atualizarCupom();
                atualizarExibicaoTotal();
                limparCamposInput();
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
    //->
private void atualizarItemAtual(Produto p) {
    Toolkit.getDefaultToolkit().beep();
    // Use toUpperCase() e remova o "R$" extra se o nf.format já trouxer
    String texto = p.getDescricao().toUpperCase() + "  |  QTD: " + txtQuantidade.getText() +
            "  |  " + nf.format(p.getPrecoVenda());
    lblItemAtual.setText(texto);

    lblItemAtual.setForeground(Color.YELLOW);
    new javax.swing.Timer(150, e -> {
        lblItemAtual.setForeground(Color.GREEN);
    }).start();
}
//<-
    private void finalizarVenda() {
        if (vendaAtual.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carrinho vazio!");
            return;
        }

        vendaAtual.recalcularTotais();

        Window window = SwingUtilities.getWindowAncestor(this);
        Frame parent = (window instanceof Frame) ? (Frame) window : null;
        FinalizarVendaDialog dialog = new FinalizarVendaDialog(parent, vendaAtual.getTotalLiquido());
        dialog.setVisible(true);

        if (!dialog.isVendaConfirmada()) {
            return;
        }

        try {
            if (dialog.getMetodoSelecionado() == null) {
                JOptionPane.showMessageDialog(this, "Selecione a forma de pagamento.");
                return;
            }

            BigDecimal valorPago = dialog.getValorPago();
            if (valorPago == null) {
                valorPago = BigDecimal.ZERO;
            }

            vendaAtual.setIdUsuario(usuario.getId());
            vendaAtual.setMetodoPagamento(dialog.getMetodoSelecionado());
            vendaAtual.setValorPago(valorPago);
            vendaAtual.setTroco(dialog.getTroco());

            if (valorPago.compareTo(vendaAtual.getTotalLiquido()) < 0) {
                JOptionPane.showMessageDialog(this,
                        "Valor pago é menor que o total da venda!");
                return;
            }

            caixaController.finalizarVenda(vendaAtual);

            JOptionPane.showMessageDialog(this, "Venda concluída!");
            limparVenda();

        } catch (Exception ex) {
            String mensagem = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
            JOptionPane.showMessageDialog(this, "Erro: " + mensagem);
        }
    }

    private void removerItemPorSeqDialog() {
        if (vendaAtual.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum item no cupom!");
            return;
        }

        String input = JOptionPane.showInputDialog(this,
                "Digite o número SEQ do item a remover:");

        if (input == null || input.trim().isEmpty()) return;

        try {
            int seq = Integer.parseInt(input.trim());
            removerItemPorSeq(seq);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número inválido!");
        }
    }

    private void removerItemPorSeq(int seq) {
        if (seq <= 0 || seq > vendaAtual.getItens().size()) {
            JOptionPane.showMessageDialog(this, "SEQ inexistente!");
            return;
        }

        vendaAtual.getItens().remove(seq - 1);

        vendaAtual.recalcularTotais();
        atualizarCupom();
        atualizarExibicaoTotal();
    }

    private void atualizarCupom() {
    StringBuilder sb = new StringBuilder();
    
    // Cabeçalho Dinâmico (Simulando que você já carregou o objeto 'estabelecimento')
    sb.append("              ").append("NOME DA SUA LOJA").append("\n");
    sb.append("      CNPJ: 00.000.000/0001-00  IE: 123456789\n");
    sb.append("-------------------------------------------------------------------\n");
    sb.append(String.format("%-4s %-8s %-20s %-6s %-12s\n", "SEQ", "CÓD", "DESC", "QTD", "VALOR"));
    sb.append("-------------------------------------------------------------------\n");

    int seq = 1;
    for (ItemVenda item : vendaAtual.getItens()) {
        sb.append(String.format("%03d  %-8d %-20.20s %-6s %-12s\n", 
            seq++, item.getIdProduto(), item.getNomeProduto(), item.getQuantidade(), nf.format(item.getSubtotal())));
    }

    // Rodapé Fiscal
    BigDecimal tributos = vendaAtual.getTotalLiquido().multiply(new BigDecimal("0.1345"));
    
    sb.append("-------------------------------------------------------------------\n");
    sb.append(String.format("TOTAL LIQUIDO: %s\n", nf.format(vendaAtual.getTotalLiquido())));
    sb.append(String.format("VALOR PAGO:    %s\n", nf.format(vendaAtual.getValorPago() != null ? vendaAtual.getValorPago() : BigDecimal.ZERO)));
    sb.append(String.format("TROCO:         %s\n", nf.format(vendaAtual.getTroco() != null ? vendaAtual.getTroco() : BigDecimal.ZERO)));
    sb.append("-------------------------------------------------------------------\n");
    sb.append("Trib. aprox.: ").append(nf.format(tributos)).append(" (Lei Fed. 12.741/12)\n");
    sb.append("       OBRIGADO PELA PREFERENCIA!       \n");

    areaCupom.setText(sb.toString());
}
    private void atualizarExibicaoTotal() {
        vendaAtual.recalcularTotais();
        txtTotalVenda.setText(nf.format(vendaAtual.getTotalLiquido()));
    }

    private void limparCamposInput() {
        txtCodigoBarras.setText("");
        txtQuantidade.setText("1");
        txtCodigoBarras.requestFocus();
    }

    private void limparVenda() {
        vendaAtual = new Venda();
        areaCupom.setText("");
        txtTotalVenda.setText("R$ 0,00");
        limparCamposInput();
        //->
        lblItemAtual.setText("AGUARDANDO PRODUTO...");
        //<-
    }
}
