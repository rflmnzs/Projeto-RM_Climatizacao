package interfaces;

import dao.MaquinaDAO;
import entidades.Maquina;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JPanelCadastrarMaquinas extends JPanel {

    private JTextField txtTipo, txtModelo;
    private Maquina maquinaEditando;

    public JPanelCadastrarMaquinas(JfrmMenuPrincipal frame) {
        this(frame, null);
    }

    public JPanelCadastrarMaquinas(JfrmMenuPrincipal frame, Maquina maquina) {
        this.maquinaEditando = maquina;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel(maquina == null ? "Cadastrar Máquina" : "Editar Máquina", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        titulo.setForeground(new Color(40, 40, 40));
        add(titulo, BorderLayout.NORTH);

      
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(600, 250));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));
        card.setLayout(new GridLayout(2, 2, 15, 20));

        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 15);

        
        card.add(estilizarLabel("Tipo Máquina:", fontLabel));
        txtTipo = estilizarTextField(maquina != null ? maquina.getTipo_maquina() : "", fontField);
        card.add(txtTipo);

        
        card.add(estilizarLabel("Modelo:", fontLabel));
        txtModelo = estilizarTextField(maquina != null ? maquina.getModelo() : "", fontField);
        card.add(txtModelo);

        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setBackground(new Color(245, 247, 250));
        centerWrap.add(card);

        add(centerWrap, BorderLayout.CENTER);

        
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        botoes.setBackground(new Color(245, 247, 250));

        JButton btnSalvar = criarBotaoPrimario(maquina == null ? "Cadastrar" : "Salvar");
        JButton btnCancelar = criarBotaoSecundario("Cancelar");

        botoes.add(btnSalvar);
        botoes.add(btnCancelar);

        add(botoes, BorderLayout.SOUTH);

        
        btnSalvar.addActionListener(e -> salvar(frame));
        btnCancelar.addActionListener(e -> frame.carregarTela(new JPanelMaquinas(frame)));
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
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(42, 105, 230));
            }
            @Override
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
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(220, 220, 220));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(235, 235, 235));
            }
        });

        return btn;
    }

    
    private void salvar(JfrmMenuPrincipal frame) {
        try {
            String tipo = txtTipo.getText().trim();
            String modelo = txtModelo.getText().trim();

            if (tipo.isEmpty() || modelo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            MaquinaDAO dao = new MaquinaDAO();

            if (maquinaEditando == null) {
                Maquina nova = new Maquina(tipo, modelo); // sem BTUs
                dao.create(nova);
                JOptionPane.showMessageDialog(this, "Máquina cadastrada!");
            } else {
                maquinaEditando.setTipo_maquina(tipo);
                maquinaEditando.setModelo(modelo);

                dao.update(maquinaEditando);
                JOptionPane.showMessageDialog(this, "Máquina atualizada!");
            }

            frame.carregarTela(new JPanelMaquinas(frame));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
