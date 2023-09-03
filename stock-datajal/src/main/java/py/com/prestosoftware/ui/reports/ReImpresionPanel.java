package py.com.prestosoftware.ui.reports;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXDatePicker;
import javax.swing.JCheckBox;

public class ReImpresionPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private ReImpresionPanelInterfaz panelInterfaz = null;
	private JXDatePicker dtpFecha;
	private JCheckBox chkImpresion;
	
	public void setPanelInterfaz(ReImpresionPanelInterfaz panelInterfaz) {
		this.panelInterfaz = panelInterfaz;
	}
	
	public ReImpresionPanelInterfaz getPanelInterfaz() {
		return panelInterfaz;
	}

	/**
	 * Create the dialog.
	 */
	public ReImpresionPanel() {
		setTitle("OPCIONES DE REIMPRESIÓN");
		setModal(true);
		setBounds(100, 100, 671, 266);
		getContentPane().setLayout(null);
		
		JButton btnFactura = new JButton("FACTURA");
		btnFactura.setFont(new Font("Verdana", Font.BOLD, 14));
		btnFactura.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					panelInterfaz.cargaFecha(dtpFecha.getDate());
					panelInterfaz.imprimirFactura(chkImpresion.isSelected());
					dispose();
				}
			}
		});
		btnFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInterfaz.cargaFecha(dtpFecha.getDate());
				panelInterfaz.imprimirFactura(chkImpresion.isSelected());
				dispose();
			}
		});
		btnFactura.setBounds(246, 80, 139, 52);
		getContentPane().add(btnFactura);
		
		JButton btnRemision = new JButton("Nota Interna");
		btnRemision.setFont(new Font("Verdana", Font.BOLD, 14));
		btnRemision.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					panelInterfaz.cargaFecha(dtpFecha.getDate());
					panelInterfaz.imprimirNota(chkImpresion.isSelected());
					dispose();
				}
			}
		});
		btnRemision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelInterfaz.cargaFecha(dtpFecha.getDate());
				panelInterfaz.imprimirNota(chkImpresion.isSelected());
				dispose();
			}
		});
		btnRemision.setBounds(41, 80, 139, 52);
		getContentPane().add(btnRemision);
		
		JLabel lblImpresionFactura = new JLabel("Fecha Factura");
		lblImpresionFactura.setFont(new Font("Verdana", Font.BOLD, 10));
		lblImpresionFactura.setHorizontalAlignment(SwingConstants.CENTER);
		lblImpresionFactura.setBounds(10, 11, 131, 52);
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
		
		dtpFecha = new JXDatePicker();
		dtpFecha.setBounds(157, 27, 109, 20);
		dtpFecha.setFormats("dd/MM/yyyy");
		dtpFecha.setDate(new Date());
		getContentPane().add(dtpFecha);
		
		chkImpresion = new JCheckBox("Imprimir directo en impresora predeterminada");
		chkImpresion.setBounds(100, 155, 391, 21);
		getContentPane().add(chkImpresion);
		//dtpFecha.setColumns(10);
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);

	}
}
