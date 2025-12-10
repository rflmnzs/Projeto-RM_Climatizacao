package interfaces;

import dao.NotaFiscalDAO;
import entidades.NotaFiscal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JPanelCadastrarNotaFiscal extends JPanel {

    private JTextField txtIdCliente;
    private JTextField txtNumNota;
    private JFormattedTextField txtDataEmissao;

    private NotaFiscal notaEditando;
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public JPanelCadastrarNotaFiscal(JfrmMenuPrincipal frame) {
        this(frame, null);
    }

    public JPanelCadastrarNotaFiscal(JfrmMenuPrincipal frame, NotaFiscal nota) {

        this.notaEditando = nota;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel(
                nota == null ? "Cadastrar Nota Fiscal" : "Editar Nota Fiscal",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        titulo.setForeground(new Color(40, 40, 40));
        add(titulo, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(600, 350));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));
        card.setLayout(new GridLayout(3, 2, 15, 20));

        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 15);

        
        card.add(estilizarLabel("ID do Cliente:", fontLabel));
        txtIdCliente = estilizarTextField(
                nota != null ? String.valueOf(nota.getIdCliente()) : "",
                fontField
        );
        card.add(txtIdCliente);

        
        card.add(estilizarLabel("Número da Nota:", fontLabel));
        txtNumNota = estilizarTextField(
                nota != null ? nota.getNumNotaFiscal() : "",
                fontField
        );
        card.add(txtNumNota);

        
        card.add(estilizarLabel("Data de Emissão (dd/mm/aaaa):", fontLabel));
        txtDataEmissao = criarCampoData(
                nota != null ? nota.getDataEmissao().format(df) : ""
        );
        card.add(txtDataEmissao);

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

        btnSalvar.addActionListener(e -> salvar(frame));
        btnCancelar.addActionListener(e -> frame.carregarTela(new JPanelNotaFiscal(frame)));
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

    private JFormattedTextField criarCampoData(String valorInicial) {
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(mf);
            campo.setText(valorInicial);
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

    private JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 120, 246));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }

    private JButton criarBotaoSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setForeground(new Color(90, 90, 90));
        btn.setBackground(new Color(235, 235, 235));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }

    

    private void salvar(JfrmMenuPrincipal frame) {

        
        String idCliTxt = txtIdCliente.getText().trim();

        if (idCliTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o ID do Cliente.");
            return;
        }

        int idCliente;

        try {
            idCliente = Integer.parseInt(idCliTxt);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID do Cliente deve ser número inteiro.");
            return;
        }

        String numNota = txtNumNota.getText().trim();
        String dataTxt = txtDataEmissao.getText().trim();

        if (numNota.isEmpty() || dataTxt.contains("_")) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!");
            return;
        }

        LocalDate dataEmissao;
        try {
            dataEmissao = LocalDate.parse(dataTxt, df);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data inválida!");
            return;
        }

        NotaFiscalDAO dao = new NotaFiscalDAO();
        boolean sucesso;

        if (notaEditando == null) {

            
            NotaFiscal nova = new NotaFiscal(
                    0,
                    1,        
                    idCliente,
                    numNota,
                    dataEmissao
            );

            sucesso = dao.create(nova);

        } else {

            notaEditando.setIdCliente(idCliente);
            notaEditando.setNumNotaFiscal(numNota);
            notaEditando.setDataEmissao(dataEmissao);

            sucesso = dao.update(notaEditando);
        }

        JOptionPane.showMessageDialog(
                this,
                sucesso ? "Nota Fiscal salva com sucesso!" : "Erro ao salvar!"
        );

        frame.carregarTela(new JPanelNotaFiscal(frame));
    }
}
