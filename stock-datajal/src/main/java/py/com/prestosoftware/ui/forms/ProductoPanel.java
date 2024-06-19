package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import py.com.prestosoftware.ui.helpers.Fechas;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.miginfocom.swing.MigLayout;
import py.com.prestosoftware.data.models.Categoria;
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Impuesto;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Lotes;
import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.VentaDetalle;
import py.com.prestosoftware.domain.services.LoteService;
import py.com.prestosoftware.domain.services.SubgrupoService;
import py.com.prestosoftware.ui.controllers.MarcaController;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.CellRendererOthers;
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
import py.com.prestosoftware.ui.table.LotesTableModel;
import py.com.prestosoftware.ui.table.MarcaComboBoxModel;
import py.com.prestosoftware.ui.table.NcmComboBoxModel;
import py.com.prestosoftware.ui.table.ProductTableModel;
import py.com.prestosoftware.ui.table.ProductoDepositoTableModel;
import py.com.prestosoftware.ui.table.ProductoPrecioTableModel;
import py.com.prestosoftware.ui.table.SubgrupoComboBoxModel;
import py.com.prestosoftware.ui.table.TamanhoComboBoxModel;
import py.com.prestosoftware.ui.table.UnidadMedidaComboBoxModel;
import py.com.prestosoftware.ui.table.VentaItemTableModel;
import py.com.prestosoftware.util.Notifications;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;

@Component
public class ProductoPanel extends JDialog implements MarcaInterfaz {

	private static final long serialVersionUID = 1L;

	private static final int MARCA_CODE = 1;
	private JTextField tfDescripcion, tfDesFiscal;
	private JTextField tfProductoId;
	private JCheckBox chActivo;
	private JButton btnGuardar, btnCancelar, btnCerrar;
	private JComboBox<Marca> cbMarca;
	private JComboBox<Categoria> cbCategoria;
	private JComboBox<Grupo> cbClasificacion;
	private JComboBox<Impuesto> cbImpuesto;
	private JComboBox<ListaPrecio> cbListaPrecio;
	private JTextArea tfPrincipioActivo;
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
	private LotesTableModel itemTableModel;
	private JLabel lblEsServicio;
	private JCheckBox chServicio;
	private JCheckBox chkLote;
	private JLabel label_7;
	private JLabel lblPrecioC;
	private JTextField tfPrecioC;
	// private JTextField tfPrecioB;
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

	private LoteService loteService;
	private ProductoInterfaz interfaz;
	private JTextField tfPrecioCompra;
	private JLabel label_2;
	private JTextField tfDep01;
	private JLabel lblNewLabel_1;
	private JTextField tfOtrasReferencias;
	private JButton btnNewMarcas;
	private MarcaController marcaController;
	private JLabel lblPrecioB;
	private JTextField tfPrecioB;
	private JLabel lblCategoria;
	private JTextField tfCodigoBarra;
	private JLabel lblPrincipioActivo;
	private JTable tbLotes;
	private JPanel pnlLotes;
	private JPanel pnlInfo;
	private JFormattedTextField tfVencimiento;

	@Autowired
	public ProductoPanel(GrupoComboBoxModel grupoComboBoxModel, NcmComboBoxModel ncmComboBoxModel,
			CategoriaComboBoxModel categoriaComboBoxModel, ImpuestoComboBoxModel impuestoComboBoxModel,
			MarcaComboBoxModel marcaComboBoxModel, ColorComboBoxModel colorComboBoxModel,
			SubgrupoComboBoxModel subgrupoComboBoxModel, TamanhoComboBoxModel tamanhoComboBoxModel,
			UnidadMedidaComboBoxModel unidadMedidaComboBoxModel, ListaPrecioComboBoxModel listaComboBoxModel,
			ProductoPrecioTableModel precioTableModel, ProductoDepositoTableModel depositoTableModel,
			ProductTableModel productTableModel, SubgrupoService subgrupoService, MarcaController marcaController,
			LotesTableModel itemTableModel, LoteService loteService) {
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
		this.marcaController = marcaController;
		this.itemTableModel = itemTableModel;
		this.loteService = loteService;

		initComponents();

		JPanel pnlDatosPersonal = new JPanel();
		tabbedPane.addTab(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.pnlDatosPersonal.arg0"), null, pnlDatosPersonal, null); //$NON-NLS-1$
		tabbedPane.setEnabledAt(0, true);

		JLabel lblCodigo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblCodigo.text")); //$NON-NLS-1$
		lblCodigo.setBounds(16, 4, 98, 25);

		tfProductoId = new JTextField();
		tfProductoId.setEditable(false);
		tfProductoId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfProductoId.setBounds(118, 4, 163, 25);

		JLabel lblNombre = new JLabel("Descripción");
		lblNombre.setBounds(16, 33, 98, 25);

		tfDescripcion = new JTextField();
		tfDescripcion.setNextFocusableComponent(tfDesFiscal);
		tfDescripcion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDescripcion.selectAll();
			}
		});
		tfDescripcion.setBounds(118, 33, 566, 25);
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
		((AbstractDocument) tfDesFiscal.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfDesFiscal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfDesFiscal.selectAll();
			}
		});
		tfDesFiscal.setBounds(118, 62, 566, 25);
		((AbstractDocument) tfDesFiscal.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfDesFiscal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfPrecioCompra.requestFocus();
				} else {
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						tfDescripcion.requestFocus();
					}
				}
			}
		});
		tfDesFiscal.setNextFocusableComponent(cbCategoria);
		tfDesFiscal.setColumns(10);

		JLabel lblDescFiscal = new JLabel("Desc. Fiscal:");
		lblDescFiscal.setBounds(16, 62, 98, 25);
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
		lblEsServicio.setBounds(318, 178, 67, 25);
		pnlDatosPersonal.add(lblEsServicio);
		// lblEsServicio.setVisible(false);

		chServicio = new JCheckBox();
		// chServicio.setVisible(false);
		chServicio.setBounds(457, 178, 28, 25);
		pnlDatosPersonal.add(chServicio);

		label_7 = new JLabel("Precio A");
		label_7.setBounds(16, 178, 98, 25);
		pnlDatosPersonal.add(label_7);

		lblPrecioC = new JLabel("Precio C");
		lblPrecioC.setBounds(16, 236, 98, 25);
		pnlDatosPersonal.add(lblPrecioC);

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
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (tfPrecioC.getText().length() > 0) {
						Double precioC = FormatearValor.stringToDouble(tfPrecioC.getText());
						tfPrecioC.setText(FormatearValor.doubleAString(precioC));
					}
					chServicio.requestFocus();
				} else {
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

		tfPrecioC.setBounds(118, 236, 163, 25);
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
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (tfPrecioC.getText().length() > 0) {
						Double precioC = FormatearValor.stringToDouble(tfPrecioC.getText());
						tfPrecioC.setText(FormatearValor.doubleAString(precioC));
					}
					tabbedPane.setSelectedIndex(1);
					chServicio.requestFocus();
					// tabbedPane..requestFocus();
				} else {
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
					if (tfPrecioA.getText().length() > 0) {
						Double precioA = FormatearValor.stringToDouble(tfPrecioA.getText());
						tfPrecioA.setText(FormatearValor.doubleAString(precioA));
					}
					tfPrecioB.requestFocus();
				} else {
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
		tfPrecioA.setBounds(118, 178, 163, 25);
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
		tfPrecioCompra.setBounds(118, 149, 163, 25);
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
					if (tfPrecioCompra.getText().length() > 0) {
						Double precioCompra = FormatearValor.stringToDouble(tfPrecioCompra.getText());
						tfPrecioCompra.setText(FormatearValor.doubleAString(precioCompra));
					}
					tfPrecioA.requestFocus();
				} else {
					if (e.getKeyCode() == KeyEvent.VK_UP) {
						tfDesFiscal.requestFocus();
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
		label_2.setBounds(320, 149, 76, 25);
		pnlDatosPersonal.add(label_2);

		tfDep01 = new JTextField();
		tfDep01.setEditable(false);
		tfDep01.setColumns(10);
		tfDep01.setBounds(459, 149, 163, 25);
		pnlDatosPersonal.add(tfDep01);

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblNewLabel.text")); //$NON-NLS-1$
		lblNewLabel.setBounds(16, 149, 92, 25);
		pnlDatosPersonal.add(lblNewLabel);

		lblPrecioB = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.label.text")); //$NON-NLS-1$
		lblPrecioB.setBounds(16, 207, 98, 25);
		pnlDatosPersonal.add(lblPrecioB);

		tfPrecioB = new JTextField();
		tfPrecioB.setColumns(10);
		tfPrecioB.setBounds(118, 207, 163, 25);
		tfPrecioB.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfPrecioB.selectAll();
			}
		});
		tfPrecioB.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
						|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (tfPrecioB.getText().length() > 0) {
						Double precioB = FormatearValor.stringToDouble(tfPrecioB.getText());
						tfPrecioB.setText(FormatearValor.doubleAString(precioB));
					}
					tfPrecioC.requestFocus();
				} else {
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

		JLabel lblLote = new JLabel("Controla Lote"); // $NON-NLS-2$ //$NON-NLS-1$ //$NON-NLS-2$
		lblLote.setBounds(540, 178, 98, 25);
		pnlDatosPersonal.add(lblLote);

		chkLote = new JCheckBox(""); //$NON-NLS-1$ //$NON-NLS-2$
		chkLote.setBounds(644, 178, 93, 21);
		pnlDatosPersonal.add(chkLote);
//		chkLote.addItemListener(new ItemListener() {
//		    @Override
//		    public void itemStateChanged(ItemEvent e) {
//		        if (e.getStateChange() == ItemEvent.SELECTED) {
//		            // the checkbox was just selected
//		        } else {
//		            // the checkbox was just deselected
//		        }
//		    }
//		});
//		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				System.out.println(selected);
				if (selected) {
					tabbedPane.remove(pnlInfo);
					tabbedPane.addTab("Lotes", pnlLotes);
					tabbedPane.addTab("Info", pnlInfo);
					// pnlLotes.setVisible(true);
				} else {
					tabbedPane.remove(pnlLotes);
					// pnlLotes.setVisible(false);
				}

				// abstractButton.setText(newLabel);
			}
		};
		chkLote.addActionListener(actionListener);

		lblCategoria = new JLabel("Categoría"); //$NON-NLS-1$ //$NON-NLS-2$
		lblCategoria.setBounds(16, 91, 98, 25);
		pnlDatosPersonal.add(lblCategoria);

		cbCategoria = new JComboBox<Categoria>(categoriaComboBoxModel);
		cbCategoria.setEditable(true);
		cbCategoria.setSelectedIndex(-1);
		cbCategoria.setBounds(118, 91, 566, 25);
		pnlDatosPersonal.add(cbCategoria);

		JLabel lblGrupoClasificacion = new JLabel("Clasificación"); //$NON-NLS-1$ //$NON-NLS-2$
		lblGrupoClasificacion.setBounds(16, 120, 98, 25);
		pnlDatosPersonal.add(lblGrupoClasificacion);

		cbClasificacion = new JComboBox<Grupo>(grupoComboBoxModel);
		cbClasificacion.setEditable(true);
		cbClasificacion.setSelectedIndex(-1);
		cbClasificacion.setBounds(118, 120, 566, 25);
		pnlDatosPersonal.add(cbClasificacion);

		JLabel lblCodigoBarra = new JLabel("Código de Barras"); //$NON-NLS-1$ //$NON-NLS-2$
		lblCodigoBarra.setBounds(318, 4, 129, 25);
		pnlDatosPersonal.add(lblCodigoBarra);

		tfCodigoBarra = new JTextField();
		tfCodigoBarra.setBounds(457, 4, 226, 25);
		pnlDatosPersonal.add(tfCodigoBarra);
		tfCodigoBarra.setColumns(10);

		lblPrincipioActivo = new JLabel("Principio Activo"); //$NON-NLS-1$ //$NON-NLS-2$
		lblPrincipioActivo.setBounds(318, 216, 117, 25);
		pnlDatosPersonal.add(lblPrincipioActivo);

		tfPrincipioActivo = new JTextArea();
		tfPrincipioActivo.setBounds(457, 216, 280, 45);
		pnlDatosPersonal.add(tfPrincipioActivo);
		// AutoCompleteDecorator.decorate(cbMarca);

		pnlLotes = new JPanel();
		// tabbedPane.addTab("Lotes", null, pnlLotes, "");

		pnlLotes.setLayout(null);

		JScrollPane scrollLotes = new JScrollPane();
		scrollLotes.setBounds(10, 75, 466, 210);
		pnlLotes.add(scrollLotes);

		tbLotes = new JTable(itemTableModel) {
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
		tbLotes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tbLotes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer alignRendererHeaderCenter = new CellRendererOthers();
		alignRendererHeaderCenter.setBackground(getBackground());
		alignRendererHeaderCenter.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer alignRendererRight = new CellRendererOthers();
		alignRendererRight.setBackground(getBackground());
		alignRendererRight.setHorizontalAlignment(SwingConstants.RIGHT);
		tbLotes.getColumnModel().getColumn(0).setHeaderRenderer(alignRendererHeaderCenter);
		tbLotes.getColumnModel().getColumn(0).setPreferredWidth(105);
		tbLotes.getColumnModel().getColumn(1).setHeaderRenderer(alignRendererHeaderCenter);
		tbLotes.getColumnModel().getColumn(1).setPreferredWidth(125);
		tbLotes.getColumnModel().getColumn(1).setCellRenderer(alignRendererRight);
		tbLotes.getColumnModel().getColumn(2).setHeaderRenderer(alignRendererHeaderCenter);
		tbLotes.getColumnModel().getColumn(2).setPreferredWidth(125);
		tbLotes.getColumnModel().getColumn(2).setCellRenderer(alignRendererRight);
		tbLotes.getColumnModel().getColumn(3).setHeaderRenderer(alignRendererHeaderCenter);
		tbLotes.getColumnModel().getColumn(3).setPreferredWidth(105);
		tbLotes.getColumnModel().getColumn(3).setCellRenderer(alignRendererRight);
		tbLotes.getColumnModel().getColumn(4).setHeaderRenderer(alignRendererHeaderCenter);
		tbLotes.getColumnModel().getColumn(4).setPreferredWidth(105);
		tbLotes.getColumnModel().getColumn(4).setCellRenderer(alignRendererRight);
		Util.ocultarColumna(tbLotes, 0);
		scrollLotes.setViewportView(tbLotes);

		JLabel lblVencimiento = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblNewLabel_2.text")); //$NON-NLS-1$
		lblVencimiento.setBounds(26, 10, 80, 13);
		pnlLotes.add(lblVencimiento);

		JButton btnAddLotes = new JButton("+"); //$NON-NLS-1$ //$NON-NLS-2$
		btnAddLotes.setBounds(391, 32, 85, 21);
		btnAddLotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddLotes.setFont(new Font("Dialog", Font.BOLD, 18));
		btnAddLotes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addItem();
				}
			}
		});
		btnAddLotes.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 1 || mouseEvent.getClickCount() == 2) {
					addItem();
				}
			}
		});
		pnlLotes.add(btnAddLotes);

		tfVencimiento = new JFormattedTextField(getFormatoFecha());
		tfVencimiento.setBounds(26, 32, 80, 19);
		tfVencimiento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfVencimiento.getText().isEmpty()) {
						btnAddLotes.requestFocus();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					btnAddLotes.requestFocus();
				}
			}
		});

		pnlLotes.add(tfVencimiento);

		pnlInfo = new JPanel();
		tabbedPane.addTab("Info", null, pnlInfo, "");

		JLabel lblMarca = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblMarca.text")); //$NON-NLS-1$

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
		pnlInfo.setLayout(new MigLayout("", "[114.00px][240.00px,grow][159.00px][163px]",
				"[30px][30px][30px][30px][30px][30px][30px][30px]"));
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

		btnNewMarcas = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.btnNewButton.text")); //$NON-NLS-1$
		btnNewMarcas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showDialog(MARCA_CODE);
			}
		});
		pnlInfo.add(btnNewMarcas, "cell 2 0,alignx left,aligny center");

		lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages") //$NON-NLS-1$
				.getString("ProductoPanel.lblNewLabel_1.text")); //$NON-NLS-1$
		pnlInfo.add(lblNewLabel_1, "cell 0 1,alignx left");

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
		pnlInfo.add(tfOtrasReferencias, "cell 1 1,grow");
		tfOtrasReferencias.setColumns(10);

		JLabel lblImpuesto = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages")
				.getString("ProductoPanel.lblImpuesto.text"));
		pnlInfo.add(lblImpuesto, "cell 0 2,grow");

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
		pnlInfo.add(cbImpuesto, "cell 1 2,grow");
		pnlInfo.add(lblActivo, "cell 0 7,grow");
		pnlInfo.add(chActivo, "cell 1 7,grow");
		Util.setupScreen(this);
	}

	@SuppressWarnings("serial")
	private void initComponents() {
		setSize(954, 634);
		setTitle("REGISTRO DE MERCADERIAS");
		getContentPane().setLayout(new MigLayout("", "[788px,grow]", "[][158.00,grow][4.00px][319.00px][33.00px]"));

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
		DefaultTableCellRenderer alignRenderer = new DefaultTableCellRenderer();
		alignRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbProducto.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		tbProducto.getColumnModel().getColumn(0).setPreferredWidth(75);
		tbProducto.getColumnModel().getColumn(0).setCellRenderer(alignRenderer);
		tbProducto.getColumnModel().getColumn(1).setPreferredWidth(275);
		scrollProducto.setViewportView(tbProducto);

		pnlBuscador = new JPanel();
		getContentPane().add(pnlBuscador, "cell 0 2,growx,aligny top");
		pnlBuscador.setLayout(new BorderLayout(10, 10));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (e.getSource() instanceof JTabbedPane) {
					JTabbedPane pane = (JTabbedPane) e.getSource();
					System.out.println("Selected paneNo : " + pane.getSelectedIndex());
					if (pane.getSelectedIndex() == 1) {
						//getLotes();
					}
				}
			}

		});
		getContentPane().add(tabbedPane, "cell 0 3,grow");

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

	public List<Lotes> getLotes() {
		List<Lotes> lotes = loteService.findLotesByProductoId(Long.valueOf(tfProductoId.getText()));
		itemTableModel.clear();
		itemTableModel.addEntities(lotes);

		return lotes;
	}
	
	public List<Lotes> getLotesAgregados() {
		return itemTableModel.getEntities();
	}

	private void addItem() {
		try {
			if (isValidItem()) {

				itemTableModel.addEntity(getItem());
				tfVencimiento.requestFocus();
			}
			tfVencimiento.setText(null);
			// clearItem();
		} catch (Exception e) {
			Notifications.showAlert("Problemas para agregar items!");
			// clearItem();
		}

	}

	private void removeItem() {
		try {
			int selectedRow = tbLotes.getSelectedRow();

			if (selectedRow != -1) {
				// VentaDetalle item = itemTableModel.getEntityByRow(selectedRow);
				itemTableModel.removeRow(selectedRow);
			} else {
				Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private Boolean isValidItem() {
		if (tfProductoId.getText().isEmpty()) {
			Notifications.showAlert("Digite el codigo del Producto");
			tfProductoId.requestFocus();
			return false;
		} else if (tfVencimiento.getText().isEmpty()) {
			Notifications.showAlert("Seleccione la fecha de vencimiento");
			tfVencimiento.requestFocus();
			return false;
		}

		return true;
	}

	private Lotes getItem() {
		Lotes item = new Lotes();
		item.setIdProducto(Long.valueOf(tfProductoId.getText()));
		item.setFechaFinal(Fechas.stringDDMMAAAAADateUtil(tfVencimiento.getText()));
		item.setStock(0d);
		item.setPrecioCompra(0d);
		item.setPrecioVenta(0d);

		return item;
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

	public void setProductForm(Producto product) {
		try {
			tfProductoId.setText(product.getId() + "");
			// tfNombre.setText(product.getNombre());
			tfDescripcion.setText(product.getDescripcion() == null ? "" : product.getDescripcion());
//			tfReferencia.setText(product.getReferencia());
//			tfSubreferencia.setText(product.getSubreferencia());
			tfOtrasReferencias.setText(product.getSubreferencia() == null ? "" : product.getSubreferencia());
			tfPrecioCompra.setText(
					product.getPrecioCosto() != null ? FormatearValor.doubleAString(product.getPrecioCosto()) : "0");

			// tfPeso.setText(product.getPeso() != null ?
			// FormatearValor.doubleAString(product.getPeso()) : "");
			tfDep01.setText(product.getDepO1() != null ? FormatearValor.doubleAString(product.getDepO1()) : "");
//			tfCantidadPorCaja.setText(
//					product.getCantidadPorCaja() != null ? FormatearValor.doubleAString(product.getCantidadPorCaja()) : "");

			tfPrecioA.setText(
					product.getPrecioVentaA() != null ? FormatearValor.doubleAString(product.getPrecioVentaA()) : "0");
			tfPrecioB.setText(
					product.getPrecioVentaB() != null ? FormatearValor.doubleAString(product.getPrecioVentaB()) : "0");
			tfPrecioC.setText(
					product.getPrecioVentaC() != null ? FormatearValor.doubleAString(product.getPrecioVentaC()) : "0");
//			tfPrecioD.setText(
//					product.getPrecioVentaD() != null ? FormatearValor.doubleAString(product.getPrecioVentaD()) : "0");
//			tfPrecioE.setText(
//					product.getPrecioVentaE() != null ? FormatearValor.doubleAString(product.getPrecioVentaE()) : "0");
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
			chkLote.setSelected(product.getEsLote() == 1 ? true : false);
			if (product.getEsLote()==1) {
				tabbedPane.remove(pnlInfo);
	        	tabbedPane.addTab("Lotes", pnlLotes);
	        	tabbedPane.addTab("Info", pnlInfo);
			}else {
				tabbedPane.remove(pnlLotes);
			}
				
			pnlLotes.setVisible(false);
			if(chkLote.isSelected()) {
				getLotes();
				pnlLotes.setVisible(true);
			}
				
			tfDesFiscal.setText(product.getDescripcionFiscal());

			chActivo.setSelected(product.getActivo() == 1 ? true : false);
			tfPrincipioActivo.setText(product.getPrincipioActivo());
			tfCodigoBarra.setText(product.getCodigoBarra() != null ? product.getCodigoBarra().toString() : "");

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
			tfVencimiento.setText("");
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
			product.setSubreferencia(tfOtrasReferencias.getText());

//			product.setSeccion(tfSeccion.getText());
//			product.setRegimen((String) cbRegimen.getSelectedItem());

			product.setEsServicio(chServicio.isSelected() ? 1 : 0);
			product.setEsLote(chkLote.isSelected() ? 1 : 0);

			product.setActivo(chActivo.isSelected() ? 1 : 0);

			product.setImagenUrl(imagenUrl);
			product.setDescripcionFiscal(tfDesFiscal.getText());
			product.setPrecioCosto(FormatearValor.stringADouble(tfPrecioCompra.getText()));
			// product.setPrecioCostoPromedio(FormatearValor.stringADouble(tfPrecioPromedio.getText()));
			product.setCategoria(categoriaComboBoxModel.getSelectedItem());
			product.setGrupo(grupoComboBoxModel.getSelectedItem());
			product.setSubgrupo(subgrupoComboBoxModel.getSelectedItem());
			product.setMarca(marcaComboBoxModel.getSelectedItem());
			product.setNcm(ncmComboBoxModel.getSelectedItem());
			product.setImpuesto(impuestoComboBoxModel.getSelectedItem());
			product.setUnidadMedida(unidadMedidaComboBoxModel.getSelectedItem());
			product.setTamanho(tamanhoComboBoxModel.getSelectedItem());
			product.setColor(colorComboBoxModel.getSelectedItem());
			// product.setEsFraccionado(cbEsFraccionado.getSelectedIndex() == 0 ? 0 : 1);
			try {
				if (!tfCodigoBarra.getText().isEmpty())
					product.setCodigoBarra(BigInteger.valueOf(Long.valueOf(tfCodigoBarra.getText().toString())));
				else
					product.setCodigoBarra(null);
			} catch (Exception e) {
				product.setCodigoBarra(null);
				// TODO: handle exception
			}
			product.setPrincipioActivo(tfPrincipioActivo.getText());

			product.setPrecioVentaA(
					FormatearValor.stringADouble(!tfPrecioA.getText().isEmpty() ? tfPrecioA.getText() : "0"));
			product.setPrecioVentaB(
					FormatearValor.stringADouble(!tfPrecioB.getText().isEmpty() ? tfPrecioB.getText() : "0"));
			product.setPrecioVentaC(
					FormatearValor.stringADouble(!tfPrecioC.getText().isEmpty() ? tfPrecioC.getText() : "0"));
			return product;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	public void clearForm() {
		tfProductoId.setText("");
		tfDescripcion.setText("");
		tfDesFiscal.setText("");

		// tfSeccion.setText("");
		tfOtrasReferencias.setText("");
		tfDep01.setText("");
		tfPrecioCompra.setText("");
		// tfUltimoPrecioCompra.setText("");
		cbMarca.setSelectedIndex(0);
		cbImpuesto.setSelectedIndex(0);
		cbCategoria.setSelectedIndex(-1);
		cbClasificacion.setSelectedIndex(-1);
		tfPrincipioActivo.setText("");
		tfCodigoBarra.setText("");

		fileChooser = new JFileChooser();
		imagenUrl = null;
		// chEsProm
		chActivo.setSelected(true);
		chkLote.setSelected(false);

		tfPrecioA.setText("");
		tfPrecioB.setText("");
		tfPrecioC.setText("");
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
			marcaController.setOrigen("Productos");
			;
			marcaController.prepareAndOpenFrame();
			// marcaController.setOrigen("PRODUCTO");
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
