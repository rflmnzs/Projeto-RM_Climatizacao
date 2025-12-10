package interfaces;

import dao.ClienteDAO;
import entidades.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class JPanelClientes extends JPanel {

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    public JPanelClientes(JfrmMenuPrincipal frame) {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JLabel titulo = new JLabel("Gerenciar Clientes", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // tabelas
        String[] colunas = {"ID", "Nome", "CPF", "Contato", "Endereço"};
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

        // botoes
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

        
        btnListar.addActionListener(e -> listarClientes());
        btnCadastrar.addActionListener(e -> frame.carregarTela(new JPanelCadastrarCliente(frame)));
        btnDeletar.addActionListener(e -> deletarCliente());
        btnEditar.addActionListener(e -> editarCliente(frame));
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

    private void listarClientes() {
        modeloTabela.setRowCount(0);
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> clientes = dao.read();

        for (Cliente c : clientes) {
            modeloTabela.addRow(new Object[]{
                c.getId_cliente(), c.getNome(), c.getCpf(),
                c.getContato(), c.getEndereco()
            });
        }
    }

    private void deletarCliente() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            int id = (int) modeloTabela.getValueAt(linha, 0);
            ClienteDAO dao = new ClienteDAO();

            if (dao.delete(id)) {
                JOptionPane.showMessageDialog(this, "Cliente deletado com sucesso!");
                listarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao deletar cliente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!");
        }
    }

    private void editarCliente(JfrmMenuPrincipal frame) {
        int linha = tabela.getSelectedRow();

        if (linha >= 0) {
            int id = (int) modeloTabela.getValueAt(linha, 0);
            String nome = (String) modeloTabela.getValueAt(linha, 1);
            String cpf = (String) modeloTabela.getValueAt(linha, 2);
            String contato = (String) modeloTabela.getValueAt(linha, 3);
            String endereco = (String) modeloTabela.getValueAt(linha, 4);

            Cliente cliente = new Cliente(id, nome, cpf, "Telefone", contato, endereco);
            frame.carregarTela(new JPanelCadastrarCliente(frame, cliente));
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.");
        }
    }
}
