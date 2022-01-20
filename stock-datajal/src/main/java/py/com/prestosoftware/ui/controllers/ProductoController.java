package py.com.prestosoftware.ui.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
        
        registerKeyEvent(productoPanel.getTfBuscador(), new KeyListener() {
        	@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					String name = productoPanel.getTfBuscador().getText();
					findByName(name.toUpperCase());
				}
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
    }

    private void loadProducts() {
        List<Producto> productos = productService.findAllByNombre();
        productTableModel.clear();
        productTableModel.addEntities(productos);
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
					
					String deposito3 = depositoService.findById(3L).get().getNombre();
					if (p.getDepO3() != null) {
						ProductoDeposito dep03 = new ProductoDeposito(deposito3, p.getDepO3());
						listProductoDeposito.add(dep03);
						
						String deposito4 = depositoService.findById(4L).get().getNombre();
						if (p.getDepO4() != null) {
							ProductoDeposito dep04 = new ProductoDeposito(deposito4, p.getDepO4());
							listProductoDeposito.add(dep04);
							
							String deposito5 = depositoService.findById(5L).get().getNombre();
							if (p.getDepO5() != null) {
								ProductoDeposito dep05 = new ProductoDeposito(deposito5, p.getDepO5());
								listProductoDeposito.add(dep05);
							}	
						}
					}
				}
			}	
		}
		return listProductoDeposito;
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
    	List<Producto> productos;
    	
    	if (name.isEmpty()) {
    		productos = productService.findAll();
		} else {
			productos = productService.findByNombre(name);		
		}
    	
        productTableModel.clear();
        productTableModel.addEntities(productos);
    }

    private void findById(String id) {
    	Optional<Producto> producto=Optional.of(new Producto());
    	if (!id.equalsIgnoreCase("0") ||Integer.valueOf(id).intValue()>0) {
    		producto = productService.findById(Long.valueOf(id));
		}
    	productoPanel.setProductForm(producto.get());  
    	List<ProductoDeposito> listProductoDeposito= getStockProductosByDeposito(producto.get());
    	depositoTableModel.clear();
    	depositoTableModel.addEntities(listProductoDeposito);
    }

    
    private void showProductFrame() {
        productoPanel.setVisible(true);
        productoPanel.getTfNombre().requestFocus();
       // productoPanel.setinterfaz(compraLocal);
    }

    public void setInterfaz(ProductoInterfaz productoInterfaz) {
    	productoPanel.setInterfaz(productoInterfaz);
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
            	cleanInputs();
            }else {
            	setProducto(product);
            	//setInterfaz(product);
            	productoPanel.dispose();	
            }
            	
        }
    }

    private void cleanInputs() {
    	productoPanel.clearForm();
    	productoPanel.getTfNombre().requestFocus();
    	addNewProduct();
    }
    
    private void closeWindow() {
    	productoPanel.dispose();;
    }
    
    private void setData() {
        int selectedRow = productoPanel.getTbProducto().getSelectedRow();
        
        if (selectedRow != -1) {
        	Producto product = productTableModel.getEntityByRow(selectedRow);
        	productoPanel.setProductForm(product);  
        	List<ProductoDeposito> listProductoDeposito= getStockProductosByDeposito(product);
        	depositoTableModel.clear();
        	depositoTableModel.addEntities(listProductoDeposito);
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

    
}
