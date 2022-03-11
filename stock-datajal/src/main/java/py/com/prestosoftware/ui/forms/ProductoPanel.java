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

	private JTextField tfDescripcion, tfDesFiscal, tfReferencia, tfSubreferencia, tfPeso;
	private JTextField tfUltimoPrecioCompra;
	private JTextField tfPrecioPromedio, tfProductoId;
	private JTextField tfUltimaSalida, tfUltimaEntrada;
	private JCheckBox chActivo;
	private JButton btnGuardar, btnCancelar, btnCerrar;
	private JButton btnExaminar, btnMasZoom, btnMenosZoom, btnRestaurar;
	private JComboBox<Grupo> cbGrupo;
	private JComboBox<Marca> cbMarca;
	private JComboBox<Color> cbColor;
	private JComboBox<Tamanho> cbTamanho;
	private JComboBox<UnidadMedida> cbUMedida;
	private JComboBox<Impuesto> cbImpuesto;
	private JComboBox<ListaPrecio> cbListaPrecio;
	private JComboBox<Subgrupo> cbSubgrupo;
	private JPanel pnlImagen, pnlImagenView;
	private JTable tbListaPrecio;
	private FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de Imagen", "jpg", "png");
	private JFileChooser fileChooser;
	private File directorio;
	private ImagenPanel zoom;
	private String imagenUrl = "";
	private JPanel pnlDeposito;
	private JTable tbDepositos;
	private JScrollPane scrollPaneDeposito;

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
	private JCheckBox cbDescripcionFiscal;
	private JLabel label;
	private JComboBox<Categoria> cbCategoria;
	private JLabel label_1;
	private JComboBox<Ncm> cbNcm;
	private JLabel label_2;
	private JLabel label_3;
	private JTextField tfStock;
	private JTextField tfCosto;
	private JComboBox<String> cbRegimen;
	private JLabel label_4;
	private JLabel label_5;
	private JTextField tfSeccion;
	private JLabel label_7;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;
	private JTextField tfPrecioE;
	private JTextField tfPrecioD;
	private JTextField tfPrecioC;
	private JTextField tfPrecioB;
	private JTextField tfPrecioA;
	private JLabel label_6;
	private JTextField tfCantidadPorCaja;
	private JLabel lblFraccionado;
	private JComboBox<String> cbEsFraccionado;
	private JButton btnNuevo;
	private JPanel pnlBuscador;
	private JLabel lblBuscador;
	private JTextField tfBuscador;
	private JButton btnBuscador;
	private JPanel panel;
	private JTable tbProducto;
	private JScrollPane scrollProducto;

	private ProductTableModel productTableModel;
	private JPanel panel_1;
	private JLabel label_13;
	private JComboBox<String> cbEsPromo;
	private JLabel lblFechaInicial;
	private JLabel lblFechaFinal;
	private JLabel lblPeriodoVigencia;
	private JXDatePicker tfFechaInicio;
	private JXDatePicker tfFechaFin;
	private JLabel label_12;
	private JLabel label_14;
	private JLabel label_16;

	private SubgrupoService subgrupoService;
	private ProductoInterfaz interfaz;

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
		getContentPane().setLayout(new MigLayout("", "[788px,grow]", "[][grow][25px][316px][45px]"));

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
		lblNombre.setBounds(16, 120, 98, 21);

		tfDescripcion = new JTextField();
		tfDescripcion.setNextFocusableComponent(tfDesFiscal);
		tfDescripcion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDescripcion.selectAll();
			}
		});
		tfDescripcion.setBounds(118, 120, 227, 21);
		((AbstractDocument) tfDescripcion.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfDescripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//tfDesFiscal.setText(tfDescripcion.getText());
					tfDesFiscal.requestFocus();
				}
			}
		});
		tfDescripcion.setColumns(10);

		tfDesFiscal = new JTextField();
		tfDesFiscal.setNextFocusableComponent(tfReferencia);
		tfDesFiscal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDesFiscal.selectAll();
			}
		});
		tfDesFiscal.setBounds(159, 145, 186, 21);
		((AbstractDocument) tfDesFiscal.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfDesFiscal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfReferencia.requestFocus();
				}
			}
		});
		tfDesFiscal.setColumns(10);

		JLabel lblDescFiscal = new JLabel("Desc. Fiscal:");
		lblDescFiscal.setBounds(16, 145, 98, 21);

		JLabel lblSubreferencia = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoPanel.lblSubreferencia.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSubreferencia.setBounds(16, 198, 98, 24);

		tfSubreferencia = new JTextField();
		tfDesFiscal.setNextFocusableComponent(tfPrecioA);
		tfSubreferencia.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfSubreferencia.selectAll();
			}
		});
		tfSubreferencia.setBounds(118, 198, 129, 24);
		((AbstractDocument) tfSubreferencia.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfSubreferencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPrecioA.requestFocus();
				}
			}
		});

		JLabel lblReferencia = new JLabel("Referencia");
		lblReferencia.setBounds(16, 170, 98, 24);

		tfReferencia = new JTextField();
		tfReferencia.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfReferencia.selectAll();
			}
		});
		tfReferencia.setBounds(118, 170, 227, 24);
		((AbstractDocument) tfReferencia.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfReferencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfSubreferencia.requestFocus();
				}
			}
		});
		tfReferencia.setColumns(10);

		tfPeso = new JTextField();
		tfPeso.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPeso.requestFocus();
			}
		});
		tfPeso.setBounds(521, 198, 129, 24);
		tfPeso.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPrecioA.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPeso.setColumns(10);

		JLabel lblPeso = new JLabel("Peso");
		lblPeso.setBounds(418, 202, 98, 20);

		cbGrupo = new JComboBox<Grupo>(grupoComboBoxModel);
		cbGrupo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				loadSubgruposByGrupo((Grupo) cbGrupo.getSelectedItem());
			}
		});
		cbGrupo.setBounds(118, 33, 180, 25);
		cbGrupo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbNcm.requestFocus();
				}
			}
		});

		JLabel lblGrupo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblGrupo.text")); //$NON-NLS-1$
		lblGrupo.setBounds(16, 33, 87, 25);

		JLabel lblSubGrupo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblSubGrupo.text")); //$NON-NLS-1$
		lblSubGrupo.setBounds(16, 62, 98, 25);

		cbSubgrupo = new JComboBox<Subgrupo>(subgrupoComboBoxModel);
		cbSubgrupo.setBounds(118, 62, 180, 25);
		pnlDatosPersonal.setLayout(null);
		pnlDatosPersonal.add(lblCodigo);
		pnlDatosPersonal.add(tfProductoId);
		pnlDatosPersonal.add(lblGrupo);
		pnlDatosPersonal.add(cbGrupo);
		pnlDatosPersonal.add(lblSubGrupo);
		pnlDatosPersonal.add(cbSubgrupo);
		pnlDatosPersonal.add(lblNombre);
		pnlDatosPersonal.add(tfDescripcion);
		pnlDatosPersonal.add(lblDescFiscal);
		pnlDatosPersonal.add(tfDesFiscal);
		pnlDatosPersonal.add(lblReferencia);
		pnlDatosPersonal.add(tfReferencia);
		pnlDatosPersonal.add(lblSubreferencia);
		pnlDatosPersonal.add(tfSubreferencia);
		pnlDatosPersonal.add(lblPeso);
		pnlDatosPersonal.add(tfPeso);

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
		label_7.setBounds(419, 4, 98, 25);
		pnlDatosPersonal.add(label_7);

		label_8 = new JLabel("Precio B");
		label_8.setBounds(419, 33, 98, 25);
		pnlDatosPersonal.add(label_8);

		label_9 = new JLabel("Precio C");
		label_9.setBounds(419, 62, 98, 25);
		pnlDatosPersonal.add(label_9);

		label_10 = new JLabel("Precio D");
		label_10.setBounds(419, 91, 98, 25);
		pnlDatosPersonal.add(label_10);

		label_11 = new JLabel("Precio E");
		label_11.setBounds(419, 120, 98, 21);
		pnlDatosPersonal.add(label_11);

		tfPrecioE = new JTextField();
		tfPrecioE.setNextFocusableComponent(cbEsFraccionado);
		tfPrecioE.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioE.selectAll();
			}
		});
		tfPrecioE.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbEsFraccionado.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecioE.setBounds(521, 120, 163, 21);
		tfPrecioE.setColumns(10);
		pnlDatosPersonal.add(tfPrecioE);

		tfPrecioD = new JTextField();
		tfPrecioD.setNextFocusableComponent(tfPrecioE);
		tfPrecioD.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioD.selectAll();
			}
		});
		tfPrecioD.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfPrecioE.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecioD.setBounds(521, 91, 163, 25);
		tfPrecioD.setColumns(10);
		pnlDatosPersonal.add(tfPrecioD);

		tfPrecioC = new JTextField();
		tfPrecioC.setNextFocusableComponent(tfPrecioD);
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
					tfPrecioD.requestFocus();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecioC.setBounds(521, 62, 163, 25);
		tfPrecioC.setColumns(10);
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
		tfPrecioB.setBounds(521, 33, 163, 25);
		tfPrecioB.setColumns(10);
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
		tfPrecioA.setBounds(521, 4, 163, 25);
		tfPrecioA.setColumns(10);
		pnlDatosPersonal.add(tfPrecioA);

		label_6 = new JLabel("Cant. por Caja");
		label_6.setBounds(419, 170, 98, 24);
		pnlDatosPersonal.add(label_6);

		tfCantidadPorCaja = new JTextField();
		tfCantidadPorCaja.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfCantidadPorCaja.selectAll();
			}
		});
		tfCantidadPorCaja.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tabbedPane.setSelectedIndex(1);
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfCantidadPorCaja.setBounds(521, 170, 114, 24);
		tfCantidadPorCaja.setColumns(10);
		pnlDatosPersonal.add(tfCantidadPorCaja);

		lblFraccionado = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblFraccionado.text"));
		lblFraccionado.setBounds(419, 145, 98, 21);
		pnlDatosPersonal.add(lblFraccionado);

		cbEsFraccionado = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[] { "NO", "SI" }));
		cbEsFraccionado.setNextFocusableComponent(tfCantidadPorCaja);
		cbEsFraccionado.setBounds(521, 145, 73, 21);
		pnlDatosPersonal.add(cbEsFraccionado);

		btnNuevo = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.btnNuevo.text")); //$NON-NLS-1$
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNuevo.setBounds(778, 16, 73, 25);
		btnNuevo.setMnemonic('N');
		pnlDatosPersonal.add(btnNuevo);

		label_12 = new JLabel("*");
		label_12.setVerticalAlignment(SwingConstants.BOTTOM);
		label_12.setHorizontalAlignment(SwingConstants.CENTER);
		label_12.setForeground(java.awt.Color.RED);
		label_12.setFont(new Font("Dialog", Font.BOLD, 20));
		label_12.setBounds(99, 33, 18, 25);
		pnlDatosPersonal.add(label_12);

		label_14 = new JLabel("*");
		label_14.setVerticalAlignment(SwingConstants.BOTTOM);
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setForeground(java.awt.Color.RED);
		label_14.setFont(new Font("Dialog", Font.BOLD, 20));
		label_14.setBounds(96, 62, 18, 25);
		pnlDatosPersonal.add(label_14);

		label_16 = new JLabel("*");
		label_16.setVerticalAlignment(SwingConstants.BOTTOM);
		label_16.setHorizontalAlignment(SwingConstants.CENTER);
		label_16.setForeground(java.awt.Color.RED);
		label_16.setFont(new Font("Dialog", Font.BOLD, 20));
		label_16.setBounds(96, 170, 18, 25);
		pnlDatosPersonal.add(label_16);
		
		cbDescripcionFiscal = new JCheckBox();
		cbDescripcionFiscal.setBounds(118, 147, 28, 21);
		cbDescripcionFiscal.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {                 
            	tfDesFiscal.setText(e.getStateChange()==1?"EN CUMPLIMIENTO DE LA RES. 39/2020, DETALLAMOS LAS SIGUIENTES INFORMACIONES COMPLEMENTARIAS: REGISTRO"
            			+ " DE IMPORTADOR N° 1489, REGISTRO DE ENTIDAD COMERCIAL ANTE SENAVE: N° 216, REGISTRO DEL PRODUCTO EN SENAVE N° 608 ":" ");    
            }    
         });  
		pnlDatosPersonal.add(cbDescripcionFiscal);

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
		tfUltimoPrecioCompra.setEditable(false);
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
		tfPrecioPromedio.setEditable(false);
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

		label_2 = new JLabel("Medio CIF");
		pnlInfo.add(label_2, "cell 2 6,grow");

		label_3 = new JLabel("Saldo Disp.");
		pnlInfo.add(label_3, "cell 2 5,alignx left,growy");

		tfStock = new JTextField();
		tfStock.setEditable(false);
		tfStock.setColumns(10);
		pnlInfo.add(tfStock, "cell 3 5,grow");

		tfCosto = new JTextField();
		tfCosto.setEditable(false);
		tfCosto.setColumns(10);
		pnlInfo.add(tfCosto, "cell 3 6,grow");

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

		/*
		 * JPanel pnlListaPrecio = new JPanel(); tabbedPane.addTab("Lista de Precios",
		 * null, pnlListaPrecio, null); tabbedPane.setEnabledAt(2, false);
		 * pnlListaPrecio.setLayout(new MigLayout("", "[695px]", "[56px][165px]"));
		 * 
		 * scrollPane = new JScrollPane(); pnlListaPrecio.add(scrollPane,
		 * "cell 0 1,grow");
		 * 
		 * tbListaPrecio = new JTable(precioTableModel);
		 * tbListaPrecio.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * scrollPane.setViewportView(tbListaPrecio);
		 * 
		 * pnlNivel = new JPanel(); pnlListaPrecio.add(pnlNivel, "cell 0 0,grow");
		 * pnlNivel.setLayout(null);
		 * 
		 * lblNivelPrecio = new JLabel("Nivel Precio"); lblNivelPrecio.setBounds(6, 6,
		 * 97, 16); pnlNivel.add(lblNivelPrecio);
		 * 
		 * lblValor = new JLabel("Valor"); lblValor.setBounds(161, 6, 61, 16);
		 * pnlNivel.add(lblValor);
		 * 
		 * cbListaPrecio = new JComboBox<ListaPrecio>(listaComboBoxModel);
		 * cbListaPrecio.addKeyListener(new KeyAdapter() {
		 * 
		 * @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() ==
		 * KeyEvent.VK_ENTER) { tfPrecioValor.requestFocus(); } } });
		 * cbListaPrecio.setBounds(6, 24, 143, 26); pnlNivel.add(cbListaPrecio);
		 * 
		 * tfPrecioValor = new JTextField(); tfPrecioValor.addKeyListener(new
		 * KeyAdapter() {
		 * 
		 * @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() ==
		 * KeyEvent.VK_ENTER) { btnAddPrecio.requestFocus(); } }
		 * 
		 * @Override public void keyTyped(KeyEvent e) { Util.validateNumero(e); } });
		 * tfPrecioValor.setText(""); tfPrecioValor.setBounds(161, 24, 162, 26);
		 * pnlNivel.add(tfPrecioValor); tfPrecioValor.setColumns(10);
		 * 
		 * btnAddPrecio = new
		 * JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").
		 * getString("ProductoPanel.button.text")); //$NON-NLS-1$ //$NON-NLS-2$
		 * btnAddPrecio.addKeyListener(new KeyAdapter() {
		 * 
		 * @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() ==
		 * KeyEvent.VK_ENTER) { addListaPrecio(); } } });
		 * btnAddPrecio.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { addListaPrecio(); } });
		 * btnAddPrecio.setBounds(335, 24, 61, 29); pnlNivel.add(btnAddPrecio);
		 * 
		 * btnRemovePrecio = new
		 * JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").
		 * getString("ProductoPanel.button_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		 * btnRemovePrecio.addKeyListener(new KeyAdapter() {
		 * 
		 * @Override public void keyPressed(KeyEvent e) { if (e.getKeyCode() ==
		 * KeyEvent.VK_ENTER) { removeListaPrecio(); } } });
		 * btnRemovePrecio.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { removeListaPrecio(); } });
		 * btnRemovePrecio.setBounds(397, 24, 61, 29); pnlNivel.add(btnRemovePrecio);
		 */
		pnlDeposito = new JPanel();
		tabbedPane.addTab("Stock en Depositos", null, pnlDeposito, null);
		pnlDeposito.setLayout(new MigLayout("", "[693px]", "[123px]"));

		scrollPaneDeposito = new JScrollPane();
		pnlDeposito.add(scrollPaneDeposito, "cell 0 0,grow");

		tbDepositos = new JTable(depositoTableModel);
		scrollPaneDeposito.setViewportView(tbDepositos);

		pnlImagen = new JPanel();
		tabbedPane.addTab("Imagen", null, pnlImagen, null);

		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);

		pnlImagenView = new JPanel();
		pnlImagenView.setLayout(gridLayout);

		btnExaminar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.btnExaminar.text")); //$NON-NLS-1$
		btnExaminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				examinarImagen();
			}
		});

		btnMasZoom = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.button.text")); //$NON-NLS-1$
		btnMasZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aumentarImagen();
			}
		});

		btnMenosZoom = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.button_1.text")); //$NON-NLS-1$
		btnMenosZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disminuirImagen();
			}
		});

		btnRestaurar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.btnNewButton.text")); //$NON-NLS-1$
		btnRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restaurarImagen();
			}
		});
		pnlImagen.setLayout(new MigLayout("", "[117px][117px][50px][1px][165px]", "[278px][25px]"));
		pnlImagen.add(pnlImagenView, "cell 0 0 5 1,grow");
		pnlImagen.add(btnExaminar, "cell 0 1,growx,aligny top");
		pnlImagen.add(btnMasZoom, "cell 2 1,growx,aligny top");
		pnlImagen.add(btnRestaurar, "cell 4 1,alignx right,aligny top");
		pnlImagen.add(btnMenosZoom, "cell 4 1,alignx left,aligny top");

		panel_1 = new JPanel();
		tabbedPane.addTab("Promociones", null, panel_1, null);
		panel_1.setLayout(null);

		label_13 = new JLabel("Es promo");
		label_13.setBounds(12, 8, 87, 25);
		panel_1.add(label_13);

		cbEsPromo = new JComboBox<String>();
		cbEsPromo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbEsPromo.getSelectedItem().equals("SI"))
					habilitarFechas(true);
				else
					habilitarFechas(false);
			}
		});
		cbEsPromo.setModel(new DefaultComboBoxModel<String>(new String[] { "NO", "SI" }));
		cbEsPromo.setBounds(117, 8, 72, 25);
		panel_1.add(cbEsPromo);

		lblFechaInicial = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblFechaInicial.text")); //$NON-NLS-1$
		lblFechaInicial.setBounds(12, 76, 87, 25);
		panel_1.add(lblFechaInicial);

		lblFechaFinal = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblFechaFinal.text")); //$NON-NLS-1$
		lblFechaFinal.setBounds(12, 118, 87, 25);
		panel_1.add(lblFechaFinal);

		lblPeriodoVigencia = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblPeriodoVigencia.text")); //$NON-NLS-1$
		lblPeriodoVigencia.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPeriodoVigencia.setForeground(java.awt.Color.RED);
		lblPeriodoVigencia.setBounds(12, 45, 165, 25);
		panel_1.add(lblPeriodoVigencia);

		tfFechaInicio = new JXDatePicker(new Date());
		tfFechaInicio.setEnabled(false);
		tfFechaInicio.setBounds(117, 79, 114, 19);
		panel_1.add(tfFechaInicio);

		tfFechaFin = new JXDatePicker(new Date());
		tfFechaFin.setEnabled(false);
		tfFechaFin.setBounds(117, 119, 114, 19);
		panel_1.add(tfFechaFin);

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

	private void loadSubgruposByGrupo(Grupo grupo) {
		java.util.List<Subgrupo> subgrupos = subgrupoService.findByGrupo(grupo);

		if (subgrupos == null || subgrupos.size() == 0)
			return;

		subgrupoComboBoxModel.clear();
		subgrupoComboBoxModel.addElements(subgrupos);
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
		
		tfReferencia.setText(product.getReferencia());
		tfSubreferencia.setText(product.getSubreferencia());
		cbRegimen.setSelectedItem(product.getRegimen());
		tfSeccion.setText(product.getSeccion());
		tfCosto.setText(
				product.getPrecioCosto() != null ? FormatearValor.doubleAString(product.getPrecioCosto()) : "0");
		tfPeso.setText(product.getPeso() != null ? FormatearValor.doubleAString(product.getPeso()) : "");
		tfStock.setText(product.getStock() != null ? FormatearValor.doubleAString(product.getStock()) : "");
		tfCantidadPorCaja.setText(
				product.getCantidadPorCaja() != null ? FormatearValor.doubleAString(product.getCantidadPorCaja()) : "");

		tfPrecioA.setText(
				product.getPrecioVentaA() != null ? FormatearValor.doubleAString(product.getPrecioVentaA()) : "0");
		tfPrecioB.setText(
				product.getPrecioVentaB() != null ? FormatearValor.doubleAString(product.getPrecioVentaB()) : "0");
		tfPrecioC.setText(
				product.getPrecioVentaC() != null ? FormatearValor.doubleAString(product.getPrecioVentaC()) : "0");
		tfPrecioD.setText(
				product.getPrecioVentaD() != null ? FormatearValor.doubleAString(product.getPrecioVentaD()) : "0");
		tfPrecioE.setText(
				product.getPrecioVentaE() != null ? FormatearValor.doubleAString(product.getPrecioVentaE()) : "0");
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
		cbDescripcionFiscal.setSelected(product.getDescripcionFiscal().isEmpty()?false:true);
		
		chActivo.setSelected(product.getActivo() == 1 ? true : false);

		cbEsFraccionado.setSelectedIndex(product.getEsFraccionado() == 1 ? 1 : 0);

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
		}

		// product.setNombre(tfNombre.getText());
		product.setDescripcion(tfDescripcion.getText());
		product.setReferencia(tfReferencia.getText());
		product.setSubreferencia(tfSubreferencia.getText());
		product.setSeccion(tfSeccion.getText());
		product.setRegimen((String) cbRegimen.getSelectedItem());
		product.setCantidadPorCaja(
				tfCantidadPorCaja.getText().isEmpty() ? 0 : Double.valueOf(tfCantidadPorCaja.getText()));
		product.setPeso(tfPeso.getText().isEmpty() ? 0 : Double.valueOf(tfPeso.getText()));
		product.setEsPromo((String) cbEsPromo.getSelectedItem());
		product.setEsServicio(chServicio.isSelected() ? 1 : 0);
		product.setActivo(chActivo.isSelected() ? 1 : 0);
		product.setImagenUrl(imagenUrl);
		product.setDescripcionFiscal(tfDesFiscal.getText());

		product.setCategoria(categoriaComboBoxModel.getSelectedItem());
		product.setGrupo(grupoComboBoxModel.getSelectedItem());
		product.setSubgrupo(subgrupoComboBoxModel.getSelectedItem());
		product.setMarca(marcaComboBoxModel.getSelectedItem());
		product.setNcm(ncmComboBoxModel.getSelectedItem());
		product.setImpuesto(impuestoComboBoxModel.getSelectedItem());
		product.setUnidadMedida(unidadMedidaComboBoxModel.getSelectedItem());
		product.setTamanho(tamanhoComboBoxModel.getSelectedItem());
		product.setColor(colorComboBoxModel.getSelectedItem());
		product.setEsFraccionado(cbEsFraccionado.getSelectedIndex() == 0 ? 0 : 1);

		product.setPrecioVentaA(
				FormatearValor.stringADouble(!tfPrecioA.getText().isEmpty() ? tfPrecioA.getText() : "0"));
		product.setPrecioVentaB(
				FormatearValor.stringADouble(!tfPrecioB.getText().isEmpty() ? tfPrecioB.getText() : "0"));
		product.setPrecioVentaC(
				FormatearValor.stringADouble(!tfPrecioC.getText().isEmpty() ? tfPrecioC.getText() : "0"));
		product.setPrecioVentaD(
				FormatearValor.stringADouble(!tfPrecioD.getText().isEmpty() ? tfPrecioD.getText() : "0"));
		product.setPrecioVentaE(
				FormatearValor.stringADouble(!tfPrecioE.getText().isEmpty() ? tfPrecioE.getText() : "0"));

		return product;
	}

	public void clearForm() {
		tfProductoId.setText("");
		tfDescripcion.setText("");
		tfDesFiscal.setText("");
		tfReferencia.setText("");
		tfSubreferencia.setText("");
		tfSeccion.setText("");
		tfCosto.setText("");
		tfPeso.setText("");
		tfStock.setText("");
		tfCantidadPorCaja.setText("");
		tfUltimoPrecioCompra.setText("");
		tfPrecioPromedio.setText("");
		cbCategoria.setSelectedIndex(0);
		cbGrupo.setSelectedIndex(0);
		cbUMedida.setSelectedIndex(0);
		cbTamanho.setSelectedIndex(0);
		cbColor.setSelectedIndex(0);
		cbMarca.setSelectedIndex(0);
		cbNcm.setSelectedIndex(0);
		cbSubgrupo.setSelectedIndex(0);
		cbImpuesto.setSelectedIndex(0);
		cbDescripcionFiscal.setSelected(false);
		pnlImagenView = new JPanel();
		pnlImagenView.repaint();
		pnlImagen = new JPanel();
		pnlImagen.repaint();
		fileChooser = new JFileChooser();
		imagenUrl = null;
		// chEsProm
		chActivo.setSelected(true);

		tfPrecioA.setText("");
		tfPrecioB.setText("");
		tfPrecioC.setText("");
		tfPrecioD.setText("");
		tfPrecioE.setText("");

		while (precioTableModel.getRowCount() > 0) {
			precioTableModel.removeRow(0);
		}
	}

	private void habilitarFechas(boolean estado) {
		tfFechaInicio.setEnabled(estado);
		tfFechaFin.setEnabled(estado);
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

	private void examinarImagen() {
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);
		fileChooser.setCurrentDirectory(directorio);
		int result = fileChooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				zoom = new ImagenPanel(ImageIO.read(fileChooser.getSelectedFile()));
				pnlImagenView.removeAll();
				pnlImagenView.add(zoom);
				pnlImagenView.repaint();
				directorio = fileChooser.getCurrentDirectory();
				File fichero = fileChooser.getSelectedFile();
				imagenUrl = fichero.getAbsolutePath();
				imagenUrl = imagenUrl.replace("\\", "\\\\");
			} catch (IOException e) {
				System.out.println("Error: " + e);
			}
		}
	}

	public JTable getTbProducto() {
		return tbProducto;
	}

	private void visualizarImagen() {
		try {
			if (imagenUrl != null) {
				if (!imagenUrl.isEmpty()) {
					fileChooser = new JFileChooser();
					fileChooser.setSelectedFile(new File(imagenUrl));
					if (fileChooser.getSelectedFile().exists()) {
						zoom = new ImagenPanel(ImageIO.read(fileChooser.getSelectedFile()));
						pnlImagenView.removeAll();
						pnlImagenView.add(zoom);
						pnlImagenView.repaint();
					}
				}
			} else {
				fileChooser = new JFileChooser();
				imagenUrl = "C:\\eclipse\\AgroProgreso\\stock-datajal\\src\\img\\noImagen.jpg";
				fileChooser.setSelectedFile(new File(imagenUrl));
				if (fileChooser.getSelectedFile().exists()) {
					zoom = new ImagenPanel(ImageIO.read(fileChooser.getSelectedFile()));
					pnlImagenView.removeAll();
					pnlImagenView.add(zoom);
					pnlImagenView.repaint();
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

	private void aumentarImagen() {
		zoom.Aumentar(15);
	}

	private void disminuirImagen() {
		zoom.Disminuir(15);
	}

	private void restaurarImagen() {
		zoom.Restaurar();
	}
}
