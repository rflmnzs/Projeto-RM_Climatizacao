package interfaces;

import dao.NotaFiscalDAO;
import entidades.NotaFiscal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JPanelNotaFiscal extends JPanel {

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public JPanelNotaFiscal(JfrmMenuPrincipal frame) {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        
        JLabel titulo = new JLabel("Gerenciar Notas Fiscais", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        
        String[] colunas = {
                "ID Nota",
                "ID Cliente",
                "Número da Nota",
                "Data de Emissão"
        };

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
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
        JButton btnEditar = criarBotao("Editar", new Color(100, 149, 237));
        JButton btnDeletar = criarBotao("Deletar", new Color(200, 50, 50));
        JButton btnListar = criarBotao("Listar", new Color(100, 149, 237));
        JButton btnVoltar = criarBotao("Voltar", new Color(120, 120, 120));

        botoes.add(btnCadastrar);
        botoes.add(btnEditar);
        botoes.add(btnDeletar);
        botoes.add(btnListar);
        botoes.add(btnVoltar);

        add(botoes, BorderLayout.SOUTH);

        
        btnListar.addActionListener(e -> listar());
        btnCadastrar.addActionListener(e -> frame.carregarTela(new JPanelCadastrarNotaFiscal(frame)));
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
        btn.setPreferredSize(new Dimension(110, 38));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    
    private void listar() {
        modeloTabela.setRowCount(0);

        NotaFiscalDAO dao = new NotaFiscalDAO();
        List<NotaFiscal> lista = dao.read();

        for (NotaFiscal n : lista) {
            modeloTabela.addRow(new Object[]{
                    n.getIdNotaFiscal(),
                    n.getIdCliente(),
                    n.getNumNotaFiscal(),
                    n.getDataEmissao().format(df)
            });
        }
    }

    
    private void deletar() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma nota!");
            return;
        }

        int idNota = (int) modeloTabela.getValueAt(linha, 0);

        int confirmar = JOptionPane.showConfirmDialog(this,
                "Deseja deletar a Nota Fiscal " + idNota + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirmar != JOptionPane.YES_OPTION) return;

        NotaFiscalDAO dao = new NotaFiscalDAO();

        if (dao.delete(idNota)) {
            JOptionPane.showMessageDialog(this, "Nota deletada com sucesso!");
            listar();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao deletar nota.");
        }
    }

    
    private void editar(JfrmMenuPrincipal frame) {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma nota para editar.");
            return;
        }

        int idNota = (int) modeloTabela.getValueAt(linha, 0);
        int idCliente = (int) modeloTabela.getValueAt(linha, 1);
        String numNota = modeloTabela.getValueAt(linha, 2).toString();
        String dataEmissaoStr = modeloTabela.getValueAt(linha, 3).toString();

        NotaFiscal nota = new NotaFiscal(
                idNota,
                0, 
                idCliente,
                numNota,
                java.time.LocalDate.parse(dataEmissaoStr, df)
        );

        frame.carregarTela(new JPanelCadastrarNotaFiscal(frame, nota));
    }
}
