package py.com.prestosoftware.ui.search;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Configuracion;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.ProductoDeposito;
import py.com.prestosoftware.data.models.ProductoPrecio;
import py.com.prestosoftware.domain.services.ConfiguracionService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.UsuarioRolService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.CellRendererOthers;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.table.ProductDCTableModel;
import py.com.prestosoftware.ui.table.ProductoDepositoTableModel;
import py.com.prestosoftware.ui.table.ProductoPrecioTableModel;

@Component
public class ProductoVistaDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private ProductoService service;
	private ProductDCTableModel tableModel;
	private ProductoInterfaz interfaz;
	
	private List<Producto> productos;
	
	private ProductoPrecioTableModel precioTableModel;
	private ProductoDepositoTableModel depositoTableModel;
	private DepositoService depositoService;
	private ConfiguracionService configService;
	
	private JPanel pnlDatosComplementarios;
	private JLabel lblStockValor,lblPrecioCompraValor,lblPrecioVentaValor,lblCodigoValor,lblReferenciaValor,lblEanValor,lblNombreValor,lblAplicacionValor,lblAgrupacionValor,lblFabricanteValor,lblMagnitudValor;
	private String nivelPrecio;
	private UsuarioRolService usuarioRolService;

	@Autowired
	public ProductoVistaDialog(ProductoService service, 
			ProductDCTableModel tableModel, ProductoDepositoTableModel productoDepositoTableModel, DepositoService depositoService,
			ProductoPrecioTableModel productoPrecioTableModel,ConfiguracionService configService,UsuarioRolService usuarioRolService) {
		this.service = service;
		this.tableModel = tableModel;
		this.depositoTableModel = productoDepositoTableModel;
		this.depositoService = depositoService;
		this.precioTableModel = productoPrecioTableModel;
		this.configService=configService;
		this.usuarioRolService=usuarioRolService;
		setTitle("LISTA DE STOCK");
		setSize(1055, 603);
		setModal(true);
		getContentPane().setLayout(null);
		
		JPanel pnlBuscador = new JPanel();
		pnlBuscador.setBounds(0, 0, 986, 35);
		getContentPane().add(pnlBuscador);
		
		JLabel lblBuscador = new JLabel("Buscador");
		lblBuscador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBuscador.add(lblBuscador);
		
		tfBuscador = new JTextField();
		tfBuscador.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfBuscador.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				tfBuscador.selectAll();
			}
		});
		((AbstractDocument) tfBuscador.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfBuscador.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
					
				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			    	dispose();
			    } else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			    	table.requestFocus();
			    } 
				//loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
			}
			@Override
			public void keyReleased(KeyEvent e) {
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();
				textField.setText(text.toUpperCase());
				DefaultTableModel table1 = (DefaultTableModel) table.getModel();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table1);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter("(?i)" + textField.getText()));
			}
		});
		pnlBuscador.add(tfBuscador);
		tfBuscador.setColumns(30);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
			}
		});
		btnBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() ==  KeyEvent.VK_ENTER) {
					loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
				}
			}
		});
		pnlBuscador.add(btnBuscar);
		
		JButton btnActualizarLista = new JButton("Actualizar Lista");
		btnActualizarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizaLista();
			}
		});
		btnActualizarLista.setFont(new Font("Tahoma", Font.PLAIN, 14));
		pnlBuscador.add(btnActualizarLista);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 35, 531, 476);
		getContentPane().add(scrollPane);
		
		table = new JTable(tableModel);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                loadData(); 
            }
        });
		DefaultTableCellRenderer alignRenderer= new CellRendererOthers();
		alignRenderer.setBackground(getBackground());
		alignRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(0).setCellRenderer(alignRenderer);
		table.getColumnModel().getColumn(1).setPreferredWidth(275);
		table.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					aceptar();
				} else if (e.getKeyCode()==KeyEvent.VK_INSERT) {
					aceptar();
				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					 tfBuscador.requestFocus();
				} 
			}
		});
//		table.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent mouseEvent) {
//				JTable jTable = (JTable) mouseEvent.getSource();
//				Point point = mouseEvent.getPoint();
//				int row = jTable.rowAtPoint(point);
//				if (mouseEvent.getClickCount() == 2 && jTable.getSelectedRow() != -1) {
//					aceptar();
//				}
//			}
//		});
		table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getClickCount() == 2) {     // to detect doble click events
					aceptar();
		            }
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		scrollPane.setViewportView(table);
		JPanel pnlBotonera = new JPanel();
		pnlBotonera.setBounds(0, 521, 976, 35);
		getContentPane().add(pnlBotonera);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					aceptar();
				}
			}
		});
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptar();
			}
		});
		pnlBotonera.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					dispose();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		pnlBotonera.add(btnCancelar);
		
		pnlDatosComplementarios = new JPanel();
		pnlDatosComplementarios.setBackground(Color.WHITE);
		pnlDatosComplementarios.setBounds(541, 45, 488, 466);
		pnlDatosComplementarios.setBorder(new TitledBorder(null, "DATOS COMPLEMENTARIOS DEL PRODUCTO", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlDatosComplementarios, "cell 1 0 1 3,grow");
		pnlDatosComplementarios.setLayout(null);
		
		JLabel lblCodigoDC = new JLabel("Código");
		lblCodigoDC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoDC.setBounds(10, 21, 45, 18);
		pnlDatosComplementarios.add(lblCodigoDC);
		
		JLabel lblEAN = new JLabel("EAN");
		lblEAN.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEAN.setBounds(10, 67, 56, 13);
		pnlDatosComplementarios.add(lblEAN);
		
		JLabel lblReferencia = new JLabel("Referencia");
		lblReferencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReferencia.setBounds(10, 44, 79, 13);
		pnlDatosComplementarios.add(lblReferencia);
		
		JLabel lblDosPuntos1 = new JLabel(":");
		lblDosPuntos1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos1.setBounds(105, 21, 10, 18);
		pnlDatosComplementarios.add(lblDosPuntos1);
		
		JLabel lblDosPuntos2 = new JLabel(":");
		lblDosPuntos2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos2.setBounds(105, 44, 10, 13);
		pnlDatosComplementarios.add(lblDosPuntos2);
		
		JLabel lblDosPuntos3 = new JLabel(":");
		lblDosPuntos3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos3.setBounds(105, 67, 10, 13);
		pnlDatosComplementarios.add(lblDosPuntos3);
		
		JLabel lblNewLabel = new JLabel("---------------------------------------------------------------------------------------");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 91, 468, 13);
		pnlDatosComplementarios.add(lblNewLabel);
		
		JLabel lblNombreDC = new JLabel("Nombre");
		lblNombreDC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreDC.setBounds(10, 104, 79, 18);
		pnlDatosComplementarios.add(lblNombreDC);
		
		JLabel lblAplicacionDC = new JLabel("Aplicación");
		lblAplicacionDC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAplicacionDC.setBounds(10, 127, 79, 18);
		pnlDatosComplementarios.add(lblAplicacionDC);
		
		JLabel lblDosPuntos1_1 = new JLabel(":");
		lblDosPuntos1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos1_1.setBounds(105, 104, 10, 18);
		pnlDatosComplementarios.add(lblDosPuntos1_1);
		
		JLabel lblDosPuntos2_1 = new JLabel(":");
		lblDosPuntos2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos2_1.setBounds(105, 127, 10, 18);
		pnlDatosComplementarios.add(lblDosPuntos2_1);
		
		JLabel lblNewLabel_1 = new JLabel("---------------------------------------------------------------------------------------");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 154, 468, 13);
		pnlDatosComplementarios.add(lblNewLabel_1);
		
		JLabel lblAgrupacion = new JLabel("Agrupación");
		lblAgrupacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAgrupacion.setBounds(10, 173, 79, 18);
		pnlDatosComplementarios.add(lblAgrupacion);
		
		JLabel lblDosPuntos1_2 = new JLabel(":");
		lblDosPuntos1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos1_2.setBounds(105, 173, 10, 18);
		pnlDatosComplementarios.add(lblDosPuntos1_2);
		
		JLabel lblFabricante = new JLabel("Fabricante");
		lblFabricante.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFabricante.setBounds(10, 196, 79, 18);
		pnlDatosComplementarios.add(lblFabricante);
		
		JLabel lblDosPuntos2_2 = new JLabel(":");
		lblDosPuntos2_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos2_2.setBounds(105, 196, 10, 18);
		pnlDatosComplementarios.add(lblDosPuntos2_2);
		
		JLabel lblMagnitud = new JLabel("Magnitud");
		lblMagnitud.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMagnitud.setBounds(10, 219, 79, 18);
		pnlDatosComplementarios.add(lblMagnitud);
		
		JLabel lblDosPuntos3_1 = new JLabel(":");
		lblDosPuntos3_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos3_1.setBounds(105, 219, 10, 18);
		pnlDatosComplementarios.add(lblDosPuntos3_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("---------------------------------------------------------------------------------------");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 248, 468, 13);
		pnlDatosComplementarios.add(lblNewLabel_1_1);
		
		JLabel lblPrecioVenta = new JLabel("Precio Venta");
		lblPrecioVenta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecioVenta.setBounds(10, 311, 91, 18);
		pnlDatosComplementarios.add(lblPrecioVenta);
		
		JLabel lblDosPuntos3_1_1 = new JLabel(":");
		lblDosPuntos3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos3_1_1.setBounds(105, 311, 10, 18);
		pnlDatosComplementarios.add(lblDosPuntos3_1_1);
		
		JLabel lblDosPuntos2_2_1 = new JLabel(":");
		lblDosPuntos2_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos2_2_1.setBounds(105, 288, 10, 18);
		pnlDatosComplementarios.add(lblDosPuntos2_2_1);
		
		JLabel lblPrecioCompra = new JLabel("Precio Compra");
		lblPrecioCompra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecioCompra.setBounds(10, 288, 91, 18);
		pnlDatosComplementarios.add(lblPrecioCompra);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStock.setBounds(10, 265, 56, 18);
		pnlDatosComplementarios.add(lblStock);
		
		JLabel lblDosPuntos1_2_1 = new JLabel(":");
		lblDosPuntos1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDosPuntos1_2_1.setBounds(105, 265, 10, 18);
		pnlDatosComplementarios.add(lblDosPuntos1_2_1);
		
		lblCodigoValor = new JLabel("");
		lblCodigoValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoValor.setBounds(114, 21, 169, 18);
		pnlDatosComplementarios.add(lblCodigoValor);
		
		lblReferenciaValor = new JLabel("");
		lblReferenciaValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReferenciaValor.setBounds(114, 44, 169, 13);
		pnlDatosComplementarios.add(lblReferenciaValor);
		
		lblEanValor = new JLabel("");
		lblEanValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEanValor.setBounds(114, 67, 102, 13);
		pnlDatosComplementarios.add(lblEanValor);
		
		lblNombreValor = new JLabel("");
		lblNombreValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreValor.setBounds(114, 104, 364, 18);
		pnlDatosComplementarios.add(lblNombreValor);
		
		lblAplicacionValor = new JLabel("");
		lblAplicacionValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAplicacionValor.setBounds(114, 127, 364, 18);
		pnlDatosComplementarios.add(lblAplicacionValor);
		
		lblAgrupacionValor = new JLabel("");
		lblAgrupacionValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAgrupacionValor.setBounds(114, 173, 364, 18);
		pnlDatosComplementarios.add(lblAgrupacionValor);
		
		lblFabricanteValor = new JLabel("");
		lblFabricanteValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFabricanteValor.setBounds(114, 196, 364, 18);
		pnlDatosComplementarios.add(lblFabricanteValor);
		
		lblMagnitudValor = new JLabel("");
		lblMagnitudValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMagnitudValor.setBounds(114, 219, 364, 18);
		pnlDatosComplementarios.add(lblMagnitudValor);
		
		lblStockValor = new JLabel("");
		lblStockValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStockValor.setBounds(114, 265, 364, 18);
		pnlDatosComplementarios.add(lblStockValor);
		
		lblPrecioCompraValor = new JLabel("");
		lblPrecioCompraValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecioCompraValor.setBounds(114, 288, 364, 18);
		pnlDatosComplementarios.add(lblPrecioCompraValor);
		
		lblPrecioVentaValor = new JLabel("");
		lblPrecioVentaValor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecioVentaValor.setBounds(114, 311, 364, 18);
		pnlDatosComplementarios.add(lblPrecioVentaValor);
		
		Util.setupScreen(this);
//		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
//		Dimension ventana = this.getSize(); 
//		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		getConfig();
	}
	
	public void loadProductos(String filter) {
		if (filter.equalsIgnoreCase("")|| filter.isEmpty()||productos==null) {
			productos = service.findAllByNombre();
		}
		
        tableModel.clear();
        tableModel.addEntities(productos);
        //table.requestFocus();
    }
	
	public void actualizaLista() {
		productos = service.findAllByNombre();
		tableModel.clear();
        tableModel.addEntities(productos);
	}
	
	public ProductoInterfaz getInterfaz() {
		return interfaz;
	}
	
	public void setInterfaz(ProductoInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
	private void aceptar() {
		try {
			int[] selectedRow = table.getSelectedRows();
			Long selectedId = (Long) table.getValueAt(selectedRow[0], 0);

			Producto producto= productos.stream().filter(p -> p.getId().equals(selectedId.longValue()))
					  .findAny()
					  .orElse(null);
			if(producto!=null) {
				interfaz.getEntity(producto);
			}else {
				Optional<Producto> p = service.findById(selectedId);
				interfaz.getEntity(p.get());
			}			
		} catch (Exception e) {
			// TODO: handle exception
		}		
		dispose();
	}
	
	private Configuracion conf;

	public void getConfig() {
		Optional<Configuracion> config = configService.findByEmpresaId(new Empresa(GlobalVars.EMPRESA_ID));

		if (config.isPresent()) {
			this.conf = config.get();
			nivelPrecio=this.conf.getPrecioDefinido();
		}
	}
	
	private Producto getProducto() {
		return productos.get(table.getSelectedRow());         
	}
	
	public Long getProductoId() {
		return productos.get(table.getSelectedRow()).getId();         
	}

	public void getProductos() {
		loadProductos("");
	}
	public void inicializaProductos() {
		productos=new ArrayList<Producto>();
	}
	private void getStockProductosByDeposito(Producto p) {
		depositoTableModel.clear();
		
		if (p != null) {
			String deposito = depositoService.findById(1L).get().getNombre();
			
			if (p.getDepO1() != null) {
				ProductoDeposito dep01 = new ProductoDeposito(deposito, p.getDepO1());
				lblStockValor.setText(p.getDepO1().toString());
				depositoTableModel.addEntity(dep01);
				
				String deposito2 = depositoService.findById(2L).get().getNombre();
				if (p.getDepO2() != null) {
					ProductoDeposito dep02 = new ProductoDeposito(deposito2, p.getDepO2());
					depositoTableModel.addEntity(dep02);
					
					String deposito3 = depositoService.findById(3L).get().getNombre();
					if (p.getDepO3() != null) {
						ProductoDeposito dep03 = new ProductoDeposito(deposito3, p.getDepO3());
						depositoTableModel.addEntity(dep03);
						
						String deposito4 = depositoService.findById(4L).get().getNombre();
						if (p.getDepO4() != null) {
							ProductoDeposito dep04 = new ProductoDeposito(deposito4, p.getDepO4());
							depositoTableModel.addEntity(dep04);
							
							String deposito5 = depositoService.findById(5L).get().getNombre();
							if (p.getDepO5() != null) {
								ProductoDeposito dep05 = new ProductoDeposito(deposito5, p.getDepO5());
								depositoTableModel.addEntity(dep05);
							}	
						}
					}
				}
			}	
		}
	}
	//TODO Buscar por codigo y referencia
	private void getPreciosByProducto(Producto p) {
		precioTableModel.clear();
		
		if (p != null) {
			if (p.getPrecioVentaA() != null) {
				ProductoPrecio precio01 = new ProductoPrecio("Precio A", (p.getPrecioVentaA() != null ? p.getPrecioVentaA():0));
				precioTableModel.addEntity(precio01);
			
				if (p.getPrecioVentaB() != null) {
					ProductoPrecio precio02 = new ProductoPrecio("Precio B", (p.getPrecioVentaB() != null ? p.getPrecioVentaB():0));
					precioTableModel.addEntity(precio02);
					
					if (p.getPrecioVentaC() != null) {
						ProductoPrecio precio03 = new ProductoPrecio("Precio C", (p.getPrecioVentaC() != null ? p.getPrecioVentaC():0));
						precioTableModel.addEntity(precio03);
						
						if (p.getPrecioVentaD() != null) {
							ProductoPrecio precio04 = new ProductoPrecio("Precio D", (p.getPrecioVentaD() != null ? p.getPrecioVentaD():0));
							precioTableModel.addEntity(precio04);
							
							if (p.getPrecioVentaE() != null) {
								ProductoPrecio precio05 = new ProductoPrecio("Precio E", (p.getPrecioVentaE() != null ? p.getPrecioVentaE():0));
								precioTableModel.addEntity(precio05);
							}	
						}
					}
				}
			}	
		}
	}
	
	public void inicializarSeleccion() {
		lblEanValor.setText("");
		lblReferenciaValor.setText("");
		lblStockValor.setText("");      
		lblNombreValor.setText("");
	}
	
	private void loadData() {
		int[] selectedRow = table.getSelectedRows();
		if (selectedRow.length > 0) {
			
			Long selectedId = (Long) table.getValueAt(selectedRow[0], 0);

			Producto producto= productos.stream().filter(p -> p.getId().equals(selectedId.longValue()))
					  .findAny()
					  .orElse(null);
			
			
//			Long productoId = tableModel.getEntityByRow(selectedRow).getId();
//			Producto p = service.getStockDepositoByProductoId(productoId);
//			
			if (producto != null) {
				getStockProductosByDeposito(producto);
				getPreciosByProducto(producto);
				getDatosComplementarios(producto);
			}
	    }
	}

	private void getDatosComplementarios(Producto p) {
		try {
			lblEanValor.setText(p.getReferencia()!= null?p.getReferencia().toString():"");
			lblReferenciaValor.setText(p.getSubreferencia()!=null?p.getSubreferencia():"");
			lblStockValor.setText(FormatearValor.doubleAString(p.getDepO1()));			
		} catch (Exception e) {
			System.out.println("error de referencia o deposito"+p.getDepO1());
			// TODO: handle exception
		}
		if (usuarioRolService!=null&&usuarioRolService.hasRole(Long.valueOf(GlobalVars.USER_ID), "PUEDE VER PRECIO DE COMPRAS")) {
			try {
			lblPrecioCompraValor.setText(FormatearValor.doubleAString(p.getPrecioCosto()));
			} catch (Exception e) {
				System.out.println("error precio compras"+p.getPrecioCosto());
				lblPrecioCompraValor.setText("0");
			}
		}else {
			lblPrecioCompraValor.setText("0");
		}
		String precio="";
		try {
			if(nivelPrecio.equalsIgnoreCase("Precio A")) {
				double precioA=Math.round(p.getPrecioVentaA());
				precio=FormatearValor.doubleAString(precioA);
			}else if (nivelPrecio.equalsIgnoreCase("Precio B")) {
				double precioB=Math.round(p.getPrecioVentaB());
				precio=FormatearValor.doubleAString(precioB);
			}else if (nivelPrecio.equalsIgnoreCase("Precio C")) {
				double precioC=Math.round(p.getPrecioVentaC());
				precio=FormatearValor.doubleAString(precioC);
			}
			lblPrecioVentaValor.setText(precio);
		} catch (Exception e) {
			System.out.println("error precio Venta");
			lblPrecioVentaValor.setText("0");
		}
		try {
			lblCodigoValor.setText(p.getId().toString());
			lblNombreValor.setText(p.getDescripcion()!=null?p.getDescripcion():"");
			lblMagnitudValor.setText("1- UNIDAD");
			lblAgrupacionValor.setText("1- "+p.getGrupo()!=null?p.getGrupo().toString():"");
			lblFabricanteValor.setText(p.getMarca()!=null?(p.getMarca().getId()+"- "+p.getMarca().getNombre()):"");			
		} catch (Exception e) {
			System.out.println("error precio Venta");
			lblPrecioVentaValor.setText("0");
		}		
	}
	
	public void limpiaDatosComplementarios() {
		lblEanValor.setText("");
		lblReferenciaValor.setText("");
		lblStockValor.setText("");
		lblPrecioCompraValor.setText("");
		lblPrecioVentaValor.setText("");
		lblCodigoValor.setText("");
		lblNombreValor.setText("");
		lblMagnitudValor.setText("");
		lblAgrupacionValor.setText("");
		lblFabricanteValor.setText("");
	}
}