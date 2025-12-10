package interfaces;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class JPanelMenu extends JPanel {

    public JPanelMenu(JfrmMenuPrincipal frame) {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("MENU PRINCIPAL", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(new Color(50, 50, 50));
        titulo.setBorder(new EmptyBorder(40, 0, 30, 0));
        add(titulo, BorderLayout.NORTH);

        // Painel central (2 linhas, 3 colunas → agora 5 botões)
        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(Color.WHITE);
        painelCentral.setLayout(new GridLayout(2, 3, 20, 20));
        painelCentral.setBorder(new EmptyBorder(20, 80, 40, 80));

        // Botões
        JButton btnClientes       = criarBotao("Gerenciar Clientes", new Color(64, 123, 255));
        JButton btnMaquinas       = criarBotao("Gerenciar Máquinas", new Color(64, 123, 255));
        JButton btnTabelaServicos = criarBotao("Tabela de Serviços", new Color(64, 123, 255));
        JButton btnNotasFiscais   = criarBotao("Gerenciar Notas",    new Color(64, 123, 255));
        JButton btnFuncionarios   = criarBotao("Gerenciar Funcionários", new Color(64, 123, 255));

        // Ações dos botões
        btnClientes.addActionListener(e -> frame.carregarTela(new JPanelClientes(frame)));
        btnMaquinas.addActionListener(e -> frame.carregarTela(new JPanelMaquinas(frame)));
        btnTabelaServicos.addActionListener(e -> frame.carregarTela(new JPanelTabelaServicos(frame)));
        btnNotasFiscais.addActionListener(e -> frame.carregarTela(new JPanelNotaFiscal(frame)));
        btnFuncionarios.addActionListener(e -> frame.carregarTela(new JPanelFuncionario(frame)));

        // Adicionando botões ao grid (sem Serviços Concluídos)
        painelCentral.add(btnClientes);
        painelCentral.add(btnMaquinas);
        painelCentral.add(btnTabelaServicos);
        painelCentral.add(btnNotasFiscais);
        painelCentral.add(btnFuncionarios);

        // Preenche o último espaço do grid (para manter o layout bonito)
        painelCentral.add(new JLabel("")); // espaço vazio

        add(painelCentral, BorderLayout.CENTER);

        // Painel sair
        JPanel painelSair = new JPanel();
        painelSair.setBackground(Color.WHITE);
        painelSair.setBorder(new EmptyBorder(10, 0, 30, 0));

        JButton btnSair = criarBotaoPequeno("Sair", new Color(220, 53, 69));
        btnSair.addActionListener(e -> System.exit(0));

        painelSair.add(btnSair);
        add(painelSair, BorderLayout.SOUTH);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 18));
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(new EmptyBorder(15, 20, 15, 20));
        botao.setOpaque(true);

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { botao.setBackground(cor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt)  { botao.setBackground(cor); }
        });

        return botao;
    }

    private JButton criarBotaoPequeno(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(new EmptyBorder(8, 16, 8, 16));
        botao.setOpaque(true);

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { botao.setBackground(cor.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt)  { botao.setBackground(cor); }
        });

        return botao;
    }
}
