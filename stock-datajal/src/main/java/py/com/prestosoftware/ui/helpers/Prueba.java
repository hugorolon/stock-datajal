package py.com.prestosoftware.ui.helpers;

import java.text.DecimalFormat;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class Prueba {

	private JFrame frame;
	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JPanel panel_1;
	private JLabel label;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prueba window = new Prueba();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Prueba() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setTitle("LISTA DE STOCK");
		frame.setSize(900, 600);
		//frame.setModal(true);
		frame.getContentPane().setLayout(null);
		
		JPanel pnlBuscador = new JPanel();
		pnlBuscador.setBounds(0, 0, 900, 35);
		frame.getContentPane().add(pnlBuscador);
		
		JLabel lblBuscador = new JLabel("Buscador");
		pnlBuscador.add(lblBuscador);
		
		tfBuscador = new JTextField();
//		tfBuscador.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusGained(FocusEvent e) {
//				tfBuscador.selectAll();
//			}
//		});
//		((AbstractDocument) tfBuscador.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
//		tfBuscador.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
//					
//				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
//			    	dispose();
//			    } else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
//			    	table.requestFocus();
//			    } else if (e.getKeyCode()==KeyEvent.VK_F5) {
//			    	
//			    }
//				loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
//				
//			}
//		});
//		pnlBuscador.add(tfBuscador);
//		tfBuscador.setColumns(30);
//		
//		btnBuscar = new JButton("Buscar");
//		btnBuscar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
//			}
//		});
//		btnBuscar.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() ==  KeyEvent.VK_ENTER) {
//					loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
//				}
//			}
//		});
//		pnlBuscador.add(btnBuscar);
//		
//		scrollPane = new JScrollPane();
//		scrollPane.setBounds(0, 35, 614, 408);
//		frame.getContentPane().add(scrollPane);
//		
//		table = new JTable(tableModel);
//		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		table.setDefaultRenderer(Object.class, new CellRendererOperaciones());
//		table.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
//					aceptar();
//				} else if (e.getKeyCode()==KeyEvent.VK_INSERT) {
//					aceptar();
//				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
//					 tfBuscador.requestFocus();
//				} 
//			}
//		});
//		table.addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//				if (e.getClickCount() == 2) {     // to detect doble click events
//					aceptar();
//		            }
//			}
//			
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		scrollPane.setViewportView(table);
//		JPanel pnlBotonera = new JPanel();
//		pnlBotonera.setBounds(0, 443, 900, 35);
//		frame.getContentPane().add(pnlBotonera);
//		
//		btnAceptar = new JButton("Aceptar");
//		btnAceptar.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode()==KeyEvent.VK_ENTER){
//					aceptar();
//				}
//			}
//		});
//		btnAceptar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				aceptar();
//			}
//		});
//		pnlBotonera.add(btnAceptar);
//		
//		btnCancelar = new JButton("Cancelar");
//		btnCancelar.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//					dispose();
//				}
//			}
//		});
//		btnCancelar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				dispose();
//			}
//		});
//		pnlBotonera.add(btnCancelar);
//		
//		panel = new JPanel();
//		panel.setBounds(626, 35, 262, 197);
//		frame.getContentPane().add(panel);
//		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
//		
//		lblNewLabel = new JLabel("");
//		lblNewLabel.setSize(0, 30);
//		panel.add(lblNewLabel);
//		
//		panel_1 = new JPanel();
//		panel_1.setBounds(626, 244, 262, 199);
//		frame.getContentPane().add(panel_1);
//		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
//		
//		label = new JLabel("");
//		panel_1.add(label);
//		
//		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
//		Dimension ventana = frame.getSize(); 
//		frame.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
//		
//		loadProductos("");	
	}
	
}
