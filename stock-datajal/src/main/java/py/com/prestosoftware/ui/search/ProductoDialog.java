package py.com.prestosoftware.ui.search;

import javax.swing.JDialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.ProductoDeposito;
import py.com.prestosoftware.data.models.ProductoPrecio;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.table.ProductTableModel;
import py.com.prestosoftware.ui.table.ProductoDepositoTableModel;
import py.com.prestosoftware.ui.table.ProductoPrecioTableModel;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.FlowLayout;
import java.awt.Point;

@Component
public class ProductoDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private ProductoService service;
	private ProductTableModel tableModel;
	private ProductoInterfaz interfaz;
	
	private List<Producto> productos;
	
	private ProductoPrecioTableModel precioTableModel;
	private ProductoDepositoTableModel depositoTableModel;
	private DepositoService depositoService;
	private JButton btnActualizarLista;
	

	@Autowired
	public ProductoDialog(ProductoService service, 
			ProductTableModel tableModel, ProductoDepositoTableModel productoDepositoTableModel, DepositoService depositoService,
			ProductoPrecioTableModel productoPrecioTableModel) {
		this.service = service;
		this.tableModel = tableModel;
		this.depositoTableModel = productoDepositoTableModel;
		this.depositoService = depositoService;
		this.precioTableModel = productoPrecioTableModel;
		
		setTitle("LISTA DE STOCK");
		setSize(803, 518);
		setModal(true);
		getContentPane().setLayout(null);
		
		JPanel pnlBuscador = new JPanel();
		pnlBuscador.setBounds(0, 0, 787, 35);
		getContentPane().add(pnlBuscador);
		
		JLabel lblBuscador = new JLabel("Buscador");
		pnlBuscador.add(lblBuscador);
		
		tfBuscador = new JTextField();
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
		
		btnActualizarLista = new JButton("Actualizar Lista");
		btnActualizarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizaLista();
			}
		});
		pnlBuscador.add(btnActualizarLista);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 35, 787, 408);
		getContentPane().add(scrollPane);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                loadData(); 
            }
        });
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
		pnlBotonera.setBounds(0, 443, 787, 35);
		getContentPane().add(pnlBotonera);
		
		btnAceptar = new JButton("Aceptar");
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
		
		
//		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
//		Dimension ventana = this.getSize(); 
//		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		Util.setupScreen(this);
		loadProductos("");	
	}
	
	public void loadProductos(String filter) {
		//if (filter.isEmpty()&&productos==null) {
			productos = service.findAllByNombre();
		//}
		
        tableModel.clear();
        tableModel.addEntities(productos);
        //table.requestFocus();
    }
	
	public void actualizaLista() {
		productos = service.findAll();
		//}
		
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
	
	private Producto getProducto() {
		return productos.get(table.getSelectedRow());         
	}
	
	public Long getProductoId() {
		return productos.get(table.getSelectedRow()).getId();         
	}

	public void getProductos() {
		loadProductos("");
	}
	
	private void getStockProductosByDeposito(Producto p) {
		depositoTableModel.clear();
		
		if (p != null) {
			String deposito = depositoService.findById(1L).get().getNombre();
			
			if (p.getDepO1() != null) {
				ProductoDeposito dep01 = new ProductoDeposito(deposito, p.getDepO1());
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
	
	private void loadData() {
		int selectedRow = table.getSelectedRow();
        
		if (selectedRow != -1) {
			Long productoId = tableModel.getEntityByRow(selectedRow).getId();
			Producto p = service.getStockDepositoByProductoId(productoId);
			
			if (p != null) {
				getStockProductosByDeposito(p);
				getPreciosByProducto(p);
			}
	    }
	}
}
