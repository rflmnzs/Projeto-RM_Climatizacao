package interfaces;

import dao.ServicoDAO;
import entidades.Servico;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class JPanelCadastrarServicoConcluido extends JPanel {

    private JFormattedTextField txtDataSolicitacao, txtDataConclusao;
    private JTextField txtDescricao;
    private JTextField txtClienteId;
    private JFormattedTextField txtValorTotal;

    private Servico servicoEditando;

    private final Locale localeBR = new Locale("pt", "BR");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(localeBR);

    public JPanelCadastrarServicoConcluido(JfrmMenuPrincipal frame) {
        this(frame, null);
    }

    public JPanelCadastrarServicoConcluido(JfrmMenuPrincipal frame, Servico servico) {
        this.servicoEditando = servico;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel(
                servico == null ? "Cadastrar Serviço Concluído" : "Editar Serviço Concluído",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        titulo.setForeground(new Color(40, 40, 40));
        add(titulo, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(600, 400));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));
        card.setLayout(new GridLayout(5, 2, 15, 20));

        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 15);

        card.add(estilizarLabel("Data Solicitação:", fontLabel));
        txtDataSolicitacao = criarCampoData(servico != null ? servico.getData_solicitacao() : "");
        card.add(txtDataSolicitacao);

        card.add(estilizarLabel("Descrição:", fontLabel));
        txtDescricao = estilizarTextField(servico != null ? servico.getDescricao() : "", fontField);
        card.add(txtDescricao);

        card.add(estilizarLabel("Data Conclusão:", fontLabel));
        txtDataConclusao = criarCampoData(servico != null ? servico.getData_conclusao() : "");
        card.add(txtDataConclusao);

        card.add(estilizarLabel("Valor Total:", fontLabel));
        txtValorTotal = criarCampoMoeda(servico != null ? servico.getValor_total() : null);
        card.add(txtValorTotal);

        card.add(estilizarLabel("ID Cliente:", fontLabel));
        txtClienteId = estilizarTextField(servico != null ? String.valueOf(servico.getCliente_id()) : "", fontField);
        card.add(txtClienteId);

        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setBackground(new Color(245, 247, 250));
        centerWrap.add(card);
        add(centerWrap, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        botoes.setBackground(new Color(245, 247, 250));

        JButton btnSalvar = criarBotaoPrimario("Salvar");
        JButton btnCancelar = criarBotaoSecundario("Cancelar");

        botoes.add(btnSalvar);
        botoes.add(btnCancelar);

        add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvarServico(frame));
        btnCancelar.addActionListener(e -> frame.carregarTela(new JPanelServicosConcluidos(frame)));
    }

    private JLabel estilizarLabel(String texto, Font fonte) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fonte);
        lbl.setForeground(new Color(70, 70, 70));
        return lbl;
    }

    private JTextField estilizarTextField(String texto, Font fonte) {
        JTextField campo = new JTextField(texto);
        campo.setFont(fonte);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    private JFormattedTextField criarCampoData(String valor) {
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.setPlaceholderCharacter('_');

            JFormattedTextField campo = new JFormattedTextField(mf);
            campo.setText(valor);
            campo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    new EmptyBorder(8, 10, 8, 10)
            ));
            return campo;

        } catch (Exception e) {
            return new JFormattedTextField();
        }
    }

    private JFormattedTextField criarCampoMoeda(Double valorInicial) {
        JFormattedTextField campo = new JFormattedTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        if (valorInicial != null) {
            campo.setText(currencyFormat.format(valorInicial));
        }

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    String texto = campo.getText()
                            .replace("R$", "")
                            .replace(".", "")
                            .replace(",", ".")
                            .trim();

                    if (!texto.isEmpty()) {
                        double valor = Double.parseDouble(texto);
                        campo.setText(currencyFormat.format(valor));
                    }

                } catch (Exception ignored) {
                }
            }
        });

        return campo;
    }

    private JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 120, 246));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(42, 105, 230));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(52, 120, 246));
            }
        });

        return btn;
    }

    private JButton criarBotaoSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setForeground(new Color(90, 90, 90));
        btn.setBackground(new Color(235, 235, 235));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(220, 220, 220));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(235, 235, 235));
            }
        });

        return btn;
    }

    private String converterParaSQL(String dataBR) {
        try {
            SimpleDateFormat br = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sql = new SimpleDateFormat("yyyy-MM-dd");
            return sql.format(br.parse(dataBR));

        } catch (Exception e) {
            return null;
        }
    }

    private void salvarServico(JfrmMenuPrincipal frame) {

        String dataSolic = txtDataSolicitacao.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String dataConc = txtDataConclusao.getText().trim();
        String valorTxt = txtValorTotal.getText().trim();
        String clienteIdTxt = txtClienteId.getText().trim();

        if (dataSolic.contains("_") || valorTxt.isEmpty() || descricao.isEmpty() || clienteIdTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos corretamente!");
            return;
        }

        String dataSolicSQL = converterParaSQL(dataSolic);
        String dataConcSQL = converterParaSQL(dataConc);

        if (dataSolicSQL == null || dataConcSQL == null) {
            JOptionPane.showMessageDialog(this, "Data inválida!");
            return;
        }

        int clienteId;
        try {
            clienteId = Integer.parseInt(clienteIdTxt);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID do cliente inválido.");
            return;
        }

        double valor;
        try {
            Number number = currencyFormat.parse(valorTxt);
            valor = number.doubleValue();

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido.");
            return;
        }

        ServicoDAO dao = new ServicoDAO();
        boolean sucesso;

        if (servicoEditando == null) {
            Servico novo = new Servico(0, clienteId, dataSolicSQL, descricao, dataConcSQL, valor);
            dao.create(novo);
            sucesso = true;

        } else {
            servicoEditando.setCliente_id(clienteId);
            servicoEditando.setData_solicitacao(dataSolicSQL);
            servicoEditando.setDescricao(descricao);
            servicoEditando.setData_conclusao(dataConcSQL);
            servicoEditando.setValor_total(valor);
            sucesso = dao.update(servicoEditando);
        }

        JOptionPane.showMessageDialog(
                this,
                sucesso ? "Serviço salvo com sucesso!" : "Erro ao salvar serviço."
        );

        frame.carregarTela(new JPanelServicosConcluidos(frame));
    }
}
