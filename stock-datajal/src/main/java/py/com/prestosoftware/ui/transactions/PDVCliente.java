package py.com.prestosoftware.ui.transactions;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Venta;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.VentaService;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.domain.validations.VentaValidator;
import py.com.prestosoftware.ui.helpers.DigitoVerificador;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.ImpresionUtil;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.ConsultaCliente;
import py.com.prestosoftware.ui.table.CotPDVTableModel;
import py.com.prestosoftware.ui.table.VentaItemTableModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class PDVCliente extends JFrame implements ClienteInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int CLIENTE_CODE = 1;

	private JLabel lblDireccion;
	private JTextField tfClienteNombre;
	private JTextField tfClienteID;
	private JTextField tfClienteRuc;
	private JTextField tfClienteDireccion;
	private JTextField tfDvRuc;
	private JLabel lblTelefono;
	private JLabel lblNombre;
	private JPanel pnlCliente;
	private JButton btnAceptar;
	private JLabel lblTotal;
	private JLabel txtTftotal;

	private VentaItemTableModel itemTableModel;
	private ConsultaCliente clientDialog;
	private ClienteService clienteService;
	private ProductoService productoService;
	private VentaService ventaService;
	private MovimientoCajaService pagoService;
	private VentaValidator ventaValidator;

	@Autowired
	public PDVCliente(VentaItemTableModel itemTableModel, ConsultaCliente clientDialog, VentaValidator ventaValidator,
			VentaService ventaService, ClienteService clienteService, CotPDVTableModel cotizacionModel,
			MovimientoCajaService movimientoCajaService, ProductoService productoService) {
		this.itemTableModel = itemTableModel;
		this.clientDialog = clientDialog;
		this.ventaValidator = ventaValidator;
		this.ventaService = ventaService;
		this.clienteService = clienteService;
		this.pagoService = movimientoCajaService;
		this.productoService = productoService;

		setSize(766, 400);
		setTitle("PDV - CLIENTE");
		setResizable(false);

		initComponents();

		Util.setupScreen(this);

		tfClienteRuc.requestFocus();
		tfClienteRuc.selectAll();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(null);

		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarVenta();
			}
		});
		btnAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					guardarVenta();
				}
			}
		});
		btnAceptar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAceptar.setBounds(408, 316, 175, 50);
		getContentPane().add(btnAceptar);

		lblTotal = new JLabel("TOTAL:");
		lblTotal.setBounds(6, 8, 68, 30);
		getContentPane().add(lblTotal);

		txtTftotal = new JLabel();
		txtTftotal.setForeground(Color.RED);
		txtTftotal.setFont(new Font("Verdana", Font.BOLD, 16));
		txtTftotal.setBounds(78, 8, 152, 30);
		getContentPane().add(txtTftotal);

		btnCancelar = new JButton("CANCELAR");
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					dispose();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCancelar.setBounds(584, 316, 175, 50);
		getContentPane().add(btnCancelar);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 46, 753, 268);
		getContentPane().add(tabbedPane);

		pnlCliente = new JPanel();
		tabbedPane.addTab("CLIENTE", null, pnlCliente, null);
		pnlCliente.setLayout(null);

		JLabel lblClienteID = new JLabel("RUC:");
		lblClienteID.setBounds(6, 10, 80, 30);
		pnlCliente.add(lblClienteID);

		tfClienteRuc = new JTextField();
		tfClienteRuc.setBounds(86, 10, 100, 30);
		pnlCliente.add(tfClienteRuc);
		((AbstractDocument) tfClienteRuc.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
//		tfClienteRuc.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusGained(FocusEvent e) {
//				tfClienteRuc.selectAll();
//			}
//		});
		tfClienteRuc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					findClienteByRuc();
				}
			}
		});
		// tfClienteRuc.setVisible(false);
		tfClienteRuc.setColumns(10);

		tfDvRuc = new JTextField();
		tfDvRuc.setBounds(187, 10, 27, 30);
		pnlCliente.add(tfDvRuc);
		tfDvRuc.setEditable(false);
		tfDvRuc.setColumns(10);

		lblNombre = new JLabel("NOMBRE:");
		lblNombre.setBounds(6, 50, 80, 30);
		pnlCliente.add(lblNombre);

		tfClienteNombre = new JTextField();
		tfClienteNombre.setEnabled(false);
		tfClienteNombre.setBounds(86, 50, 410, 30);
		pnlCliente.add(tfClienteNombre);
		tfClienteNombre.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfClienteNombre.selectAll();
			}
		});
		((AbstractDocument) tfClienteNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfClienteNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfClienteCelular.requestFocus();
				}
			}
		});
		tfClienteNombre.setColumns(10);

		lblTelefono = new JLabel("CELULAR:");
		lblTelefono.setBounds(6, 90, 80, 30);
		pnlCliente.add(lblTelefono);

		lblDireccion = new JLabel("DIRECCION:"); //0983554020
		lblDireccion.setBounds(6, 130, 80, 30);
		pnlCliente.add(lblDireccion);
		// lblDireccion.setVisible(false);

		tfClienteDireccion = new JTextField();
		tfClienteDireccion.setEnabled(false);
		tfClienteDireccion.setBounds(86, 130, 410, 30);
		pnlCliente.add(tfClienteDireccion);
		((AbstractDocument) tfClienteDireccion.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfClienteDireccion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfClienteDireccion.selectAll();
			}
		});
		tfClienteDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (isNewClient) {
						saveClient();
						Notifications.showAlert("Cliente guardado con exito.!");
					}

					tabbedPane.setSelectedIndex(1);
					tfRecibidoGs.requestFocus();
				}
			}
		});
		tfClienteDireccion.setColumns(10);

		tfClienteCelular = new JTextField();
		tfClienteCelular.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfClienteCelular.selectAll();
			}
		});
		tfClienteCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfClienteDireccion.requestFocus();
				}
			}
		});
		tfClienteCelular.setText("0");
		tfClienteCelular.setEnabled(false);
		tfClienteCelular.setColumns(10);
		tfClienteCelular.setBounds(88, 90, 98, 30);
		pnlCliente.add(tfClienteCelular);

		lblID = new JLabel("CLIENTE ID:");
		lblID.setBounds(260, 10, 84, 30);
		pnlCliente.add(lblID);

		tfClienteID = new JTextField();
		tfClienteID.setBounds(344, 10, 152, 30);
		pnlCliente.add(tfClienteID);
		tfClienteID.setEnabled(false);
		tfClienteID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfClienteID.selectAll();
			}
		});
		tfClienteID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(CLIENTE_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfClienteID.getText().isEmpty()) {
						Long clienteId = Long.parseLong(tfClienteID.getText());
						findClientById(clienteId);
					} else {
						showDialog(CLIENTE_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					showDialog(CLIENTE_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfClienteID.setText("0");
		tfClienteID.setColumns(10);

		lblValorSaldo = new JLabel("SALDO:");
		lblValorSaldo.setBounds(508, 10, 59, 30);
		pnlCliente.add(lblValorSaldo);

		textField_1 = new JTextField();
		textField_1.setBounds(579, 10, 163, 30);
		pnlCliente.add(textField_1);
		textField_1.setText("0");
		textField_1.setEnabled(false);
		textField_1.setColumns(10);

		pnlPago = new JPanel();
		tabbedPane.addTab("FORMA DE PAGO", null, pnlPago, null);
		pnlPago.setLayout(null);

		tfTotalGs = new JTextField();
		tfTotalGs.setEnabled(false);
		tfTotalGs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalGs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfTotalGs.setColumns(10);
		tfTotalGs.setBounds(55, 40, 165, 39);
		pnlPago.add(tfTotalGs);

		tfFaltanteGs = new JTextField();
		tfFaltanteGs.setEnabled(false);
		tfFaltanteGs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfFaltanteGs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfFaltanteGs.setColumns(10);
		tfFaltanteGs.setBounds(228, 40, 165, 39);
		pnlPago.add(tfFaltanteGs);

		tfVueltoGs = new JTextField();
		tfVueltoGs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfVueltoGs.selectAll();
			}
		});
		tfVueltoGs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// calculateVuelto();
					Double vuelto = FormatearValor
							.stringToDouble(!tfVueltoGs.getText().isEmpty() ? tfVueltoGs.getText() : "0");
					Double recibido = FormatearValor
							.stringToDouble(!tfRecibidoGs.getText().isEmpty() ? tfRecibidoGs.getText() : "0");
					Double faltante = FormatearValor
							.stringToDouble(!tfFaltanteGs.getText().isEmpty() ? tfFaltanteGs.getText() : "0");

					if (vuelto > 0) {
						Double vueltoSaldo = recibido - faltante;
						
						System.out.println("Vuelto Saldo: " + vueltoSaldo);
						System.out.println("Vuelto: " + vuelto);

						if (vuelto == vueltoSaldo) {
							btnAceptar.requestFocus();
						} else {
							tfVueltoUs.setText(FormatearValor.doubleAString(vueltoSaldo / cotUs));
							tfVueltoRs.setText(FormatearValor.doubleAString(vueltoSaldo / cotRs));
							tfVueltoPs.setText(FormatearValor.doubleAString(vueltoSaldo / cotPs));

							tfVueltoUs.requestFocus();
						}
					} else {
						tfVueltoGs.setText("0");

						tfVueltoUs.setText(FormatearValor.doubleAString(vuelto / cotUs));
						tfVueltoRs.setText(FormatearValor.doubleAString(vuelto / cotRs));
						tfVueltoPs.setText(FormatearValor.doubleAString(vuelto / cotPs));

						tfVueltoUs.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfVueltoGs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfVueltoGs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVueltoGs.setColumns(10);
		tfVueltoGs.setBounds(574, 40, 165, 39);
		pnlPago.add(tfVueltoGs);

		tfVueltoUs = new JTextField();
		tfVueltoUs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfVueltoUs.selectAll();
			}
		});
		tfVueltoUs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// calculateVuelto();
					Double vuelto = FormatearValor
							.stringToDouble(!tfVueltoUs.getText().isEmpty() ? tfVueltoUs.getText() : "0");
					Double recibido = 0d;
					Double faltante = FormatearValor
							.stringToDouble(!tfFaltanteUs.getText().isEmpty() ? tfFaltanteGs.getText() : "0");

					if (!tfRecibidoGs.getText().isEmpty() && !tfRecibidoGs.getText().equals("0")) {
						recibido = FormatearValor.stringToDouble(tfRecibidoUs.getText());
					} else {
						recibido = FormatearValor
								.stringToDouble(!tfRecibidoUs.getText().isEmpty() ? tfRecibidoGs.getText() : "0");
					}

					if (vuelto > 0) {
						Double vueltoSaldo = recibido - faltante;

						if (vuelto == vueltoSaldo) {
							btnAceptar.requestFocus();
						} else {
							tfVueltoUs.setText(FormatearValor.doubleAString(vueltoSaldo / cotUs));
							tfVueltoRs.setText(FormatearValor.doubleAString(vueltoSaldo / cotRs));
							tfVueltoPs.setText(FormatearValor.doubleAString(vueltoSaldo / cotPs));

							tfVueltoUs.requestFocus();
						}
					} else {
						tfVueltoGs.setText("0");

						tfVueltoUs.setText(FormatearValor.doubleAString(vuelto / cotUs));
						tfVueltoRs.setText(FormatearValor.doubleAString(vuelto / cotRs));
						tfVueltoPs.setText(FormatearValor.doubleAString(vuelto / cotPs));

						tfVueltoUs.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfVueltoUs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfVueltoUs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVueltoUs.setColumns(10);
		tfVueltoUs.setBounds(574, 87, 165, 39);
		pnlPago.add(tfVueltoUs);

		tfVueltoRs = new JTextField();
		tfVueltoRs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfVueltoRs.selectAll();
			}
		});
		tfVueltoRs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//calculateVuelto();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfVueltoRs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfVueltoRs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVueltoRs.setColumns(10);
		tfVueltoRs.setBounds(574, 134, 165, 39);
		pnlPago.add(tfVueltoRs);

		tfVueltoPs = new JTextField();
		tfVueltoPs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfVueltoPs.selectAll();
			}
		});
		tfVueltoPs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					calculateVuelto();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfVueltoPs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfVueltoPs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfVueltoPs.setColumns(10);
		tfVueltoPs.setBounds(574, 181, 165, 39);
		pnlPago.add(tfVueltoPs);

		txtVuelto = new JLabel();
		txtVuelto.setHorizontalAlignment(SwingConstants.CENTER);
		txtVuelto.setFont(new Font("Verdana", Font.BOLD, 16));
		txtVuelto.setText("VUELTO");
		txtVuelto.setBounds(574, 8, 165, 24);
		pnlPago.add(txtVuelto);

		tfRecibidoGs = new JTextField();
		tfRecibidoGs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfRecibidoGs.selectAll();
			}
		});
		tfRecibidoGs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					
					//convertir todo a moneda padron
//					Double value = !tfRecibidoGs.getText().isEmpty() ? 
//							tfRecibidoGs.getText() : 0;
					
					calculateValue(tfRecibidoGs.getText(), "GS");
					
//					Double total = FormatearValor
//							.stringToDouble(!tfTotalGs.getText().isEmpty() ? tfTotalGs.getText() : "0");
//					Double recibido = FormatearValor
//							.stringToDouble(!tfRecibidoGs.getText().isEmpty() ? tfRecibidoGs.getText() : "0");
//					Double faltante = FormatearValor
//							.stringToDouble(!tfFaltanteGs.getText().isEmpty() ? tfFaltanteGs.getText() : "0");
//
//					if (recibido > 0) {
//						if (recibido >= faltante) {
//							tfVueltoGs.setText(FormatearValor.doubleAString(recibido - faltante));
//							tfVueltoGs.requestFocus();
//							
//							tfFaltanteUs.setText("0");
//							tfFaltanteRs.setText("0");
//							tfFaltantePs.setText("0");
//						} else {
//							Double saldo = total - recibido;
//							tfFaltanteGs.setText(FormatearValor.doubleAString(saldo));
//							// calcular saldos
//							tfFaltanteUs.setText(FormatearValor.doubleAString(saldo / cotUs));
//							tfFaltanteRs.setText(FormatearValor.doubleAString(saldo / cotRs));
//							tfFaltantePs.setText(FormatearValor.doubleAString(saldo / cotPs));
//
//							tfRecibidoUs.setText(FormatearValor.doubleAString(saldo / cotUs));
//							tfRecibidoUs.requestFocus();
//						}
//					} else {
//						tfRecibidoGs.setText("0");
//						tfRecibidoUs.requestFocus();
//					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tfRecibidoGs.getText().length() > 3) {
		            tfRecibidoGs.setText(FormatearValor.formatearValor(FormatearValor.desformatearValor(tfRecibidoGs.getText())));
		        }
			}
		});
		tfRecibidoGs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfRecibidoGs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRecibidoGs.setBounds(401, 40, 165, 39);
		pnlPago.add(tfRecibidoGs);
		tfRecibidoGs.setColumns(10);

		txtGs = new JLabel();
		txtGs.setFont(new Font("Verdana", Font.BOLD, 16));
		txtGs.setText("GS.");
		txtGs.setBounds(8, 40, 39, 39);
		pnlPago.add(txtGs);

		tfFaltanteUs = new JTextField();
		tfFaltanteUs.setEnabled(false);
		tfFaltanteUs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfFaltanteUs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfFaltanteUs.setColumns(10);
		tfFaltanteUs.setBounds(228, 87, 165, 39);
		pnlPago.add(tfFaltanteUs);

		tfFaltanteRs = new JTextField();
		tfFaltanteRs.setEnabled(false);
		tfFaltanteRs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfFaltanteRs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfFaltanteRs.setColumns(10);
		tfFaltanteRs.setBounds(228, 134, 165, 39);
		pnlPago.add(tfFaltanteRs);

		tfFaltantePs = new JTextField();
		tfFaltantePs.setEnabled(false);
		tfFaltantePs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfFaltantePs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfFaltantePs.setColumns(10);
		tfFaltantePs.setBounds(228, 181, 165, 39);
		pnlPago.add(tfFaltantePs);

		tfTotalUs = new JTextField();
		tfTotalUs.setEnabled(false);
		tfTotalUs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalUs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfTotalUs.setColumns(10);
		tfTotalUs.setBounds(55, 87, 165, 39);
		pnlPago.add(tfTotalUs);

		tfTotalRs = new JTextField();
		tfTotalRs.setEnabled(false);
		tfTotalRs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalRs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfTotalRs.setColumns(10);
		tfTotalRs.setBounds(55, 134, 165, 39);
		pnlPago.add(tfTotalRs);

		tfTotalPs = new JTextField();
		tfTotalPs.setEnabled(false);
		tfTotalPs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalPs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfTotalPs.setColumns(10);
		tfTotalPs.setBounds(55, 181, 165, 39);
		pnlPago.add(tfTotalPs);

		txtUs = new JLabel();
		txtUs.setText("US.");
		txtUs.setFont(new Font("Verdana", Font.BOLD, 16));
		txtUs.setBounds(8, 87, 39, 39);
		pnlPago.add(txtUs);

		txtRs = new JLabel();
		txtRs.setText("RS.");
		txtRs.setFont(new Font("Verdana", Font.BOLD, 16));
		txtRs.setBounds(8, 134, 39, 39);
		pnlPago.add(txtRs);

		txtPs = new JLabel();
		txtPs.setText("PS.");
		txtPs.setFont(new Font("Verdana", Font.BOLD, 16));
		txtPs.setBounds(8, 181, 39, 39);
		pnlPago.add(txtPs);

		tfRecibidoUs = new JTextField();
		tfRecibidoUs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfRecibidoUs.selectAll();
			}
		});
		tfRecibidoUs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Double total = FormatearValor
							.stringToDouble(!tfTotalUs.getText().isEmpty() ? tfTotalUs.getText() : "0");
					Double recibido = FormatearValor
							.stringToDouble(!tfRecibidoUs.getText().isEmpty() ? tfRecibidoUs.getText() : "0");
					Double faltante = FormatearValor
							.stringToDouble(!tfFaltanteUs.getText().isEmpty() ? tfFaltanteUs.getText() : "0");

					if (recibido > 0) {
						if (recibido >= faltante) {
							tfVueltoUs.setText(FormatearValor.doubleAString(recibido - faltante));
							tfVueltoUs.requestFocus();
							
							tfFaltanteRs.setText("0");
							tfFaltantePs.setText("0");
							
						} else {
							Double saldo = total - recibido;
							tfFaltanteUs.setText(FormatearValor.doubleAString(saldo));
							// calcular saldos
							Double saldoGs = saldo * cotGs;
							tfFaltanteGs.setText(FormatearValor.doubleAString(saldoGs));
							tfFaltanteRs.setText(FormatearValor.doubleAString(saldoGs / cotRs));
							tfFaltantePs.setText(FormatearValor.doubleAString(saldoGs / cotPs));

							tfRecibidoRs.requestFocus();
						}
					} else {
						tfRecibidoGs.setText("0");
						tfRecibidoUs.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tfRecibidoUs.getText().length() > 3) {
		            tfRecibidoUs.setText(FormatearValor.formatearValor(FormatearValor.desformatearValor(tfRecibidoUs.getText())));
		        }
			}
		});
		tfRecibidoUs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfRecibidoUs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRecibidoUs.setColumns(10);
		tfRecibidoUs.setBounds(401, 87, 165, 39);
		pnlPago.add(tfRecibidoUs);

		tfRecibidoRs = new JTextField();
		tfRecibidoRs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfRecibidoRs.selectAll();
			}
		});
		tfRecibidoRs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// calculateValue();
					Double total = FormatearValor
							.stringToDouble(!tfTotalRs.getText().isEmpty() ? tfTotalRs.getText() : "0");
					Double recibido = FormatearValor
							.stringToDouble(!tfRecibidoRs.getText().isEmpty() ? tfRecibidoRs.getText() : "0");
					Double faltante = FormatearValor
							.stringToDouble(!tfFaltanteRs.getText().isEmpty() ? tfFaltanteRs.getText() : "0");

					if (recibido > 0) {
						if (recibido >= faltante) {
							tfVueltoRs.setText(FormatearValor.doubleAString(recibido - faltante));
							tfVueltoRs.requestFocus();
						} else {
							Double saldo = total - recibido;
							tfFaltanteRs.setText(FormatearValor.doubleAString(saldo));
							// calcular saldos
							Double saldoGs = saldo * cotGs;
							tfFaltanteGs.setText(FormatearValor.doubleAString(saldoGs));
							tfFaltanteUs.setText(FormatearValor.doubleAString(saldoGs / cotUs));
							tfFaltantePs.setText(FormatearValor.doubleAString(saldoGs / cotPs));

							tfRecibidoPs.requestFocus();
						}
					} else {
						tfRecibidoGs.setText("0");
						tfRecibidoUs.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tfRecibidoRs.getText().length() > 3) {
		            tfRecibidoRs.setText(FormatearValor.formatearValor(FormatearValor.desformatearValor(tfRecibidoRs.getText())));
		        }
			}
		});
		tfRecibidoRs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfRecibidoRs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRecibidoRs.setColumns(10);
		tfRecibidoRs.setBounds(401, 134, 165, 39);
		pnlPago.add(tfRecibidoRs);

		tfRecibidoPs = new JTextField();
		tfRecibidoPs.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfRecibidoPs.selectAll();
			}
		});
		tfRecibidoPs.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// calculateValue();
					Double total = FormatearValor
							.stringToDouble(!tfTotalPs.getText().isEmpty() ? tfTotalPs.getText() : "0");
					Double recibido = FormatearValor
							.stringToDouble(!tfRecibidoPs.getText().isEmpty() ? tfRecibidoPs.getText() : "0");
					Double faltante = FormatearValor
							.stringToDouble(!tfFaltantePs.getText().isEmpty() ? tfFaltantePs.getText() : "0");

					if (recibido > 0) {
						if (recibido >= faltante) {
							tfVueltoPs.setText(FormatearValor.doubleAString(recibido - faltante));
							tfVueltoPs.requestFocus();
						} else {
							Double saldo = total - recibido;
							tfFaltantePs.setText(FormatearValor.doubleAString(saldo));
							// calcular saldos
							Double saldoGs = saldo * cotGs;
							tfFaltanteGs.setText(FormatearValor.doubleAString(saldoGs));
							tfFaltanteUs.setText(FormatearValor.doubleAString(saldoGs / cotUs));
							tfFaltanteRs.setText(FormatearValor.doubleAString(saldoGs / cotRs));

							if (saldo == 0) {
								btnAceptar.requestFocus();
							} else {
								Notifications.showAlert("El total debe coincider con el Monto a Pagar");
								tfRecibidoGs.requestFocus();
							}
						}
					} else {
						tfRecibidoGs.setText("0");
						tfRecibidoUs.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (tfRecibidoPs.getText().length() > 3) {
		            tfRecibidoPs.setText(FormatearValor.formatearValor(FormatearValor.desformatearValor(tfRecibidoPs.getText())));
		        }
			}
		});
		tfRecibidoPs.setFont(new Font("Verdana", Font.BOLD, 16));
		tfRecibidoPs.setHorizontalAlignment(SwingConstants.RIGHT);
		tfRecibidoPs.setColumns(10);
		tfRecibidoPs.setBounds(401, 181, 165, 39);
		pnlPago.add(tfRecibidoPs);

		txtTotal = new JLabel();
		txtTotal.setText("CONSOLIDADDO");
		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txtTotal.setFont(new Font("Verdana", Font.BOLD, 16));
		txtTotal.setBounds(55, 8, 165, 24);
		pnlPago.add(txtTotal);

		txtFaltante = new JLabel();
		txtFaltante.setText("FALTANTE");
		txtFaltante.setHorizontalAlignment(SwingConstants.CENTER);
		txtFaltante.setFont(new Font("Verdana", Font.BOLD, 16));
		txtFaltante.setBounds(228, 8, 165, 24);
		pnlPago.add(txtFaltante);

		txtAPagar = new JLabel();
		txtAPagar.setForeground(Color.RED);
		txtAPagar.setText("A PAGAR");
		txtAPagar.setHorizontalAlignment(SwingConstants.CENTER);
		txtAPagar.setFont(new Font("Verdana", Font.BOLD, 16));
		txtAPagar.setBounds(401, 8, 165, 24);
		pnlPago.add(txtAPagar);

		lblTicket = new JLabel("TICKET:");
		lblTicket.setBounds(271, 8, 68, 30);
		getContentPane().add(lblTicket);

		tfTicketNro = new JLabel();
		tfTicketNro.setFont(new Font("Verdana", Font.BOLD, 16));
		tfTicketNro.setForeground(Color.RED);
		tfTicketNro.setBounds(343, 8, 152, 30);
		getContentPane().add(tfTicketNro);

		lblCaja = new JLabel("CAJA:");
		lblCaja.setBounds(535, 8, 68, 30);
		getContentPane().add(lblCaja);

		tfCajaId = new JLabel();
		tfCajaId.setForeground(Color.RED);
		tfCajaId.setFont(new Font("Verdana", Font.BOLD, 16));
		tfCajaId.setBounds(607, 8, 152, 30);
		getContentPane().add(tfCajaId);
	}

	private void findClienteByRuc() {
		// buscamos el cliente por ruc, si existe pasa a aceptar
		if (!tfClienteRuc.getText().isEmpty()) {
			Cliente cliente = clienteService.findByRuc(tfClienteRuc.getText());

			if (cliente != null) {
				tfClienteID.setText(String.valueOf(cliente.getId()));
				tfClienteRuc.setText(cliente.getDvruc());
				tfClienteNombre.setText(cliente.getRazonSocial() != null ? cliente.getRazonSocial() : "");
				tfClienteDireccion.setText(cliente.getDireccion() != null ? cliente.getDireccion() : "");
				tfClienteCelular.setText(cliente.getCelular() != null ? cliente.getCelular() : "");
				tabbedPane.setSelectedIndex(1);
				tfRecibidoGs.requestFocus();
			} else {
				isNewClient = true;
				clearClientData();
				habilitarCampos(true);
				calculateDV();
			}
		} else {
			Notifications.showAlert("Debes ingresar RUC del Cliente");
			tfClienteNombre.requestFocus();
			clearClientData();
		}
	}

	private boolean isNewClient = false;
	private Long newClientId = 0L;

	private void saveClient() {
		Cliente c = new Cliente();
		c.setNombre(tfClienteNombre.getText());
		c.setRazonSocial(tfClienteNombre.getText());
		c.setDvruc(tfDvRuc.getText());
		c.setDireccion(tfClienteDireccion.getText());
		c.setCelular(tfClienteCelular.getText());

		if (tfDvRuc.getText().isEmpty()) {
			c.setCiruc("44444401");
			c.setDvruc("");
		}

		Cliente cliente = clienteService.save(c);

		if (cliente != null) {
			newClientId = cliente.getId();
		}
	}

	private void habilitarCampos(boolean isEditing) {
		tfClienteNombre.requestFocus();
		
		tfClienteNombre.setEnabled(isEditing);
		tfClienteCelular.setEnabled(isEditing);
		tfClienteDireccion.setEnabled(isEditing);
	}
	
	private void clearClientData() {
		tfClienteNombre.setText("");
		tfClienteCelular.setText("");
		tfClienteDireccion.setText("");
	}

	private void guardarVenta() {
		save(ventaPDV);
	}

	private void calculateDV() {
		if (!tfDvRuc.getText().isEmpty()) {
			tfDvRuc.setText(String.valueOf(DigitoVerificador.calcular(tfClienteRuc.getText(), 11)));
		} else {
			Notifications.showAlert("Debes digitar RUC valido.!");
		}
	}

	private Venta ventaPDV;
	private Long cajaId;
	private Double cotGs, cotUs, cotRs, cotPs = 0d;

	public void cargarCliente(Venta venta, String cajaNro, String totalGs, String totalUs, String totalRs,
			String totalPs, String ventaId, Double mGs, Double mUs, Double mRs, Double mPs) {
		if (venta != null) {
			ventaPDV = venta;
			cajaId = Long.valueOf(cajaNro);

			cotGs = mGs;
			cotUs = mUs;
			cotRs = mRs;
			cotPs = mPs;

			// totales
			tfTotalGs.setText(totalGs);
			tfTotalUs.setText(totalUs);
			tfTotalRs.setText(totalRs);
			tfTotalPs.setText(totalPs);

			// faltantes
			tfFaltanteGs.setText(totalGs);
			tfFaltanteUs.setText(totalUs);
			tfFaltanteRs.setText(totalRs);
			tfFaltantePs.setText(totalPs);

			tfRecibidoGs.setText(totalGs);

			tfTicketNro.setText(ventaId);
			tfCajaId.setText(cajaNro);

			txtTftotal.setText(totalGs);
			tfClienteRuc.setText(venta.getClienteRuc());
			tfClienteID.setText(String.valueOf(venta.getCliente().getId()));
			tfClienteNombre.setText(venta.getClienteNombre());
			tfClienteDireccion.setText(venta.getClienteDireccion());

			tfClienteRuc.requestFocus();
			tfClienteRuc.selectAll();
		}
	}

//	private void openPdvFormaPago() {
//		dispose();
//		super.dispose();

//		lanzamientoCaja.setVisible(true);
//		lanzamientoCaja.isPDV(new Caja(cajaId), notaId);
//		lanzamientoCaja.getCotizaciones();
//	}

	private void clearForm() {
		tfClienteID.setText("");
		tfClienteNombre.setText("");
		tfClienteRuc.setText("");
		tfClienteDireccion.setText("");

	}

	private void showDialog(int code) {
		clientDialog.setInterfaz(this);
		clientDialog.setVisible(true);
	}

	@Override
	public void getEntity(Cliente cliente) {
		if (cliente != null) {
			tfClienteID.setText(String.valueOf(cliente.getId()));
			tfClienteNombre.setText(cliente.getRazonSocial());
			tfClienteRuc.setText(cliente.getCiruc());
			tfClienteDireccion.setText(cliente.getDireccion());
		}
	}

	public void setVenta(Venta v) {
		tfClienteID.setText(String.valueOf(v.getCliente().getId()));
		tfClienteNombre.setText(v.getClienteNombre());
		tfClienteRuc.setText("");
		tfClienteDireccion.setText("");
		itemTableModel.addEntities(v.getItems());
	}

	private boolean validateCabezera() {
		return true;
	}

	Long notaId = 0L;
	private JLabel lblID;
	private JTextField tfClienteCelular;
	private JLabel lblValorSaldo;
	private JTextField textField_1;
	private JButton btnCancelar;
	private JTabbedPane tabbedPane;
	private JPanel pnlPago;
	private JLabel lblTicket;
	private JLabel tfTicketNro;
	private JLabel lblCaja;
	private JLabel tfCajaId;
	private JTextField tfTotalGs;
	private JTextField tfFaltanteGs;
	private JTextField tfVueltoGs;
	private JTextField tfVueltoUs;
	private JTextField tfVueltoRs;
	private JTextField tfVueltoPs;
	private JLabel txtVuelto;
	private JTextField tfRecibidoGs;
	private JLabel txtGs;
	private JTextField tfFaltanteUs;
	private JTextField tfFaltanteRs;
	private JTextField tfFaltantePs;
	private JTextField tfTotalUs;
	private JTextField tfTotalRs;
	private JTextField tfTotalPs;
	private JLabel txtUs;
	private JLabel txtRs;
	private JLabel txtPs;
	private JTextField tfRecibidoUs;
	private JTextField tfRecibidoRs;
	private JTextField tfRecibidoPs;
	private JLabel txtTotal;
	private JLabel txtFaltante;
	private JLabel txtAPagar;

	private void save(Venta ventaPDV) {
//		Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA");
//		if (respuesta == 0) {
		if (validateCabezera()) { // && validateItems(itemTableModel.getEntities())
			// Venta venta = getVentaFrom();

			Venta venta = ventaPDV;

			if (isNewClient) {
				venta.setCliente(new Cliente(newClientId));
				venta.setClienteNombre(tfClienteNombre.getText());
				venta.setClienteRuc(tfClienteRuc.getText() + tfDvRuc.getText());
				venta.setClienteDireccion("SIN DIRECCION");

				isNewClient = false;
				newClientId = 0L;
			}

			Optional<ValidationError> errors = ventaValidator.validate(venta);
			if (errors.isPresent()) {
				ValidationError validationError = errors.get();
				Notifications.showFormValidationAlert(validationError.getMessage());
			} else {
				Venta v = ventaService.save(venta);
				if (v != null) {
					saveMovimientoCaja(v);
				}
			}
		}
	}

	private void calculateVuelto() {
		Double vueltoGs = FormatearValor.stringToDouble(!tfVueltoGs.getText().isEmpty() ? tfVueltoGs.getText() : "0");
		Double vueltoUs = FormatearValor.stringToDouble(!tfVueltoUs.getText().isEmpty() ? tfVueltoUs.getText() : "0");
		Double vueltoRs = FormatearValor.stringToDouble(!tfVueltoRs.getText().isEmpty() ? tfVueltoRs.getText() : "0");
		Double vueltoPs = FormatearValor.stringToDouble(!tfVueltoPs.getText().isEmpty() ? tfVueltoPs.getText() : "0");

		if (vueltoGs > 0) {

		} else {

			if (vueltoUs > 0) {

				if (vueltoRs > 0) {

					if (vueltoPs > 0) {

					} else {

					}

				} else {

				}
			} else {

			}
		}
	}

	private void calculateValue(String valorIngresado, String tipoMoneda) {
		Double faltaGs = FormatearValor
				.stringToDouble(!tfFaltanteGs.getText().isEmpty() ? tfFaltanteGs.getText() : "0");
		Double faltaUs = FormatearValor
				.stringToDouble(!tfFaltanteUs.getText().isEmpty() ? tfFaltanteUs.getText() : "0");
		Double faltaRs = FormatearValor
				.stringToDouble(!tfFaltanteRs.getText().isEmpty() ? tfFaltanteRs.getText() : "0");
		Double faltaPs = FormatearValor
				.stringToDouble(!tfFaltantePs.getText().isEmpty() ? tfFaltantePs.getText() : "0");

		Double recibidoGs = FormatearValor
				.stringToDouble(!tfRecibidoGs.getText().isEmpty() ? tfRecibidoGs.getText() : "0");
		Double recibidoUs = FormatearValor
				.stringToDouble(!tfRecibidoUs.getText().isEmpty() ? tfRecibidoUs.getText() : "0");
		Double recibidoRs = FormatearValor
				.stringToDouble(!tfRecibidoRs.getText().isEmpty() ? tfRecibidoRs.getText() : "0");
		Double recibidoPs = FormatearValor
				.stringToDouble(!tfRecibidoPs.getText().isEmpty() ? tfRecibidoPs.getText() : "0");

		Double vueltoGs = FormatearValor.stringToDouble(!tfVueltoGs.getText().isEmpty()?tfVueltoGs.getText():"0");
		Double vueltoUs = FormatearValor.stringToDouble(!tfVueltoUs.getText().isEmpty()?tfVueltoUs.getText():"0");
		Double vueltoRs = FormatearValor.stringToDouble(!tfVueltoRs.getText().isEmpty()?tfVueltoRs.getText():"0");
		Double vueltoPs = FormatearValor.stringToDouble(!tfVueltoPs.getText().isEmpty()?tfVueltoPs.getText():"0");;
		Double saldo=0d;
		//convertir todos los valores recibido a moneda Gs.
		
		//Sumar los valores recibidos
		
		//Sumar los valores faltantes y convertir a Gs.
		
		//Restar recibidos - faltantes
		
		if(tipoMoneda.equalsIgnoreCase("GS")) {
			if (recibidoGs >= faltaGs) {
				vueltoGs = recibidoGs - faltaGs;
				tfFaltanteGs.setText(FormatearValor.doubleAString(0d));
				tfVueltoGs.setText(FormatearValor.doubleAString(vueltoGs));
				// calcular el vuelto en varias monedas
				btnAceptar.requestFocus();
			} else {
				tfVueltoGs.setText(FormatearValor.doubleAString(0d));
				saldo=recibidoGs-faltaGs;
				tfFaltanteGs.setText(FormatearValor.doubleAString(saldo));
				faltaUs=saldo/cotUs;
				faltaRs=saldo/cotRs;
				faltaPs=saldo/cotPs;
			}
		}else {
			if(tipoMoneda.equalsIgnoreCase("US")) {
				if (recibidoUs >= faltaUs) {
					vueltoUs = recibidoUs - faltaUs;
					tfFaltanteUs.setText(FormatearValor.doubleAString(0d));
					tfVueltoUs.setText(FormatearValor.doubleAString(vueltoUs));
					btnAceptar.requestFocus();
				}else {
					saldo=recibidoUs-faltaUs;
					tfFaltanteUs.setText(FormatearValor.doubleAString(saldo));
					faltaUs=saldo/cotUs;
					faltaRs=saldo/cotRs;
					faltaPs=saldo/cotPs;
				}
			}else {
				if(tipoMoneda.equalsIgnoreCase("RS")) {
					if (recibidoRs >= faltaRs) {
						vueltoRs = recibidoRs - faltaRs;
						tfFaltanteRs.setText(FormatearValor.doubleAString(0d));
						tfFaltantePs.setText(FormatearValor.doubleAString(0d));
						tfVueltoRs.setText(FormatearValor.doubleAString(vueltoRs));
						btnAceptar.requestFocus();

						if (recibidoPs >= faltaPs) {
							vueltoPs = recibidoPs - faltaPs;
							tfVueltoRs.setText(FormatearValor.doubleAString(vueltoPs));
							btnAceptar.requestFocus();
						} 
					}
				}
			}
		}
	}

	private void saveMovimientoCaja(Venta venta) {
		MovimientoCaja mov = new MovimientoCaja();
		mov.setPlanCuentaId(1);
		mov.setTipoOperacion("E");
		mov.setCaja(venta.getCaja());
		mov.setFecha(venta.getFecha());
		mov.setIdReferencia(venta.getCliente().getId());
		mov.setNotaReferencia(venta.getClienteNombre());
		mov.setNotaNro(String.valueOf(venta.getId()));
		mov.setNotaValor(venta.getTotalGeneral());
		mov.setObs("PDV");

		mov.setValorM01(!tfRecibidoGs.getText().isEmpty() ? FormatearValor.stringToDouble(tfRecibidoGs.getText()) : 0);
		mov.setValorM02(!tfRecibidoUs.getText().isEmpty() ? FormatearValor.stringToDouble(tfRecibidoUs.getText()) : 0);
		mov.setValorM03(!tfRecibidoRs.getText().isEmpty() ? FormatearValor.stringToDouble(tfRecibidoRs.getText()) : 0);
		mov.setValorM04(!tfRecibidoPs.getText().isEmpty() ? FormatearValor.stringToDouble(tfRecibidoPs.getText()) : 0);

		MovimientoCaja movimientoCaja = pagoService.save(mov);

		if (movimientoCaja != null) {
			updateTransaction(movimientoCaja, venta.getCaja(), venta);
			Notifications.showAlert("VENTA REGISTRADO CON EXITO..!");

			ImpresionUtil.generaTicket(Fechas.formatoDDMMAAAA(venta.getFecha()), venta.getClienteNombre(),
					venta.getClienteRuc(), String.valueOf(venta.getId()), String.valueOf(venta.getCaja().getId()),
					String.valueOf(venta.getTotalGeneral()), tfRecibidoGs.getText(), tfVueltoGs.getText(),
					venta.getItems());

			clearForm();
		}
	}

	private void updateTransaction(MovimientoCaja movimientoCaja, Caja caja, Venta venta) {
		if (venta != null) {
			List<Producto> productos = new ArrayList<>();
			for (VentaDetalle e : venta.getItems()) {
				Optional<Producto> pOptional = productoService.findById(e.getProductoId());

				if (pOptional.isPresent()) {
					Producto p = pOptional.get();

					Double salidaPend = p.getSalidaPend() != null ? p.getSalidaPend() : 0;
					Double cantItem = e.getCantidad() != null ? e.getCantidad() : 1;

					p.setDepO1(salidaPend - cantItem);
					p.setSalidaPend(salidaPend - cantItem);

					// agregamos a la lista
					productos.add(p);
				}
			}

			// actualiza producto
			productoService.updateStock(productos);
		}
	}

	private void findClientById(Long id) {
		Optional<Cliente> cliente = clienteService.findById(id);

		if (cliente.isPresent()) {
			String nombre = cliente.get().getRazonSocial();
			String ciruc = cliente.get().getCiruc();
			String direccion = cliente.get().getDireccion();

			// lblRuc.setVisible(true);
			lblDireccion.setVisible(true);
			tfClienteRuc.setVisible(true);
			tfClienteDireccion.setVisible(true);

			tfClienteNombre.setText(nombre);
			tfClienteRuc.setText(ciruc);
			tfClienteDireccion.setText(direccion);

			if (cliente.get().getId() == 0) {
				// habilitar nombre, ruc, direccion
				tfClienteNombre.setEnabled(true);
				tfClienteRuc.setEnabled(true);
				tfClienteDireccion.setEnabled(true);
				tfClienteNombre.requestFocus();

//				tfCondPago.setEditable(false);
//				tfCondPago.setText("0");
//				calculateVencimiento();
			} else {
//				tfCajaID.requestFocus();
			}
		} else {
			Notifications.showAlert("No existe Cliente con el codigo informado.!");
		}
	}
}
