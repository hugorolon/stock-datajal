package py.com.prestosoftware.ui.transactions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.CuentaCliente;
import py.com.prestosoftware.data.models.CuentaProveedor;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.CuentaClienteService;
import py.com.prestosoftware.domain.services.CuentaProveedorService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.ConsultaCliente;
import py.com.prestosoftware.ui.search.ConsultaProveedor;
import py.com.prestosoftware.ui.search.ProveedorInterfaz;
import py.com.prestosoftware.util.Notifications;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import java.awt.Font;
import java.util.Date;
import java.util.Optional;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class AjusteCuentaDebitoPanel extends JDialog implements ClienteInterfaz, ProveedorInterfaz {

	private static final long serialVersionUID = 1L;
	
	private static final int CLIENTE_CODE = 1;
	private static final int PROVEEDOR_CODE = 2;
    private JTextField tfNombre;
    private JTextField tfDocumento;
    private JTextField tfCodigo;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
 
    private CuentaClienteService cService;
    private CuentaProveedorService pService;
    private ClienteService clienteService;
    private ProveedorService proveedorService;
    private ConsultaCliente clienteDialog;
    private ConsultaProveedor proveedorDialog;

    @Autowired
    public AjusteCuentaDebitoPanel(CuentaClienteService cService, CuentaProveedorService pService,
    		ConsultaCliente depositoDialog, ConsultaProveedor proveedorDialog,
    		ClienteService clienteService, ProveedorService proveedorService) {
    	this.cService = cService;
    	this.pService = pService;
    	this.clienteDialog = depositoDialog;
    	this.proveedorDialog = proveedorDialog;
    	this.clienteService = clienteService;
    	this.proveedorService = proveedorService;
    	
    	setSize(600, 350);
    	setTitle("AJUSTE DE CUENTAS DÉBITO");
    	setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);
        
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setBounds(6, 282, 588, 40);
        getContentPane().add(panel);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (validateFecha() && validateValues() && validateCodigo()) {
        				save();
					} else {
						Notifications.showAlert("Campos Obligatorios. Verifique los datos ingresados.");
					}
				}
        	}
        });
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (validateFecha() && validateValues()) {
    				save();
				} else {
					Notifications.showAlert("Campos Obligatorios. Verifique los datos ingresados.");
				}
        	}
        });
        panel.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			clearForm();
				}
        	}
        });
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clearForm();
        	}
        });
        panel.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			dispose();
				}
        	}
        });
        btnCerrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        panel.add(btnCerrar);
        
        lblHistorico = new JLabel("HISTORICO");
        lblHistorico.setBounds(6, 240, 90, 30);
        getContentPane().add(lblHistorico);
        
        tfHistorico = new JTextField();
        ((AbstractDocument) tfHistorico.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfHistorico.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfHistorico.selectAll();
        	}
        });
        tfHistorico.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        tfHistorico.setFont(new Font("Arial", Font.PLAIN, 14));
        tfHistorico.setColumns(10);
        tfHistorico.setBounds(104, 240, 490, 30);
        getContentPane().add(tfHistorico);
        
        panel_1 = new JPanel();
        panel_1.setBounds(6, 6, 588, 211);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        lblValor = new JLabel("VALOR");
        lblValor.setBounds(234, 81, 90, 30);
        panel_1.add(lblValor);
        
        tfValor = new JTextField();
        tfValor.setBounds(321, 81, 102, 30);
        panel_1.add(tfValor);
        tfValor.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfValor.selectAll();
        	}
        });
        tfValor.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfHistorico.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfValor.setToolTipText("");
        tfValor.setColumns(10);
        
        tfVencimiento = new JFormattedTextField(getFormatoFecha());
        tfVencimiento.setBounds(104, 155, 102, 30);
        panel_1.add(tfVencimiento);
        tfVencimiento.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfVencimiento.selectAll();
        	}
        });
        tfVencimiento.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (!validateFecha()) {
						Notifications.showAlert("Vencimiento debe ser mayor o igual a Fecha.!");
					} else {
						tfValor.requestFocus();
					}
				}
        	}
        });
        tfVencimiento.setText(Fechas.formatoDDMMAAAA(new Date()));
        tfVencimiento.setFont(new Font("Arial", Font.PLAIN, 14));
        tfVencimiento.setColumns(10);
        
        tfFecha = new JFormattedTextField(getFormatoFecha());
        tfFecha.setBounds(104, 118, 102, 30);
        panel_1.add(tfFecha);
        tfFecha.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfFecha.selectAll();
        	}
        });
        tfFecha.setText(Fechas.formatoDDMMAAAA(new Date()));
        tfFecha.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfVencimiento.requestFocus();
				}
        	}
        });
        tfFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        tfFecha.setColumns(10);
        
        lblValorAPagar = new JLabel("VENCIMIENTO");
        lblValorAPagar.setBounds(6, 155, 90, 30);
        panel_1.add(lblValorAPagar);
        
        lblPrecio = new JLabel("FECHA");
        lblPrecio.setBounds(6, 118, 90, 30);
        panel_1.add(lblPrecio);
        
        JLabel lblCantidad = new JLabel("CODIGO");
        lblCantidad.setBounds(6, 81, 90, 30);
        panel_1.add(lblCantidad);
        
        tfCodigo = new JTextField();
        tfCodigo.setBounds(104, 81, 102, 30);
        panel_1.add(tfCodigo);
        tfCodigo.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfCodigo.selectAll();
        	}
        });
        tfCodigo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
        			if (!tfCodigo.getText().isEmpty()) {
        				Long id = Long.valueOf(tfCodigo.getText());
        		
        				if (tipoCuenta.equals("CLIENTE")) {
        					findClienteById(id);
        				} else {
        					findProveedorById(id);
        				}
        				
        				tfFecha.requestFocus();
					} else {
						openDialog();
					}
        		}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCodigo.setFont(new Font("Arial", Font.PLAIN, 14));
        tfCodigo.setColumns(10);
        
        tfDocumento = new JTextField();
        tfDocumento.setBounds(104, 44, 102, 30);
        panel_1.add(tfDocumento);
        tfDocumento.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			//lblDocFinal.setText(tfDocumento.getText() + "/" + tfNota.getText());
					tfCodigo.requestFocus();
				}
        	}
        });
        tfDocumento.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfDocumento.selectAll();
        	}
        });
        tfDocumento.setFont(new Font("Arial", Font.PLAIN, 14));
        tfDocumento.setColumns(10);
        
        JLabel lblCodigo = new JLabel("DOCUMENTO");
        lblCodigo.setBounds(6, 44, 90, 30);
        panel_1.add(lblCodigo);
        
        lblNota = new JLabel("NOTA");
        lblNota.setBounds(6, 7, 90, 30);
        panel_1.add(lblNota);
        
        tfNota = new JTextField();
        tfNota.setBounds(104, 7, 102, 30);
        panel_1.add(tfNota);
        tfNota.setEditable(false);
        tfNota.setToolTipText("");
        tfNota.setColumns(10);
        
        JLabel lblDescripcion = new JLabel("NOMBRE");
        lblDescripcion.setBounds(234, 44, 90, 30);
        panel_1.add(lblDescripcion);
        
        tfNombre = new JTextField();
        tfNombre.setBounds(321, 44, 261, 30);
        panel_1.add(tfNombre);
        tfNombre.setEditable(false);
        tfNombre.setFont(new Font("Arial", Font.PLAIN, 14));
        tfNombre.setColumns(10);
    }
    
    private MaskFormatter formatoFecha;
    
    private MaskFormatter getFormatoFecha() {
		try {
			if (formatoFecha == null) {
				formatoFecha = new MaskFormatter("##/##/####");
				formatoFecha.setPlaceholderCharacter('_');
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return formatoFecha;
	}
    
    public void validatePrecioCosto(String tipo) {
    	if (tipo.equals("CLIENTE")) {
			setTitle("AJUSTE DE DEBITO CLIENTE");
		} else {
			setTitle("AJUSTE DE DEBITO PROVEEDOR");
		}
    }
    
    private void openDialog() {
    	if (tipoCuenta.equals("CLIENTE")) {
			showDialog(CLIENTE_CODE);
		} else {
			showDialog(PROVEEDOR_CODE);
		}
	}
    
    private void findClienteById(Long id) {
    	Optional<Cliente> cliente = clienteService.findById(id);
    	
    	if (cliente.isPresent()) {
    		tfNombre.setText(cliente.get().getNombre());
		}
    }
    
    private void findProveedorById(Long id) {
    	Optional<Proveedor> proveedor = proveedorService.findById(id);
    	
    	if (proveedor.isPresent()) {
    		tfNombre.setText(proveedor.get().getNombre());
		}
    }
    
    private boolean validateValues() {
    	boolean result = true;
    	
    	if (tfDocumento.getText().isEmpty()) {
    		Notifications.showAlert("Documento es Obligatorio.!");
    		tfDocumento.requestFocus();
			result = false;
		} else if (tfCodigo.getText().isEmpty()) {
			Notifications.showAlert("Codigo es Obligatorio.!");
			tfCodigo.requestFocus();
			result = false;
		} else if (tfFecha.getText().isEmpty()) {
			Notifications.showAlert("Fecha es Obligatorio.!");
			tfFecha.requestFocus();
			result = false;
		} else if (tfVencimiento.getText().isEmpty()) {
			Notifications.showAlert("Vencimiento es Obligatorio.!");
			tfVencimiento.requestFocus();
			result = false;
		} else if (tfValor.getText().isEmpty()) {
			Notifications.showAlert("Valor es Obligatorio.!");
			tfValor.requestFocus();
			result = false;
		}
    	
    	return result;
    }
   
    private JLabel lblNota;
    private JTextField tfNota;
    private JLabel lblPrecio;
    private JTextField tfFecha;
    
    private CuentaProveedor getProveedorValue() {
    	CuentaProveedor cp = new CuentaProveedor();
    	
    	cp.setProveedor(new Proveedor(Long.valueOf(tfCodigo.getText())));
    	cp.setProveedorNombre(tfNombre.getText());
    	cp.setTipo("AJUSTES");
    	cp.setFecha(Fechas.stringDDMMAAAAADateSQL(tfFecha.getText()));
    	cp.setVencimiento(Fechas.stringDDMMAAAAADateUtil(tfVencimiento.getText()));
    	//cp.setDocumento(lblDocFinal.getText());
    	cp.setHora(new Date());
    	cp.setUsuarioId(GlobalVars.USER_ID);
    	cp.setObs(tfHistorico.getText());
    	cp.setDebito(FormatearValor.stringADouble(tfValor.getText()));
    	
    	return cp;
    }

    public CuentaCliente getClienteValue() {
    	CuentaCliente cc = new CuentaCliente();
        
    	cc.setCliente(new Cliente(Long.valueOf(tfCodigo.getText())));
    	cc.setClienteNombre(tfNombre.getText());
    	cc.setTipo("AJUSTES");
    	cc.setFecha(Fechas.stringDDMMAAAAADateSQL(tfFecha.getText()));
    	cc.setVencimiento(Fechas.stringDDMMAAAAADateUtil(tfVencimiento.getText()));
    	//cc.setDocumento(lblDocFinal.getText());
    	cc.setHora(new Date());
    	cc.setUsuarioId(GlobalVars.USER_ID);
    	cc.setObs(tfHistorico.getText());
    	cc.setDebito(FormatearValor.stringADouble(tfValor.getText()));
    	
        return cc;
    }
    
    public void clearForm() {
    	tfDocumento.setText("");
    	tfCodigo.setText("");
    	tfNombre.setText("");
    	tfFecha.setText("");
    	tfVencimiento.setText("");
    	tfValor.setText("");
    	tfHistorico.setText("");
    	//lblDocFinal.setText("");
    	
    	tfFecha.setText(Fechas.formatoDDMMAAAA(new Date()));
    	tfVencimiento.setText(Fechas.formatoDDMMAAAA(new Date()));
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
    
    private void save() {
    	Integer respuesta = JOptionPane.showConfirmDialog(null, "CONFIRMA");
		if (respuesta == 0) {
			if (tipoCuenta.equals("CLIENTE")) {
	    		CuentaCliente t = getClienteValue();
	    		cService.save(t);
	    	} else {
	    		CuentaProveedor t = getProveedorValue();
	    		pService.save(t);
	    	} 
	    	
	    	Notifications.showAlert("Ajuste de Cuenta realizado con exito.!");
			clearForm();
			newAjusteCuenta();
		} else {
			tfDocumento.requestFocus();
		}
    }
    
    public void newAjusteCuenta() {
    	if (tipoCuenta.equals("CLIENTE")) {
    		long max = cService.addNew();
    		tfNota.setText(String.valueOf(max + 1));
    	} else {
    		long max = pService.addNew();
    		tfNota.setText(String.valueOf(max + 1));
    	}
    	
    	tfDocumento.requestFocus();
	}
    
    private String tipoCuenta = "";
    private JTextField tfVencimiento;
    private JLabel lblValorAPagar;
    private JLabel lblValor;
    private JTextField tfValor;
    private JLabel lblHistorico;
    private JTextField tfHistorico;
    private JPanel panel_1;
    
    public void setTipoCuenta(String tipo) {
    	this.tipoCuenta = tipo;
    }
    
    private boolean validateCodigo() {
    	boolean result = true;
    	
    	Long id = Long.valueOf(tfCodigo.getText());
    	
    	if (tipoCuenta.equals("CLIENTE")) {
    		Optional<Cliente> cliente = clienteService.findById(id);
        	
        	if (!cliente.isPresent()) {
        		tfCodigo.requestFocus();
        		Notifications.showAlert("Codigo del Cliente, no existe.!");
        		result = false;
    		}
    	} else {
    		Optional<Proveedor> proveedor = proveedorService.findById(id);
        	
        	if (!proveedor.isPresent()) {
        		tfCodigo.requestFocus();
        		Notifications.showAlert("Codigo del Proveedor, no existe.!");
        		result = false;
    		}	
    	} 
    	
    	return result;
    }
    
    private boolean validateFecha() {
    	boolean result = true;
    	int dif = Fechas.diferenciaFechasDias(tfFecha.getText(), tfVencimiento.getText());
    	
    	if (dif < 0) {
			result = false;
		}
    	
    	return result;
    }

	@Override
	public void getEntity(Cliente c) {
		tfCodigo.setText(String.valueOf(c.getId()));
		tfNombre.setText(c.getNombre());
		tfFecha.requestFocus();
	}

	@Override
	public void getEntity(Proveedor p) {
		tfCodigo.setText(String.valueOf(p.getId()));
		tfNombre.setText(p.getNombre());
		tfFecha.requestFocus();
	}
}
