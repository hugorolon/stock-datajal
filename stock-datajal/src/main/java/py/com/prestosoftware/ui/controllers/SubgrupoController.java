package py.com.prestosoftware.ui.controllers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Subgrupo;
import py.com.prestosoftware.domain.services.GrupoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.SubgrupoService;
import py.com.prestosoftware.domain.validations.SubgrupoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.SubgrupoFrame;
import py.com.prestosoftware.ui.forms.SubgrupoPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.GrupoComboBoxModel;
import py.com.prestosoftware.ui.table.SubgrupoTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class SubgrupoController extends AbstractFrameController {

    private SubgrupoFrame frame;
    private SubgrupoTableModel tableModel;
    private SubgrupoService service;
    private SubgrupoValidator validator;
    
    private GrupoComboBoxModel grupoComboModel;
    private GrupoService grupoService;
    private ProductoService productoService;
   
    @Autowired
    public SubgrupoController(SubgrupoFrame frame, SubgrupoTableModel tableModel, 
    		SubgrupoService service, SubgrupoValidator validator, ProductoService productoService,
    		GrupoComboBoxModel grupoComboModel, GrupoService grupoService) {
        this.frame = frame;
        this.tableModel = tableModel;
        this.service = service;
        this.validator = validator;
        this.productoService = productoService;
        this.grupoComboModel = grupoComboModel;
        this.grupoService = grupoService;
    }

    @PostConstruct
    private void prepareListeners() {
        TableSearchPanel searchPanel = frame.getSearchPanel();
        
        registerAction(frame.getFormPanel().getBtnGuardar(), (e) -> save());
        registerAction(frame.getFormPanel().getBtnCancelar(), (e) -> cleanInputs());
        registerAction(frame.getFormPanel().getBtnCerrar(), (e) -> closeWindow());
        
        registerAction(searchPanel.getSearchBtn(), (e) -> findAll(searchPanel.getTfSearchField().getText()));
        registerKeyEvent(searchPanel.getTfSearchField(), new KeyListener() {
        	@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					String name = searchPanel.getTfSearchField().getText();
					findAll(name);
				}
			}
		});
        
        registerSelectRow(frame.getTablePanel().getTable().getSelectionModel(), new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setData();
			}
		});
    }

    @Override
    public void prepareAndOpenFrame() {
        loadSubGrupos();
        loadGrupos();
        showFrame();
        addNewSubGrupo();
    }
    
    private void loadGrupos() {
    	List<Grupo> deps = grupoService.findAll();
    	grupoComboModel.clear();
    	grupoComboModel.addElements(deps);
    }

    private void loadSubGrupos() {
        List<Subgrupo> subgrupos = service.findAll();
        tableModel.clear();
        tableModel.addEntities(subgrupos);
    }
    
    private void addNewSubGrupo() {
    	long id = service.getRowCount();
    	frame.getFormPanel().setNewSubGrupo(id + 1);
    }
    
    private void findAll(String name) {
    	List<Subgrupo> grupos;
    	
    	if (name.isEmpty()) {
    		grupos = service.findAll();
		} else {
			grupos = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(grupos);
    }

    private void save() {
        SubgrupoPanel formPanel = frame.getFormPanel();
        Subgrupo subgrupo = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(subgrupo);
        
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(subgrupo);
            loadSubGrupos();
            cleanInputs();
            addNewSubGrupo();
            
            //calcular los incrementos de los %
            if (subgrupo.getPorcentajePrecioA() != null || subgrupo.getPorcentajePrecioA() > 0)
            	updatePrecioVentaPorcentaje(subgrupo);
            
            Notifications.showAlert("Subgrupo guardado con exito.!");
        }
    }
    
    private void updatePrecioVentaPorcentaje(Subgrupo subgrupo) {
    	//consultar todos los productos por subrgrupo y incrementarle el porcentaje
    	List<Producto> productos =  productoService.getProductoBySubrupo(subgrupo);
    	
    	if (productos.size() == 0) {
			return;
		}
    	for (Producto e : productos) {
    		if (e.getPrecioVentaA() != null)
    			e.setPrecioVentaA(e.getPrecioVentaA() + ((subgrupo.getPorcentajePrecioA() / 100) * e.getPrecioVentaA()));
    		
    		if (e.getPrecioVentaB() != null)
    			e.setPrecioVentaB(e.getPrecioVentaB() + ((subgrupo.getPorcentajePrecioB() / 100) * e.getPrecioVentaB()));
    		
    		if (e.getPrecioVentaC() != null)
    			e.setPrecioVentaC(e.getPrecioVentaC() + ((subgrupo.getPorcentajePrecioC() / 100) * e.getPrecioVentaC()));
    	
    		productoService.save(e);	
		}
    	
    	
    }
    
    private void showFrame() {
        frame.setVisible(true);
        frame.getFormPanel().getTfNombre().requestFocus();
    }

    private void cleanInputs() {
    	frame.getFormPanel().clearForm();
    	frame.getFormPanel().getTfNombre().requestFocus();
    	addNewSubGrupo();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	Subgrupo subgrupo = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(subgrupo);   
        }
    }

}
