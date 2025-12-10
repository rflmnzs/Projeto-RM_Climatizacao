package interfaces;

import dao.Tabela_ServicoDAO;
import dao.FuncionarioDAO;
import dao.ClienteDAO;
import entidades.Tabela_Servico;
import entidades.Funcionario;
import entidades.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class JPanelTabelaServicos extends JPanel {

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public JPanelTabelaServicos(JfrmMenuPrincipal frame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Tabela de Serviços", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);


        String[] colunas = {"ID Tabela", "Serviço", "Funcionário", "Cliente", "Prazo", "Preço", "Descrição"};

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tabela.setRowHeight(28);

        JTableHeader header = tabela.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 15));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(scroll, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 15));
        botoes.setBackground(Color.WHITE);

        JButton btnListar = criarBotao("Listar", new Color(70, 130, 180));
        JButton btnCadastrar = criarBotao("Cadastrar", new Color(70, 130, 180));
        JButton btnEditar = criarBotao("Editar", new Color(70, 130, 180));
        JButton btnDeletar = criarBotao("Deletar", new Color(200, 50, 50));
        JButton btnVoltar = criarBotao("Voltar", Color.GRAY);

        botoes.add(btnListar);
        botoes.add(btnCadastrar);
        botoes.add(btnEditar);
        botoes.add(btnDeletar);
        botoes.add(btnVoltar);

        add(botoes, BorderLayout.SOUTH);

        btnListar.addActionListener(e -> listar());
        btnCadastrar.addActionListener(e -> frame.carregarTela(new JPanelCadastrarNovoServico(frame)));
        btnEditar.addActionListener(e -> editar(frame));
        btnDeletar.addActionListener(e -> deletar());
        btnVoltar.addActionListener(e -> frame.carregarTela(new JPanelMenu(frame)));
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(115, 38));
        return btn;
    }

    // LISTAR
    private void listar() {
        modeloTabela.setRowCount(0);

        Tabela_ServicoDAO dao = new Tabela_ServicoDAO();
        FuncionarioDAO daoF = new FuncionarioDAO();
        ClienteDAO daoC = new ClienteDAO();

        List<Tabela_Servico> lista = dao.read();

        for (Tabela_Servico t : lista) {

     
            String nomeFuncionario = "Não encontrado";
            Funcionario f = daoF.read().stream()
                    .filter(x -> x.getIdFuncionario() == t.getIdFuncionario())
                    .findFirst().orElse(null);

            if (f != null) nomeFuncionario = f.getNome();

           
            String nomeCliente = "Não encontrado";
            Cliente c = daoC.read().stream()
                    .filter(x -> x.getId_cliente() == t.getIdCliente())
                    .findFirst().orElse(null);

            if (c != null) nomeCliente = c.getNome();

            modeloTabela.addRow(new Object[]{
                    t.getIdTabelaServico(),
                    t.getServico(),
                    nomeFuncionario,
                    nomeCliente,
                    df.format(t.getPrazoServico()),
                    moeda.format(t.getPrecoServico()),
                    t.getDescricao()
            });
        }
    }

    // EDITAR
    private void editar(JfrmMenuPrincipal frame) {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço!");
            return;
        }

        int idTabela = (int) modeloTabela.getValueAt(linha, 0);
        String servico = modeloTabela.getValueAt(linha, 1).toString();
        String nomeFuncionario = modeloTabela.getValueAt(linha, 2).toString();
        String nomeCliente = modeloTabela.getValueAt(linha, 3).toString();
        String prazoStr = modeloTabela.getValueAt(linha, 4).toString();
        String precoStr = modeloTabela.getValueAt(linha, 5).toString();
        String descricao = modeloTabela.getValueAt(linha, 6).toString();

        FuncionarioDAO daoF = new FuncionarioDAO();
        ClienteDAO daoC = new ClienteDAO();

       
        int idFuncionario = daoF.read().stream()
                .filter(f -> f.getNome().equals(nomeFuncionario))
                .findFirst().get()
                .getIdFuncionario();

        // Encontrar ID do Cliente
        int idCliente = daoC.read().stream()
                .filter(c -> c.getNome().equals(nomeCliente))
                .findFirst().get()
                .getId_cliente();

        LocalDate prazo;
        try {
            prazo = LocalDate.parse(prazoStr, df);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Data inválida!");
            return;
        }

        double preco;
        try {
            preco = moeda.parse(precoStr).doubleValue();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Preço inválido!");
            return;
        }

        // Criar objeto atualizado
        Tabela_Servico t = new Tabela_Servico(
                idTabela,
                idFuncionario,
                idCliente,
                servico,
                prazo,
                preco,
                descricao
        );

        frame.carregarTela(new JPanelCadastrarNovoServico(frame, t));
    }

    // DELETAR
    private void deletar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um serviço!");
            return;
        }

        int idTabela = (int) modeloTabela.getValueAt(linha, 0);

        int confirmar = JOptionPane.showConfirmDialog(
                this,
                "Deseja deletar o serviço ID = " + idTabela + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION
        );

        if (confirmar != JOptionPane.YES_OPTION) return;

        Tabela_ServicoDAO dao = new Tabela_ServicoDAO();
        boolean ok = dao.delete(idTabela);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Serviço deletado!");
            listar();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao deletar serviço.");
        }
    }
}
