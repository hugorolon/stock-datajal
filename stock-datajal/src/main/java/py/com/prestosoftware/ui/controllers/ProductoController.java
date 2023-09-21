package py.com.prestosoftware.ui.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import py.com.prestosoftware.data.models.Categoria;
import py.com.prestosoftware.data.models.Color;
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Impuesto;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.data.models.Ncm;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.ProductoDeposito;
import py.com.prestosoftware.data.models.Subgrupo;
import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.data.models.UnidadMedida;
import py.com.prestosoftware.domain.services.CategoriaService;
import py.com.prestosoftware.domain.services.ColorService;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.GrupoService;
import py.com.prestosoftware.domain.services.ImpuestoService;
import py.com.prestosoftware.domain.services.ListaPrecioService;
import py.com.prestosoftware.domain.services.MarcaService;
import py.com.prestosoftware.domain.services.NcmService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.SubgrupoService;
import py.com.prestosoftware.domain.services.TamanhoService;
import py.com.prestosoftware.domain.services.UnidadMedidaService;
import py.com.prestosoftware.domain.validations.ProductoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.ProductoPanel;
import py.com.prestosoftware.ui.search.ProductoInterfaz;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.table.CategoriaComboBoxModel;
import py.com.prestosoftware.ui.table.ColorComboBoxModel;
import py.com.prestosoftware.ui.table.GrupoComboBoxModel;
import py.com.prestosoftware.ui.table.ImpuestoComboBoxModel;
import py.com.prestosoftware.ui.table.ListaPrecioComboBoxModel;
import py.com.prestosoftware.ui.table.MarcaComboBoxModel;
import py.com.prestosoftware.ui.table.NcmComboBoxModel;
import py.com.prestosoftware.ui.table.ProductTableModel;
import py.com.prestosoftware.ui.table.ProductoDepositoTableModel;
import py.com.prestosoftware.ui.table.SubgrupoComboBoxModel;
import py.com.prestosoftware.ui.table.TamanhoComboBoxModel;
import py.com.prestosoftware.ui.table.UnidadMedidaComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class ProductoController extends AbstractFrameController {

    private ProductoService productService;
    private ProductoValidator productValidator;
    private CategoriaService categoriaService;
    private GrupoService grupoService;
    private SubgrupoService subGrupoService;
    private NcmService ncmService;
    private ImpuestoService impuestoService;
    private DepositoService depositoService;
    private MarcaService marcaService;
    private TamanhoService tamanhoService;
    private UnidadMedidaService unidadMedidaService;
    private ColorService colorService;
    private ListaPrecioService listaPrecioService;
   // private ProductoInterfaz interfaz;
    
    private ProductoPanel productoPanel;
    private ProductTableModel productTableModel;
    private CategoriaComboBoxModel categoriaComboBoxModel;
    private GrupoComboBoxModel grupoComboBoxModel;
    private SubgrupoComboBoxModel subgrupoComboBoxModel;
    private NcmComboBoxModel ncmComboBoxModel;
    private ImpuestoComboBoxModel impuestoComboBoxModel;
    private MarcaComboBoxModel marcaComboBoxModel;
    private TamanhoComboBoxModel tamanhoComboBoxModel;
    private UnidadMedidaComboBoxModel umedidaComboBoxModel;
    private ColorComboBoxModel colorComboBoxModel;
    private ListaPrecioComboBoxModel listaComboBoxModel;
    private ProductoDepositoTableModel depositoTableModel;
    private String origen;
    private Producto producto;
    private List<Producto> productos;
    private DefaultTableModel table1;
    
    @Autowired
    public ProductoController(ProductoPanel productFrame, ProductTableModel productTableModel, ProductoService productService, 
    	ProductoValidator productValidator, CategoriaService categoriaService, GrupoService grupoService, ListaPrecioService listaPrecioService,
    	NcmService ncmService, ImpuestoService impuestoService, MarcaService marcaService, TamanhoService tamanhoService, SubgrupoService subGrupoService,
    	UnidadMedidaService unidadMedidaService, ColorService colorService, DepositoService depositoService, CategoriaComboBoxModel categoriaComboBoxModel,
    	GrupoComboBoxModel grupoComboBoxModel, NcmComboBoxModel ncmComboBoxModel, ImpuestoComboBoxModel impuestoComboBoxModel,
    	MarcaComboBoxModel marcaComboBoxModel, TamanhoComboBoxModel tamanhoComboBoxModel, UnidadMedidaComboBoxModel unidadMedidaComboBoxModel, 
    	ColorComboBoxModel colorComboBoxModel, ListaPrecioComboBoxModel listaComboBoxModel, SubgrupoComboBoxModel subgrupoComboBoxModel, ProductoDepositoTableModel depositoTableModel) {
        this.productoPanel = productFrame;  
        this.productTableModel = productTableModel;
        this.depositoTableModel=depositoTableModel;
        this.productService = productService;
        this.productValidator = productValidator;
        this.categoriaService = categoriaService;
        this.grupoService = grupoService;
        this.ncmService = ncmService;
        this.impuestoService = impuestoService;
        this.marcaService = marcaService;
        this.tamanhoService = tamanhoService;
        this.unidadMedidaService = unidadMedidaService;
        this.colorService = colorService;
        this.listaPrecioService = listaPrecioService;
        this.subGrupoService = subGrupoService;
        this.depositoService = depositoService;
        this.categoriaComboBoxModel = categoriaComboBoxModel;
        this.grupoComboBoxModel = grupoComboBoxModel;
        this.ncmComboBoxModel = ncmComboBoxModel;
        this.impuestoComboBoxModel = impuestoComboBoxModel;
        this.marcaComboBoxModel = marcaComboBoxModel;
        this.tamanhoComboBoxModel = tamanhoComboBoxModel;
        this.umedidaComboBoxModel = unidadMedidaComboBoxModel;
        this.colorComboBoxModel = colorComboBoxModel;
        this.listaComboBoxModel = listaComboBoxModel;
        this.subgrupoComboBoxModel = subgrupoComboBoxModel;
    }

    @PostConstruct
    private void prepareListeners() {
        registerAction(productoPanel.getBtnGuardar(), (e) -> save());
        registerAction(productoPanel.getBtnCancelar(), (e) -> cleanInputs());
        registerAction(productoPanel.getBtnCerrar(), (e) -> closeWindow());
        registerAction(productoPanel.getBtnNuevo(), (e) -> cleanInputs());
  
        registerAction(productoPanel.getBtnBuscador(), (e) -> findByName(productoPanel.getTfBuscador().getText()));
        
        registerSelectRow(productoPanel.getTbProducto().getSelectionModel(), (e) -> setData());
        
        registerKeyEvent(productoPanel.getTfBuscador(), new KeyListener() {
        	@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					String name = productoPanel.getTfBuscador().getText();
					findByName(name.toUpperCase());
				} else if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
					productoPanel.dispose();
			    } else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			    	productoPanel.getTbProducto().requestFocus();
			    } 
			}

			@Override
			public void keyReleased(KeyEvent e) {
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();
				textField.setText(text.toUpperCase());
				DefaultTableModel table1 = (DefaultTableModel) productoPanel.getTbProducto().getModel();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table1);
				productoPanel.getTbProducto().setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter("(?i)" + textField.getText()));
			}
		});
        
        registerKeyEvent(productoPanel.getTfProductoId(), new KeyListener() {
        	@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					String id = productoPanel.getTfProductoId().getText();
					 findById(id);
				}
			}
		});
        
        registerButtonEvent(productoPanel.getBtnNuevo(), new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					cleanInputs();
				}
			}
		});
        
        registerSelectRow(productoPanel.getTbProducto().getSelectionModel(), new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setData();	
			}
		});
    }

    @Override
    public void prepareAndOpenFrame() {
    	try {
            loadProducts();
            loadCategorias();
            loadGrupos();
            loadNcms();
            loadImpuestos();
            loadMarcas();
            loadUnidadDeMedidas();
            loadColors();
            loadTamanhos();
            loadListaPrecios();
            //loadSubGrupos();
            showProductFrame();
            
            productoPanel.getBtnNuevo().requestFocus();			
		} catch (Exception e) {
			// TODO: handle exception
		}

    }

    private void loadProducts() {
        this.productos = productService.findAllByNombre();
        productoPanel.getProductTableModel().clear();
        productoPanel.getProductTableModel().addEntities(this.productos);
    }

    private void loadCategorias() {
        List<Categoria> categorias = categoriaService.findAll();
        categoriaComboBoxModel.clear();
        categoriaComboBoxModel.addElements(categorias);
    }
    
    private void loadGrupos() {
        List<Grupo> grupos = grupoService.findAll();
        grupoComboBoxModel.clear();
        grupoComboBoxModel.addElements(grupos);
    }
    
    private void loadSubGrupos() {
    	List<Subgrupo> subgrupos = subGrupoService.findAll();
    	subgrupoComboBoxModel.clear();
        subgrupoComboBoxModel.addElements(subgrupos);
    }
    
    private void loadNcms() {
        List<Ncm> ncms = ncmService.findAll();
        ncmComboBoxModel.clear();
        ncmComboBoxModel.addElements(ncms);
    }
    
    private void loadImpuestos() {
        List<Impuesto> impuestos = impuestoService.findAll();
        impuestoComboBoxModel.clear();
        impuestoComboBoxModel.addElements(impuestos);
    }
    
    
    private List<ProductoDeposito>  getStockProductosByDeposito(Producto p) {
    	try {
    		List<ProductoDeposito> listProductoDeposito= new ArrayList<ProductoDeposito>();
    		if (p != null) {
    			String deposito = depositoService.findById(1L).get().getNombre();
    			if (p.getDepO1() != null) {
    				ProductoDeposito dep01 = new ProductoDeposito(deposito, p.getDepO1());
    				listProductoDeposito.add(dep01);
    				
    				String deposito2 = depositoService.findById(2L).get().getNombre();
    				if (p.getDepO2() != null) {
    					ProductoDeposito dep02 = new ProductoDeposito(deposito2, p.getDepO2());
    					listProductoDeposito.add(dep02);
    				}
    			}	
    		}
    		return listProductoDeposito;	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
    
    private void loadMarcas() {
        List<Marca> marcas = marcaService.findAll();
        marcaComboBoxModel.clear();
        marcaComboBoxModel.addElements(marcas);
    }
    
    private void loadUnidadDeMedidas() {
        List<UnidadMedida> umedidas = unidadMedidaService.findAll();
        umedidaComboBoxModel.clear();
        umedidaComboBoxModel.addElements(umedidas);
    }
    
    private void loadColors() {
        List<Color> colores = colorService.findAll();
        colorComboBoxModel.clear();
        colorComboBoxModel.addElements(colores);
    }
    
    private void loadListaPrecios() {
    	List<ListaPrecio> listaPrecios = listaPrecioService.findAll();
        listaComboBoxModel.clear();
        listaComboBoxModel.addElements(listaPrecios);
    }
    
    private void loadTamanhos() {
        List<Tamanho> tamanhos = tamanhoService.findAll();
        tamanhoComboBoxModel.clear();
        tamanhoComboBoxModel.addElements(tamanhos);
    }
    
    public void addNewProduct() {
    	long id = productService.addNewProduct();
    	productoPanel.setNewProducto(id + 1);
    }
    
    private void findByName(String name) {
    	try {
    		if (name.isEmpty()) {
        		productos = productService.findAll();
    		} else {
    			productos = productService.findByNombre(name);		
    		}
        	productoPanel.getProductTableModel().clear();
        	productoPanel.getProductTableModel().addEntities(productos);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    private void findById(String id) {
    	try {
    		Optional<Producto> producto=Optional.of(new Producto());
        	if (!id.equalsIgnoreCase("0") ||Integer.valueOf(id).intValue()>0) {
        		producto = productService.findById(Long.valueOf(id));
    		}
        	productoPanel.setProductForm(producto.get());  
        	List<ProductoDeposito> listProductoDeposito= getStockProductosByDeposito(producto.get());
        	depositoTableModel.clear();
        	depositoTableModel.addEntities(listProductoDeposito);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    
    private void showProductFrame() {
    	productoPanel.setVisible(true);
        productoPanel.getTfNombre().requestFocus();
    }

    public void setInterfaz(ProductoInterfaz productoInterfaz) {
    	productoPanel.setInterfaz(productoInterfaz);
    }
    
    public ProductoInterfaz getInterfaz() {
		return productoPanel.getInterfaz();
	}

    
	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	private void save() {
        Producto product = productoPanel.getProductForm();
        Optional<ValidationError> errors = productValidator.validate(product);
        
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            productService.save(product);
            if(origen.equalsIgnoreCase("MENU")) {
            	loadProducts();
            	
            }else {
            	productoPanel.getInterfaz().getEntity(product);
            	setProducto(product);
            	productoPanel.dispose();	
            }
            cleanInputs();	
            productoPanel.getMarcaComboBoxModel().setSelectedItem(product.getMarca());
        }
    }

    private void cleanInputs() {
    	try {
        	productoPanel.clearForm();
        	productoPanel.getTfNombre().requestFocus();
        	//addNewProduct();	
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    private void closeWindow() {
    	productoPanel.dispose();;
    }
    
    private void setData() {
    	try {
    		int[] selectedRow = productoPanel.getTbProducto().getSelectedRows();
            if (selectedRow.length>0 && selectedRow[0] != -1) {
            	Long selectedId = (Long) productoPanel.getTbProducto().getValueAt(selectedRow[0], 0);
            	//Producto product =(Producto) this.getTable1().getEntityByRow(selectedRow);
            	Producto product= this.productos.stream().filter(pro -> pro.getId().equals(selectedId.longValue()))
      				  .findAny()
      				  .orElse(null);
            	productoPanel.setProductForm(product);  
            }
		} catch (Exception e) {
			// TODO: handle exception
		}   
    }
    
   
	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public DefaultTableModel getTable1() {
		return table1;
	}

	public void setTable1(DefaultTableModel table1) {
		this.table1 = table1;
	}

	public ProductTableModel getProductTableModel() {
		return productTableModel;
	}

	public void setProductTableModel(ProductTableModel productTableModel) {
		this.productTableModel = productTableModel;
	}

    
}
