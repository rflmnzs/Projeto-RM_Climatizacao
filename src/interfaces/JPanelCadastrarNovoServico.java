package interfaces;

import dao.Tabela_ServicoDAO;
import dao.FuncionarioDAO;
import dao.ClienteDAO;
import entidades.Tabela_Servico;
import entidades.Funcionario;
import entidades.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class JPanelCadastrarNovoServico extends JPanel {

    private JTextField txtServico, txtPreco, txtDescricao;
    private JFormattedTextField txtPrazo;
    private JComboBox<String> cbFuncionario;
    private JComboBox<String> cbCliente;

    private int[] funcionariosIds;
    private int[] clientesIds;

    private Tabela_Servico servicoEditando;

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public JPanelCadastrarNovoServico(JfrmMenuPrincipal frame) {
        this(frame, null);
    }

    public JPanelCadastrarNovoServico(JfrmMenuPrincipal frame, Tabela_Servico servico) {
        this.servicoEditando = servico;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel titulo = new JLabel(servico == null ? "Cadastrar Serviço" : "Editar Serviço", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI Semibold", Font.BOLD, 26));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        titulo.setForeground(new Color(40, 40, 40));
        add(titulo, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(600, 430));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225), 1),
                new EmptyBorder(30, 40, 30, 40)
        ));
        card.setLayout(new GridLayout(6, 2, 15, 20));

        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 15);

        // SERVIÇO
        card.add(label("Serviço:", fontLabel));
        txtServico = campo(servico != null ? servico.getServico() : "", fontField);
        card.add(txtServico);

        // FUNCIONÁRIO
        card.add(label("Funcionário:", fontLabel));
        carregarFuncionarios(servico);
        card.add(cbFuncionario);

        // CLIENTE
        card.add(label("Cliente:", fontLabel));
        carregarClientes(servico);
        card.add(cbCliente);

        // PRAZO SERVIÇO
        card.add(label("Prazo (dd/mm/aaaa):", fontLabel));
        txtPrazo = campoData(servico != null ? df.format(servico.getPrazoServico()) : "");
        card.add(txtPrazo);

        // PREÇO
        card.add(label("Preço:", fontLabel));
        String precoInicial = servico != null ? moeda.format(servico.getPrecoServico()) : "";
        txtPreco = campo(precoInicial, fontField);
        card.add(txtPreco);

        // DESCRIÇÃO
        card.add(label("Descrição:", fontLabel));
        txtDescricao = campo(servico != null ? servico.getDescricao() : "", fontField);
        card.add(txtDescricao);

        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setBackground(new Color(245, 247, 250));
        centerWrap.add(card);

        add(centerWrap, BorderLayout.CENTER);

        // BOTÕES
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        botoes.setBackground(new Color(245, 247, 250));

        JButton btnSalvar = botao("Salvar", new Color(52, 120, 246));
        JButton btnCancelar = botao("Cancelar", new Color(120, 120, 120));

        botoes.add(btnSalvar);
        botoes.add(btnCancelar);

        add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvar(frame));
        btnCancelar.addActionListener(e -> frame.carregarTela(new JPanelTabelaServicos(frame)));
    }

    // MÉTODOS DE INTERFACE

    private JLabel label(String texto, Font f) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(f);
        lbl.setForeground(new Color(70, 70, 70));
        return lbl;
    }

    private JTextField campo(String texto, Font f) {
        JTextField t = new JTextField(texto);
        t.setFont(f);
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return t;
    }

    private JFormattedTextField campoData(String valor) {
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            mf.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(mf);
            campo.setText(valor);
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

    private JButton botao(String texto, Color cor) {
        JButton b = new JButton(texto);
        b.setFocusPainted(false);
        b.setBackground(cor);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 40));
        return b;
    }

  
    // COMBOBOX FUNCIONÁRIO
 

    private void carregarFuncionarios(Tabela_Servico servico) {
        FuncionarioDAO dao = new FuncionarioDAO();
        List<Funcionario> lista = dao.read();

        funcionariosIds = new int[lista.size()];
        String[] nomes = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            funcionariosIds[i] = lista.get(i).getIdFuncionario();
            nomes[i] = lista.get(i).getNome();
        }

        cbFuncionario = new JComboBox<>(nomes);

        if (servico != null) {
            for (int i = 0; i < funcionariosIds.length; i++) {
                if (funcionariosIds[i] == servico.getIdFuncionario()) {
                    cbFuncionario.setSelectedIndex(i);
                }
            }
        }
    }

    // COMBOBOX CLIENTE
  

    private void carregarClientes(Tabela_Servico servico) {
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> lista = dao.read();

        clientesIds = new int[lista.size()];
        String[] nomes = new String[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            clientesIds[i] = lista.get(i).getId_cliente(); // ← CORRETO
            nomes[i] = lista.get(i).getNome();
        }

        cbCliente = new JComboBox<>(nomes);

        if (servico != null) {
            for (int i = 0; i < clientesIds.length; i++) {
                if (clientesIds[i] == servico.getIdCliente()) {
                    cbCliente.setSelectedIndex(i);
                }
            }
        }
    }

    
    // SALVAR SERVIÇO
  

    private void salvar(JfrmMenuPrincipal frame) {

        try {
            String nomeServico = txtServico.getText().trim();
            String prazoStr = txtPrazo.getText().trim();
            String precoStr = txtPreco.getText()
                    .replace("R$", "")
                    .replace(".", "")
                    .replace(",", ".")
                    .trim();

            if (nomeServico.isEmpty() || prazoStr.contains("_") || precoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            LocalDate prazo = LocalDate.parse(prazoStr, df);
            double preco = Double.parseDouble(precoStr);

            int idFuncionario = funcionariosIds[cbFuncionario.getSelectedIndex()];
            int idCliente = clientesIds[cbCliente.getSelectedIndex()];

            Tabela_ServicoDAO dao = new Tabela_ServicoDAO();
            boolean sucesso;

            if (servicoEditando == null) {
                Tabela_Servico novo = new Tabela_Servico(
                        idFuncionario,
                        idCliente,
                        nomeServico,
                        prazo,
                        preco,
                        txtDescricao.getText()
                );

                dao.create(novo);
                sucesso = true;

            } else {
                servicoEditando.setServico(nomeServico);
                servicoEditando.setPrazoServico(prazo);
                servicoEditando.setPrecoServico(preco);
                servicoEditando.setDescricao(txtDescricao.getText());
                servicoEditando.setIdFuncionario(idFuncionario);
                servicoEditando.setIdCliente(idCliente);

                sucesso = dao.update(servicoEditando);
            }

            JOptionPane.showMessageDialog(this,
                    sucesso ? "Serviço salvo com sucesso!" : "Erro ao salvar serviço.");

            frame.carregarTela(new JPanelTabelaServicos(frame));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
