package py.com.prestosoftware.ui.forms;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.table.CiudadComboBoxModel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.ui.table.ProveedorTableModel;
import py.com.prestosoftware.util.Notifications;
import javax.swing.JTabbedPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.AbstractDocument;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

@Component
public class ProveedorPanel extends JDialog {

	private static final long serialVersionUID = 1L;
   
    private JLabel lblEmail, lblCelular, lblTelefono, lblWeb, lblFax;
    private JLabel lblBuscador, lblContacto, lblCodigo, lblSkype;
    private JTextField tfCelular, tfTelefono, tfFax;
    private JTextField tfWeb, tfSkype, tfContacto, tfBuscador;
    private JTextField tfNombre, tfRazonSocial, tfCiruc, tfDvRuc, tfDireccion;
    private JTextField tfProveedorId, tfPlazo, tfClase, tfEmail;
    private JButton btnGuardar, btnCancelar, btnCerrar, btnBuscar;
    private JTable tbProveedor;
    private JPanel pnlContacto;
    private JScrollPane scrollProveedor;
    
    private JComboBox<Ciudad> cbCiudad;
    private JComboBox<Empresa> cbEmpresa;
	private JComboBox<String> cbTipo;
    
    private CiudadComboBoxModel ciudadComboBoxModel;
    private EmpresaComboBoxModel empresaComboBoxModel;
    private ProveedorTableModel proveedorTableModel;
    private JLabel label;
    private JLabel label_1;
    private JLabel label_2;
    private JLabel label_3;
    private JLabel label_4;
    private JLabel label_5;

    @Autowired
    public ProveedorPanel(CiudadComboBoxModel ciudadCboxModel, EmpresaComboBoxModel empresaCboxModel,
    		ProveedorTableModel proveedorTableModel) {
    	this.ciudadComboBoxModel = ciudadCboxModel;
    	this.empresaComboBoxModel = empresaCboxModel;
    	this.proveedorTableModel = proveedorTableModel;
    	
    	setSize(900, 500);
        
        initComponents();
        
        Util.setupScreen(this);
    }

    private void initComponents() {
    	getContentPane().setLayout(new MigLayout("", "[88px][1px][694px][4px][85px]", "[29px][111px][269px][37px]"));
    	
    	lblBuscador = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblBuscador.text"));
    	getContentPane().add(lblBuscador, "cell 0 0,grow");
    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        
    	getContentPane().add(tabbedPane, "cell 0 2 5 1,grow");
        
        JPanel pnlDatosPersonal = new JPanel();
        tabbedPane.addTab("Datos Personales", null, pnlDatosPersonal, null);
        
        JLabel lblTipo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblTipo.text"));
        lblTipo.setBounds(6, 46, 98, 30);
        
        cbTipo = new JComboBox<String>();
        cbTipo.setBounds(122, 46, 163, 30);
        cbTipo.setModel(new DefaultComboBoxModel<String>(new String[] {"FISICO", "JURIDICO", "EXTRANJERO"}));
        
        JLabel lblNombre = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblNombre.text"));
        lblNombre.setBounds(6, 84, 98, 30);
        
        tfNombre = new JTextField();
        tfNombre.setBounds(122, 84, 251, 30);
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfNombre.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			tfRazonSocial.setText(tfNombre.getText());
					tfRazonSocial.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
        JLabel lblRaznSocial = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblRaznSocial.text"));
        lblRaznSocial.setBounds(6, 122, 98, 30);
        
        tfRazonSocial = new JTextField();
        tfRazonSocial.setBounds(122, 122, 251, 30);
        ((AbstractDocument) tfRazonSocial.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfRazonSocial.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfCiruc.requestFocus();
				}
        	}
        });
        tfRazonSocial.setColumns(10);
        
        tfCiruc = new JTextField();
        tfCiruc.setBounds(122, 160, 104, 30);
        tfCiruc.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (!tfCiruc.getText().isEmpty()) {
            			tfDvRuc.setText(String.valueOf(Util.calculateRucDV(tfCiruc.getText())));
            			tfDireccion.requestFocus();
            		} else {
            			Notifications.showAlert("Debes digitar CI/RUC");
            		}
				}
        		
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCiruc.setColumns(10);
        
        JLabel lblCiRuc = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblCiRuc.text"));
        lblCiRuc.setBounds(6, 160, 98, 30);
        
        JLabel lblCiudad = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblCiudad.text"));
        lblCiudad.setBounds(416, 8, 67, 30);
        
        cbCiudad = new JComboBox<>(ciudadComboBoxModel);
        cbCiudad.setBounds(514, 8, 156, 30);
        cbCiudad.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		cbEmpresa.requestFocus();
        	}
        });
        
        JLabel lblDvRuc = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblDvRuc.text"));
        lblDvRuc.setBounds(252, 160, 57, 30);
        
        tfDvRuc = new JTextField();
        tfDvRuc.setBounds(313, 160, 60, 30);
        tfDvRuc.setEditable(false);
        tfDvRuc.setColumns(10);
        
        JLabel lblDireccin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblDireccin.text"));
        lblDireccin.setBounds(6, 198, 98, 30);
        
        tfDireccion = new JTextField();
        tfDireccion.setBounds(122, 198, 251, 30);
        ((AbstractDocument) tfDireccion.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfDireccion.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbCiudad.requestFocus();
				}
        	}
        });
        tfDireccion.setColumns(10);
        
        tfPlazo = new JTextField();
        tfPlazo.setBounds(514, 122, 114, 30);
        tfPlazo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			tabbedPane.setSelectedIndex(1);
					tfEmail.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfPlazo.setColumns(10);
        
        JLabel lblObs = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblObs.text"));
        lblObs.setBounds(416, 122, 67, 30);
        
        tfClase = new JTextField();
        tfClase.setBounds(514, 84, 114, 30);
        ((AbstractDocument) tfClase.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfClase.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPlazo.requestFocus();
				}
        	}
        });
        tfClase.setColumns(10);
        
        JLabel lblClase = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblClase.text"));
        lblClase.setBounds(416, 84, 67, 30);
        
        cbEmpresa = new JComboBox<Empresa>(empresaComboBoxModel);
        cbEmpresa.setBounds(514, 46, 156, 30);
        cbEmpresa.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfClase.requestFocus();
				}
        	}
        });
        
        JLabel lblEmpresa = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblEmpresa.text"));
        lblEmpresa.setBounds(416, 46, 67, 30);
        
        tfProveedorId = new JTextField();
        tfProveedorId.setBounds(122, 8, 104, 30);
        tfProveedorId.setEditable(false);
        tfProveedorId.setColumns(10);
        pnlDatosPersonal.setLayout(null);
        
        lblCodigo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.lblCodigo.text"));
        lblCodigo.setBounds(6, 8, 98, 30);
        pnlDatosPersonal.add(lblCodigo);
        pnlDatosPersonal.add(tfProveedorId);
        pnlDatosPersonal.add(lblCiudad);
        pnlDatosPersonal.add(cbCiudad);
        pnlDatosPersonal.add(lblCiRuc);
        pnlDatosPersonal.add(tfCiruc);
        pnlDatosPersonal.add(lblDvRuc);
        pnlDatosPersonal.add(tfDvRuc);
        pnlDatosPersonal.add(lblDireccin);
        pnlDatosPersonal.add(tfDireccion);
        pnlDatosPersonal.add(lblTipo);
        pnlDatosPersonal.add(cbTipo);
        pnlDatosPersonal.add(lblEmpresa);
        pnlDatosPersonal.add(cbEmpresa);
        pnlDatosPersonal.add(lblRaznSocial);
        pnlDatosPersonal.add(tfRazonSocial);
        pnlDatosPersonal.add(lblObs);
        pnlDatosPersonal.add(tfPlazo);
        pnlDatosPersonal.add(lblNombre);
        pnlDatosPersonal.add(tfNombre);
        pnlDatosPersonal.add(lblClase);
        pnlDatosPersonal.add(tfClase);
        
        label_1 = new JLabel("*");
        label_1.setBounds(102, 84, 18, 30);
        label_1.setVerticalAlignment(SwingConstants.BOTTOM);
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setForeground(Color.RED);
        label_1.setFont(new Font("Dialog", Font.BOLD, 20));
        pnlDatosPersonal.add(label_1);
        
        label_2 = new JLabel("*");
        label_2.setBounds(102, 122, 18, 30);
        label_2.setVerticalAlignment(SwingConstants.BOTTOM);
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setForeground(Color.RED);
        label_2.setFont(new Font("Dialog", Font.BOLD, 20));
        pnlDatosPersonal.add(label_2);
        
        label_3 = new JLabel("*");
        label_3.setBounds(102, 160, 18, 30);
        label_3.setVerticalAlignment(SwingConstants.BOTTOM);
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setForeground(Color.RED);
        label_3.setFont(new Font("Dialog", Font.BOLD, 20));
        pnlDatosPersonal.add(label_3);
        
        label_5 = new JLabel("*");
        label_5.setBounds(102, 198, 18, 30);
        label_5.setVerticalAlignment(SwingConstants.BOTTOM);
        label_5.setHorizontalAlignment(SwingConstants.CENTER);
        label_5.setForeground(Color.RED);
        label_5.setFont(new Font("Dialog", Font.BOLD, 20));
        pnlDatosPersonal.add(label_5);
        
        pnlContacto = new JPanel();
        tabbedPane.addTab("Contactos", null, pnlContacto, null);
        
        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(6, 6, 71, 30);
        
        tfEmail = new JTextField();
        tfEmail.setBounds(113, 6, 194, 30);
        tfEmail.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfWeb.requestFocus();
				}
        	}
        });
        tfEmail.setColumns(10);
        
        lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(439, 6, 79, 30);
        
        tfTelefono = new JTextField();
        tfTelefono.setBounds(530, 6, 114, 30);
        tfTelefono.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfCelular.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfTelefono.setColumns(10);
        
        tfCelular = new JTextField();
        tfCelular.setBounds(530, 46, 114, 30);
        tfCelular.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfFax.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCelular.setColumns(10);
        
        lblCelular = new JLabel("Célular:");
        lblCelular.setBounds(439, 46, 79, 30);
        
        lblFax = new JLabel("Fax:");
        lblFax.setBounds(439, 86, 79, 30);
        
        tfFax = new JTextField();
        tfFax.setBounds(530, 86, 114, 30);
        tfFax.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfFax.setColumns(10);
        
        lblWeb = new JLabel("Web:");
        lblWeb.setBounds(6, 48, 71, 30);
        
        tfWeb = new JTextField();
        tfWeb.setBounds(113, 48, 194, 30);
        tfWeb.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfContacto.requestFocus();
				}
        	}
        });
        tfWeb.setColumns(10);
        
        lblSkype = new JLabel("Skype:");
        lblSkype.setBounds(6, 125, 71, 30);
        
        tfSkype = new JTextField();
        tfSkype.setBounds(113, 125, 114, 30);
        tfSkype.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfTelefono.requestFocus();
				}
        	}
        });
        tfSkype.setColumns(10);
        
        lblContacto = new JLabel("Contacto:");
        lblContacto.setBounds(6, 86, 71, 30);
        
        tfContacto = new JTextField();
        tfContacto.setBounds(113, 86, 194, 30);
        ((AbstractDocument) tfContacto.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfContacto.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfSkype.requestFocus();
				}
        	}
        });
        tfContacto.setColumns(10);
        
        label = new JLabel("*");
        label.setBounds(93, 6, 18, 30);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.RED);
        label.setFont(new Font("Dialog", Font.BOLD, 20));
        pnlContacto.setLayout(null);
        pnlContacto.add(lblSkype);
        pnlContacto.add(lblEmail);
        pnlContacto.add(label);
        pnlContacto.add(lblWeb);
        pnlContacto.add(lblContacto);
        pnlContacto.add(tfWeb);
        pnlContacto.add(tfSkype);
        pnlContacto.add(tfContacto);
        pnlContacto.add(lblCelular);
        pnlContacto.add(tfCelular);
        pnlContacto.add(lblTelefono);
        pnlContacto.add(tfTelefono);
        pnlContacto.add(lblFax);
        pnlContacto.add(tfFax);
        pnlContacto.add(tfEmail);
        
        label_4 = new JLabel("*");
        label_4.setVerticalAlignment(SwingConstants.BOTTOM);
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setForeground(Color.RED);
        label_4.setFont(new Font("Dialog", Font.BOLD, 20));
        label_4.setBounds(511, 46, 18, 30);
        pnlContacto.add(label_4);
        
        JPanel pnlBotonera = new JPanel();
        getContentPane().add(pnlBotonera, "cell 0 3 5 1,grow");
        
        btnGuardar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.btnGuardar.text")); //$NON-NLS-1$ //$NON-NLS-2$
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.btnCancelar.text")); //$NON-NLS-1$ //$NON-NLS-2$
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.btnCerrar.text")); //$NON-NLS-1$ //$NON-NLS-2$
        pnlBotonera.add(btnCerrar);
        
        tfBuscador = new JTextField();
        tfBuscador.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
        	}
        });
        ((AbstractDocument) tfBuscador.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        getContentPane().add(tfBuscador, "cell 2 0,grow");
        tfBuscador.setText("");
        tfBuscador.setColumns(10);
        
        btnBuscar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorPanel.btnBuscar.text"));
        getContentPane().add(btnBuscar, "cell 4 0,grow");
        
        scrollProveedor = new JScrollPane();
        getContentPane().add(scrollProveedor, "cell 0 1 5 1,grow");
        
        tbProveedor = new JTable(proveedorTableModel);
        tbProveedor.setDefaultRenderer(Object.class, new CellRendererOperaciones());
        scrollProveedor.setViewportView(tbProveedor);
    }
    
    public void setProveedorForm(Proveedor proveedor) {
    	tfProveedorId.setText(proveedor.getId() + "");
    	tfNombre.setText(proveedor.getNombre());
    	tfRazonSocial.setText(proveedor.getRazonSocial());
    	tfCiruc.setText(proveedor.getRuc());
    	tfDvRuc.setText(proveedor.getDvruc());
    	tfDireccion.setText(proveedor.getDireccion());
    	tfCelular.setText(proveedor.getCelular());
    	tfTelefono.setText(proveedor.getTelefono());
    	tfFax.setText(proveedor.getFax());
    	tfEmail.setText(proveedor.getEmail());
    	tfSkype.setText(proveedor.getSkype());
    	tfWeb.setText(proveedor.getWeb());
    	tfContacto.setText(proveedor.getContacto());
    	tfClase.setText(proveedor.getClase());
    	tfPlazo.setText(proveedor.getPlazo() + "");
    	
    	ciudadComboBoxModel.setSelectedItem(proveedor.getCiudad());
    	empresaComboBoxModel.setSelectedItem(proveedor.getEmpresa());
    	cbTipo.setSelectedItem(proveedor.getTipo());
    }

    public Proveedor getProveedorFrom() {
        Proveedor proveedor = new Proveedor();
        
        if (!tfProveedorId.getText().isEmpty()) {
        	proveedor.setId(Long.parseLong(tfProveedorId.getText()));
		}
        
        proveedor.setRazonSocial(tfRazonSocial.getText());
        proveedor.setNombre(tfNombre.getText());
        proveedor.setRuc(tfCiruc.getText());
        proveedor.setDvruc(tfDvRuc.getText());
        proveedor.setDireccion(tfDireccion.getText());
        proveedor.setCelular(tfCelular.getText());
        proveedor.setTelefono(tfTelefono.getText());
        proveedor.setFax(tfFax.getText());
        proveedor.setEmail(tfEmail.getText());
        proveedor.setSkype(tfSkype.getText());
        proveedor.setWeb(tfWeb.getText());
        proveedor.setContacto(tfContacto.getText());
        proveedor.setClase(tfClase.getText());
        proveedor.setPlazo(Integer.parseInt(tfPlazo.getText()));
        proveedor.setCiudad((Ciudad)ciudadComboBoxModel.getSelectedItem());
        proveedor.setEmpresa((Empresa)empresaComboBoxModel.getSelectedItem());
        proveedor.setTipo((String)cbTipo.getSelectedItem());
        
        return proveedor;
    }

    public void clearForm() {
    	tfProveedorId.setText("");
        tfNombre.setText("");
    	tfRazonSocial.setText("");
    	tfCiruc.setText("");
    	tfDvRuc.setText("");
    	tfDireccion.setText("");
    	tfCelular.setText("");
    	tfTelefono.setText("");
    	tfFax.setText("");
    	tfSkype.setText("");
    	tfEmail.setText("");
    	tfWeb.setText("");
    	tfContacto.setText("");
    	tfClase.setText("");
    	tfPlazo.setText("");
    	tfPlazo.setText("");
    	cbCiudad.setSelectedIndex(0);
    	cbEmpresa.setSelectedIndex(0);
    	cbTipo.setSelectedIndex(0);
    }
    
    public JButton getBtnBuscar() {
		return btnBuscar;
	}
    
    public JTable getTbProveedor() {
		return tbProveedor;
	}
    
    public JTextField getTfBuscador() {
		return tfBuscador;
	}

    public JButton getBtnGuardar() {
		return btnGuardar;
	}
    
    public JButton getBtnCancelar() {
		return btnCancelar;
	}
    
    public JButton getBtnCerrar() {
		return btnCerrar;
	}
    
    public JTextField getTfNombre() {
		return tfNombre;
	}

	public void setNewProveedor(long id) {
		tfProveedorId.setText(String.valueOf(id));
	}
}
