package interfaces;

import dao.ClienteDAO;
import entidades.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

public class JPanelCadastrarCliente extends JPanel {

    private JTextField txtNome, txtEndereco, txtContato;
    private JFormattedTextField txtCpf;
    private Cliente clienteEditando;

    public JPanelCadastrarCliente(JfrmMenuPrincipal frame) {
        this(frame, null);
    }

    public JPanelCadastrarCliente(JfrmMenuPrincipal frame, Cliente cliente) {
        this.clienteEditando = cliente;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        // titulo 
        JLabel titulo = new JLabel(
                cliente == null ? "Cadastrar Cliente" : "Editar Cliente",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        titulo.setForeground(new Color(40, 40, 40));
        add(titulo, BorderLayout.NORTH);

        // tela 
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(600, 350));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));
        card.setLayout(new GridLayout(4, 2, 15, 20));

        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 15);

        // nome
        card.add(criarLabel("Nome:", fontLabel));
        txtNome = criarTextField(cliente != null ? cliente.getNome() : "", fontField);
        card.add(txtNome);

        // cpf
        card.add(criarLabel("CPF:", fontLabel));
        txtCpf = criarCampoCPF(cliente != null ? cliente.getCpf() : "");
        card.add(txtCpf);

        // Contato 
        card.add(criarLabel("Contato:", fontLabel));
        txtContato = criarCampoContato(cliente != null ? cliente.getContato() : "");
        card.add(txtContato);

        // Endereço
        card.add(criarLabel("Endereço:", fontLabel));
        txtEndereco = criarTextField(cliente != null ? cliente.getEndereco() : "", fontField);
        card.add(txtEndereco);

        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setBackground(new Color(245, 247, 250));
        centerWrap.add(card);
        add(centerWrap, BorderLayout.CENTER);

        // botoes
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        botoes.setBackground(new Color(245, 247, 250));

        JButton btnSalvar = criarBotaoPrimario("Salvar");
        JButton btnCancelar = criarBotaoSecundario("Cancelar");

        botoes.add(btnSalvar);
        botoes.add(btnCancelar);

        add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvarCliente(frame));
        btnCancelar.addActionListener(e -> frame.carregarTela(new JPanelClientes(frame)));
    }
    
    private JFormattedTextField criarCampoCPF(String valor) {
        try {
            MaskFormatter mf = new MaskFormatter("###.###.###-##");
            mf.setPlaceholderCharacter('_');
            mf.setAllowsInvalid(false);
            mf.setOverwriteMode(true);

            JFormattedTextField campo = new JFormattedTextField(mf);
            campo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    new EmptyBorder(8, 10, 8, 10)
            ));

            if (valor != null && !valor.isEmpty()) {
                String digits = valor.replaceAll("[^0-9]", "");
                if (digits.length() == 11) {
                    campo.setText(formatarCPFVisual(digits));
                } else {
                    campo.setText(valor);
                }
            }

            return campo;

        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private String formatarCPFVisual(String cpf) {
        return cpf.substring(0, 3) + "." +
               cpf.substring(3, 6) + "." +
               cpf.substring(6, 9) + "-" +
               cpf.substring(9, 11);
    }
    private JTextField criarCampoContato(String valorInicial) {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        ));

        if (valorInicial != null && !valorInicial.isEmpty()) {
            String digits = valorInicial.replaceAll("[^0-9]", "");
            campo.setText(formatarContatoVisual(digits));
        }

        campo.addKeyListener(new java.awt.event.KeyAdapter() {
           
            public void keyReleased(java.awt.event.KeyEvent e) {
                SwingUtilities.invokeLater(() -> {

                    String digits = campo.getText().replaceAll("[^0-9]", "");

                    if (digits.length() > 11)
                        digits = digits.substring(0, 11);

                    campo.setText(formatarContatoVisual(digits));
                });
            }
        });

        return campo;
    }

    private String formatarContatoVisual(String digits) {
        if (digits.isEmpty()) return "";

        if (digits.length() <= 2)
            return "(" + digits;

        if (digits.length() <= 6)
            return "(" + digits.substring(0, 2) + ")" + digits.substring(2);

        if (digits.length() <= 10)
            return "(" + digits.substring(0, 2) + ")" +
                    digits.substring(2, 6) + "-" +
                    digits.substring(6);

        return "(" + digits.substring(0, 2) + ")" +
                digits.substring(2, 7) + "-" +
                digits.substring(7);
    }

    private JLabel criarLabel(String texto, Font fonte) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fonte);
        lbl.setForeground(new Color(70, 70, 70));
        return lbl;
    }

    private JTextField criarTextField(String texto, Font fonte) {
        JTextField campo = new JTextField(texto);
        campo.setFont(fonte);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }


    private JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 120, 246));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(42, 105, 230)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(52, 120, 246)); }
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
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(220, 220, 220)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(235, 235, 235)); }
        });

        return btn;
    }
    private void salvarCliente(JfrmMenuPrincipal frame) {
        String nome = txtNome.getText().trim();

        try { txtCpf.commitEdit(); } catch (Exception ignored) {}

        String cpfDigits = txtCpf.getText().replaceAll("[^0-9]", "");
        String contatoDigits = txtContato.getText().replaceAll("[^0-9]", "");
        String endereco = txtEndereco.getText().trim();

        if (nome.isEmpty() || cpfDigits.length() != 11) {
            JOptionPane.showMessageDialog(this, "Preencha corretamente o Nome e o CPF (11 dígitos).");
            return;
        }

        if (contatoDigits.length() != 10 && contatoDigits.length() != 11) {
            JOptionPane.showMessageDialog(this, "O contato deve ter 10 ou 11 dígitos.");
            return;
        }

        ClienteDAO dao = new ClienteDAO();
        boolean sucesso;

        if (clienteEditando == null) {

            Cliente novo = new Cliente(
                    0,
                    nome,
                    cpfDigits,
                    "Telefone",
                    contatoDigits,
                    endereco
            );

            dao.create(novo);
            sucesso = true;

        } else {

            clienteEditando.setNome(nome);
            clienteEditando.setCpf(cpfDigits);
            clienteEditando.setContato(contatoDigits);
            clienteEditando.setEndereco(endereco);

            sucesso = dao.update(clienteEditando);
        }

        JOptionPane.showMessageDialog(this,
                sucesso ? "Cliente salvo com sucesso!" : "Erro ao salvar cliente.");

        frame.carregarTela(new JPanelClientes(frame));
    }
}
