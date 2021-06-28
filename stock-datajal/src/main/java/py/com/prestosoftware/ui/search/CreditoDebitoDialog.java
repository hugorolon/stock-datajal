package py.com.prestosoftware.ui.search;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Cliente;
//import py.com.prestosoftware.data.models.CuentaCliente;
import py.com.prestosoftware.data.models.CuentaProveedor;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.ClienteService;
//import py.com.prestosoftware.domain.services.CuentaClienteService;
import py.com.prestosoftware.domain.services.CuentaProveedorService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.util.Notifications;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.Color;

@Component
public class CreditoDebitoDialog extends JFrame implements ClienteInterfaz, ProveedorInterfaz {
	
	private static final long serialVersionUID = 1L;
	
	private static final int CLIENTE_CODE = 3;
	private static final int PROVEEDOR_CODE = 6;
	private static final String PENDIENTE = "PENDIENTE";
	
	private static final int TB_COL_SALDO = 5;
	private static final int TB_COL_VALOR = 6;
	
	private JTextField tfNombre;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
//	private CuentaClienteInterfaz cuentaClienteInterfaz;
	private CuentaProveedorInterfaz cuentaProveedorInterfaz;
	
	//compras
	private CuentaProveedorService pService;
	//private CuentaProveedorTableModel pTableModel;
	private List<CuentaProveedor> proveedorCuentas;
	
	//ventas
//	private CuentaClienteService cService;
	//private CuentaClienteTableModel tableModel;
//	private List<CuentaCliente> clienteCuentas;
	
	//DefaultTableModel
	private DefaultTableModel dtmTable = new DefaultTableModel(null, 
		new String[] { "ID", "DOC.", "FECHA VENC.", "TOTAL NOTA", "TOTAL PAGADO", "SALDO", "VALOR A PAGAR"});
	
	
	//dialogs
	private ConsultaCliente clienteDialog;
	private ConsultaProveedor proveedorDialog;
	private ClienteService clienteService;
	private ProveedorService proveedorService;
	
	private boolean isClient;
	
	private JLabel lblMonto;
	private JTextField tfMonto;
	private JTextField tfCodigo;
	private JPanel pnlBotones;
	private JPanel panel;
	private JTextField tfTtotal;
	private JTextField tfTotalPagado;
	private JTextField tfTotalSaldo;
	private JTextField tfTotalPago;

	@Autowired
	public CreditoDebitoDialog(
//			CuentaClienteService cService, 
			CuentaProveedorService pService,
			ConsultaCliente clienteDialog, ConsultaProveedor proveedorDialog,
			ClienteService clienteService, ProveedorService proveedorService) {
//		this.cService = cService;
		this.pService = pService;
		this.clienteDialog = clienteDialog;
		this.proveedorDialog = proveedorDialog;
		this.clienteService = clienteService;
		this.proveedorService = proveedorService;
		
		initializeUI();
	}
	
	@SuppressWarnings("serial")
	private void initializeUI() {
		setTitle("CONSULTAS DE DEBITOS/CREDITOS");
		setSize(700, 350);
		//setModal(true);
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, BorderLayout.NORTH);
		pnlBuscador.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblBuscador = new JLabel("Codigo:");
		pnlBuscador.add(lblBuscador);
		
		tfNombre = new JTextField();
		tfNombre.setEditable(false);
		((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfNombre.getText().isEmpty()) {
						tfMonto.requestFocus();
					} else {
						if (isClient())
							showDialog(CLIENTE_CODE);
						else
							showDialog(PROVEEDOR_CODE);
					}
				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			    	dispose();
			    } else if(e.getKeyCode()==KeyEvent.VK_DOWN){
			    	table.requestFocus();
			    }
			}
		});
		
		tfCodigo = new JTextField();
		tfCodigo.setColumns(5);
		tfCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (isClient()) {
						if (tfCodigo.getText().isEmpty())
							showDialog(CLIENTE_CODE);
						else
							findByCodigo(Long.valueOf(tfCodigo.getText()), true);
					} else {
						if (tfCodigo.getText().isEmpty())
							showDialog(PROVEEDOR_CODE);
						else
							findByCodigo(Long.valueOf(tfCodigo.getText()), false);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_F11) {
					if (isClient())
						showDialog(CLIENTE_CODE);
					else
						showDialog(PROVEEDOR_CODE);
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfCodigo.setText("");
		pnlBuscador.add(tfCodigo);
		pnlBuscador.add(tfNombre);
		tfNombre.setColumns(10);
		
		lblMonto = new JLabel("Monto:");
		pnlBuscador.add(lblMonto);
		
		tfMonto = new JTextField();
		tfMonto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfMonto.selectAll();
			}
		});
		tfMonto.setText("0");
		tfMonto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (tfMonto.getText().isEmpty()) {
						loadFacturas(false);
					} else { // distribuir
						loadFacturas(true);
					}
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		pnlBuscador.add(tfMonto);
		tfMonto.setColumns(10);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable(dtmTable) {
	        public boolean isCellEditable(int fila, int columna) {
	            if (columna==6){
	            	return true;
	            } else {
	            	return false;
	            }
	        }
	    };
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				calcularValor();
			}
		});
		
		setupTable();
		
		scrollPane.setViewportView(table);
		
		JPanel pnlFooter = new JPanel();
		getContentPane().add(pnlFooter, BorderLayout.SOUTH);
		pnlFooter.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		pnlFooter.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 5));
		
		tfTtotal = new JTextField();
		tfTtotal.setEditable(false);
		tfTtotal.setFont(new Font("Verdana", Font.BOLD, 14));
		tfTtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTtotal.setText("");
		panel.add(tfTtotal);
		tfTtotal.setColumns(7);
		
		tfTotalPagado = new JTextField();
		tfTotalPagado.setEditable(false);
		tfTotalPagado.setFont(new Font("Verdana", Font.BOLD, 14));
		tfTotalPagado.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalPagado.setText("");
		panel.add(tfTotalPagado);
		tfTotalPagado.setColumns(7);
		
		tfTotalSaldo = new JTextField();
		tfTotalSaldo.setEditable(false);
		tfTotalSaldo.setForeground(Color.RED);
		tfTotalSaldo.setFont(new Font("Verdana", Font.BOLD, 14));
		tfTotalSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalSaldo.setText("");
		panel.add(tfTotalSaldo);
		tfTotalSaldo.setColumns(7);
		
		tfTotalPago = new JTextField();
		tfTotalPago.setEditable(false);
		tfTotalPago.setForeground(Color.GREEN);
		tfTotalPago.setFont(new Font("Verdana", Font.BOLD, 14));
		tfTotalPago.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTotalPago.setText("");
		panel.add(tfTotalPago);
		tfTotalPago.setColumns(7);
		
		pnlBotones = new JPanel();
		pnlFooter.add(pnlBotones, BorderLayout.CENTER);
		
		btnAceptar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("ClienteDialog.btnAceptar.text")); //$NON-NLS-1$ //$NON-NLS-2$
		pnlBotones.add(btnAceptar);
		btnAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					aceptar();
				}
			}
		});
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptar();
			}
		});
		
		btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.search.messages").getString("ClienteDialog.btnCancelar.text")); //$NON-NLS-1$ //$NON-NLS-2$
		pnlBotones.add(btnCancelar);
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
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}
	
	public void setShowInLanzamiento() {
		lblMonto.setVisible(true);
		tfMonto.setVisible(true);
	}
	
	private void loadFacturas(boolean distribuirValor) {
		clearTable();
		
		if (isClient()) {
//			clienteCuentas = cService.findPendientes(new Cliente(Long.valueOf(tfCodigo.getText())), PENDIENTE);
//			loadTable(clienteCuentas, null);
		} else {
			proveedorCuentas = pService.findPendientes(new Proveedor(Long.valueOf(tfCodigo.getText())), PENDIENTE);
			//loadTable(null, proveedorCuentas);
		}
	
		calculateTotals();
		
		if (distribuirValor) distribuirValorEnTabla();
		
		//Util.forzarFocoGrilla(table, 0, 6);
    }
	
	private void clearTable() {
		while (dtmTable.getRowCount() > 0) {
			dtmTable.removeRow(0);
		}
	}
	
	//private void loadTable(
			//List<CuentaCliente> cclientes, 
//			List<CuentaProveedor> ccproveedores) {
//		//if (cclientes != null && cclientes.size() > 0) {
//			for (CuentaProveedor e : ccproveedores) {
//				Double valorTotal = e.getValorTotal() != null ?  e.getValorTotal():0;
//				Double valorPagado = e.getValorPagado() != null ? e.getValorPagado():0;
//				Double saldo = valorTotal - valorPagado;
//				
//				loadItemTable(e.getId(), e.getDocumento(), e.getVencimiento(), 
//						FormatearValor.formatearValor(valorTotal), 
//						FormatearValor.formatearValor(valorPagado), 
//						FormatearValor.formatearValor(saldo), 
//						FormatearValor.formatearValor(0d));				
//			}
//		} else 
//			if (ccproveedores != null && ccproveedores.size() > 0) {
//			for (CuentaProveedor e : ccproveedores) {
//				Double valorTotal 	= e.getValorTotal() != null ? e.getValorTotal():0;
//				Double valorPagado 	= e.getValorPagado() != null ? e.getValorPagado():0;
//				Double saldo 		= valorTotal - valorPagado;
//				
//				loadItemTable(e.getId(), e.getDocumento(), e.getVencimiento(), 
//						FormatearValor.formatearValor(valorTotal), 
//						FormatearValor.formatearValor(valorPagado), 
//						FormatearValor.formatearValor(saldo), 
//						FormatearValor.formatearValor(0d));				
//			}
//		}
	//}
	
	private void distribuirValorEnTabla() {
		String montoAPagar = tfMonto.getText().isEmpty() ? "0" : tfMonto.getText();
		
		if (montoAPagar.equals("0")) return;
		
		Double valorMonto = Double.valueOf(montoAPagar); //FormatearValor.stringADoubleFormat(montoAPagar);
		
		for (Integer fila=0; fila < dtmTable.getRowCount(); fila++) {
			String totalS = String.valueOf(table.getValueAt(fila, 4));
			Double totalSaldo = FormatearValor.stringADoubleFormat(totalS);
			
			if (valorMonto >= totalSaldo) {
				table.setValueAt(valorMonto - totalSaldo, fila, 5);
				valorMonto = valorMonto - totalSaldo;
			} else {
				table.setValueAt(valorMonto, fila, 5);
				return;
			}
		}
	}
	
	private void loadItemTable(Long id, String doc, Date fecha, String valorTotal,
			String valorPagado, String valorSaldo, String valorAPagar) {
		
		Object[] campos = { 
				id,
				doc, 
				Fechas.formatoDDMMAAAA(fecha), 
				valorTotal, 
				valorPagado, 
				valorSaldo, 
				valorAPagar	
		};
		
		dtmTable.addRow(campos);
	}

	private void setupTable() {
		int[] columns = { 0, 1, 2, 3, 4, 5, 6 };
		int[] Tamanhocolumns = { 100, 100, 100, 100 };
		
		Util.ocultarColumna(table, 0);
		Util.tipoColumna(table, 6, columns);
		Util.tamanhoTablaFijo(table, 3, columns, Tamanhocolumns);
		table.setRowHeight(25);
	}
	
	public void clearForm() {
		tfCodigo.setText("");
		tfMonto.setText("");
		tfNombre.setText("");
		tfTtotal.setText("0");
		tfTotalPagado.setText("0");
		tfTotalPago.setText("0");
		tfTotalSaldo.setText("0");
		
		clearTable();
	}
	
	private void aceptar() {
		if (isClient()) {
//			CuentaCliente cc = new CuentaCliente();
//			List<CuentaCliente> cuentas = new ArrayList<CuentaCliente>();
			
			for(Integer fila=0; fila< dtmTable.getRowCount(); fila++){
				String valor = String.valueOf(table.getValueAt(fila, TB_COL_VALOR));
				String documento = String.valueOf(table.getValueAt(fila, 1));
				
				if (!valor.equals("")) {
					if (tfCodigo.getText().isEmpty()) {
						Notifications.showAlert("Cliente debe ser seleccionado.!");
						tfCodigo.requestFocus();
						return;
					}
					
					Double valorPagado = FormatearValor.stringADouble(valor);
					Double valorTotal = 0d;
					
//					cuentas.add(new CuentaCliente(new Cliente(Long.valueOf(tfCodigo.getText())), 
//							tfNombre.getText(), "CAJA", documento, new Date(), 0d, 
//							valorPagado, new Date(), new Moneda(GlobalVars.BASE_MONEDA_ID),
//							"", "", GlobalVars.USER_ID, 
//							valorTotal, valorPagado));
				}
			}
			
			//cc.setCuentas(cuentas);
			
			//cuentaClienteInterfaz.getEntity(cc);
		} else {
			CuentaProveedor cp = new CuentaProveedor();
			List<CuentaProveedor> cuentas = new ArrayList<CuentaProveedor>();
			
			for(Integer fila=0; fila< dtmTable.getRowCount(); fila++){
				String valor = String.valueOf(table.getValueAt(fila, TB_COL_VALOR));
				String documento = String.valueOf(table.getValueAt(fila, 1));
				
				//String id = String.valueOf(table.getValueAt(fila, 1));
				
				if (!valor.equals("")) {
					if (tfCodigo.getText().isEmpty()) {
						Notifications.showAlert("Proveedor debe ser seleccionado.!");
						tfCodigo.requestFocus();
						return;
					}
					
					Double valorPagado = FormatearValor.stringADouble(valor);
					Double valorTotal = 0d;
					
					cuentas.add(new CuentaProveedor(new Proveedor(Long.valueOf(tfCodigo.getText())), 
							tfNombre.getText(), documento, "CAJA", new Date(), new Date(), 
							valorPagado, 0d, new Date(), new Moneda(GlobalVars.BASE_MONEDA_ID),
							"", "", GlobalVars.USER_ID, 
							valorTotal, valorPagado));
				}
			}
			
			cp.setCuentas(cuentas);
			cuentaProveedorInterfaz.getEntity(cp);
		}
		
		dispose();
	}
	
	private void showDialog(int code) {
		switch (code) {
			case CLIENTE_CODE:
				clienteDialog.setInterfaz(this);
				clienteDialog.setVisible(true);
				break;
			case PROVEEDOR_CODE:
				proveedorDialog.setInterfaz(this);
				proveedorDialog.setVisible(true);
				break;
			default:
				break;
		}
	}
	
	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}
	
	public boolean isClient() {
		return isClient;
	}

//	public CuentaClienteInterfaz getCuentaClienteInterfaz() {
//		return cuentaClienteInterfaz;
//	}
//
//	public void setCuentaClienteInterfaz(CuentaClienteInterfaz cuentaClienteInterfaz) {
//		this.cuentaClienteInterfaz = cuentaClienteInterfaz;
//	}

	public CuentaProveedorInterfaz getCuentaProveedorInterfaz() {
		return cuentaProveedorInterfaz;
	}

	public void setCuentaProveedorInterfaz(CuentaProveedorInterfaz cuentaProveedorInterfaz) {
		this.cuentaProveedorInterfaz = cuentaProveedorInterfaz;
	}

	private void findByCodigo(Long codigo, boolean esCliente) {
		if (esCliente) {
			Optional<Cliente> cliente = clienteService.findById(codigo);
			if (cliente.isPresent()) {
				setCliente(String.valueOf(cliente.get().getId()), cliente.get().getNombre());
			} else {
				Notifications.showAlert("No se encuentra Cliente con este codigo");
			}
		} else {
			Optional<Proveedor> proveedor = proveedorService.findById(codigo);
			if (proveedor.isPresent()) {
				setProveedor(String.valueOf(proveedor.get().getId()), proveedor.get().getNombre());
			} else {
				Notifications.showAlert("No se encuentra Proveedor con este codigo");
			}
		}
	}
	
	private void calculateTotals() {
		Double totalValor 	= 0d;
		Double totalPagado 	= 0d;
		
		for (Integer fila=0; fila < dtmTable.getRowCount(); fila++) {
			String total = String.valueOf(table.getValueAt(fila, 3));
			String totalP = String.valueOf(table.getValueAt(fila, 4));
			
			totalValor 	+= Double.valueOf(total); 	//FormatearValor.stringADoubleFormat(total)
			totalPagado += Double.valueOf(totalP); 	//FormatearValor.stringADoubleFormat(totalP);
		}
		
		tfTtotal.setText(FormatearValor.doubleAString(totalValor)); 
		tfTotalPagado.setText(FormatearValor.doubleAString(totalPagado));
		tfTotalSaldo.setText(FormatearValor.doubleAString(totalValor - totalPagado));
	}
	
	private void calcularValor() {
		if (!table.isEditing() && table.editCellAt(table.getSelectedRow(), table.getSelectedColumn())) {
			table.getEditorComponent().requestFocusInWindow(); 
        }
		
		int cantidadFilas = table.getRowCount();
        int fila = table.getSelectedRow();
  
        if (table.getSelectedColumn()==6) {
        	Double valorSaldo = Double.valueOf(String.valueOf(table.getValueAt(fila-1, TB_COL_SALDO)));
        	String valorAPagar = String.valueOf(table.getValueAt(fila-1, TB_COL_VALOR));
        	
        	if (!valorAPagar.isEmpty()) {
        		Double valorDigitado = Double.valueOf(valorAPagar);
        		
        		if (valorDigitado > valorSaldo) {
        			Notifications.showAlert("El valor a pagar no puede ser mayor al saldo");
        			table.setValueAt(valorSaldo, fila-1, TB_COL_VALOR);
        			Util.forzarFocoGrilla(table, cantidadFilas-1, TB_COL_VALOR);
        			return;
        		}
        		
        		if (isClient()) 
            		table.setValueAt(valorAPagar, fila-1, TB_COL_VALOR);
    			else
    				table.setValueAt(valorAPagar, fila-1, TB_COL_VALOR);
        	}
        	
	        Util.forzarFocoGrilla(table, cantidadFilas-1, TB_COL_VALOR);
	        
        }
        
        if (fila==0) {//termina la grilla
        	System.out.println("termino la fila");
			if (table.getSelectedColumn()==2){//simboliza a la ultima columna de seleccion, sabra Dios xq...
				System.out.println("paso aqui");
				btnAceptar.requestFocus();
			}
			
			calcularTotalSaldo();
			
			
			//btnAceptar.requestFocus();
        }
        
		if (fila == 0) {//termina la grilla
			if (table.getSelectedColumn()==2) {//simboliza a la ultima columna de seleccion, sabra Dios xq...
//				if(VariablesGlobales.esSeleccionado==true){
//	            	VariablesGlobales.esSeleccionado=false;
//	            	BigDecimal saldoCuota=FormatearValor.stringABigDecimal(String.valueOf(jTable.getValueAt(cantidadFilas-1, 6)));
//		          	
//	            	if (saldoCuota.compareTo(totalAPagar)>0){
//		          		saldoCuota=totalAPagar;
//		          	} 
//		          	if(totalAPagar.compareTo(BigDecimal.ZERO)>0){
//		          		String saldoCuotaString=FormatearValor.bigDecimalAStringDosDecimales(saldoCuota);
//		          		jTable.setValueAt(saldoCuotaString, cantidadFilas-1, 7);
//		          	}
//		          	if(VariablesGlobales.salirGrilla==true){//el . activa tambien la seleccion, x eso esta aqui ubicado el if
//		          		jTable.setValueAt(BigDecimal.ZERO, cantidadFilas-1, 7);
//		        		bGuardar.requestFocus();
//		        	}else{
//		                forzarFocoGrilla(cantidadFilas-1, 7);
//		        	}
//	            }else{
//	            	jTable.setValueAt(BigDecimal.ZERO, cantidadFilas-1, 7);
//	            	bGuardar.requestFocus();
//	            }
//			} else if (table.getSelectedColumn()==0) { //simboliza que esta en la ultima columna, que rayos paso aqui?
//				BigDecimal pago=FormatearValor.stringABigDecimal(String.valueOf(jTable.getValueAt(cantidadFilas-1, 7)));
//				pago=pago.setScale(monedaDecimales,RoundingMode.HALF_UP);//valida los decimales de la moneda usada
//				String pagoString=FormatearValor.bigDecimalAStringDosDecimales(pago);
//	        	jTable.setValueAt(pagoString, cantidadFilas-1, 7);//escribe el nuevo valor
//				
//	        	if(pago.compareTo(BigDecimal.ZERO)<=0){
//					jTable.setValueAt("", cantidadFilas-1, 1);
//				}
//	        	
//				bGuardar.requestFocus();
		    }
			
//			calcularTotalSaldo();	//vuelve a calcular los totales
		}
	}
	
	private void calcularTotalSaldo() {
		Double totalMontoAcumulado = !tfTotalPago.getText().isEmpty() ?
			FormatearValor.stringADouble(tfTotalPago.getText()) : 0;
			
		Double totalMontoSaldo = !tfTotalSaldo.getText().isEmpty() ?
					FormatearValor.stringADouble(tfTotalSaldo.getText()) : 0;
		
		for (Integer fila=0; fila < table.getRowCount();fila++) {
			String aux = String.valueOf(table.getValueAt(fila, TB_COL_VALOR));
			
			if (!aux.equals("") || !aux.equals("0")) {
				Double montoPago 	= FormatearValor.stringADouble(aux);
				totalMontoAcumulado = totalMontoAcumulado + montoPago;
				totalMontoSaldo 	= totalMontoSaldo - montoPago;
			}
		}
		
		tfTotalSaldo.setText(FormatearValor.doubleAString(totalMontoSaldo));
		tfTotalPago.setText(FormatearValor.doubleAString(totalMontoAcumulado));
	}
	
	private void setCliente(String cod, String cliente) {
		tfCodigo.setText(cod);
		tfNombre.setText(cliente);
		tfMonto.requestFocus();
		tfMonto.setText("0");
	}
	
	private void setProveedor(String cod, String proveedor) {
		tfCodigo.setText(cod);
		tfNombre.setText(proveedor);
		tfMonto.requestFocus();
		tfMonto.setText("0");
	}

	@Override
	public void getEntity(Proveedor p) {
		if (p != null) {
			setProveedor(String.valueOf(p.getId()), p.getNombre());
		}
	}

	@Override
	public void getEntity(Cliente c) {
		if (c != null) {
			setCliente(String.valueOf(c.getId()), c.getNombre());
		}
	}

}