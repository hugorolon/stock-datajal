package py.com.prestosoftware.ui.forms;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.shared.FormBtnPanel;
import py.com.prestosoftware.ui.table.CiudadComboBoxModel;
import py.com.prestosoftware.ui.table.ClientTableModel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.ui.table.ListaPrecioComboBoxModel;
import py.com.prestosoftware.util.Notifications;

import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.text.AbstractDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

@Component
public class ClientePanel extends JDialog {

	private static final long serialVersionUID = 1L;
	
    private JTextField tfNombre, tfRazonSocial, tfCiruc, tfDvRuc, tfDireccion, tfCreditoDisponible;
    private JTextField tfObs, tfClase, tfLimiteCredito, tfDiaCredito, tfCreditoSaldo, tfPlazo;
    private JCheckBox chTieneCredito;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    
    private JPanel pnlContacto;
    private JLabel label;
    private JTextField tfEmail;
    private JLabel label_1;
    private JTextField tfWeb;
    private JLabel label_2;
    private JLabel label_3;
    private JLabel label_4;
    private JTextField tfTelefono;
    private JTextField tfCelular;
    private JTextField tfFax;
    private JLabel lblSkype;
    private JTextField tfSkype;
    private JLabel label_6;
    private JTextField tfContacto;
    private JCheckBox chActivo;
    private JLabel label_7;
    private JTextField tfId;
    private JLabel lblCodigo;
    private JLabel lblNivelPrecio;
    
    private JLabel lblBuscador;
    private JTextField tfBuscador;
    private JButton btnBuscar;
    private JTable tbCliente;
    private JScrollPane scrollPane;
    
    private JComboBox<Ciudad> cbCiudad;
    private JComboBox<Empresa> cbEmpresa;
    private JComboBox<String> cbTipo;
    private JComboBox<ListaPrecio> cbListaPrecio;
    
    private CiudadComboBoxModel ciudadComboBoxModel;
    private EmpresaComboBoxModel empresaComboBoxModel;
    private ListaPrecioComboBoxModel listaPrecioComboBoxModel;
    private ClientTableModel clientTableModel;
    private JLabel lblNombre;
    private JTextField tfcelular2;
    private JLabel lblOblig;
    private JLabel label_5;
    private JLabel label_8;
    private JLabel label_9;
    private JLabel label_10;

    @Autowired
    public ClientePanel(CiudadComboBoxModel ciudadCboxModel, EmpresaComboBoxModel empresaCboxModel, 
    					ListaPrecioComboBoxModel listaPrecioCboxModel, FormBtnPanel formBtnPanel,
    					ClientTableModel clientTableModel) {
    	this.ciudadComboBoxModel = ciudadCboxModel;
    	this.empresaComboBoxModel = empresaCboxModel;
    	this.listaPrecioComboBoxModel = listaPrecioCboxModel;
    	this.clientTableModel = clientTableModel;
    	
    	setSize(900, 500);
        
        initComponents();
        
        Util.setupScreen(this);
    }
    
    //VERIFICAR EL RUC DEL CLIENTE, QUE NO PUEDA REPETIR

    private void initComponents() {
    	getContentPane().setLayout(new MigLayout("", "[70px][8px][688px][12px][106px]", "[25px][125px][273px][33px]"));
    	
    	JPanel panel = new JPanel();
    	getContentPane().add(panel, "cell 0 3 5 1,grow");
    	
    	btnGuardar = new JButton("Guardar");
    	panel.add(btnGuardar);
    	
    	btnCancelar = new JButton("Cancelar");
    	panel.add(btnCancelar);
    	
    	btnCerrar = new JButton("Cerrar");
    	panel.add(btnCerrar);
    	JTabbedPane tabbedPane = new JTabbedPane();
        
        JPanel pnlDatosPersonal = new JPanel();
        tabbedPane.addTab("Datos Personales", null, pnlDatosPersonal, null);
        
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(6, 102, 84, 30);
        
        cbTipo = new JComboBox<String>();
        cbTipo.setBounds(117, 102, 163, 30);
        cbTipo.setModel(new DefaultComboBoxModel<String>(new String[] {"FISICO", "JURIDICO", "EXTRANJERO"}));
        
        tfNombre = new JTextField();
        tfNombre.setBounds(117, 38, 243, 30);
        tfNombre.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			tfRazonSocial.setText(tfNombre.getText());
					tfRazonSocial.requestFocus();
					tfRazonSocial.selectAll();
				}
        	}
        });
        tfNombre.setColumns(10);
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        
        JLabel lblRaznSocial = new JLabel("Razón Social:");
        lblRaznSocial.setBounds(6, 72, 84, 30);
        
        tfRazonSocial = new JTextField();
        tfRazonSocial.setBounds(117, 72, 243, 30);
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
        tfCiruc.setBounds(117, 136, 140, 30);
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
        });
        tfCiruc.setColumns(10);
        
        JLabel lblCiRuc = new JLabel("C.I/RUC:");
        lblCiRuc.setBounds(6, 136, 84, 30);
        
        JLabel lblCiudad = new JLabel("Ciudad:");
        lblCiudad.setBounds(6, 204, 84, 30);
        
        cbCiudad = new JComboBox<>(ciudadComboBoxModel);
        cbCiudad.setBounds(117, 204, 163, 30);
        cbCiudad.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfClase.requestFocus();
				}
        	}
        });
        
        JLabel lblDvRuc = new JLabel("DV RUC");
        lblDvRuc.setBounds(279, 136, 48, 30);
        
        tfDvRuc = new JTextField();
        tfDvRuc.setBounds(329, 136, 42, 30);
        tfDvRuc.setEditable(false);
        tfDvRuc.setColumns(10);
        
        JLabel lblDireccin = new JLabel("Dirección:");
        lblDireccin.setBounds(6, 170, 84, 30);
        
        tfDireccion = new JTextField();
        tfDireccion.setBounds(117, 170, 254, 30);
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
        
        tfObs = new JTextField();
        tfObs.setBounds(503, 38, 243, 64);
        ((AbstractDocument) tfObs.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfObs.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbEmpresa.requestFocus();
				}
        	}
        });
        tfObs.setColumns(10);
        
        JLabel lblObs = new JLabel("Obs.:");
        lblObs.setBounds(383, 38, 75, 30);
        
        tfClase = new JTextField();
        tfClase.setBounds(503, 4, 60, 30);
        ((AbstractDocument) tfClase.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfClase.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfObs.requestFocus();
				}
        	}
        });
        tfClase.setColumns(10);
        
        JLabel lblClase = new JLabel("Clase:");
        lblClase.setBounds(383, 4, 75, 30);
        
        cbEmpresa = new JComboBox<Empresa>(empresaComboBoxModel);
        cbEmpresa.setBounds(503, 106, 163, 30);
        cbEmpresa.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbListaPrecio.requestFocus();
				}
        	}
        });
        
        JLabel lblEmpresa = new JLabel("Empresa:");
        lblEmpresa.setBounds(383, 106, 75, 30);
        
        chActivo = new JCheckBox();
        chActivo.setBounds(503, 174, 163, 30);
        chActivo.setSelected(true);
        
        label_7 = new JLabel("Activo:");
        label_7.setBounds(383, 174, 75, 30);
        
        tfId = new JTextField();
        tfId.setBounds(117, 4, 163, 30);
        tfId.setEditable(false);
        
        lblCodigo = new JLabel("Codigo:");
        lblCodigo.setBounds(6, 4, 84, 30);
        
        lblNivelPrecio = new JLabel("Nivel Precio:");
        lblNivelPrecio.setBounds(379, 140, 84, 30);
        
        cbListaPrecio = new JComboBox<ListaPrecio>(listaPrecioComboBoxModel);
        cbListaPrecio.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			tabbedPane.setSelectedIndex(1);
					chTieneCredito.requestFocus();
				}
        	}
        });
        cbListaPrecio.setBounds(503, 140, 163, 30);
        pnlDatosPersonal.setLayout(null);
        pnlDatosPersonal.add(lblCodigo);
        pnlDatosPersonal.add(tfId);
        pnlDatosPersonal.add(lblCiudad);
        pnlDatosPersonal.add(cbCiudad);
        pnlDatosPersonal.add(lblTipo);
        pnlDatosPersonal.add(cbTipo);
        pnlDatosPersonal.add(lblEmpresa);
        pnlDatosPersonal.add(cbEmpresa);
        pnlDatosPersonal.add(tfNombre);
        pnlDatosPersonal.add(lblClase);
        pnlDatosPersonal.add(tfClase);
        pnlDatosPersonal.add(lblRaznSocial);
        pnlDatosPersonal.add(lblCiRuc);
        pnlDatosPersonal.add(tfRazonSocial);
        pnlDatosPersonal.add(tfCiruc);
        pnlDatosPersonal.add(lblDvRuc);
        pnlDatosPersonal.add(tfDvRuc);
        pnlDatosPersonal.add(lblObs);
        pnlDatosPersonal.add(tfObs);
        pnlDatosPersonal.add(lblNivelPrecio);
        pnlDatosPersonal.add(cbListaPrecio);
        pnlDatosPersonal.add(lblDireccin);
        pnlDatosPersonal.add(tfDireccion);
        pnlDatosPersonal.add(label_7);
        pnlDatosPersonal.add(chActivo);
        
        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(6, 38, 60, 30);
        pnlDatosPersonal.add(lblNombre);
        
        lblOblig = new JLabel("*");
        lblOblig.setVerticalAlignment(SwingConstants.BOTTOM);
        lblOblig.setHorizontalAlignment(SwingConstants.CENTER);
        lblOblig.setFont(new Font("Dialog", Font.BOLD, 20));
        lblOblig.setForeground(Color.RED);
        lblOblig.setBounds(98, 38, 18, 30);
        pnlDatosPersonal.add(lblOblig);
        
        label_5 = new JLabel("*");
        label_5.setVerticalAlignment(SwingConstants.BOTTOM);
        label_5.setHorizontalAlignment(SwingConstants.CENTER);
        label_5.setForeground(Color.RED);
        label_5.setFont(new Font("Dialog", Font.BOLD, 20));
        label_5.setBounds(98, 72, 18, 30);
        pnlDatosPersonal.add(label_5);
        
        label_8 = new JLabel("*");
        label_8.setVerticalAlignment(SwingConstants.BOTTOM);
        label_8.setHorizontalAlignment(SwingConstants.CENTER);
        label_8.setForeground(Color.RED);
        label_8.setFont(new Font("Dialog", Font.BOLD, 20));
        label_8.setBounds(98, 136, 18, 30);
        pnlDatosPersonal.add(label_8);
        
        label_9 = new JLabel("*");
        label_9.setVerticalAlignment(SwingConstants.BOTTOM);
        label_9.setHorizontalAlignment(SwingConstants.CENTER);
        label_9.setForeground(Color.RED);
        label_9.setFont(new Font("Dialog", Font.BOLD, 20));
        label_9.setBounds(483, 106, 18, 30);
        pnlDatosPersonal.add(label_9);
        
        label_10 = new JLabel("*");
        label_10.setVerticalAlignment(SwingConstants.BOTTOM);
        label_10.setHorizontalAlignment(SwingConstants.CENTER);
        label_10.setForeground(Color.RED);
        label_10.setFont(new Font("Dialog", Font.BOLD, 20));
        label_10.setBounds(98, 170, 18, 30);
        pnlDatosPersonal.add(label_10);
        
        JPanel pnlCredito = new JPanel();
        tabbedPane.addTab("Créditos", null, pnlCredito, "");
        
        JLabel lblTieneCredito = new JLabel("Tiene Crédito:");
        
        chTieneCredito = new JCheckBox();
        chTieneCredito.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent e) {
        		if (e.getStateChange() == ItemEvent.SELECTED) {
        			tfLimiteCredito.setEditable(true);
        			tfDiaCredito.setEditable(true);
        			tfPlazo.setEditable(true);
        			tfLimiteCredito.requestFocus();
				} else {
					tfLimiteCredito.setEditable(false);
					tfDiaCredito.setEditable(false);
        			tfPlazo.setEditable(false);
        			tfLimiteCredito.setText("");
        			tfDiaCredito.setText("");
        			tfPlazo.setText("");
				}
        	}
        });
        
        chTieneCredito.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfLimiteCredito.requestFocus();
				}
        	}
        });
        
        JLabel lblLimiteCredito = new JLabel("Limite Crédito:");
        
        JLabel lblDiaCredito = new JLabel("Día Crédito:");
        
        tfLimiteCredito = new JFormattedTextField();
        tfLimiteCredito.setEditable( false );
        tfLimiteCredito.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfDiaCredito.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfLimiteCredito.setColumns(10);
        
        tfDiaCredito = new JFormattedTextField();
        tfDiaCredito.setEditable(false );
        tfDiaCredito.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPlazo.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfDiaCredito.setColumns(10);
        
        JLabel lblCreditoSaldo = new JLabel("Crédito Saldo:");
        
        tfCreditoSaldo = new JFormattedTextField();
        tfCreditoSaldo.setEditable(false);
        tfCreditoSaldo.setColumns(10);
        
        JLabel lblCreditoDisponible = new JLabel("Crédito Disponible:");
        
        tfCreditoDisponible = new JFormattedTextField();
        tfCreditoDisponible.setEditable(false);
        tfCreditoDisponible.setColumns(10);
        
        tfPlazo = new JFormattedTextField();
        tfPlazo.setText("0");
        tfPlazo.setEditable(false);
        tfPlazo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tabbedPane.setSelectedIndex(2);
					tfEmail.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfPlazo.setColumns(10);
        
        JLabel lblPlazo = new JLabel("Cond. Pago");
        GroupLayout gl_pnlCredito = new GroupLayout(pnlCredito);
        gl_pnlCredito.setHorizontalGroup(
        	gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCredito.createSequentialGroup()
        			.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pnlCredito.createSequentialGroup()
        					.addGap(6)
        					.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        						.addGroup(gl_pnlCredito.createSequentialGroup()
        							.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        								.addComponent(lblTieneCredito, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
        								.addGroup(gl_pnlCredito.createSequentialGroup()
        									.addGap(129)
        									.addComponent(chTieneCredito, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)))
        							.addGap(18)
        							.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        								.addGroup(gl_pnlCredito.createSequentialGroup()
        									.addGap(129)
        									.addComponent(tfCreditoSaldo, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
        								.addComponent(lblCreditoSaldo, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(gl_pnlCredito.createSequentialGroup()
        							.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        								.addGroup(gl_pnlCredito.createSequentialGroup()
        									.addGap(129)
        									.addComponent(tfLimiteCredito, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
        								.addComponent(lblLimiteCredito, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
        							.addGap(18)
        							.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        								.addGroup(gl_pnlCredito.createSequentialGroup()
        									.addGap(129)
        									.addComponent(tfCreditoDisponible, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
        								.addComponent(lblCreditoDisponible, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))
        						.addGroup(gl_pnlCredito.createSequentialGroup()
        							.addGap(129)
        							.addComponent(tfDiaCredito, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
        						.addComponent(lblDiaCredito, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)))
        				.addGroup(gl_pnlCredito.createSequentialGroup()
        					.addGap(6)
        					.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        						.addGroup(gl_pnlCredito.createSequentialGroup()
        							.addGap(129)
        							.addComponent(tfPlazo, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
        						.addComponent(lblPlazo, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap(255, Short.MAX_VALUE))
        );
        gl_pnlCredito.setVerticalGroup(
        	gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCredito.createSequentialGroup()
        			.addGap(10)
        			.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblTieneCredito, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chTieneCredito, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfCreditoSaldo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblCreditoSaldo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(10)
        			.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        				.addComponent(tfLimiteCredito, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblLimiteCredito, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfCreditoDisponible, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblCreditoDisponible, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(10)
        			.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        				.addComponent(tfDiaCredito, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblDiaCredito, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pnlCredito.createParallelGroup(Alignment.LEADING)
        				.addComponent(tfPlazo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblPlazo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(90))
        );
        pnlCredito.setLayout(gl_pnlCredito);
        getContentPane().add(tabbedPane, "cell 0 2 5 1,growx,aligny top");
        
        pnlContacto = new JPanel();
        tabbedPane.addTab("Contacto", null, pnlContacto, null);
        
        label = new JLabel("Email:");
        label.setBounds(6, 4, 79, 30);
        
        tfEmail = new JTextField();
        tfEmail.setBounds(84, 4, 226, 30);
        tfEmail.setColumns(10);
        tfEmail.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfWeb.requestFocus();
				}
        	}
        });
        
        label_1 = new JLabel("Web:");
        label_1.setBounds(6, 38, 79, 30);
        
        tfWeb = new JTextField();
        tfWeb.setBounds(84, 38, 226, 30);
        tfWeb.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfSkype.requestFocus();
				}
        	}
        });
        
           tfWeb.setColumns(10);
           
           label_2 = new JLabel("Fax:");
           label_2.setBounds(6, 208, 79, 30);
           
           label_3 = new JLabel("Célular:");
           label_3.setBounds(6, 174, 79, 30);
           
           label_4 = new JLabel("Teléfono:");
           label_4.setBounds(6, 140, 79, 30);
           
           tfTelefono = new JTextField();
           tfTelefono.setBounds(84, 140, 163, 30);
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
           tfCelular.setBounds(84, 174, 163, 30);
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
           
           tfFax = new JTextField();
           tfFax.setBounds(84, 208, 163, 30);
           tfFax.addKeyListener(new KeyAdapter() {
           	@Override
           	public void keyPressed(KeyEvent e) {
           		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chTieneCredito.requestFocus();
				}
           	}
           	@Override
           	public void keyTyped(KeyEvent e) {
           		Util.validateNumero(e);
           	}
           });
           tfFax.setColumns(10);
           
           lblSkype = new JLabel("Skype:");
           lblSkype.setBounds(6, 72, 79, 30);
           
           tfSkype = new JTextField();
           tfSkype.setBounds(84, 72, 226, 30);
           tfSkype.addKeyListener(new KeyAdapter() {
           	@Override
           	public void keyPressed(KeyEvent e) {
           		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfContacto.requestFocus();
				}
           	}
           });
           tfSkype.setColumns(10);
           
           label_6 = new JLabel("Contacto:");
           label_6.setBounds(6, 106, 79, 30);
           
           tfContacto = new JTextField();
           tfContacto.setBounds(84, 106, 226, 30);
           ((AbstractDocument) tfContacto.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
           tfContacto.addKeyListener(new KeyAdapter() {
           	@Override
           	public void keyPressed(KeyEvent e) {
           		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfTelefono.requestFocus();
				}
           	}
           });
           tfContacto.setColumns(10);
           pnlContacto.setLayout(null);
           pnlContacto.add(tfEmail);
           pnlContacto.add(label);
           pnlContacto.add(tfTelefono);
           pnlContacto.add(label_4);
           pnlContacto.add(tfWeb);
           pnlContacto.add(label_1);
           pnlContacto.add(tfCelular);
           pnlContacto.add(label_3);
           pnlContacto.add(lblSkype);
           pnlContacto.add(tfSkype);
           pnlContacto.add(label_2);
           pnlContacto.add(tfFax);
           pnlContacto.add(label_6);
           pnlContacto.add(tfContacto);
           
           JLabel lblClular = new JLabel("Célular 2:");
           lblClular.setBounds(417, 174, 79, 30);
           pnlContacto.add(lblClular);
           
           tfcelular2 = new JTextField();
           tfcelular2.setColumns(10);
           tfcelular2.setBounds(495, 174, 163, 30);
           pnlContacto.add(tfcelular2);
           
           lblBuscador = new JLabel("Buscador:");
           getContentPane().add(lblBuscador, "cell 0 0,grow");
           
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
           tfBuscador.setColumns(10);
           
           btnBuscar = new JButton("Buscar");
           getContentPane().add(btnBuscar, "cell 4 0,growx,aligny top");
           
           scrollPane = new JScrollPane();
           getContentPane().add(scrollPane, "cell 0 1 5 1,grow");
           
           tbCliente = new JTable(clientTableModel);
           tbCliente.setDefaultRenderer(Object.class, new CellRendererOperaciones());
           scrollPane.setViewportView(tbCliente);
    }
    
    public void setClientForm(Cliente client) {
    	tfId.setText(client.getId() + "");
    	tfNombre.setText(client.getNombre());
    	tfRazonSocial.setText(client.getRazonSocial());
    	tfCiruc.setText(client.getCiruc());
    	tfDvRuc.setText(client.getDvruc());
    	tfDireccion.setText(client.getDireccion());
    	tfCelular.setText(client.getCelular());
    	tfcelular2.setText(client.getCelular2());
    	tfTelefono.setText(client.getTelefono());
    	tfFax.setText(client.getFax());
    	tfEmail.setText(client.getEmail());
    	tfSkype.setText(client.getSkype());
    	tfWeb.setText(client.getWeb());
    	tfContacto.setText(client.getContacto());
    	tfClase.setText(client.getClase());
    	tfObs.setText(client.getObs());
    	
    	tfCreditoDisponible.setText(client.getCreditoDisponible() != null ? String.valueOf(client.getCreditoDisponible()) : "");
    	tfCreditoSaldo.setText(client.getCreditoSaldo() != null ? String.valueOf(client.getCreditoSaldo()) : "");
    	tfDiaCredito.setText(String.valueOf(client.getDiaCredito()));
    	tfLimiteCredito.setText(client.getLimiteCredito() != null ? String.valueOf(client.getLimiteCredito()) : "");
    	tfPlazo.setText(client.getPlazo() + "");
    	
    	ciudadComboBoxModel.setSelectedItem(client.getCiudad());
    	empresaComboBoxModel.setSelectedItem(client.getEmpresa());
    	listaPrecioComboBoxModel.setSelectedItem(client.getListaPrecio());
    	cbTipo.setSelectedItem(client.getTipo());
    	
    	chTieneCredito.setSelected(client.getTieneCredito() == 1 ? true : false);
    	chActivo.setSelected(client.getActivo() == 1 ? true : false);
    }

    public Cliente getClientFromForm() {
        Cliente client = new Cliente();
        
        if (!tfId.getText().isEmpty()) {
        	client.setId(Long.parseLong(tfId.getText()));
		}
        
        client.setRazonSocial(tfRazonSocial.getText());
        client.setNombre(tfNombre.getText());
    	client.setCiruc(tfCiruc.getText());
    	client.setDvruc(tfDvRuc.getText());
    	client.setDireccion(tfDireccion.getText());
    	client.setCelular(tfCelular.getText());
    	client.setTelefono(tfTelefono.getText());
    	client.setFax(tfFax.getText());
    	client.setEmail(tfEmail.getText());
    	client.setSkype(tfSkype.getText());
    	client.setWeb(tfWeb.getText());
    	client.setContacto(tfContacto.getText());
    	client.setClase(tfClase.getText());
    	client.setObs(tfObs.getText());
    	client.setCelular2(tfcelular2.getText());
//    	client.setCreditoDisponible(Double.valueOf(tfCreditoDisponible.getText()));
//    	client.setCreditoSaldo(Double.valueOf(tfCreditoSaldo.getText()));
    	client.setDiaCredito(tfDiaCredito.getText().isEmpty() ? 0 : Integer.parseInt(tfDiaCredito.getText()));
    	client.setLimiteCredito(tfLimiteCredito.getText().isEmpty() ? 0 : Double.valueOf(tfLimiteCredito.getText()));
    	client.setPlazo(tfPlazo.getText().isEmpty() ? 0 : Integer.parseInt(tfPlazo.getText()));
    	
    	client.setCiudad((Ciudad)ciudadComboBoxModel.getSelectedItem());
    	client.setEmpresa((Empresa)empresaComboBoxModel.getSelectedItem());
    	client.setListaPrecio((ListaPrecio)listaPrecioComboBoxModel.getSelectedItem());
    	client.setTipo((String)cbTipo.getSelectedItem());
    	
    	client.setTieneCredito(chTieneCredito.isSelected() ? 1 : 0);
    	client.setActivo(chActivo.isSelected() ? 1 : 0);
    	client.setFechaRegistro(new Date());
        
        return client;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
    	tfRazonSocial.setText("");
    	tfCiruc.setText("");
    	tfDvRuc.setText("");
    	tfDireccion.setText("");
    	tfCelular.setText("");
    	tfcelular2.setText("");
    	tfTelefono.setText("");
    	tfFax.setText("");
    	tfSkype.setText("");
    	tfEmail.setText("");
    	tfWeb.setText("");
    	tfContacto.setText("");
    	tfClase.setText("");
    	tfObs.setText("");
    	tfCreditoDisponible.setText("");
    	tfCreditoSaldo.setText("");
    	tfDiaCredito.setText("");
    	tfLimiteCredito.setText("");
    	tfPlazo.setText("");
    	cbCiudad.setSelectedIndex(0);
    	cbEmpresa.setSelectedIndex(0);
    	cbListaPrecio.setSelectedIndex(0);
    	cbTipo.setSelectedIndex(0);
    	chTieneCredito.setSelected(false);
    	chActivo.setSelected(false);
    }
    
    public JButton getBtnBuscar() {
		return btnBuscar;
	}
    
    public JTextField getTfBuscador() {
		return tfBuscador;
	}
    
    public JTable getTbCliente() {
		return tbCliente;
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

	public void setNewClient(long clientId) {
		tfId.setText(String.valueOf(clientId));
	}
	
	public JTextField getTfNombre() {
		return tfNombre;
	}
}
