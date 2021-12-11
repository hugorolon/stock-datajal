package py.com.prestosoftware.ui.forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.miginfocom.swing.MigLayout;
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.CiudadService;
import py.com.prestosoftware.domain.services.EmpresaService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.validations.ProveedorValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ProveedorInterfaz;
import py.com.prestosoftware.ui.table.CiudadComboBoxModel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class ProveedorAddPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblCodigo;
	private JTextField tfNombre, tfRazonSocial, tfCiruc, tfDvRuc, tfDireccion;
	private JTextField tfProveedorId, tfPlazo, tfClase;
	private JButton btnGuardar, btnCancelar;

	private JComboBox<Ciudad> cbCiudad;
	private JComboBox<Empresa> cbEmpresa;
	private JComboBox<String> cbTipo;

	private CiudadComboBoxModel ciudadComboBoxModel;
	private EmpresaComboBoxModel empresaComboBoxModel;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_5;
	private ProveedorService proveedorService;
	private ProveedorValidator proveedorValidator;
	private CiudadService ciudadService;
	private EmpresaService empresaService;
	private ProveedorInterfaz interfaz;
	private JTextField tfCelular;
	private JTextField tfEmail;

	@Autowired
	public ProveedorAddPanel(CiudadComboBoxModel ciudadCboxModel, EmpresaComboBoxModel empresaCboxModel,
			ProveedorValidator proveedorValidator, ProveedorService proveedorService, CiudadService ciudadService,
			EmpresaService empresaService) {
		this.ciudadComboBoxModel = ciudadCboxModel;
		this.empresaComboBoxModel = empresaCboxModel;
		this.proveedorValidator = proveedorValidator;
		this.proveedorService = proveedorService;
		this.empresaService = empresaService;
		this.ciudadService = ciudadService;
		setSize(1070, 421);

		initComponents();

		Util.setupScreen(this);
	}

	private void initComponents() {
		getContentPane().setLayout(new MigLayout("", "[88px][1px][694px][4px][217.00px]", "[29px][233.00px][39.00][]"));
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));

		getContentPane().add(tabbedPane, "cell 0 1 5 2,grow");

		JPanel pnlDatosPersonal = new JPanel();
		tabbedPane.addTab("Datos Personales", null, pnlDatosPersonal, null);

		JLabel lblTipo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblTipo.text"));
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipo.setBounds(6, 46, 98, 30);

		cbTipo = new JComboBox<String>();
		cbTipo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbTipo.setBounds(122, 46, 163, 30);
		cbTipo.setModel(new DefaultComboBoxModel<String>(new String[] { "FISICO", "JURIDICO", "EXTRANJERO" }));

		JLabel lblNombre = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblNombre.text"));
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

		JLabel lblRaznSocial = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblRaznSocial.text"));
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

		JLabel lblCiRuc = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblCiRuc.text"));
		lblCiRuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCiRuc.setBounds(6, 160, 98, 30);

		JLabel lblCiudad = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblCiudad.text"));
		lblCiudad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCiudad.setBounds(416, 8, 67, 30);

		cbCiudad = new JComboBox<>(ciudadComboBoxModel);
		cbCiudad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbCiudad.setBounds(514, 8, 156, 30);
		cbCiudad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				cbEmpresa.requestFocus();
			}
		});

		JLabel lblDvRuc = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblDvRuc.text"));
		lblDvRuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDvRuc.setBounds(252, 160, 57, 30);

		tfDvRuc = new JTextField();
		tfDvRuc.setBounds(313, 160, 60, 30);
		tfDvRuc.setColumns(10);

		JLabel lblDireccin = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblDireccin.text"));
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

		tfPlazo = new JTextField();
		tfPlazo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfPlazo.setBounds(514, 122, 114, 30);
		tfPlazo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPlazo.setColumns(10);

		JLabel lblObs = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblObs.text"));
		lblObs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObs.setBounds(416, 122, 67, 30);

		tfClase = new JTextField();
		tfClase.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

		JLabel lblClase = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblClase.text"));
		lblClase.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblClase.setBounds(416, 84, 67, 30);

		cbEmpresa = new JComboBox<Empresa>(empresaComboBoxModel);
		cbEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbEmpresa.setBounds(514, 46, 156, 30);
		cbEmpresa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfClase.requestFocus();
				}
			}
		});

		JLabel lblEmpresa = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblEmpresa.text"));
		lblEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmpresa.setBounds(416, 46, 67, 30);

		tfProveedorId = new JTextField();
		tfProveedorId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfProveedorId.setBounds(122, 8, 104, 30);
		tfProveedorId.setEditable(false);
		tfProveedorId.setColumns(10);
		pnlDatosPersonal.setLayout(null);

		lblCodigo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProveedorPanel.lblCodigo.text"));
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		
		JLabel lblCelular = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorAddPanel.lblCelular.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCelular.setBounds(416, 160, 67, 30);
		pnlDatosPersonal.add(lblCelular);
		
		tfCelular = new JTextField();
		tfCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfCelular.setBounds(514, 160, 114, 30);
		pnlDatosPersonal.add(tfCelular);
		tfCelular.setColumns(10);
		
		JLabel lblEmail = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProveedorAddPanel.lblEmail.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(416, 198, 67, 30);
		pnlDatosPersonal.add(lblEmail);
		
		JLabel label_3_1 = new JLabel("*");
		label_3_1.setVerticalAlignment(SwingConstants.BOTTOM);
		label_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_3_1.setForeground(Color.RED);
		label_3_1.setFont(new Font("Dialog", Font.BOLD, 20));
		label_3_1.setBounds(486, 160, 18, 30);
		pnlDatosPersonal.add(label_3_1);
		
		JLabel label_3_2 = new JLabel("*");
		label_3_2.setVerticalAlignment(SwingConstants.BOTTOM);
		label_3_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_3_2.setForeground(Color.RED);
		label_3_2.setFont(new Font("Dialog", Font.BOLD, 20));
		label_3_2.setBounds(486, 198, 18, 30);
		pnlDatosPersonal.add(label_3_2);
		
		tfEmail = new JTextField();
		tfEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfEmail.setBounds(514, 198, 114, 30);
		pnlDatosPersonal.add(tfEmail);
		tfEmail.setColumns(10);

		
		JPanel pnlBotonera = new JPanel();
		getContentPane().add(pnlBotonera, "cell 0 3 5 1,grow");

		btnGuardar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProveedorPanel.btnGuardar.text")); //$NON-NLS-1$
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptar();
			}
		});
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBotonera.add(btnGuardar);

		btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProveedorPanel.btnCancelar.text")); //$NON-NLS-1$
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBotonera.add(btnCancelar);
	}

	public void setProveedorForm(Proveedor proveedor) {
		tfProveedorId.setText(proveedor.getId() + "");
		tfNombre.setText(proveedor.getNombre());
		tfRazonSocial.setText(proveedor.getRazonSocial());
		tfCiruc.setText(proveedor.getRuc());
		tfDvRuc.setText(proveedor.getDvruc());
		tfDireccion.setText(proveedor.getDireccion());
		tfClase.setText(proveedor.getClase());
		tfPlazo.setText(proveedor.getPlazo() + "");
		tfEmail.setText(proveedor.getEmail());
		tfCelular.setText(proveedor.getCelular());

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
		proveedor.setClase(tfClase.getText());
		proveedor.setPlazo(tfPlazo.getText().equalsIgnoreCase("") ? 0 : Integer.parseInt(tfPlazo.getText()));
		proveedor.setCiudad((Ciudad) ciudadComboBoxModel.getSelectedItem());
		proveedor.setEmpresa((Empresa) empresaComboBoxModel.getSelectedItem());
		proveedor.setTipo((String) cbTipo.getSelectedItem());
		proveedor.setEmail(tfEmail.getText());
		proveedor.setCelular(tfCelular.getText());

		return proveedor;
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

	public ProveedorInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(ProveedorInterfaz interfaz) {
		this.interfaz = interfaz;
	}

	private void aceptar() {

		Proveedor proveedor = this.getProveedorFrom();
		Optional<Proveedor> proveedorRec = Optional.ofNullable(new Proveedor());
		Optional<ValidationError> errors = proveedorValidator.validate(proveedor);
		if (errors.isPresent()) {
			ValidationError validationError = errors.get();
			Notifications.showFormValidationAlert(validationError.getMessage());
		} else {
			proveedorService.save(proveedor);
			proveedorRec = proveedorService.findById(proveedor.getId());
			clearForm();
		}
		if (proveedorRec != null) {
			interfaz.getEntity(proveedor);
		}

		dispose();
	}

	public void addNewProveedor() {
		long Id = proveedorService.addNewProveedor();
		this.setNewProveedor(Id + 1);
	}

	public void clearForm() {
		tfProveedorId.setText("");
		tfNombre.setText("");
		tfRazonSocial.setText("");
		tfCiruc.setText("");
		tfDvRuc.setText("");
		tfDireccion.setText("");
		tfClase.setText("");
		tfPlazo.setText("");
		tfPlazo.setText("");
		tfCelular.setText("");
		tfEmail.setText("");
		cbCiudad.setSelectedIndex(0);
		cbEmpresa.setSelectedIndex(0);
		cbTipo.setSelectedIndex(0);
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

	public void setNewProveedor(long id) {
		tfProveedorId.setText(String.valueOf(id));
	}
}
