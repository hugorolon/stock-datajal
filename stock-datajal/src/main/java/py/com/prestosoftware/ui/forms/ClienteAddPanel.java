package py.com.prestosoftware.ui.forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.AbstractDocument;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.miginfocom.swing.MigLayout;
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.domain.services.CiudadService;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.EmpresaService;
import py.com.prestosoftware.domain.validations.ClienteValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.controllers.CiudadController;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.CiudadInterfaz;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.table.CiudadComboBoxModel;
import py.com.prestosoftware.ui.table.ClienteComboBoxModel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class ClienteAddPanel extends JDialog implements CiudadInterfaz , ItemListener {

	private static final long serialVersionUID = 1L;
	private static final int CIUDAD_CODE = 1;
	private JLabel lblCodigo;
	private JTextField tfNombre, tfRazonSocial, tfCiruc, tfDvRuc, tfDireccion;
	private JTextField tfClienteId;
	private JButton btnGuardar, btnCancelar;

	private JComboBox<Ciudad> cbCiudad;
	private CiudadController ciudadController;
	private CiudadComboBoxModel ciudadComboBoxModel;
	private ClienteComboBoxModel clienteComboBoxModel;
	private EmpresaComboBoxModel empresaComboBoxModel;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_5;
	private ClienteService clienteService;
	private ClienteValidator clienteValidator;
	private CiudadService ciudadService;
	private EmpresaService empresaService;
	private ClienteInterfaz interfaz;
	private JTextField tfCelular;
	private JTextField tfEmail;
	private JComboBox cbCliente;
	private JCheckBox chTieneCredito;
	private JTextField  tfCreditoDisponible;
    private JTextField tfLimiteCredito, tfDiaCredito, tfCreditoSaldo, tfPlazo;
    private JPanel pnlContacto;
    private JLabel label;
    private JTextField tfWeb;
    private JLabel label_4;
    private JTextField tfTelefono;
    private JTextField tfFax;
    private JLabel label_6;
    private JTextField tfContacto;
    private JTextField tfId;
    private JTextField tfcelular2;
    
    
	@Autowired
	public ClienteAddPanel(CiudadComboBoxModel ciudadCboxModel, EmpresaComboBoxModel empresaCboxModel,
			ClienteValidator clienteValidator, ClienteService clienteService, CiudadService ciudadService,
			EmpresaService empresaService, CiudadController ciudadController, ClienteComboBoxModel clienteComboBoxModel) {
		this.ciudadComboBoxModel = ciudadCboxModel;
		this.empresaComboBoxModel = empresaCboxModel;
		this.clienteValidator = clienteValidator;
		this.clienteService = clienteService;
		this.empresaService = empresaService;
		this.ciudadService = ciudadService;
		this.clienteComboBoxModel =clienteComboBoxModel;
		this.ciudadController=ciudadController;
		setSize(990, 413);

		initComponents();
		AutoCompleteDecorator.decorate(cbCliente);
		AutoCompleteDecorator.decorate(cbCiudad);


		Util.setupScreen(this);
	}

	private void initComponents() {
		getContentPane().setLayout(new MigLayout("", "[88px][1px][694px][4px][217.00px]", "[15.00px][-43.00][][10.00][]"));
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));

		getContentPane().add(tabbedPane, "cell 0 1 5 2,grow");

		JPanel pnlDatosPersonal = new JPanel();
		tabbedPane.addTab("Datos Personales", null, pnlDatosPersonal, null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(6, 84, 98, 30);

		tfNombre = new JTextField();
		tfNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

		JLabel lblRaznSocial = new JLabel("Razon Social");
		lblRaznSocial.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRaznSocial.setBounds(6, 122, 98, 30);

		tfRazonSocial = new JTextField();
		tfRazonSocial.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		tfCiruc.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

		JLabel lblCiRuc = new JLabel("CI");
		lblCiRuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCiRuc.setBounds(6, 160, 98, 30);

		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCiudad.setBounds(416, 8, 67, 30);

		cbCiudad = new JComboBox<>(ciudadComboBoxModel);
		cbCiudad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbCiudad.setBounds(514, 8, 206, 30);
		cbCiudad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				tfCelular.requestFocus();
			}
		});

		JLabel lblDvRuc = new JLabel("DV");
		lblDvRuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDvRuc.setBounds(252, 160, 57, 30);

		tfDvRuc = new JTextField();
		tfDvRuc.setBounds(313, 160, 60, 30);
		tfDvRuc.setColumns(10);

		JLabel lblDireccin = new JLabel("Dirección");
		lblDireccin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDireccin.setBounds(6, 198, 98, 30);

		tfDireccion = new JTextField();
		tfDireccion.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

		tfClienteId = new JTextField();
		tfClienteId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfClienteId.setBounds(122, 44, 104, 30);
		tfClienteId.setEditable(false);
		tfClienteId.setColumns(10);
		pnlDatosPersonal.setLayout(null);

		lblCodigo = new JLabel("Codigo");
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigo.setBounds(6, 44, 98, 30);
		pnlDatosPersonal.add(lblCodigo);
		pnlDatosPersonal.add(tfClienteId);
		pnlDatosPersonal.add(lblCiudad);
		pnlDatosPersonal.add(cbCiudad);
		pnlDatosPersonal.add(lblCiRuc);
		pnlDatosPersonal.add(tfCiruc);
		pnlDatosPersonal.add(lblDvRuc);
		pnlDatosPersonal.add(tfDvRuc);
		pnlDatosPersonal.add(lblDireccin);
		pnlDatosPersonal.add(tfDireccion);
		pnlDatosPersonal.add(lblRaznSocial);
		pnlDatosPersonal.add(tfRazonSocial);
		pnlDatosPersonal.add(lblNombre);
		pnlDatosPersonal.add(tfNombre);

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
		
		JLabel lblNombreCliente = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ClienteAddPanel.lblNombreCliente.text")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
		lblNombreCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreCliente.setBounds(6, 10, 110, 30);
		pnlDatosPersonal.add(lblNombreCliente);
		
		cbCliente = new JComboBox<>(clienteComboBoxModel);
		cbCliente.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				traeCliente();
			}
				});
//		cbCliente.addPropertyChangeListener(new PropertyChangeListener() {
//			public void propertyChange(PropertyChangeEvent evt) {
//				
//			}
//		});
		cbCliente.setBounds(122, 8, 251, 30);
		pnlDatosPersonal.add(cbCliente);
		
		JButton btnNuevaCiudad = new JButton("+"); //$NON-NLS-1$ //$NON-NLS-2$
		btnNuevaCiudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(CIUDAD_CODE);
			}
		});
		btnNuevaCiudad.setBounds(723, 8, 49, 30);
		pnlDatosPersonal.add(btnNuevaCiudad);

		JPanel pnlCredito = new JPanel();
        tabbedPane.addTab("Créditos", null, pnlCredito, "");
        
        JLabel lblTieneCredito = new JLabel("Tiene Crédito:");
        lblTieneCredito.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        chTieneCredito = new JCheckBox();
        chTieneCredito.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
        lblLimiteCredito.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        JLabel lblDiaCredito = new JLabel("Día Crédito:");
        lblDiaCredito.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        tfLimiteCredito = new JFormattedTextField();
        tfLimiteCredito.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
        tfDiaCredito.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
        lblCreditoSaldo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        tfCreditoSaldo = new JFormattedTextField();
        tfCreditoSaldo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tfCreditoSaldo.setEditable(false);
        tfCreditoSaldo.setColumns(10);
        
        JLabel lblCreditoDisponible = new JLabel("Crédito Disponible:");
        lblCreditoDisponible.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        tfCreditoDisponible = new JFormattedTextField();
        tfCreditoDisponible.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tfCreditoDisponible.setEditable(false);
        tfCreditoDisponible.setColumns(10);
        
        tfPlazo = new JFormattedTextField();
        tfPlazo.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tfPlazo.setText("30");
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
        lblPlazo.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
        getContentPane().add(tabbedPane, "cell 0 4 5 1,alignx center,aligny top");
        
        pnlContacto = new JPanel();
        tabbedPane.addTab("Contacto", null, pnlContacto, null);
        
        label = new JLabel("Email:");
        label.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label.setBounds(6, 4, 79, 30);
        
        tfEmail = new JTextField();
        tfEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        label_1.setBounds(6, 38, 79, 30);
        
        tfWeb = new JTextField();
        tfWeb.setFont(new Font("Tahoma", Font.PLAIN, 14));
        tfWeb.setBounds(84, 38, 226, 30);
        tfWeb.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfContacto.requestFocus();
				}
        	}
        });
        
           tfWeb.setColumns(10);
           
           label_2 = new JLabel("Fax:");
           label_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
           label_2.setBounds(6, 208, 79, 30);
           
           label_3 = new JLabel("Célular:");
           label_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
           label_3.setBounds(6, 142, 79, 30);
           
           label_4 = new JLabel("Teléfono:");
           label_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
           label_4.setBounds(6, 107, 79, 30);
           
           tfTelefono = new JTextField();
           tfTelefono.setFont(new Font("Tahoma", Font.PLAIN, 14));
           tfTelefono.setBounds(84, 107, 163, 30);
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
           tfCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
           tfCelular.setBounds(84, 142, 163, 30);
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
           tfFax.setFont(new Font("Tahoma", Font.PLAIN, 14));
           tfFax.setBounds(84, 209, 163, 30);
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
           
           label_6 = new JLabel("Contacto:");
           label_6.setFont(new Font("Tahoma", Font.PLAIN, 14));
           label_6.setBounds(6, 72, 79, 30);
           
           tfContacto = new JTextField();
           tfContacto.setFont(new Font("Tahoma", Font.PLAIN, 14));
           tfContacto.setBounds(84, 72, 226, 30);
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
           pnlContacto.add(label_2);
           pnlContacto.add(tfFax);
           pnlContacto.add(label_6);
           pnlContacto.add(tfContacto);
           
           JLabel lblClular = new JLabel("Célular 2:");
           lblClular.setFont(new Font("Tahoma", Font.PLAIN, 14));
           lblClular.setBounds(6, 178, 79, 30);
           pnlContacto.add(lblClular);
           
           tfcelular2 = new JTextField();
           tfcelular2.setFont(new Font("Tahoma", Font.PLAIN, 14));
           tfcelular2.setColumns(10);
           tfcelular2.setBounds(84, 178, 163, 30);
           pnlContacto.add(tfcelular2);
        
		
		JPanel pnlBotonera = new JPanel();
		getContentPane().add(pnlBotonera, "cell 0 2 5 1,grow");

		btnGuardar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ClientePanel.btnGuardar.text")); //$NON-NLS-1$
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptar();
			}
		});
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBotonera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar"); //$NON-NLS-1$
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBotonera.add(btnCancelar);
		loadClients();
	}

	public void setClienteForm(Cliente cliente) {
		tfClienteId.setText(cliente.getId() + "");
		tfNombre.setText(cliente.getNombre());
		tfRazonSocial.setText(cliente.getRazonSocial());
		tfCiruc.setText(cliente.getCiruc());
		tfDvRuc.setText(cliente.getDvruc());
		tfDireccion.setText(cliente.getDireccion());
		tfEmail.setText(cliente.getEmail());
		tfCelular.setText(cliente.getCelular());
		
    	tfcelular2.setText(cliente.getCelular2());
    	tfTelefono.setText(cliente.getTelefono());
    	tfFax.setText(cliente.getFax());
    	tfEmail.setText(cliente.getEmail());
    	tfWeb.setText(cliente.getWeb());
    	tfContacto.setText(cliente.getContacto().toString());
    	
    	tfCreditoDisponible.setText(cliente.getCreditoDisponible() != null ? String.valueOf(cliente.getCreditoDisponible()) : "");
    	tfCreditoSaldo.setText(cliente.getCreditoSaldo() != null ? String.valueOf(cliente.getCreditoSaldo()) : "");
    	tfDiaCredito.setText(cliente.getDiaCredito() != null ? String.valueOf(cliente.getDiaCredito()):"");
    	tfLimiteCredito.setText(cliente.getLimiteCredito() != null ? FormatearValor.doubleAString(cliente.getLimiteCredito()) : "");
    	tfPlazo.setText(String.valueOf(cliente.getPlazo()));

		ciudadComboBoxModel.setSelectedItem(cliente.getCiudad());
		empresaComboBoxModel.setSelectedItem(cliente.getEmpresa());
	}

	public Cliente getClienteFrom() {
		Cliente cliente = new Cliente();

		if (!tfClienteId.getText().isEmpty()) {
			cliente.setId(Long.parseLong(tfClienteId.getText()));
		}

		cliente.setRazonSocial(tfRazonSocial.getText());
		cliente.setNombre(tfNombre.getText());
		cliente.setCiruc(tfCiruc.getText());
		cliente.setDvruc(tfDvRuc.getText());
		cliente.setDireccion(tfDireccion.getText());
		cliente.setCiudad((Ciudad) ciudadComboBoxModel.getSelectedItem());
		cliente.setEmpresa((Empresa) empresaComboBoxModel.getSelectedItem());
		//contacto
		cliente.setEmail(tfEmail.getText());
		cliente.setCelular(tfCelular.getText());
		cliente.setCelular2(tfcelular2.getText());
		cliente.setTelefono(tfTelefono.getText());	
    	cliente.setFax(tfFax.getText());
    	cliente.setWeb(tfWeb.getText());
    	cliente.setContacto(tfContacto.getText());
    	//credito
    	cliente.setTieneCredito(chTieneCredito.isSelected() ? 1 : 0);
//    	client.setCreditoDisponible(Double.valueOf(tfCreditoDisponible.getText()));
//    	client.setCreditoSaldo(Double.valueOf(tfCreditoSaldo.getText()));
    	cliente.setDiaCredito(tfDiaCredito.getText().isEmpty() ? 0 : Integer.parseInt(tfDiaCredito.getText()));
    	cliente.setLimiteCredito(tfLimiteCredito.getText().isEmpty() ? 0 : Double.valueOf(FormatearValor.sinSeparadorDeMiles(tfLimiteCredito.getText())));
    	cliente.setPlazo(tfPlazo.getText().isEmpty() ? 0 : Integer.parseInt(tfPlazo.getText()));
		
		cliente.setActivo(1);
		cliente.setTipoEntidad(1);
		cliente.setFechaRegistro(new Date());

		return cliente;
	}

	public void loadCiudades() {
		if (ciudadComboBoxModel != null) {
			List<Ciudad> ciudades = ciudadService.findAll();
			ciudadComboBoxModel.clear();
			ciudadComboBoxModel.addElements(ciudades);
		}
	}

	public void loadEmpresas() {
		if (empresaComboBoxModel != null) {
			List<Empresa> empresas = empresaService.findAll();
			empresaComboBoxModel.clear();
			empresaComboBoxModel.addElements(empresas);
		}
	}

	public ClienteInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(ClienteInterfaz interfaz) {
		this.interfaz = interfaz;
	}

	private void aceptar() {

		Cliente cliente = this.getClienteFrom();
		Optional<Cliente> clienteRec = Optional.ofNullable(new Cliente());
		Optional<ValidationError> errors = clienteValidator.validate(cliente);
		if (errors.isPresent()) {
			ValidationError validationError = errors.get();
			Notifications.showFormValidationAlert(validationError.getMessage());
		} else {
			try {
				clienteService.save(cliente);
			} catch (Exception e) {
				Notifications.showAlert("Error al guardar cliente");
				e.printStackTrace();
				return;
			}
			clienteRec = clienteService.findById(cliente.getId());
			clearForm();
		}
		if (clienteRec != null) {
			interfaz.getEntity(cliente);
		}

		dispose();
	}

	public void addNewCliente() {
		clearForm();
		long Id = clienteService.addNewClient();
		this.setNewCliente(Id);
		cbCliente.setSelectedItem("");
	}

	private void traeCliente() {
		if(cbCliente!=null&&cbCliente.getSelectedItem()!=null && cbCliente.getSelectedItem().toString().length()>0) {
			Cliente c=clienteService.findByNombreEquals(cbCliente.getSelectedItem().toString().toUpperCase());
			tfClienteId.setText(c.getId().toString());
			tfNombre.setText(c.getNombre());
			tfRazonSocial.setText(c.getRazonSocial());
			tfCiruc.setText(c.getCiruc());
			tfDvRuc.setText(c.getDvruc());
			tfDireccion.setText(c.getDireccion());
			tfCelular.setText(c.getCelular());
			tfEmail.setText(c.getEmail());
			cbCiudad.setSelectedItem(c.getCiudad());
			
			tfCelular.setText(c.getCelular());
	    	tfcelular2.setText(c.getCelular2());
	    	tfTelefono.setText(c.getTelefono());
	    	tfFax.setText(c.getFax());
	    	tfEmail.setText(c.getEmail());
	    	tfWeb.setText(c.getWeb());
	    	tfContacto.setText(c.getContacto());
//	    	tfClase.setText(client.getClase());
//	    	tfObs.setText(client.getObs());
	    	
	    	tfCreditoDisponible.setText(c.getCreditoDisponible() != null ? String.valueOf(c.getCreditoDisponible()) : "");
	    	tfCreditoSaldo.setText(c.getCreditoSaldo() != null ? String.valueOf(c.getCreditoSaldo()) : "");
	    	tfDiaCredito.setText(c.getDiaCredito() != null ? String.valueOf(c.getDiaCredito()):"");
	    	tfLimiteCredito.setText(c.getLimiteCredito() != null ? FormatearValor.doubleAString(c.getLimiteCredito()) : "");
	    	tfPlazo.setText(String.valueOf(c.getPlazo()));
			
			
			
		}		
	}
	
	public void clearForm() {
		tfClienteId.setText("");
		tfNombre.setText("");
		tfRazonSocial.setText("");
		tfCiruc.setText("");
		tfDvRuc.setText("");
		tfDireccion.setText("");
		tfCelular.setText("");
		tfEmail.setText("");
		cbCiudad.setSelectedIndex(0);
		cbCliente.setSelectedItem("");
    	tfcelular2.setText("");
    	tfTelefono.setText("");
    	tfFax.setText("");
    	tfEmail.setText("");
    	tfWeb.setText("");
    	tfContacto.setText("");
    	tfCreditoDisponible.setText("");
    	tfCreditoSaldo.setText("");
    	tfDiaCredito.setText("");
    	tfLimiteCredito.setText("");
    	tfPlazo.setText("");
    	cbCiudad.setSelectedIndex(0);
    	chTieneCredito.setSelected(false);
	}
	
	private void showDialog(int code) {
		switch (code) {
		case CIUDAD_CODE:
			ciudadController.setInterfaz(this);
			ciudadController.setOrigen("Proveedor");;
			ciudadController.prepareAndOpenFrame();
			//marcaController.setOrigen("PRODUCTO");
			break;
		default:
			break;
		}
	}
	
	@Override
	public void getEntity(Ciudad ciudad) {
		ciudadComboBoxModel.addElement(ciudad);
		ciudadComboBoxModel.setSelectedItem(ciudad);
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public void setNewCliente(long id) {
		tfClienteId.setText(String.valueOf(id));
	}
	private void loadClients() {
		List<Cliente> clientes = clienteService.findAll();
		clienteComboBoxModel.clear();
		//for (Cliente cliente : clientes) {
			clienteComboBoxModel.addElement(null);
			clienteComboBoxModel.addElements(clientes);
		//}
		
	}

	public JComboBox getCbCliente() {
		return cbCliente;
	}

	public void setCbCliente(JComboBox cbCliente) {
		this.cbCliente = cbCliente;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}

	
//	public void itemStateChangedClienteAdd(ItemEvent e) {
//		// TODO Auto-generated method stub
//		traeCliente();
//	}
	
	
}
