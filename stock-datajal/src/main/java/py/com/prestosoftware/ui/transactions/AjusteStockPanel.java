package py.com.prestosoftware.ui.transactions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.AjusteStock;
import py.com.prestosoftware.data.models.AjusteStockDetalle;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.domain.services.AjusteStockService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.validations.AjusteStockValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.helpers.CellRendererOperaciones;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.DepositoDialog;
import py.com.prestosoftware.ui.search.DepositoInterfaz;
import py.com.prestosoftware.ui.search.ProductoDialog;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.table.AjusteStockTableModel;
import py.com.prestosoftware.util.Notifications;

import javax.swing.JTabbedPane;
import javax.swing.text.AbstractDocument;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

@Component
public class AjusteStockPanel extends JDialog implements DepositoInterfaz, ProductoInterfaz {

	private static final long serialVersionUID = 1L;
	
	private static final int DEPOSITO_CODE = 1;
	private static final int PRODUCTO_CODE = 2;
	
    private JTextField tfDeposito;
    private JTextField tfDescripcion;
    private JTextField tfProductoID;
    private JTextField tfDepositoId;
    private JTable tbProductos;
    private JTextField tfCantidad;
    private JTextField tfObs;
    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    
    private AjusteStockTableModel itemTableModel;
    private DepositoDialog depositoDialog;
    private ProductoDialog productoDialog;
    private DepositoService depositoService;
    private ProductoService productoService;
    private AjusteStockService tService;
    private AjusteStockValidator tValidator;

    @Autowired
    public AjusteStockPanel(AjusteStockTableModel itemTableModel, DepositoDialog depositoDialog, 
    		ProductoDialog productoDialog, DepositoService depositoService, AjusteStockValidator tValidator,
    		ProductoService productoService, AjusteStockService tService) {
    	this.itemTableModel = itemTableModel;
    	this.depositoDialog = depositoDialog;
    	this.productoDialog = productoDialog;
    	this.depositoService = depositoService;
    	this.tValidator = tValidator;
    	this.productoService = productoService;
    	this.tService = tService;
    	
    	setSize(900, 550);
    	setTitle("AJUSTE DE STOCK");
    	setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);
        
        initComponents();
    }

    @SuppressWarnings("serial")
	private void initComponents() {
    	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(6, 42, 882, 345);
        getContentPane().add(tabbedPane);
        
        JPanel pnlProducto = new JPanel();
        tabbedPane.addTab("Productos", null, pnlProducto, null);
        pnlProducto.setLayout(null);
        
        JLabel lblCodigo = new JLabel("CODIGO");
        lblCodigo.setBounds(6, 10, 78, 30);
        pnlProducto.add(lblCodigo);
        
        JLabel lblDescripcion = new JLabel("DESCRIPCIÓN");
        lblDescripcion.setBounds(258, 10, 180, 30);
        pnlProducto.add(lblDescripcion);
        
        tfDescripcion = new JTextField();
        tfDescripcion.setEditable(false);
        tfDescripcion.setFont(new Font("Arial", Font.PLAIN, 14));
        tfDescripcion.setColumns(10);
        tfDescripcion.setBounds(257, 40, 321, 30);
        pnlProducto.add(tfDescripcion);
        
        tfProductoID = new JTextField();
        tfProductoID.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfProductoID.selectAll();
        	}
        });
        tfProductoID.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_F4) {
					showDialog(PRODUCTO_CODE);
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfProductoID.getText().isEmpty()) {
						findProductoById(Long.valueOf(tfProductoID.getText()));
					} else {
						showDialog(PRODUCTO_CODE);
					}
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					tfCantidad.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					clearForm();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfProductoID.setFont(new Font("Arial", Font.PLAIN, 14));
        tfProductoID.setBounds(6, 40, 87, 30);
        pnlProducto.add(tfProductoID);
        tfProductoID.setColumns(10);
        
        btnRemove = new JButton(" - ");
        btnRemove.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			removeItem();
				}
        	}
        });
        btnRemove.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		removeItem();
        	}
        });
        btnRemove.setBounds(805, 40, 60, 30);
        pnlProducto.add(btnRemove);
        
        JScrollPane scrollProducto = new JScrollPane();
        scrollProducto.setBounds(6, 81, 859, 225);
        pnlProducto.add(scrollProducto);
        
        tbProductos = new JTable(itemTableModel) {
        	public boolean isCellEditable(int fila, int columna) {
				return false;
			}
        };
        scrollProducto.setViewportView(tbProductos);
        tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbProductos.setDefaultRenderer(Object.class, new CellRendererOperaciones());
        tbProductos.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		getItemSelected();
        	}
        });
        tbProductos.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					removeItem();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) {
					getItemSelected();
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					tfProductoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        
        tfCantidad = new JTextField();
        tfCantidad.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfCantidad.selectAll();
        	}
        });
        tfCantidad.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
        			if (!tfCantidad.getText().isEmpty()) {
        				btnAdd.requestFocus();
					} else {
						Notifications.showAlert("Debes digitar cantidad.!");
					}
        		} else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
        			btnAdd.requestFocus();
        		} else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
        			tfProductoID.requestFocus();
        		}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCantidad.setFont(new Font("Arial", Font.PLAIN, 14));
        tfCantidad.setColumns(10);
        tfCantidad.setBounds(98, 40, 151, 30);
        pnlProducto.add(tfCantidad);
        
        JLabel lblCantidad = new JLabel("CANTIDAD");
        lblCantidad.setBounds(98, 10, 151, 30);
        pnlProducto.add(lblCantidad);
        
        btnAdd = new JButton(" + ");
        btnAdd.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (isValidItem()) {
    				addItem();
    			}
        	}
        });
        btnAdd.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (isValidItem()) {
        				addItem();
        			}
				}
        	}
        });
        btnAdd.setBounds(742, 40, 63, 30);
        pnlProducto.add(btnAdd);
        
        lblPrecio = new JLabel("COSTO PROM.");
        lblPrecio.setBounds(587, 10, 153, 30);
        pnlProducto.add(lblPrecio);
        
        tfCosto = new JTextField();
        tfCosto.setFont(new Font("Arial", Font.PLAIN, 14));
        tfCosto.setColumns(10);
        tfCosto.setBounds(587, 40, 153, 30);
        pnlProducto.add(tfCosto);
        
        JPanel panel = new JPanel();
        panel.setBounds(12, 476, 876, 40);
        getContentPane().add(panel);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			save();
				}
        	}
        });
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		save();
        	}
        });
        panel.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			clearForm();
				}
        	}
        });
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clearForm();
        	}
        });
        panel.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			dispose();
				}
        	}
        });
        btnCerrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        panel.add(btnCerrar);
        
        tfObs = new JTextField();
        tfObs.setFont(new Font("Dialog", Font.BOLD, 14));
        ((AbstractDocument) tfObs.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfObs.setBounds(101, 439, 787, 30);
        getContentPane().add(tfObs);
        tfObs.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfProductoID.requestFocus();
				}
        	}
        });
        tfObs.setColumns(10);
        
        JLabel lblObs = new JLabel("Obs.:");
        lblObs.setBounds(12, 439, 82, 30);
        getContentPane().add(lblObs);
        
        lblNota = new JLabel("Nota:");
        lblNota.setBounds(6, 5, 62, 30);
        getContentPane().add(lblNota);
        
        tfNota = new JTextField();
        tfNota.setBounds(56, 6, 137, 30);
        getContentPane().add(tfNota);
        tfNota.setEditable(false);
        tfNota.setToolTipText("");
        tfNota.setColumns(10);
        
        JLabel lblDeposito = new JLabel("Deposito:");
        lblDeposito.setBounds(487, 5, 62, 30);
        getContentPane().add(lblDeposito);
        
        tfDepositoId = new JTextField();
        tfDepositoId.setBounds(561, 5, 75, 30);
        getContentPane().add(tfDepositoId);
        tfDepositoId.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfDepositoId.selectAll();
        	}
        });
        tfDepositoId.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_F4) {
        			showDialog(DEPOSITO_CODE);
        			tfProductoID.requestFocus();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!tfDepositoId.getText().isEmpty()) {
						findDepositoById(Long.valueOf(tfDepositoId.getText()));
						tfProductoID.requestFocus();
					} else {
						showDialog(DEPOSITO_CODE);
						tfProductoID.requestFocus();
					}
				}
        	}
			@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfDepositoId.setText("0");
        tfDepositoId.setToolTipText("");
        tfDepositoId.setColumns(10);
        
        tfDeposito = new JTextField();
        tfDeposito.setBounds(635, 5, 253, 30);
        getContentPane().add(tfDeposito);
        tfDeposito.setEditable(false);
        tfDeposito.setColumns(10);
        
        lblCantItem = new JLabel("Cant. Item:");
        lblCantItem.setHorizontalAlignment(SwingConstants.LEFT);
        lblCantItem.setBounds(6, 397, 74, 30);
        getContentPane().add(lblCantItem);
        
        tfCantItem = new JTextField();
        tfCantItem.setEditable(false);
        tfCantItem.setColumns(10);
        tfCantItem.setBounds(101, 397, 55, 30);
        getContentPane().add(tfCantItem);
        
        tfTotal = new JTextField();
        tfTotal.setForeground(Color.RED);
        tfTotal.setFont(new Font("Arial", Font.BOLD, 16));
        tfTotal.setEditable(false);
        tfTotal.setColumns(10);
        tfTotal.setBounds(758, 397, 130, 30);
        getContentPane().add(tfTotal);
        
        lblTotal = new JLabel("Total:");
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotal.setBounds(670, 397, 67, 30);
        getContentPane().add(lblTotal);
    }
    
    public void validatePrecioCosto(String tipo) {
    	if (tipo.equals("ENTRADA")) {
			tfCosto.setEditable(true);
			setTitle("AJUSTE DE ENTRADA");
		} else {
			setTitle("AJUSTE DE SALIDA");
			tfCosto.setEditable(false);
		}
    }
    
    public void setCliente(Optional<Cliente> cliente) {
    	if (cliente.isPresent()) {
    		String nombre = cliente.get().getRazonSocial();
    		tfDeposito.setText(nombre);
    		tfProductoID.requestFocus(); 
    	}
    }
    
    private void getItemSelected() {		
		int selectedRow = tbProductos.getSelectedRow();
        
        if (selectedRow != -1) {
        	AjusteStockDetalle item = itemTableModel.getEntityByRow(selectedRow);
        	
        	tfProductoID.setText(String.valueOf(item.getProductoId()));
        	tfDescripcion.setText(String.valueOf(item.getProducto()));
			tfCantidad.setText(FormatearValor.doubleAString(item.getCantidadNueva()));
        }
    }
    
    private void findProductoById(Long id) {
    	Optional<Producto> producto = productoService.findById(id);
    	
    	if (producto.isPresent()) {
    		String nombre = producto.get().getDescripcion();
    		Double precio = producto.get().getPrecioCostoPromedio() != null ? producto.get().getPrecioCostoPromedio():0;
    		tfDescripcion.setText(nombre);
    		tfCosto.setText(FormatearValor.doubleAString(precio));
    		
    		tfCantidad.requestFocus();
    	}
    }
    
    private void addItem() {
    	Boolean esDuplicado = false;
		Integer fila = -1;
		for (Integer i = 0; i < tbProductos.getRowCount(); i++) {
			String itemId = String.valueOf(tbProductos.getValueAt(i, 0));
			
			if (tfProductoID.getText().trim().equals(itemId) && !itemId.equals("")) {
				esDuplicado = true;
				fila = i;
			}
		}
		if (esDuplicado) {
			Integer respuesta = JOptionPane.showConfirmDialog(null, "Registro ya existe en la grilla, desea actualizar los datos?");
			if (respuesta == 0) {
				if (isValidItem()) {
					actualizarRegristroGrilla(fila, String.valueOf(tbProductos.getValueAt(fila, 3)));
					tfProductoID.requestFocus();
				}
			} else {
				tfProductoID.requestFocus();
			}
		} else {
			Long productoId = Long.valueOf(tfProductoID.getText());
	    	Double cantidad = FormatearValor.stringToDouble(tfCantidad.getText());
	    	
	    	Optional<Producto> p = productoService.findById(productoId);
	    	
	    	if (p.isPresent()) {
				Double stock = p.get().getStock() != null ? p.get().getStock() : 0;
				
				if (tipoAjuste.equals("SALIDA")) {
					if (cantidad <= stock) {
						addItemToList();
						calculateItem();
					} else {
						tfProductoID.requestFocus();
						Notifications.showAlert("No tiene suficiente Stock para este Producto. Stock actual : " + FormatearValor.doubleAString(stock));
					}
				} else {
					addItemToList();
					calculateItem();
				}
			}
		}
		
		clearItem();
    }
    
    private void addItemToList() {
    	itemTableModel.addEntity(getItem());
    }
    
    private void actualizarRegristroGrilla(Integer fila, String cantAnteriorItem) {
    	itemTableModel.removeRow(fila);
    	
    	Long productoId = Long.valueOf(tfProductoID.getText());
    	Double cantidad = FormatearValor.stringADouble(tfCantidad.getText());
    	
    	Optional<Producto> p = productoService.findById(productoId);
    	
    	if (p.isPresent()) {
			Double stock = p.get().getStock() != null ? p.get().getStock() : 0;
			
			if (cantidad <= stock) {
				addItemToList();
				calculateItem();
			} else {
				tfProductoID.requestFocus();
				Notifications.showAlert("No tiene suficiente Stock para este Producto. "
						+ "Stock actual es de: " + stock);
			}
		}
    	
    	clearItem();
	}
   
    private JLabel lblNota;
    private JTextField tfNota;
    private JLabel lblPrecio;
    private JTextField tfCosto;

    public AjusteStock getFormValue() {
    	AjusteStock t = new AjusteStock();
        
    	t.setFecha(new Date());
    	t.setDeposito(new Deposito(Long.valueOf(tfDepositoId.getText())));
    	t.setUsuario(new Usuario(GlobalVars.USER_ID));
    	t.setTipoAjuste(tipoAjuste);
    	t.setSituacion("ACTIVO");
    	t.setObs(tfObs.getText());
        return t;
    }
    
    public AjusteStockDetalle getItem() {
    	AjusteStockDetalle item = new AjusteStockDetalle();
    	item.setProductoId(Long.valueOf(tfProductoID.getText()));
    	item.setProducto(tfDescripcion.getText());
    	item.setCantidadNueva(FormatearValor.stringADouble(tfCantidad.getText()));
    	item.setCosto(FormatearValor.stringADouble(tfCosto.getText()));
    	
    	Double costo = FormatearValor.stringADouble(tfCosto.getText());
    	Double cant = FormatearValor.stringADouble(tfCantidad.getText());
    	
    	item.setSubtotalCosto(costo * cant);
    	
    	return item;
    }
    
    private void calculateItem() {
		Double cantItem = itemTableModel.getEntities().stream().mapToDouble(i -> i.getCantidadNueva()).sum();
		Double total = itemTableModel.getEntities().stream().mapToDouble(i -> i.getSubtotalCosto()).sum();
		setTotals(cantItem, total);
	}
    
    private void setTotals(Double cantItem, Double total) {
    	tfTotal.setText(FormatearValor.doubleAString(total));

		if (cantItem != 0) {
			tfCantItem.setText(FormatearValor.doubleAString(cantItem));
		}
	}
    
    public void clearItem() {
    	tfProductoID.setText("");
    	tfDescripcion.setText("");
    	tfCantidad.setText("");
    	tfCosto.setText("");
    	tfProductoID.requestFocus();
    }

    public void clearForm() {
    	tfDepositoId.setText("");
        tfDeposito.setText("");
    	tfProductoID.setText("");
    	tfCantidad.setText("");
    	tfDescripcion.setText("");
    	tfObs.setText("");
    	tfCantItem.setText("");
    	tfTotal.setText("");
    	
    	while (itemTableModel.getRowCount() > 0) {
    		itemTableModel.removeRow(0);
		}
    	
    	tfDepositoId.requestFocus();
    }
    
    public int removeItem() {
    	int row[] = tbProductos.getSelectedRows();
    	
    	if (tbProductos.getSelectedRow() != -1) {
			for (Integer i = row.length; i > 0; i--) {
				itemTableModel.removeRow(row[i - 1]);
			}
			
			if (tbProductos.getRowCount() == 0) {
				btnRemove.setEnabled(false);
			}
			
			tfProductoID.requestFocus();
		} else {
			Notifications.showAlert("Debe seleccionar un Item para quitar de la lista");
		}
    	
    	return row[0];
    }
    
    private void findDepositoById(Long id) {
    	Optional<Deposito> deposito = depositoService.findById(id);
    	
    	if (deposito.isPresent()) {
    		setDeposito(deposito);
		}
	}
    
    private void setDeposito(Optional<Deposito> deposito) {
    	if (deposito.isPresent()) {
    		String nombre = deposito.get().getNombre();
    		tfDeposito.setText(nombre);
    	}
    }
    
    private void showDialog(int code) {
    	switch (code) {
			case DEPOSITO_CODE:
				depositoDialog.setInterfaz(this);
		    	depositoDialog.setVisible(true);	
				break;
			case PRODUCTO_CODE:
				productoDialog.setInterfaz(this);
				productoDialog.setVisible(true);
				break;
			default:
				break;
		}
    }
    
    private void save() {
		if (validateCabezera() && validateItems(itemTableModel.getEntities())) {
			AjusteStock t = getFormValue();
	        t.setItems(itemTableModel.getEntities());
	        
	        Optional<ValidationError> errors = tValidator.validate(t);
	        
	        if (errors.isPresent()) {
	            ValidationError validationError = errors.get();
	            Notifications.showFormValidationAlert(validationError.getMessage());
	        } else {
	        	AjusteStock a = tService.save(t);
		            
		        if (a != null) {
		            updateStockProduct(a.getItems());
	        	}
		        
		        Notifications.showAlert("Ajuste de Stock registrado con exito.!");
		        clearForm();
	        	//newTransferenciaProducto();
	        }  
		}
    }
    
    private void updateStockProduct(List<AjusteStockDetalle> items) {
    	List<Producto> productos = new ArrayList<>();
    	
    	for (Producto producto : productos) {
    		Optional<Producto> pOptional = productoService.findById(producto.getId());
    		
    		if (pOptional.isPresent()) {
    			Producto p = pOptional.get();
    			Double stock = p.getStock() != null ? p.getStock():0;
    			
    			if (tipoAjuste.equals("ENTRADA")) {
    				p.setStock(stock + producto.getCantidadPorCaja());
				} else {
					p.setStock(stock - producto.getCantidadPorCaja());
				}
				
				productos.add(p);
    		}				
		}
    	
    	productoService.updateStock(productos);
	}
    
//    public void newTransferenciaProducto() {
//		long max = tService.getRowCount();
//		tfVentaId.setText(String.valueOf(max + 1));
//		tfClienteID.requestFocus();
//	}
    
    private boolean validateCabezera() {
    	//validar deposito
		if (tfDepositoId.getText().isEmpty()) {
			Notifications.showAlert("El codigo del Deposito Origen es obligatorio");
			tfDepositoId.requestFocus();
    		return false;
		}
		
    	Optional<Deposito> deposito = depositoService.findById(Long.valueOf(tfDepositoId.getText()));
    	
    	if (!deposito.isPresent()) {
    		Notifications.showAlert("El codigo del Deposito Origen no existe.!");
    		tfDepositoId.requestFocus();
			return false;
		}
    	
    	return true;
    }
    
    private boolean validateItems(List<AjusteStockDetalle> items) {    	
    	for (AjusteStockDetalle e : items) {
    		Optional<Producto> producto = productoService.findById(e.getProductoId());
    		
    		if (producto.isPresent()) {
    			//verificar la cantidad
    			Producto p = producto.get();
    			Double stockDep01 = p.getDepO1() != null ? p.getDepO1() : 0;
    			Double stockDep02 = p.getDepO2() != null ? p.getDepO2() : 0;
    			
    			Double stock = stockDep01 + stockDep02;
    			
    			if (stock < e.getCantidadNueva() ) {
    				Notifications.showAlert("La cantidad Nueva del producto: " + producto.get().getDescripcion() + 
    						" sobrepasa el Stock Actual: " + producto.get().getStock());
    				break;
				}
			}
		}
    	
    	return true;
    }
    
    private Boolean isValidItem() {
		if (tfProductoID.getText().isEmpty()) {
			Notifications.showAlert("Digite el codigo del Producto");
			tfProductoID.requestFocus();
			return false;
		} else if (tfCantidad.getText().isEmpty()) {
			Notifications.showAlert("Digite la cantidad");
			tfCantidad.requestFocus();
			return false;
		} else if (!tfCantidad.getText().isEmpty() && FormatearValor.stringADouble(tfCantidad.getText()) <= 0) {
			Notifications.showAlert("La cantidad debe ser mayor a cero");
			tfCantidad.requestFocus();
			return false;
		}
		
		return true;
	}
    
    public void newAjuste() {
		long max = tService.getRowCount();
		tfNota.setText(String.valueOf(max + 1));
		tfDepositoId.requestFocus();
	}
    
    private String tipoAjuste = "";
    private JLabel lblCantItem;
    private JTextField tfCantItem;
    private JTextField tfTotal;
    private JLabel lblTotal;
    
    public void setTipoAjuste(String tipo) {
    	this.tipoAjuste = tipo;
    }

	@Override
	public void getEntity(Deposito d) {
		if (d != null) {
			tfDepositoId.setText(String.valueOf(d.getId()));
			tfDeposito.setText(d.getNombre());
		}
	}

	@Override
	public void getEntity(Producto producto) {
		if (producto != null) {
			tfProductoID.setText(String.valueOf(producto.getId()));
			tfDescripcion.setText(producto.getDescripcion());
			tfCantidad.setText(String.valueOf(1));
			tfCantidad.requestFocus();
		}
	}
}
