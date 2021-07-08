package py.com.prestosoftware.ui.transactions;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import org.springframework.stereotype.Component;
import java.awt.BorderLayout;
import javax.swing.JPanel;

@Component
public class ConfigInitPanel extends JDialog {

	/**
	 * Create the dialog.
	 */
	public ConfigInitPanel() {
		setBounds(100, 100, 841, 574);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Empresas", null, panel, null);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Ventas", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Compras", null, panel_2, null);
		panel_2.setLayout(null);

	}

}
