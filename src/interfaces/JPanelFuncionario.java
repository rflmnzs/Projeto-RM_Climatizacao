package interfaces;

import dao.FuncionarioDAO;
import entidades.Funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class JPanelFuncionario extends JPanel {

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    public JPanelFuncionario(JfrmMenuPrincipal frame) {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        
        JLabel titulo = new JLabel("Gerenciar Funcionários", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        
        String[] colunas = {"ID", "CPF", "Nome", "Área de Atuação"};
        modeloTabela = new DefaultTableModel(colunas, 0);

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

        Color corPadrao = new Color(100, 149, 237);

        JButton btnCadastrar = criarBotao("Cadastrar", corPadrao);
        JButton btnEditar    = criarBotao("Editar", corPadrao);
        JButton btnDeletar   = criarBotao("Deletar", new Color(200, 50, 50));
        JButton btnListar    = criarBotao("Listar", corPadrao);
        JButton btnVoltar    = criarBotao("Voltar", new Color(120, 120, 120));

        botoes.add(btnCadastrar);
        botoes.add(btnEditar);
        botoes.add(btnDeletar);
        botoes.add(btnListar);
        botoes.add(btnVoltar);

        add(botoes, BorderLayout.SOUTH);

       
        btnListar.addActionListener(e -> listarFuncionarios());
        btnCadastrar.addActionListener(e -> frame.carregarTela(new JPanelCadastrarFuncionario(frame)));
        btnDeletar.addActionListener(e -> deletarFuncionario());
        btnEditar.addActionListener(e -> editarFuncionario(frame));
        btnVoltar.addActionListener(e -> frame.carregarTela(new JPanelMenu(frame)));
    }

    
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botao.setPreferredSize(new Dimension(110, 38));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    
    private void listarFuncionarios() {
        modeloTabela.setRowCount(0);
        FuncionarioDAO dao = new FuncionarioDAO();
        List<Funcionario> funcionarios = dao.read();

        for (Funcionario f : funcionarios) {
            modeloTabela.addRow(new Object[]{
                f.getIdFuncionario(),
                f.getCpf(),
                f.getNome(),
                f.getAreaAtuacao()
            });
        }
    }

    
    private void deletarFuncionario() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário!");
            return;
        }

        int id = (int) modeloTabela.getValueAt(linha, 0);
        FuncionarioDAO dao = new FuncionarioDAO();

        if (dao.delete(id)) {
            JOptionPane.showMessageDialog(this, "Funcionário deletado com sucesso!");
            listarFuncionarios();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao deletar funcionário.");
        }
    }

    
    private void editarFuncionario(JfrmMenuPrincipal frame) {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário para editar.");
            return;
        }

        int id = (int) modeloTabela.getValueAt(linha, 0);
        String cpf = (String) modeloTabela.getValueAt(linha, 1);
        String nome = (String) modeloTabela.getValueAt(linha, 2);
        String area = (String) modeloTabela.getValueAt(linha, 3);

        Funcionario funcionario = new Funcionario(id, cpf, nome, area);
        frame.carregarTela(new JPanelCadastrarFuncionario(frame, funcionario));
    }
}
