
package interfaces;

import dao.ServicoDAO;
import entidades.Servico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class JPanelServicosConcluidos extends JPanel {

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public JPanelServicosConcluidos(JfrmMenuPrincipal frame) {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Gerenciar Serviços Concluídos", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "ID Cliente", "Data Solicitação", "Descrição", "Data Conclusão", "Valor Total"};

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tabela.setRowHeight(28);

        JTableHeader header = tabela.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(100, 149, 237));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        botoes.setBackground(Color.WHITE);

        JButton btnCadastrar = criarBotao("Cadastrar", new Color(100, 149, 237));
        JButton btnEditar    = criarBotao("Editar", new Color(100, 149, 237));
        JButton btnDeletar   = criarBotao("Deletar", new Color(200, 50, 50));
        JButton btnListar    = criarBotao("Listar", new Color(100, 149, 237));
        JButton btnVoltar    = criarBotao("Voltar", new Color(120, 120, 120));

        botoes.add(btnCadastrar);
        botoes.add(btnEditar);
        botoes.add(btnDeletar);
        botoes.add(btnListar);
        botoes.add(btnVoltar);

        add(botoes, BorderLayout.SOUTH);

        btnListar.addActionListener(e -> listarServicos());
        btnCadastrar.addActionListener(e -> frame.carregarTela(new JPanelCadastrarServicoConcluido(frame)));
        btnDeletar.addActionListener(e -> deletarServico());
        btnEditar.addActionListener(e -> editarServico(frame));
        btnVoltar.addActionListener(e -> frame.carregarTela(new JPanelMenu(frame)));
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(110, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void listarServicos() {
        modeloTabela.setRowCount(0);
        ServicoDAO dao = new ServicoDAO();
        List<Servico> lista = dao.read();
        for (Servico s : lista) {
            String dataSolic = formataData(s.getData_solicitacao());
            String dataConc = formataData(s.getData_conclusao());
            String valor = moeda.format(s.getValor_total());
            modeloTabela.addRow(new Object[]{
                    s.getId_servico(),
                    s.getCliente_id(),
                    dataSolic,
                    s.getDescricao(),
                    dataConc,
                    valor
            });
        }
    }

    private String formataData(String data) {
        if (data == null) return "";
        try {
            return LocalDate.parse(data).format(df);
        } catch (Exception ex) {
            return data;
        }
    }

    private void deletarServico() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço!");
            return;
        }

        int id = (int) modeloTabela.getValueAt(linha, 0);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja deletar o serviço ID = " + id + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        ServicoDAO dao = new ServicoDAO();

        if (dao.delete(id)) {
            JOptionPane.showMessageDialog(this, "Serviço deletado!");
            listarServicos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao deletar serviço.");
        }
    }

    private void editarServico(JfrmMenuPrincipal frame) {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço!");
            return;
        }

        int id = (int) modeloTabela.getValueAt(linha, 0);
        int clienteId = (int) modeloTabela.getValueAt(linha, 1);
        String dataSolic = modeloTabela.getValueAt(linha, 2).toString();
        String descricao = modeloTabela.getValueAt(linha, 3).toString();
        String dataConc = modeloTabela.getValueAt(linha, 4).toString();
        String valorStr = modeloTabela.getValueAt(linha, 5).toString();

        double valor;
        try {
            valor = moeda.parse(valorStr).doubleValue();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao converter valor!");
            return;
        }

        Servico servico = new Servico(
                id,
                clienteId,
                dataSolic,
                descricao,
                dataConc,
                valor
        );

        frame.carregarTela(new JPanelCadastrarServicoConcluido(frame, servico));
    }
}
