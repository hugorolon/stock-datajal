package py.com.prestosoftware.ui.forms;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Impuesto;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Subgrupo;
import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.data.models.UnidadMedida;
import py.com.prestosoftware.domain.services.GrupoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.SubgrupoService;
import py.com.prestosoftware.domain.validations.ProductoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.table.GrupoComboBoxModel;
import py.com.prestosoftware.ui.table.SubgrupoComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class ProductoAddPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JLabel lblCodigo;
	private JTextField tfDescripcion, tfDescFiscal, tfReferencia, tfSubReferencia;
	private JTextField tfProductoId;
	private JButton btnGuardar, btnCancelar;
	private GrupoComboBoxModel grupoComboBoxModel;
	private SubgrupoComboBoxModel subgrupoComboBoxModel;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private GrupoService grupoService;
	private ProductoService productoService;
	private ProductoValidator productoValidator;
	private ProductoInterfaz interfaz;
	private JLabel lblPrecioA;
	private JLabel label;
	private JTextField tfPrecioA;
	private JLabel lblPrecioB;
	private JLabel label_4;
	private JTextField tfPrecioB;
	private JLabel lblPrecioC;
	private JLabel label_5;
	private JTextField tfPrecioC;
	private JComboBox<Grupo> cbGrupo;
	private JComboBox<Subgrupo> cbSubGrupo;
	private SubgrupoService subgrupoService;


	@Autowired
	public ProductoAddPanel(GrupoService grupoService, GrupoComboBoxModel grupoComboBoxModel, SubgrupoComboBoxModel subgrupoComboBoxModel, ProductoValidator productoValidator, ProductoService productoService, SubgrupoService subgrupoService) {
		this.grupoService =grupoService;
		this.grupoComboBoxModel =grupoComboBoxModel;
		this.subgrupoComboBoxModel =subgrupoComboBoxModel;
		this.productoValidator = productoValidator;
		this.productoService = productoService;
		this.subgrupoService =subgrupoService;
		setSize(1070, 461);

		
		initComponents();

		Util.setupScreen(this);
	}

	private void initComponents() {
		getContentPane().setLayout(new MigLayout("", "[88px][1px][694px][4px][217.00px]", "[29px][233.00px][39.00][][][]"));
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));

		getContentPane().add(tabbedPane, "cell 0 1 5 4,grow");

		JPanel pnlDatosPersonal = new JPanel();
		tabbedPane.addTab("Básico", null, pnlDatosPersonal, null);

		JLabel lblGrupo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblTipo.text"));
		lblGrupo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGrupo.setBounds(6, 46, 98, 30);

		cbGrupo = new JComboBox<Grupo>(grupoComboBoxModel);
		cbGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				loadSubgruposByGrupo((Grupo) cbGrupo.getSelectedItem());
			}
		});
		cbGrupo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbGrupo.setBounds(122, 46, 163, 30);

		JLabel lblDescripcion = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblNombre.text"));
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescripcion.setBounds(6, 124, 98, 30);

		tfDescripcion = new JTextField();
		tfDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfDescripcion.setBounds(122, 124, 251, 30);
		((AbstractDocument) tfDescripcion.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfDescripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfDescFiscal.setText(tfDescripcion.getText());
					tfDescFiscal.requestFocus();
				}
			}
		});
		tfDescripcion.setColumns(10);

		JLabel lblDescFiscal = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblRaznSocial.text"));
		lblDescFiscal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescFiscal.setBounds(6, 162, 98, 30);

		tfDescFiscal = new JTextField();
		tfDescFiscal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfDescFiscal.setBounds(122, 162, 251, 30);
		((AbstractDocument) tfDescFiscal.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfDescFiscal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfReferencia.requestFocus();
				}
			}
		});
		tfDescFiscal.setColumns(10);

		tfReferencia = new JTextField();
		tfReferencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfReferencia.setBounds(122, 200, 104, 30);
		tfReferencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfReferencia.getText().isEmpty()) {
						tfSubReferencia.setText(String.valueOf(Util.calculateRucDV(tfReferencia.getText())));
						tfSubReferencia.requestFocus();
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
		tfReferencia.setColumns(10);

		JLabel lblReferencia = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblCiRuc.text"));
		lblReferencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReferencia.setBounds(6, 200, 98, 30);

		JLabel lblSubReferencia = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblDvRuc.text"));
		lblSubReferencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSubReferencia.setBounds(6, 240, 98, 30);

		tfSubReferencia = new JTextField();
		tfSubReferencia.setBounds(122, 240, 104, 30);
		tfSubReferencia.setColumns(10);

		cbSubGrupo = new JComboBox<Subgrupo>(subgrupoComboBoxModel);
		cbSubGrupo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbSubGrupo.setBounds(122, 84, 163, 30);
		cbSubGrupo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfDescripcion.requestFocus();
				}
			}
		});

		JLabel lblSubGrupo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblEmpresa.text"));
		lblSubGrupo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSubGrupo.setBounds(6, 84, 77, 30);

		tfProductoId = new JTextField();
		tfProductoId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfProductoId.setBounds(122, 8, 104, 30);
		tfProductoId.setEditable(false);
		tfProductoId.setColumns(10);
		pnlDatosPersonal.setLayout(null);

		lblCodigo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblCodigo.text"));
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigo.setBounds(6, 8, 98, 30);
		pnlDatosPersonal.add(lblCodigo);
		pnlDatosPersonal.add(tfProductoId);
		pnlDatosPersonal.add(lblReferencia);
		pnlDatosPersonal.add(tfReferencia);
		pnlDatosPersonal.add(lblSubReferencia);
		pnlDatosPersonal.add(tfSubReferencia);
		pnlDatosPersonal.add(lblGrupo);
		pnlDatosPersonal.add(cbGrupo);
		pnlDatosPersonal.add(lblSubGrupo);
		pnlDatosPersonal.add(cbSubGrupo);
		pnlDatosPersonal.add(lblDescFiscal);
		pnlDatosPersonal.add(tfDescFiscal);
		pnlDatosPersonal.add(lblDescripcion);
		pnlDatosPersonal.add(tfDescripcion);

		label_1 = new JLabel("*");
		label_1.setBounds(102, 124, 18, 30);
		label_1.setVerticalAlignment(SwingConstants.BOTTOM);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Dialog", Font.BOLD, 20));
		pnlDatosPersonal.add(label_1);

		label_2 = new JLabel("*");
		label_2.setBounds(102, 162, 18, 30);
		label_2.setVerticalAlignment(SwingConstants.BOTTOM);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Dialog", Font.BOLD, 20));
		pnlDatosPersonal.add(label_2);

		label_3 = new JLabel("*");
		label_3.setBounds(102, 200, 18, 30);
		label_3.setVerticalAlignment(SwingConstants.BOTTOM);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Dialog", Font.BOLD, 20));
		pnlDatosPersonal.add(label_3);
		
		lblPrecioA = new JLabel("Precio A :");
		lblPrecioA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecioA.setBounds(416, 84, 98, 30);
		pnlDatosPersonal.add(lblPrecioA);
		
		label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setFont(new Font("Dialog", Font.BOLD, 20));
		label.setBounds(512, 84, 18, 30);
		pnlDatosPersonal.add(label);
		
		tfPrecioA = new JTextField();
		tfPrecioA.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfPrecioA.setColumns(10);
		tfPrecioA.setBounds(532, 84, 251, 30);
		pnlDatosPersonal.add(tfPrecioA);
		
		lblPrecioB = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoAddPanel.lblPrecioB.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPrecioB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecioB.setBounds(416, 124, 98, 30);
		pnlDatosPersonal.add(lblPrecioB);
		
		label_4 = new JLabel("*");
		label_4.setVerticalAlignment(SwingConstants.BOTTOM);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Dialog", Font.BOLD, 20));
		label_4.setBounds(512, 124, 18, 30);
		pnlDatosPersonal.add(label_4);
		
		tfPrecioB = new JTextField();
		tfPrecioB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfPrecioB.setColumns(10);
		tfPrecioB.setBounds(532, 124, 251, 30);
		pnlDatosPersonal.add(tfPrecioB);
		
		lblPrecioC = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoAddPanel.lblPrecioC.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPrecioC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecioC.setBounds(416, 162, 98, 30);
		pnlDatosPersonal.add(lblPrecioC);
		
		label_5 = new JLabel("*");
		label_5.setVerticalAlignment(SwingConstants.BOTTOM);
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("Dialog", Font.BOLD, 20));
		label_5.setBounds(512, 162, 18, 30);
		pnlDatosPersonal.add(label_5);
		
		tfPrecioC = new JTextField();
		tfPrecioC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfPrecioC.setColumns(10);
		tfPrecioC.setBounds(532, 162, 251, 30);
		pnlDatosPersonal.add(tfPrecioC);
		
				
				JPanel pnlBotonera = new JPanel();
				getContentPane().add(pnlBotonera, "cell 0 5 5 1,grow");
				
						btnGuardar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
								.getString("ProductoPanel.btnGuardar.text")); //$NON-NLS-1$
						btnGuardar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								aceptar();
							}
						});
						btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 14));
						pnlBotonera.add(btnGuardar);
						
								btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
										.getString("ProductoPanel.btnCancelar.text")); //$NON-NLS-1$
								btnCancelar.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										dispose();
									}
								});
								btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
								pnlBotonera.add(btnCancelar);
	}

	private void loadSubgruposByGrupo(Grupo grupo) {
		java.util.List<Subgrupo> subgrupos = subgrupoService.findByGrupo(grupo);

		if (subgrupos == null || subgrupos.size() == 0)
			return;

		subgrupoComboBoxModel.clear();
		subgrupoComboBoxModel.addElements(subgrupos);
	}
	
	public void setProductoForm(Producto producto) {
		tfProductoId.setText(producto.getId() + "");
		tfDescripcion.setText(producto.getDescripcion());
		tfDescFiscal.setText(producto.getDescripcionFiscal());
		tfReferencia.setText(producto.getReferencia());
		tfSubReferencia.setText(producto.getSubreferencia());
		tfPrecioA.setText(FormatearValor.doubleAString(producto.getPrecioVentaA()));
		tfPrecioB.setText(FormatearValor.doubleAString(producto.getPrecioVentaB()));
		tfPrecioC.setText(FormatearValor.doubleAString(producto.getPrecioVentaC()));
		
		grupoComboBoxModel.setSelectedItem(producto.getGrupo());
		subgrupoComboBoxModel.setSelectedItem(producto.getSubgrupo());
		cbGrupo.setSelectedItem(producto.getGrupo());
		cbSubGrupo.setSelectedItem(producto.getSubgrupo());
	}

	public Producto getProductoFrom() {
		Producto producto = new Producto();

		if (!tfProductoId.getText().isEmpty()) {
			producto.setId(Long.parseLong(tfProductoId.getText()));
		}

		producto.setDescripcion(tfDescripcion.getText());
		producto.setDescripcionFiscal(tfDescFiscal.getText());

		producto.setReferencia(tfReferencia.getText());
		producto.setSubreferencia(tfSubReferencia.getText());
		producto.setPrecioVentaA(FormatearValor.stringADoubleFormat(tfPrecioA.getText()));
		producto.setPrecioVentaB(FormatearValor.stringADoubleFormat(tfPrecioB.getText()));
		producto.setPrecioVentaC(FormatearValor.stringADoubleFormat(tfPrecioC.getText()));
		producto.setGrupo((Grupo) grupoComboBoxModel.getSelectedItem());
		producto.setSubgrupo((Subgrupo) subgrupoComboBoxModel.getSelectedItem());
		producto.setCantidadPorCaja(1.0);
		producto.setPeso(1.0);
		producto.setDepO1(0.0);
		
		return producto;
	}
	
	public void loadGrupos() {
		List<Grupo> grupos = grupoService.findAll();
        grupoComboBoxModel.clear();
        grupoComboBoxModel.addElements(grupos);
	}


	public ProductoInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(ProductoInterfaz interfaz) {
		this.interfaz = interfaz;
	}

	private void aceptar() {

		Producto producto = this.getProductoFrom();
		Optional<Producto> productoRec = Optional.ofNullable(new Producto());
		Optional<ValidationError> errors = productoValidator.validate(producto);
		if (errors.isPresent()) {
			ValidationError validationError = errors.get();
			Notifications.showFormValidationAlert(validationError.getMessage());
		} else {
			productoService.save(producto);
			productoRec = productoService.findById(producto.getId());
			clearForm();
		}
		if (productoRec != null) {
			interfaz.getEntity(producto);
		}

		dispose();
	}

//	public void addNewProducto() {
//		long Id = productoService.addNewProduct();
//		this.setNewProducto(Id + 1);
//	}

	public void clearForm() {
		tfProductoId.setText("");
		tfDescripcion.setText("");
		tfDescFiscal.setText("");
		tfReferencia.setText("");
		tfSubReferencia.setText("");
		tfPrecioA.setText("");
		tfPrecioB.setText("");
		tfPrecioC.setText("");
		cbSubGrupo.setSelectedIndex(0);
		cbGrupo.setSelectedIndex(0);
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JTextField getTfNombre() {
		return tfDescripcion;
	}

	public void setNewProducto(long id) {
		tfProductoId.setText(String.valueOf(id));
	}
}
