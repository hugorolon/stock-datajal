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
import py.com.prestosoftware.data.models.Categoria;
import py.com.prestosoftware.domain.services.CategoriaService;
import py.com.prestosoftware.domain.validations.CategoriaValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.CategoriaFrame;
import py.com.prestosoftware.ui.forms.CategoriaPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.CategoriaTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class CategoriaController extends AbstractFrameController {

    private CategoriaFrame frame;
    private CategoriaTableModel tableModel;
    private CategoriaService service;
    private CategoriaValidator validator;
   
    @Autowired
    public CategoriaController(CategoriaFrame frame, CategoriaTableModel tableModel, 
    						CategoriaService service, CategoriaValidator validator) {
        this.frame = frame;
        this.tableModel = tableModel;
        this.service = service;
        this.validator = validator;
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
    
    public CategoriaTableModel getTableModel() {
		return tableModel;
	}

    @Override
    public void prepareAndOpenFrame() {
        loadCategorias();
        showFrame();
        getRowCount();
    }

    private void loadCategorias() {
        List<Categoria> categorias = service.findAll();
        tableModel.clear();
        tableModel.addEntities(categorias);
    }
    
    private void getRowCount() {
    	long id = service.getRoxMax();
    	frame.getFormPanel().setNewCat(id + 1);
    }
    
    private void findAll(String name) {
    	List<Categoria> categorias;
    	
    	if (name.isEmpty()) {
    		categorias = service.findAll();
		} else {
			categorias = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(categorias);
    }

    private void save() {
        CategoriaPanel formPanel = frame.getFormPanel();
        Categoria categoria = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(categoria);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(categoria);
            loadCategorias();
            cleanInputs();
        }
    }
    
    private void showFrame() {
        frame.setVisible(true);
        frame.getFormPanel().getTfNombre().requestFocus();
    }

    private void cleanInputs() {
    	frame.getFormPanel().clearForm();
    	frame.getFormPanel().getTfNombre().requestFocus();
    	getRowCount();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	Categoria categoria = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(categoria);   
        }
    }

}
