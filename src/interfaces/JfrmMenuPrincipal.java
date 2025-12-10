
package interfaces;

import javax.swing.*;
import java.awt.*;

public class JfrmMenuPrincipal extends JFrame {

    private JPanel contentPanel;

    public JfrmMenuPrincipal() {
        setTitle("RM Climatização - Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        carregarTela(new JPanelMenu(this));
    }

    public void carregarTela(JPanel tela) {
        contentPanel.removeAll();
        contentPanel.add(tela, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JfrmMenuPrincipal().setVisible(true));
    }
}

