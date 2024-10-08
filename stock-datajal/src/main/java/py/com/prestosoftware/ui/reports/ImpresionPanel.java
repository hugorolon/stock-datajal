package py.com.prestosoftware.ui.reports;

import javax.swing.JDialog;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;

public class ImpresionPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JCheckBox chkBoxImpresion;
	private ImpresionPanelInterfaz panelInterfaz = null;
	
	public void setPanelInterfaz(ImpresionPanelInterfaz panelInterfaz) {
		this.panelInterfaz = panelInterfaz;
	}
	
	public ImpresionPanelInterfaz getPanelInterfaz() {
		return panelInterfaz;
	}
	
	

	/**
	 * Create the dialog.
	 */
	public ImpresionPanel() {
		setTitle("OPCIONES DE IMPRESIÓN");
		setModal(true);
		setBounds(100, 100, 635, 245);
		getContentPane().setLayout(null);
		
		JButton btnFactura = new JButton("Factura");
		btnFactura.setFont(new Font("Verdana", Font.BOLD, 14));
		btnFactura.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					panelInterfaz.imprimirFactura(chkBoxImpresion.isSelected());
					dispose();
				}
			}
		});
		btnFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInterfaz.imprimirFactura(chkBoxImpresion.isSelected());
				dispose();
			}
		});
		btnFactura.setBounds(222, 80, 139, 52);
		getContentPane().add(btnFactura);
		
		JButton btnRemision = new JButton("Nota Interna");
		btnRemision.setFont(new Font("Verdana", Font.BOLD, 14));
		btnRemision.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					panelInterfaz.imprimirNota(chkBoxImpresion.isSelected());
					dispose();
				}
			}
		});
		btnRemision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInterfaz.imprimirNota(chkBoxImpresion.isSelected());
				dispose();
			}
		});
		btnRemision.setBounds(20, 80, 139, 52);
		getContentPane().add(btnRemision);
		
		JLabel lblImpresionFactura = new JLabel("SELECCIONAR / CANCELAR");
		lblImpresionFactura.setFont(new Font("Verdana", Font.BOLD, 20));
		lblImpresionFactura.setHorizontalAlignment(SwingConstants.CENTER);
		lblImpresionFactura.setBounds(10, 11, 574, 52);
		getContentPane().add(lblImpresionFactura);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					panelInterfaz.cancelarImpresion();
					dispose();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInterfaz.cancelarImpresion();
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Verdana", Font.BOLD, 14));
		btnCancelar.setBounds(433, 80, 131, 52);
		getContentPane().add(btnCancelar);
		
		chkBoxImpresion = new JCheckBox("Imprimir directo en impresora predeterminada");
		chkBoxImpresion.setBounds(40, 167, 524, 21);
		getContentPane().add(chkBoxImpresion);
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);

	}
}
