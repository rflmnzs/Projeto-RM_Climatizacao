package interfaces;

import dao.FuncionarioDAO;
import entidades.Funcionario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

public class JPanelCadastrarFuncionario extends JPanel {

    private JFormattedTextField txtCpf;
    private JTextField txtNome, txtAreaAtuacao;
    private Funcionario funcionarioEditando;

    public JPanelCadastrarFuncionario(JfrmMenuPrincipal frame) {
        this(frame, null);
    }

    public JPanelCadastrarFuncionario(JfrmMenuPrincipal frame, Funcionario funcionario) {
        this.funcionarioEditando = funcionario;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel(
                funcionario == null ? "Cadastrar Funcionário" : "Editar Funcionário",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        titulo.setForeground(new Color(40, 40, 40));
        add(titulo, BorderLayout.NORTH);

        
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(600, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));
        card.setLayout(new GridLayout(3, 2, 15, 20));

        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 15);

        
        card.add(estilizarLabel("CPF:", fontLabel));
        txtCpf = criarCampoCPF(funcionario != null ? funcionario.getCpf() : "");
        txtCpf.setFont(fontField);
        card.add(txtCpf);

        
        card.add(estilizarLabel("Nome:", fontLabel));
        txtNome = estilizarTextField(funcionario != null ? funcionario.getNome() : "", fontField);
        card.add(txtNome);

        
        card.add(estilizarLabel("Área de Atuação:", fontLabel));
        txtAreaAtuacao = estilizarTextField(funcionario != null ? funcionario.getAreaAtuacao() : "", fontField);
        card.add(txtAreaAtuacao);

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

        
        btnSalvar.addActionListener(e -> salvarFuncionario(frame));
        btnCancelar.addActionListener(e -> frame.carregarTela(new JPanelFuncionario(frame)));
    }

    
    private JFormattedTextField criarCampoCPF(String valorInicial) {
        try {
            MaskFormatter mf = new MaskFormatter("###.###.###-##");
            mf.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(mf);

            campo.setText(valorInicial);
            campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    new EmptyBorder(8, 10, 8, 10)
            ));

            return campo;

        } catch (ParseException e) {
            System.out.println("Erro na máscara de CPF: " + e.getMessage());
            return new JFormattedTextField();
        }
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

    private JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 120, 246));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

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


    
   
    private void salvarFuncionario(JfrmMenuPrincipal frame) {

        String cpf = txtCpf.getText().replace("_", "").trim();
        String nome = txtNome.getText().trim();
        String area = txtAreaAtuacao.getText().trim();

        if (cpf.length() < 14) {
            JOptionPane.showMessageDialog(this, "CPF incompleto!");
            return;
        }

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            return;
        }

        FuncionarioDAO dao = new FuncionarioDAO();
        boolean sucesso;

        if (funcionarioEditando == null) {
            Funcionario novo = new Funcionario(cpf, nome, area);
            dao.create(novo);
            sucesso = true;
        } else {
            funcionarioEditando.setCpf(cpf);
            funcionarioEditando.setNome(nome);
            funcionarioEditando.setAreaAtuacao(area);
            sucesso = dao.update(funcionarioEditando);
        }

        JOptionPane.showMessageDialog(
                this,
                sucesso ? "Operação realizada com sucesso!" : "Erro ao salvar funcionário."
        );

        frame.carregarTela(new JPanelFuncionario(frame));
    }
}
