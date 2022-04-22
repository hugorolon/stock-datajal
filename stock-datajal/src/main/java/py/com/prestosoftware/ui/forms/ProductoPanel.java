package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.miginfocom.swing.MigLayout;
import py.com.prestosoftware.data.models.Categoria;
import py.com.prestosoftware.data.models.Color;
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Impuesto;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.data.models.Ncm;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Subgrupo;
import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.data.models.UnidadMedida;
import py.com.prestosoftware.domain.services.SubgrupoService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.ImagenPanel;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.table.CategoriaComboBoxModel;
import py.com.prestosoftware.ui.table.ColorComboBoxModel;
import py.com.prestosoftware.ui.table.GrupoComboBoxModel;
import py.com.prestosoftware.ui.table.ImpuestoComboBoxModel;
import py.com.prestosoftware.ui.table.ListaPrecioComboBoxModel;
import py.com.prestosoftware.ui.table.MarcaComboBoxModel;
import py.com.prestosoftware.ui.table.NcmComboBoxModel;
import py.com.prestosoftware.ui.table.ProductTableModel;
import py.com.prestosoftware.ui.table.ProductoDepositoTableModel;
import py.com.prestosoftware.ui.table.ProductoPrecioTableModel;
import py.com.prestosoftware.ui.table.SubgrupoComboBoxModel;
import py.com.prestosoftware.ui.table.TamanhoComboBoxModel;
import py.com.prestosoftware.ui.table.UnidadMedidaComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Component
public class ProductoPanel extends JDialog {

	private static final long serialVersionUID = 1L;

	private JTextField tfDescripcion, tfDesFiscal;
	private JTextField tfUltimoPrecioCompra;
	private JTextField tfPrecioPromedio, tfProductoId;
	private JTextField tfUltimaSalida, tfUltimaEntrada;
	private JCheckBox chActivo;
	private JButton btnGuardar, btnCancelar, btnCerrar;
	private JComboBox<Marca> cbMarca;
	private JComboBox<Color> cbColor;
	private JComboBox<Tamanho> cbTamanho;
	private JComboBox<UnidadMedida> cbUMedida;
	private JComboBox<Impuesto> cbImpuesto;
	private JComboBox<ListaPrecio> cbListaPrecio;
	private JTable tbListaPrecio;
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de Imagen", "jpg", "png");
	private JFileChooser fileChooser;
	private File directorio;
	private ImagenPanel zoom;
	private String imagenUrl = "";

	private CategoriaComboBoxModel categoriaComboBoxModel;
	private GrupoComboBoxModel grupoComboBoxModel;
	private NcmComboBoxModel ncmComboBoxModel;
	private ImpuestoComboBoxModel impuestoComboBoxModel;
	private MarcaComboBoxModel marcaComboBoxModel;
	private ColorComboBoxModel colorComboBoxModel;
	private TamanhoComboBoxModel tamanhoComboBoxModel;
	private UnidadMedidaComboBoxModel unidadMedidaComboBoxModel;
	private SubgrupoComboBoxModel subgrupoComboBoxModel;
	private ProductoPrecioTableModel precioTableModel;
	private ProductoDepositoTableModel depositoTableModel;
	private JLabel lblEsServicio;
	private JCheckBox chServicio;
	private JLabel label;
	private JComboBox<Categoria> cbCategoria;
	private JLabel label_1;
	private JComboBox<Ncm> cbNcm;
	private JComboBox<String> cbRegimen;
	private JLabel label_4;
	private JLabel label_5;
	private JTextField tfSeccion;
	private JLabel label_7;
	private JLabel label_8;
	private JLabel label_9;
	private JTextField tfPrecioC;
	private JTextField tfPrecioB;
	private JTextField tfPrecioA;
	private JButton btnNuevo;
	private JPanel pnlBuscador;
	private JLabel lblBuscador;
	private JTextField tfBuscador;
	private JButton btnBuscador;
	private JPanel panel;
	private JTable tbProducto;
	private JScrollPane scrollProducto;

	private ProductTableModel productTableModel;

	private SubgrupoService subgrupoService;
	private ProductoInterfaz interfaz;
	private JLabel lblCompra;
	private JTextField tfPrecioCompra;
	private JLabel label_2;
	private JTextField tfDep01;

	@Autowired
	public ProductoPanel(GrupoComboBoxModel grupoComboBoxModel, NcmComboBoxModel ncmComboBoxModel,
			CategoriaComboBoxModel categoriaComboBoxModel, ImpuestoComboBoxModel impuestoComboBoxModel,
			MarcaComboBoxModel marcaComboBoxModel, ColorComboBoxModel colorComboBoxModel,
			SubgrupoComboBoxModel subgrupoComboBoxModel, TamanhoComboBoxModel tamanhoComboBoxModel,
			UnidadMedidaComboBoxModel unidadMedidaComboBoxModel, ListaPrecioComboBoxModel listaComboBoxModel,
			ProductoPrecioTableModel precioTableModel, ProductoDepositoTableModel depositoTableModel,
			ProductTableModel productTableModel, SubgrupoService subgrupoService) {
		this.grupoComboBoxModel = grupoComboBoxModel;
		this.ncmComboBoxModel = ncmComboBoxModel;
		this.categoriaComboBoxModel = categoriaComboBoxModel;
		this.impuestoComboBoxModel = impuestoComboBoxModel;
		this.marcaComboBoxModel = marcaComboBoxModel;
		this.colorComboBoxModel = colorComboBoxModel;
		this.tamanhoComboBoxModel = tamanhoComboBoxModel;
		this.subgrupoComboBoxModel = subgrupoComboBoxModel;
		this.unidadMedidaComboBoxModel = unidadMedidaComboBoxModel;
		this.precioTableModel = precioTableModel;
		this.depositoTableModel = depositoTableModel;
		this.productTableModel = productTableModel;
		this.subgrupoService = subgrupoService;

		initComponents();
		Util.setupScreen(this);
	}

	private void initComponents() {
		setSize(900, 550);
		setTitle("REGISTRO DE MERCADERIAS");
		getContentPane().setLayout(new MigLayout("", "[788px,grow]", "[][158.00,grow][25px][316px][45px]"));

		lblBuscador = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblBuscador.text"));
		getContentPane().add(lblBuscador, "flowx,cell 0 0");

		panel = new JPanel();
		getContentPane().add(panel, "cell 0 1,grow");
		panel.setLayout(new BorderLayout(0, 0));

		scrollProducto = new JScrollPane();
		panel.add(scrollProducto, BorderLayout.CENTER);

		tbProducto = new JTable(productTableModel);
		tbProducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProducto.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		scrollProducto.setViewportView(tbProducto);

		pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, "cell 0 2,growx,aligny top");
		pnlBuscador.setLayout(new BorderLayout(10, 10));
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, "cell 0 3,grow");

		JPanel pnlDatosPersonal = new JPanel();
		tabbedPane.addTab(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.pnlDatosPersonal.arg0"), null, pnlDatosPersonal, null); //$NON-NLS-1$
		tabbedPane.setEnabledAt(0, true);

		JLabel lblCodigo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblCodigo.text")); //$NON-NLS-1$
		lblCodigo.setBounds(16, 4, 98, 25);

		tfProductoId = new JTextField();
		tfProductoId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfProductoId.setBounds(118, 4, 79, 25);

		JLabel lblNombre = new JLabel("Descripción");
		lblNombre.setBounds(16, 39, 98, 21);

		tfDescripcion = new JTextField();
		tfDescripcion.setNextFocusableComponent(tfDesFiscal);
		tfDescripcion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDescripcion.selectAll();
			}
		});
		tfDescripcion.setBounds(118, 39, 237, 21);
		((AbstractDocument) tfDescripcion.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfDescripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// tfDesFiscal.setText(tfDescripcion.getText());
					tfDesFiscal.requestFocus();
				}
			}
		});
		tfDescripcion.setColumns(10);

		tfDesFiscal = new JTextField();
		tfDesFiscal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDesFiscal.selectAll();
			}
		});
		tfDesFiscal.setBounds(118, 68, 237, 21);
		((AbstractDocument) tfDesFiscal.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfDesFiscal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//tfReferencia.requestFocus();
				}
			}
		});
		tfDesFiscal.setColumns(10);

		JLabel lblDescFiscal = new JLabel("Desc. Fiscal:");
		lblDescFiscal.setBounds(16, 68, 98, 21);
		tfDesFiscal.setNextFocusableComponent(tfPrecioA);
		pnlDatosPersonal.setLayout(null);
		pnlDatosPersonal.add(lblCodigo);
		pnlDatosPersonal.add(tfProductoId);
		pnlDatosPersonal.add(lblNombre);
		pnlDatosPersonal.add(tfDescripcion);
		pnlDatosPersonal.add(lblDescFiscal);
		pnlDatosPersonal.add(tfDesFiscal);

		lblEsServicio = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblEsFraccionado.text"));
		lblEsServicio.setBounds(205, 4, 67, 25);
		pnlDatosPersonal.add(lblEsServicio);
		lblEsServicio.setVisible(false);

		chServicio = new JCheckBox();
		chServicio.setVisible(false);
		chServicio.setBounds(282, 4, 28, 25);
		pnlDatosPersonal.add(chServicio);

		label_7 = new JLabel("Precio A");
		label_7.setBounds(16, 125, 98, 25);
		pnlDatosPersonal.add(label_7);

		label_8 = new JLabel("Precio B");
		label_8.setBounds(16, 154, 98, 25);
		pnlDatosPersonal.add(label_8);

		label_9 = new JLabel("Precio C");
		label_9.setBounds(16, 183, 98, 25);
		pnlDatosPersonal.add(label_9);

		tfPrecioC = new JTextField();
		tfPrecioC.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioC.selectAll();
			}
		});
		tfPrecioC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//tfPrecioD.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecioC.setBounds(118, 183, 163, 25);
		tfPrecioC.setColumns(10);
		tfPrecioC.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioC.selectAll();
			}
		});
		tfPrecioC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					Double precioC = FormatearValor.stringToDouble(tfPrecioC.getText());
					 tfPrecioC.setText(FormatearValor.doubleAString(precioC));
					btnGuardar.requestFocus();
				}else {
					 if (e.getKeyCode() == KeyEvent.VK_UP) {
						 tfPrecioB.requestFocus();
					 }
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		pnlDatosPersonal.add(tfPrecioC);

		tfPrecioB = new JTextField();
		tfPrecioB.setNextFocusableComponent(tfPrecioC);
		tfPrecioB.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioB.selectAll();
			}
		});
		tfPrecioB.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPrecioC.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecioB.setBounds(118, 154, 163, 25);
		tfPrecioB.setColumns(10);
		tfPrecioB.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioB.selectAll();
			}
		});
		tfPrecioB.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfPrecioC.requestFocus();
					Double precioB = FormatearValor.stringToDouble(tfPrecioB.getText());
					 tfPrecioB.setText(FormatearValor.doubleAString(precioB));
				}else {
					 if (e.getKeyCode() == KeyEvent.VK_UP) {
						 tfPrecioA.requestFocus();
					 }
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		pnlDatosPersonal.add(tfPrecioB);

		tfPrecioA = new JTextField();
		tfPrecioA.setNextFocusableComponent(tfPrecioB);
		tfPrecioA.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioA.selectAll();
			}
		});
		tfPrecioA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPrecioB.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecioA.setBounds(118, 125, 163, 25);
		tfPrecioA.setColumns(10);
		tfPrecioA.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioA.selectAll();
			}
		});
		tfPrecioA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					Double precioA = FormatearValor.stringToDouble(tfPrecioA.getText());
					 tfPrecioA.setText(FormatearValor.doubleAString(precioA));
					tfPrecioB.requestFocus();
				}else {
					 if (e.getKeyCode() == KeyEvent.VK_UP) {
						 tfPrecioCompra.requestFocus();
					 }
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		pnlDatosPersonal.add(tfPrecioA);

		btnNuevo = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.btnNuevo.text")); //$NON-NLS-1$
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNuevo.setBounds(778, 16, 73, 25);
		btnNuevo.setMnemonic('N');
		pnlDatosPersonal.add(btnNuevo);

		lblCompra = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.label_2.text")); //$NON-NLS-1$
		lblCompra.setBounds(16, 94, 98, 25);
		pnlDatosPersonal.add(lblCompra);

		tfPrecioCompra = new JTextField();
		tfPrecioCompra.setColumns(10);
		tfPrecioCompra.setBounds(118, 94, 163, 25);
		tfPrecioCompra.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioCompra.requestFocus();
			}
		});
		tfPrecioCompra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.getKeyCode() == KeyEvent.VK_ENTER  || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT) {
					 Double precioCompra = FormatearValor.stringToDouble(tfPrecioCompra.getText());
					 tfPrecioCompra.setText(FormatearValor.doubleAString(precioCompra));
					 tfPrecioA.requestFocus();
				 }else {
					 if (e.getKeyCode() == KeyEvent.VK_UP) {
						 tfDesFiscal.requestFocus();
					 }
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// Util.validateNumero(e);
			}
		});
		pnlDatosPersonal.add(tfPrecioCompra);
		
		label_2 = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoPanel.label_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
		label_2.setBounds(16, 218, 98, 25);
		pnlDatosPersonal.add(label_2);
		
		tfDep01 = new JTextField();
		tfDep01.setColumns(10);
		tfDep01.setBounds(118, 218, 163, 25);
		pnlDatosPersonal.add(tfDep01);

		JPanel pnlInfo = new JPanel();
		tabbedPane.addTab("Info", null, pnlInfo, "");

		JLabel lblMarca = new JLabel("Marca");

		JLabel lblColor = new JLabel("Color");

		cbMarca = new JComboBox<Marca>(marcaComboBoxModel);
		cbMarca.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				cbColor.requestFocus();
			}
		});

		cbColor = new JComboBox<Color>(colorComboBoxModel);
		cbColor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbTamanho.requestFocus();
				}
			}
		});

		JLabel lblTamanho = new JLabel("Tamaño");

		cbTamanho = new JComboBox<Tamanho>(tamanhoComboBoxModel);
		cbTamanho.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbUMedida.requestFocus();
				}
			}
		});

		JLabel lblUnidadMedida = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblUnidadMedida.text"));

		cbUMedida = new JComboBox<UnidadMedida>(unidadMedidaComboBoxModel);
		cbUMedida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
			}
		});

		chActivo = new JCheckBox();
		chActivo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfUltimoPrecioCompra.requestFocus();
				}
			}
		});
		chActivo.setSelected(true);

		JLabel lblActivo = new JLabel("Activo");

		JLabel lblPrecioCompra = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblPrecioCompra.text"));

		tfUltimoPrecioCompra = new JTextField();
		tfUltimoPrecioCompra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPrecioPromedio.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfUltimoPrecioCompra.setColumns(10);

		tfPrecioPromedio = new JTextField();
		tfPrecioPromedio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfUltimaEntrada.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecioPromedio.setColumns(10);

		JLabel lblPrecioPromedio = new JLabel("P. Promedio");
		pnlInfo.setLayout(
				new MigLayout("", "[105px][163px][94px][163px]", "[30px][30px][30px][30px][30px][30px][30px][30px]"));
		pnlInfo.add(lblMarca, "cell 0 0,grow");
		pnlInfo.add(cbMarca, "cell 1 0,grow");
		pnlInfo.add(lblColor, "cell 0 1,grow");
		pnlInfo.add(cbColor, "cell 1 1,grow");
		pnlInfo.add(lblTamanho, "cell 0 2,grow");
		pnlInfo.add(cbTamanho, "cell 1 2,grow");
		pnlInfo.add(lblUnidadMedida, "cell 0 3,grow");
		pnlInfo.add(cbUMedida, "cell 1 3,grow");
		pnlInfo.add(lblPrecioCompra, "cell 0 5,grow");
		pnlInfo.add(tfUltimoPrecioCompra, "cell 1 5,grow");
		pnlInfo.add(lblPrecioPromedio, "cell 0 6,grow");
		pnlInfo.add(tfPrecioPromedio, "cell 1 6,grow");
		pnlInfo.add(lblActivo, "cell 0 7,grow");
		pnlInfo.add(chActivo, "cell 1 7,grow");

		JLabel lblImpuesto = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblImpuesto.text"));
		pnlInfo.add(lblImpuesto, "cell 0 4,grow");

		cbImpuesto = new JComboBox<Impuesto>(impuestoComboBoxModel);
		cbImpuesto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
			}
		});
		pnlInfo.add(cbImpuesto, "cell 1 4,grow");

		JLabel lblUltimaSalida = new JLabel("Ult. Salida");
		pnlInfo.add(lblUltimaSalida, "cell 2 1,grow");

		tfUltimaSalida = new JTextField();
		tfUltimaSalida.setEditable(false);
		tfUltimaSalida.setColumns(10);
		pnlInfo.add(tfUltimaSalida, "cell 3 1,grow");

		tfUltimaEntrada = new JTextField();
		tfUltimaEntrada.setEditable(false);
		tfUltimaEntrada.setColumns(10);
		pnlInfo.add(tfUltimaEntrada, "cell 3 0,grow");

		JLabel lblUltEntrada = new JLabel("Ult. Entrada");
		pnlInfo.add(lblUltEntrada, "cell 2 0,grow");

		label = new JLabel("Categoria");
		pnlInfo.add(label, "cell 2 2,grow");

		cbCategoria = new JComboBox<Categoria>(categoriaComboBoxModel);
		pnlInfo.add(cbCategoria, "cell 3 2,grow");

		label_1 = new JLabel("NCM");
		pnlInfo.add(label_1, "cell 2 3,grow");

		cbNcm = new JComboBox<Ncm>(ncmComboBoxModel);
		pnlInfo.add(cbNcm, "cell 3 3,grow");

		cbRegimen = new JComboBox<String>();
		cbRegimen.setModel(new DefaultComboBoxModel<String>(new String[] { "GENERAL", "TURISMO" }));
		pnlInfo.add(cbRegimen, "cell 3 4,grow");

		label_4 = new JLabel("Regimen");
		pnlInfo.add(label_4, "cell 2 4,grow");

		label_5 = new JLabel("Sección");
		pnlInfo.add(label_5, "cell 2 7,grow");

		tfSeccion = new JTextField();
		tfSeccion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfSeccion.selectAll();
			}
		});
		tfSeccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tabbedPane.setSelectedIndex(2);
				}
			}
		});
		tfSeccion.setColumns(10);
		pnlInfo.add(tfSeccion, "cell 3 7,grow");

		JPanel panelBotonera = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBotonera.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		getContentPane().add(panelBotonera, "cell 0 4,growx,aligny top");

		btnGuardar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.btnGuardar.text")); //$NON-NLS-1$
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnGuardar.setMnemonic('G');
		panelBotonera.add(btnGuardar);

		btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.btnCancelar.text")); //$NON-NLS-1$
		btnCancelar.setMnemonic('C');
		panelBotonera.add(btnCancelar);

		btnCerrar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.btnCerrar.text")); //$NON-NLS-1$
		btnCerrar.setMnemonic('E');
		panelBotonera.add(btnCerrar);

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
//				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
//					dispose();
//				}
//			}
//		});
		getContentPane().add(tfBuscador, "cell 0 0,grow");
		tfBuscador.setText("");
		tfBuscador.setColumns(10);

		btnBuscador = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.btnBuscar.text"));

		getContentPane().add(btnBuscador, "cell 0 0");
	}

	public JTextField getTfBuscador() {
		return tfBuscador;
	}

	public JButton getBtnBuscador() {
		return btnBuscador;
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JTextField getTfProductoId() {
		return tfProductoId;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public void setProductForm(Producto product) {
		tfProductoId.setText(product.getId() + "");
		// tfNombre.setText(product.getNombre());
		tfDescripcion.setText(product.getDescripcion());
//		tfReferencia.setText(product.getReferencia());
//		tfSubreferencia.setText(product.getSubreferencia());
		cbRegimen.setSelectedItem(product.getRegimen());
		tfSeccion.setText(product.getSeccion());
		tfPrecioCompra.setText(
				product.getPrecioCosto() != null ? FormatearValor.doubleAString(product.getPrecioCosto()) : "0");
		tfUltimoPrecioCompra.setText(
				product.getPrecioCosto() != null ? FormatearValor.doubleAString(product.getPrecioCosto()) : "0");
		tfPrecioPromedio.setText(product.getPrecioCostoPromedio() != null
				? FormatearValor.doubleAString(product.getPrecioCostoPromedio())
				: "0");
		//tfPeso.setText(product.getPeso() != null ? FormatearValor.doubleAString(product.getPeso()) : "");
		tfDep01.setText(product.getDepO1() != null ? FormatearValor.doubleAString(product.getDepO1()) : "");
//		tfCantidadPorCaja.setText(
//				product.getCantidadPorCaja() != null ? FormatearValor.doubleAString(product.getCantidadPorCaja()) : "");

		tfPrecioA.setText(
				product.getPrecioVentaA() != null ? FormatearValor.doubleAString(product.getPrecioVentaA()) : "0");
		tfPrecioB.setText(
				product.getPrecioVentaB() != null ? FormatearValor.doubleAString(product.getPrecioVentaB()) : "0");
		tfPrecioC.setText(
				product.getPrecioVentaC() != null ? FormatearValor.doubleAString(product.getPrecioVentaC()) : "0");
//		tfPrecioD.setText(
//				product.getPrecioVentaD() != null ? FormatearValor.doubleAString(product.getPrecioVentaD()) : "0");
//		tfPrecioE.setText(
//				product.getPrecioVentaE() != null ? FormatearValor.doubleAString(product.getPrecioVentaE()) : "0");
		imagenUrl = (product.getImagenUrl() != null ? product.getImagenUrl() : null);

		categoriaComboBoxModel.setSelectedItem(product.getCategoria());
		grupoComboBoxModel.setSelectedItem(product.getGrupo());
		ncmComboBoxModel.setSelectedItem(product.getNcm());
		impuestoComboBoxModel.setSelectedItem(product.getImpuesto());
		marcaComboBoxModel.setSelectedItem(product.getMarca());
		unidadMedidaComboBoxModel.setSelectedItem(product.getUnidadMedida());
		colorComboBoxModel.setSelectedItem(product.getColor());
		tamanhoComboBoxModel.setSelectedItem(product.getTamanho());

		// chEsPromo.setSelected(product.getEsPromo() == 1 ? true : false);
		chServicio.setSelected(product.getEsServicio() == 1 ? true : false);
		tfDesFiscal.setText(product.getDescripcionFiscal());

		chActivo.setSelected(product.getActivo() == 1 ? true : false);

		

		precioTableModel.clear();
		depositoTableModel.clear();

//    	if (!product.getPrecios().isEmpty()) {
//    		precioTableModel.addEntities(product.getPrecios());
//		}

//    	if (!product.getDepositos().isEmpty()) {
//    		depositoTableModel.addEntities(product.getDepositos());
//		}

		visualizarImagen();
	}

	public Producto getProductForm() {
		Producto product = new Producto();

		if (!tfProductoId.getText().isEmpty()) {
			product.setId(Long.parseLong(tfProductoId.getText()));
			product.setReferencia(tfProductoId.getText());
		}
		if (!tfDep01.getText().isEmpty()) {
			Double cantidad = FormatearValor.stringADouble(tfDep01.getText());
			product.setDepO1(cantidad);
		}
		// product.setNombre(tfNombre.getText());
		product.setDescripcion(tfDescripcion.getText());
		
		product.setSeccion(tfSeccion.getText());
		product.setRegimen((String) cbRegimen.getSelectedItem());
		
		product.setEsServicio(chServicio.isSelected() ? 1 : 0);
		product.setActivo(chActivo.isSelected() ? 1 : 0);
		product.setImagenUrl(imagenUrl);
		product.setDescripcionFiscal(tfDesFiscal.getText());
		product.setPrecioCosto(FormatearValor.stringADouble(tfPrecioCompra.getText()));
		product.setPrecioCostoPromedio(FormatearValor.stringADouble(tfPrecioPromedio.getText()));
		product.setCategoria(categoriaComboBoxModel.getSelectedItem());
		product.setGrupo(grupoComboBoxModel.getSelectedItem());
		product.setSubgrupo(subgrupoComboBoxModel.getSelectedItem());
		product.setMarca(marcaComboBoxModel.getSelectedItem());
		product.setNcm(ncmComboBoxModel.getSelectedItem());
		product.setImpuesto(impuestoComboBoxModel.getSelectedItem());
		product.setUnidadMedida(unidadMedidaComboBoxModel.getSelectedItem());
		product.setTamanho(tamanhoComboBoxModel.getSelectedItem());
		product.setColor(colorComboBoxModel.getSelectedItem());
		//product.setEsFraccionado(cbEsFraccionado.getSelectedIndex() == 0 ? 0 : 1);

		product.setPrecioVentaA(
				FormatearValor.stringADouble(!tfPrecioA.getText().isEmpty() ? tfPrecioA.getText() : "0"));
		product.setPrecioVentaB(
				FormatearValor.stringADouble(!tfPrecioB.getText().isEmpty() ? tfPrecioB.getText() : "0"));
		product.setPrecioVentaC(
				FormatearValor.stringADouble(!tfPrecioC.getText().isEmpty() ? tfPrecioC.getText() : "0"));
		
		return product;
	}

	public void clearForm() {
		tfProductoId.setText("");
		tfDescripcion.setText("");
		tfDesFiscal.setText("");
	
		tfSeccion.setText("");
		
		tfDep01.setText("");
		tfPrecioCompra.setText("");
		tfUltimoPrecioCompra.setText("");
		tfPrecioPromedio.setText("");
		cbCategoria.setSelectedIndex(0);
		cbUMedida.setSelectedIndex(0);
		cbTamanho.setSelectedIndex(0);
		cbColor.setSelectedIndex(0);
		cbMarca.setSelectedIndex(0);
		cbNcm.setSelectedIndex(0);
		cbImpuesto.setSelectedIndex(0);

		fileChooser = new JFileChooser();
		imagenUrl = null;
		// chEsProm
		chActivo.setSelected(true);

		tfPrecioA.setText("");
		tfPrecioB.setText("");
		tfPrecioC.setText("");
		
		while (precioTableModel.getRowCount() > 0) {
			precioTableModel.removeRow(0);
		}
	}

	private void habilitarFechas(boolean estado) {
//		tfFechaInicio.setEnabled(estado);
//		tfFechaFin.setEnabled(estado);
	}

	private void addListaPrecio() {
//    	ListaPrecio pr = listaComboBoxModel.getSelectedItem();
//    	precioTableModel.addEntity(getPrecioProducto());

//    	if (!precioTableModel.getEntities().isEmpty()) {
//    		precioTableModel.addEntity(getPrecioProducto());
//		} else {
//			boolean existInTable = precioTableModel.getEntities()
//					.stream().filter(e -> e.getNivelPrecioId().getNombre().equals(pr.getNombre()));

//			if (existInTable) {
//	    		Notifications.showAlert("Nivel de Precio ya fue agregado a la lista");
//	    		cbListaPrecio.requestFocus();
//			} else {
//				precioTableModel.addEntity(getPrecioProducto());
//			}	
//		}
	}

//    private ProductoPrecio getPrecioProducto() {
//    	ProductoPrecio precio = new ProductoPrecio();
//    	precio.setNivelPrecioId(listaComboBoxModel.getSelectedItem());
//    	precio.setValor(Double.valueOf(tfPrecioValor.getText()));
//    	return precio;
//    }

	private void removeListaPrecio() {
		int row[] = tbListaPrecio.getSelectedRows();

		if (tbListaPrecio.getSelectedRow() != -1) {
			for (Integer i = row.length; i > 0; i--) {
				precioTableModel.removeRow(row[i - 1]);
			}

			cbListaPrecio.requestFocus();
		} else {
			Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
		}
	}

	public JTextField getTfNombre() {
		return tfDescripcion;
	}

	public void setNewProducto(long id) {
		tfProductoId.setText(String.valueOf(id));
	}

	public JButton getBtnNuevo() {
		return btnNuevo;
	}

	public JTable getTbProducto() {
		return tbProducto;
	}

	public ProductTableModel getProductTableModel() {
		return productTableModel;
	}

	public void setProductTableModel(ProductTableModel productTableModel) {
		this.productTableModel = productTableModel;
	}

	private void visualizarImagen() {
		try {
			if (imagenUrl != null) {
				if (!imagenUrl.isEmpty()) {
					fileChooser = new JFileChooser();
					fileChooser.setSelectedFile(new File(imagenUrl));
					if (fileChooser.getSelectedFile().exists()) {
						zoom = new ImagenPanel(ImageIO.read(fileChooser.getSelectedFile()));
//						pnlImagenView.removeAll();
//						pnlImagenView.add(zoom);
//						pnlImagenView.repaint();
					}
				}
			} else {
				fileChooser = new JFileChooser();
				imagenUrl = "C:\\eclipse\\AgroProgreso\\stock-datajal\\src\\img\\noImagen.jpg";
				fileChooser.setSelectedFile(new File(imagenUrl));
				if (fileChooser.getSelectedFile().exists()) {
					zoom = new ImagenPanel(ImageIO.read(fileChooser.getSelectedFile()));
//					pnlImagenView.removeAll();
//					pnlImagenView.add(zoom);
//					pnlImagenView.repaint();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ProductoInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(ProductoInterfaz interfaz) {
		this.interfaz = interfaz;
	}
}
