package py.com.prestosoftware.ui.search;

import javax.swing.JDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import py.com.prestosoftware.ui.table.ProductTableModel;
import py.com.prestosoftware.ui.table.ProductoDepositoTableModel;
import py.com.prestosoftware.ui.table.ProductoPrecioTableModel;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.FlowLayout;

@Component
public class ProductoDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField tfBuscador;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JTable table;
	private JScrollPane scrollPane;
	
	private JTable tbDeposito;
	private JScrollPane scrollPaneDeposito;
	private JTable tbPrecioIva;
	private JScrollPane scrollPanePrecioIva;
	
	private ProductoService service;
	private ProductTableModel tableModel;
	private ProductoInterfaz interfaz;
	
	private List<Producto> productos;
	
	private ProductoPrecioTableModel precioTableModel;
	private ProductoDepositoTableModel depositoTableModel;
	private DepositoService depositoService;
	
	private JPanel pnlDeposito;
	private JPanel pnlPrecioIva;
	

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
		setSize(900, 600);
		setModal(true);
		getContentPane().setLayout(null);
		
		JPanel pnlBuscador = new JPanel();
		pnlBuscador.setBounds(0, 0, 900, 35);
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
			    } else if (e.getKeyCode()==KeyEvent.VK_F5) {
			    	
			    }
				loadProductos(tfBuscador.getText().isEmpty() ? "" : tfBuscador.getText());
				
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 35, 614, 408);
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
		pnlBotonera.setBounds(0, 443, 900, 35);
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
		
		pnlDeposito = new JPanel();
		pnlDeposito.setBounds(626, 35, 248, 197);
		pnlDeposito.setBorder(new TitledBorder(null, "DEPOSITOS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlDeposito, "cell 1 0 1 3,grow");
		pnlDeposito.setLayout(null);
		
		scrollPaneDeposito = new JScrollPane();
		scrollPaneDeposito.setBounds(6, 18, 234, 168);
		pnlDeposito.add(scrollPaneDeposito);
		
		tbDeposito = new JTable(depositoTableModel);
		tbDeposito.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		scrollPaneDeposito.setViewportView(tbDeposito);
		
		
		pnlPrecioIva = new JPanel();
		pnlPrecioIva.setBounds(626, 244, 248, 199);
		pnlPrecioIva.setBorder(new TitledBorder(null, "PRECIO CON IVA", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnlPrecioIva, "cell 1 4,grow");
		pnlPrecioIva.setLayout(null);
		
		scrollPanePrecioIva = new JScrollPane();
		scrollPanePrecioIva.setBounds(6, 18, 232, 173);
		pnlPrecioIva.add(scrollPanePrecioIva);
		
		tbPrecioIva = new JTable(precioTableModel);
		tbPrecioIva.setDefaultRenderer(Object.class, new CellRendererOperaciones());
		scrollPanePrecioIva.setViewportView(tbPrecioIva);
		
		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		
		loadProductos("");	
	}
	
	private void loadProductos(String filter) {
		if (filter.isEmpty()) {
			productos = service.findAll();
		} else {
			//productos = service.findByNombre(name);
			productos = service.findProductByFilter(filter);
		}
		
        tableModel.clear();
        tableModel.addEntities(productos);
        //table.requestFocus();
    }
	
	public ProductoInterfaz getInterfaz() {
		return interfaz;
	}
	
	public void setInterfaz(ProductoInterfaz interfaz) {
		this.interfaz = interfaz;
	}
	
	private void aceptar() {
		for (Integer c : table.getSelectedRows()) {
			interfaz.getEntity(productos.get(c));         
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
