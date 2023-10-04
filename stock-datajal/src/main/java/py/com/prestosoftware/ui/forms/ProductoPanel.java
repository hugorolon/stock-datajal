package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.miginfocom.swing.MigLayout;
import py.com.prestosoftware.data.models.Categoria;
import py.com.prestosoftware.data.models.Impuesto;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.domain.services.SubgrupoService;
import py.com.prestosoftware.ui.controllers.MarcaController;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.ImagenPanel;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.MarcaInterfaz;
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
public class ProductoPanel extends JDialog implements MarcaInterfaz{

	private static final long serialVersionUID = 1L;

	private static final int MARCA_CODE = 1;
	private JTextField tfDescripcion;
	private JTextField tfProductoId;
	private JCheckBox chActivo;
	private JButton btnGuardar, btnCancelar, btnCerrar;
	private JComboBox<Marca> cbMarca;
	private JComboBox<Categoria> cbAgrupacion;
	private JComboBox<Marca> cbMarcas;
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
	private ImpuestoComboBoxModel impuestoComboBoxModel;
	private MarcaComboBoxModel marcaComboBoxModel;
	private ColorComboBoxModel colorComboBoxModel;
	private TamanhoComboBoxModel tamanhoComboBoxModel;
	private UnidadMedidaComboBoxModel unidadMedidaComboBoxModel;
	private SubgrupoComboBoxModel subgrupoComboBoxModel;
	private ProductoPrecioTableModel precioTableModel;
	private ProductoDepositoTableModel depositoTableModel;
	private JLabel label_7;
	//private JTextField tfPrecioB;
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
	private JTabbedPane tabbedPane;
	private JPanel pnlLubricantes;
	private JPanel pnlFiltros;

	private SubgrupoService subgrupoService;
	private ProductoInterfaz interfaz;
	private JTextField tfPrecioCompra;
	private JTextField tfBase;
	private JLabel label_2;
	private JTextField tfDep01;
	private JLabel lblNewLabel_1;
	private JTextField tfOtrasReferencias;
	private JButton btnNewMarcas;
	private MarcaController marcaController;
	private JTextField tfCodigo;
	private JTextField tfCodigoSec;
	private JLabel lblCdigoSec;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField tfViscocidad;
	private JLabel lblNewLabel_4;
	private JTextField tfOrigen;
	private JLabel lblNewLabel_5;
	private JTextField tfEnvase;
	private JTextField tfCodigoFram;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JTextField tfCodigoMan;
	private JLabel lblNewLabel_8;
	private JTextField tfReferencia;
	private JTextField tfModeloAplicacion;
	
 
	@Autowired
	public ProductoPanel(GrupoComboBoxModel grupoComboBoxModel, 
			CategoriaComboBoxModel categoriaComboBoxModel, ImpuestoComboBoxModel impuestoComboBoxModel,
			MarcaComboBoxModel marcaComboBoxModel, ColorComboBoxModel colorComboBoxModel,
			SubgrupoComboBoxModel subgrupoComboBoxModel, TamanhoComboBoxModel tamanhoComboBoxModel,
			UnidadMedidaComboBoxModel unidadMedidaComboBoxModel, ListaPrecioComboBoxModel listaComboBoxModel,
			ProductoPrecioTableModel precioTableModel, ProductoDepositoTableModel depositoTableModel,
			ProductTableModel productTableModel, SubgrupoService subgrupoService, MarcaController marcaController) {
		this.grupoComboBoxModel = grupoComboBoxModel;
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
		this.marcaController= marcaController;

		initComponents();
		AutoCompleteDecorator.decorate(cbMarcas);
		AutoCompleteDecorator.decorate(cbAgrupacion);
		Util.setupScreen(this);
	}

	private void initComponents() {
		setSize(1032, 719);
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
		tbProducto.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
               
            }
        });
		tbProducto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		DefaultTableCellRenderer alignRenderer= new DefaultTableCellRenderer();
		alignRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbProducto.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		tbProducto.getColumnModel().getColumn(0).setPreferredWidth(75);
		tbProducto.getColumnModel().getColumn(0).setCellRenderer(alignRenderer);
		tbProducto.getColumnModel().getColumn(1).setPreferredWidth(100);
		tbProducto.getColumnModel().getColumn(2).setPreferredWidth(275);
		scrollProducto.setViewportView(tbProducto);

		pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, "cell 0 2,growx,aligny top");
		pnlBuscador.setLayout(new BorderLayout(10, 10));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, "cell 0 3,grow");

		JPanel pnlDatosPersonal = new JPanel();
		tabbedPane.addTab(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.pnlDatosPersonal.arg0"), null, pnlDatosPersonal, null); //$NON-NLS-1$
		tabbedPane.setEnabledAt(0, true);

		JLabel lblCodigo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblCodigo.text")); //$NON-NLS-1$
		lblCodigo.setBounds(10, 4, 98, 25);

		tfProductoId = new JTextField();
		tfProductoId.setEditable(false);
		tfProductoId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfProductoId.setBounds(112, 4, 79, 25);

		JLabel lblNombre = new JLabel("Descripción");
		lblNombre.setBounds(10, 64, 98, 25);

		tfDescripcion = new JTextField();
		tfDescripcion.setNextFocusableComponent(tfPrecioCompra);
		tfDescripcion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDescripcion.selectAll();
			}
		});
		tfDescripcion.setBounds(112, 64, 831, 25);
		((AbstractDocument) tfDescripcion.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfDescripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// tfDesFiscal.setText(tfDescripcion.getText());
					tfPrecioCompra.requestFocus();
				}
			}
		});
		tfDescripcion.setColumns(10);
		pnlDatosPersonal.setLayout(null);
		pnlDatosPersonal.add(lblCodigo);
		pnlDatosPersonal.add(tfProductoId);
		pnlDatosPersonal.add(lblNombre);
		pnlDatosPersonal.add(tfDescripcion);

		label_7 = new JLabel("Precio A");
		label_7.setBounds(10, 124, 104, 25);
		pnlDatosPersonal.add(label_7);


		tfPrecioA = new JTextField();
		tfPrecioA.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioA.selectAll();
			}
		});
		tfPrecioA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					Double precioA = FormatearValor.stringToDouble(tfPrecioA.getText());
					tfPrecioA.setText(FormatearValor.doubleAString(precioA));
					//tfPrecioC.requestFocus();
				}else {
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						cbMarcas.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		tfPrecioA.setBounds(112, 124, 163, 25);
		tfPrecioA.setColumns(10);
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

		tfPrecioCompra = new JTextField();
		tfPrecioCompra.setColumns(10);
		tfPrecioCompra.setBounds(112, 94, 163, 25);
		tfPrecioCompra.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioCompra.requestFocus();
			}
		});
		tfPrecioCompra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					Double precioCompra = FormatearValor.stringToDouble(tfPrecioCompra.getText());
					tfPrecioCompra.setText(FormatearValor.doubleAString(precioCompra));
					tfPrecioA.requestFocus();
				} else {
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						tfDescripcion.requestFocus();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
		});
		pnlDatosPersonal.add(tfPrecioCompra);

		label_2 = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.label_2.text")); //$NON-NLS-1$
		label_2.setBounds(10, 154, 104, 25);
		pnlDatosPersonal.add(label_2);

		tfDep01 = new JTextField();
		tfDep01.setColumns(10);
		tfDep01.setBounds(112, 154, 163, 25);
		pnlDatosPersonal.add(tfDep01);

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblNewLabel.text")); //$NON-NLS-1$
		lblNewLabel.setBounds(10, 94, 92, 25);
		pnlDatosPersonal.add(lblNewLabel);
		
		JLabel lblCdigo = new JLabel("Código"); //$NON-NLS-1$ //$NON-NLS-2$
		lblCdigo.setBounds(10, 34, 98, 25);
		pnlDatosPersonal.add(lblCdigo);
		
		tfCodigo = new JTextField();
		//tfCodigo.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoPanel.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		tfCodigo.setBounds(112, 34, 163, 25);
		pnlDatosPersonal.add(tfCodigo);
		tfCodigo.setColumns(10);
		
		tfCodigoSec = new JTextField();
		tfCodigoSec.setColumns(10);
		tfCodigoSec.setBounds(410, 34, 163, 25);
		pnlDatosPersonal.add(tfCodigoSec);
		
		lblCdigoSec = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoPanel.lblCdigoSec.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblCdigoSec.setBounds(307, 34, 93, 21);
		pnlDatosPersonal.add(lblCdigoSec);
		
		cbMarcas = new JComboBox<Marca>(marcaComboBoxModel);
		cbMarcas.setBounds(112, 184, 163, 25);
		pnlDatosPersonal.add(cbMarcas);
		
		JLabel label_2_1 = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoPanel.label_2_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		label_2_1.setBounds(10, 184, 104, 25);
		pnlDatosPersonal.add(label_2_1);
		
		JLabel label_2_2 = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoPanel.label_2_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
		label_2_2.setBounds(10, 214, 104, 25);
		pnlDatosPersonal.add(label_2_2);
		
		cbAgrupacion = new JComboBox<Categoria>(categoriaComboBoxModel);
		cbAgrupacion.setBounds(112, 214, 163, 25);
		pnlDatosPersonal.add(cbAgrupacion);
		
		JLabel lblNewLabel_9 = new JLabel("Modelo Aplicación"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_9.setBounds(10, 244, 98, 25);
		pnlDatosPersonal.add(lblNewLabel_9);
		
		tfModeloAplicacion = new JTextField();
		tfModeloAplicacion.setBounds(112, 244, 831, 25);
		pnlDatosPersonal.add(tfModeloAplicacion);
		tfModeloAplicacion.setColumns(10);
		
		pnlLubricantes = new JPanel();
		tabbedPane.addTab("Lubricantes", null, pnlLubricantes, "");
		pnlLubricantes.setLayout(null);
		
		tfBase = new JTextField();
		tfBase.setBounds(79, 24, 96, 25);
		//textField.setText(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoPanel.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		pnlLubricantes.add(tfBase);
		tfBase.setColumns(10);
		
		lblNewLabel_2 = new JLabel("Base"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_2.setBounds(10, 24, 45, 25);
		pnlLubricantes.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Viscocidad"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_3.setBounds(10, 54, 59, 25);
		pnlLubricantes.add(lblNewLabel_3);
		
		tfViscocidad = new JTextField();
		tfViscocidad.setBounds(79, 54, 96, 25);
		pnlLubricantes.add(tfViscocidad);
		tfViscocidad.setColumns(10);
		
		lblNewLabel_4 = new JLabel("Origen"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_4.setBounds(10, 84, 45, 25);
		pnlLubricantes.add(lblNewLabel_4);
		
		tfOrigen = new JTextField();
		tfOrigen.setBounds(79, 84, 96, 25);
		pnlLubricantes.add(tfOrigen);
		tfOrigen.setColumns(10);
		
		lblNewLabel_5 = new JLabel("Envase"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_5.setBounds(10, 114, 45, 25);
		pnlLubricantes.add(lblNewLabel_5);
		
		tfEnvase = new JTextField();
		tfEnvase.setBounds(79, 114, 96, 25);
		pnlLubricantes.add(tfEnvase);
		tfEnvase.setColumns(10);
		
		pnlFiltros = new JPanel();
		tabbedPane.addTab("Filtros", null, pnlFiltros, "");
		pnlFiltros.setLayout(null);
		
		tfCodigoFram = new JTextField();
		tfCodigoFram.setBounds(81, 21, 96, 25);
		pnlFiltros.add(tfCodigoFram);
		tfCodigoFram.setColumns(10);
		
		lblNewLabel_6 = new JLabel("Código Fram"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_6.setBounds(10, 24, 73, 25);
		pnlFiltros.add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("Código Mann"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_7.setBounds(10, 50, 73, 25);
		pnlFiltros.add(lblNewLabel_7);
		
		tfCodigoMan = new JTextField();
		tfCodigoMan.setBounds(81, 50, 96, 25);
		pnlFiltros.add(tfCodigoMan);
		tfCodigoMan.setColumns(10);
		

		JPanel pnlInfo = new JPanel();
		tabbedPane.addTab("Info", null, pnlInfo, "");

		JLabel lblMarca = new JLabel("Marca");

		chActivo = new JCheckBox();
		chActivo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					btnGuardar.requestFocus();
				}
			}
		});
		chActivo.setSelected(true);

		JLabel lblActivo = new JLabel("Activo");
		pnlInfo.setLayout(new MigLayout("", "[114.00px][240.00px,grow][159.00px][163px]", "[30px][30px][30px][30px][30px][30px][30px][30px]"));
		pnlInfo.add(lblMarca, "flowy,cell 0 0,alignx left,aligny center");
		
				cbMarca = new JComboBox<Marca>(marcaComboBoxModel);
				cbMarca.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
								|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
							tfOtrasReferencias.requestFocus();
						}
					}
				});
				pnlInfo.add(cbMarca, "cell 1 0,grow");
		
		btnNewMarcas = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("ProductoPanel.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewMarcas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(MARCA_CODE);
			}
		});
		pnlInfo.add(btnNewMarcas, "cell 2 0,alignx left,aligny center");
		
				cbImpuesto = new JComboBox<Impuesto>(impuestoComboBoxModel);
				cbImpuesto.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
								|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
							chActivo.requestFocus();
						} else {
							if (e.getKeyCode() == KeyEvent.VK_UP) {
								tfOtrasReferencias.requestFocus();
							}
						}
					}
				});
								
								lblNewLabel_8 = new JLabel("Referencia"); //$NON-NLS-1$ //$NON-NLS-2$
								pnlInfo.add(lblNewLabel_8, "cell 0 1,alignx left");
								
								tfReferencia = new JTextField();
								pnlInfo.add(tfReferencia, "cell 1 1,grow");
								tfReferencia.setColumns(10);
						
								lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
										.getString("ProductoPanel.lblNewLabel_1.text")); //$NON-NLS-1$
								pnlInfo.add(lblNewLabel_1, "cell 0 2,alignx left");
						
								tfOtrasReferencias = new JTextField();
								((AbstractDocument) tfOtrasReferencias.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
								tfOtrasReferencias.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										tfOtrasReferencias.requestFocus();
									}
								});
								tfOtrasReferencias.addKeyListener(new KeyAdapter() {
									@Override
									public void keyPressed(KeyEvent e) {
										if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
												|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
											cbImpuesto.requestFocus();
										} else {
											if (e.getKeyCode() == KeyEvent.VK_UP) {
												cbMarca.requestFocus();
											}
										}
									}

									@Override
									public void keyTyped(KeyEvent e) {
										// Util.validateNumero(e);
									}
								});
								pnlInfo.add(tfOtrasReferencias, "cell 1 2,grow");
								tfOtrasReferencias.setColumns(10);
				
						JLabel lblImpuesto = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
								.getString("ProductoPanel.lblImpuesto.text"));
						pnlInfo.add(lblImpuesto, "cell 0 3,grow");
				pnlInfo.add(cbImpuesto, "cell 1 3,grow");
		pnlInfo.add(lblActivo, "cell 0 7,grow");
		pnlInfo.add(chActivo, "cell 1 7,grow");

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
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.setSelectedIndex(0);
				tfDescripcion.requestFocus();
			}
		});
		btnCancelar.setMnemonic('C');
		panelBotonera.add(btnCancelar);

		btnCerrar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.btnCerrar.text")); //$NON-NLS-1$
		btnCerrar.setMnemonic('E');
		panelBotonera.add(btnCerrar);

		tfBuscador = new JTextField();
		
		tfBuscador.addKeyListener(new KeyAdapter() {
			
		});
		tfBuscador.addFocusListener(new FocusAdapter() {

		});
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
		try {
			tfProductoId.setText(product.getId() + "");
			// tfNombre.setText(product.getNombre());
			tfDescripcion.setText(product.getDescripcion()==null?"":product.getDescripcion());
			tfCodigo.setText(product.getCodigo());
			tfCodigoSec.setText(product.getCodigoSec());
//			tfSubreferencia.setText(product.getSubreferencia());
			tfReferencia.setText(product.getReferencia()==null?"":product.getReferencia());
			tfOtrasReferencias.setText(product.getSubreferencia()==null?"":product.getSubreferencia());
			tfPrecioCompra.setText(
					product.getPrecioCosto() != null ? FormatearValor.doubleAString(product.getPrecioCosto()) : "0");
			
			// tfPeso.setText(product.getPeso() != null ?
			// FormatearValor.doubleAString(product.getPeso()) : "");
			tfDep01.setText(product.getDepO1() != null ? FormatearValor.doubleAString(product.getDepO1()) : "0");
//			tfCantidadPorCaja.setText(
//					product.getCantidadPorCaja() != null ? FormatearValor.doubleAString(product.getCantidadPorCaja()) : "");

			tfPrecioA.setText(
					product.getPrecioVentaA() != null ? FormatearValor.doubleAString(product.getPrecioVentaA()) : "0");
//			tfPrecioC.setText(
//					product.getPrecioVentaC() != null ? FormatearValor.doubleAString(product.getPrecioVentaC()) : "0");
//			tfPrecioD.setText(
//					product.getPrecioVentaD() != null ? FormatearValor.doubleAString(product.getPrecioVentaD()) : "0");
//			tfPrecioE.setText(
//					product.getPrecioVentaE() != null ? FormatearValor.doubleAString(product.getPrecioVentaE()) : "0");
			imagenUrl = (product.getImagenUrl() != null ? product.getImagenUrl() : null);

			categoriaComboBoxModel.setSelectedItem(product.getCategoria());
			grupoComboBoxModel.setSelectedItem(product.getGrupo());
			
			impuestoComboBoxModel.setSelectedItem(product.getImpuesto());
			marcaComboBoxModel.setSelectedItem(product.getMarca());
			unidadMedidaComboBoxModel.setSelectedItem(product.getUnidadMedida());
			colorComboBoxModel.setSelectedItem(product.getColor());
			tamanhoComboBoxModel.setSelectedItem(product.getTamanho());
			tfModeloAplicacion.setText(product.getModeloAplicacion());

			tabbedPane.remove(pnlLubricantes);
			tabbedPane.remove(pnlFiltros);
			if (product.getCategoria().getNombre().equalsIgnoreCase("lubricantes")) {
				tabbedPane.insertTab("Lubricantes", null, pnlLubricantes,"" ,1);
				tfBase.setText(product.getBase());
				tfViscocidad.setText(product.getViscocidad());
				tfOrigen.setText(product.getOrigen());
				tfEnvase.setText(product.getEnvase());
				pnlLubricantes.setVisible(true);
			}
			if (product.getCategoria().getNombre().equalsIgnoreCase("filtros")) {
				tfCodigoFram.setText(product.getCodigofram());
				tfCodigoMan.setText(product.getCodigoman());
				tabbedPane.insertTab("Filtros", null, pnlFiltros,"" ,1);
			}
			

			chActivo.setSelected(product.getActivo() == 1 ? true : false);

			precioTableModel.clear();
			depositoTableModel.clear();

			visualizarImagen();			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Producto getProductForm() {
		try {
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
			product.setCodigo(tfCodigo.getText());
			product.setCodigoSec(tfCodigoSec.getText());
			product.setDescripcion(tfDescripcion.getText());
			product.setSubreferencia(tfOtrasReferencias.getText());

//			product.setSeccion(tfSeccion.getText());
//			product.setRegimen((String) cbRegimen.getSelectedItem());

			product.setActivo(chActivo.isSelected() ? 1 : 0);
			product.setImagenUrl(imagenUrl);
//			product.setDescripcionFiscal(tfDesFiscal.getText());
			product.setPrecioCosto(FormatearValor.stringADouble(tfPrecioCompra.getText()));
			//product.setPrecioCostoPromedio(FormatearValor.stringADouble(tfPrecioPromedio.getText()));
			product.setCategoria(categoriaComboBoxModel.getSelectedItem());
			product.setGrupo(grupoComboBoxModel.getSelectedItem());
			product.setSubgrupo(subgrupoComboBoxModel.getSelectedItem());
			product.setMarca(marcaComboBoxModel.getSelectedItem());
			
			product.setImpuesto(impuestoComboBoxModel.getSelectedItem());
			product.setUnidadMedida(unidadMedidaComboBoxModel.getSelectedItem());
			product.setTamanho(tamanhoComboBoxModel.getSelectedItem());
			product.setColor(colorComboBoxModel.getSelectedItem());
			product.setModeloAplicacion(tfModeloAplicacion.getText());
			pnlLubricantes.setVisible(false);
			pnlFiltros.setVisible(false);
			if (categoriaComboBoxModel.getSelectedItem().getNombre().equalsIgnoreCase("lubricantes")) {
				product.setBase(tfBase.getText());
				product.setViscocidad(tfViscocidad.getText());
				product.setEnvase(tfEnvase.getText());
				product.setOrigen(tfOrigen.getText());
				pnlLubricantes.setVisible(true);
			}
			if (categoriaComboBoxModel.getSelectedItem().getNombre().equalsIgnoreCase("filtros")) {
				product.setCodigofram(tfCodigoFram.getText());
				product.setCodigoman(tfCodigoMan.getText());
				pnlFiltros.setVisible(true);
			}

			product.setPrecioVentaA(
					FormatearValor.stringADouble(!tfPrecioA.getText().isEmpty() ? tfPrecioA.getText() : "0"));
//			product.setPrecioVentaC(
//					FormatearValor.stringADouble(!tfPrecioC.getText().isEmpty() ? tfPrecioC.getText() : "0"));
			return product;	
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

	public void clearForm() {
		tfProductoId.setText("0");
		tfDescripcion.setText("");
		tfCodigo.setText("");
		tfCodigoFram.setText("");
		tfCodigoMan.setText("");
		// tfSeccion.setText("");
		tfOtrasReferencias.setText("");
		tfDep01.setText("");
		tfPrecioCompra.setText("");
		// tfUltimoPrecioCompra.setText("");
		cbMarca.setSelectedIndex(0);
		cbImpuesto.setSelectedIndex(0);

		fileChooser = new JFileChooser();
		imagenUrl = null;
		// chEsProm
		chActivo.setSelected(true);

		tfPrecioA.setText("");
		//tfPrecioB.setText("");
//		tfPrecioC.setText("");
		tabbedPane.setSelectedIndex(0);

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
	
	private void showDialog(int code) {
		switch (code) {
		case MARCA_CODE:
			marcaController.setInterfaz(this);
			marcaController.setOrigen("Productos");;
			marcaController.prepareAndOpenFrame();
			//marcaController.setOrigen("PRODUCTO");
			break;
		default:
			break;
		}
	}

	

	public ProductoInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(ProductoInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
	
	public MarcaComboBoxModel getMarcaComboBoxModel() {
		return marcaComboBoxModel;
	}

	public void setMarcaComboBoxModel(MarcaComboBoxModel marcaComboBoxModel) {
		this.marcaComboBoxModel = marcaComboBoxModel;
	}

	@Override
	public void getEntity(Marca marca) {
		marcaComboBoxModel.addElement(marca);
		marcaComboBoxModel.setSelectedItem(marca);
		// TODO Auto-generated method stub
		
	}
}
