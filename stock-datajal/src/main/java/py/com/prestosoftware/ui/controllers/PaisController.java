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
import py.com.prestosoftware.data.models.Pais;
import py.com.prestosoftware.domain.services.PaisService;
import py.com.prestosoftware.domain.validations.PaisValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.PaisFrame;
import py.com.prestosoftware.ui.forms.PaisPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.PaisTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class PaisController extends AbstractFrameController {

    private PaisFrame frame;
    private PaisTableModel tableModel;
    private PaisService service;
    private PaisValidator validator;
   
    @Autowired
    public PaisController(PaisFrame frame, PaisTableModel tableModel, 
    					  PaisService service, PaisValidator validator) {
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
        
        registerButtonEvent(frame.getFormPanel().getBtnGuardar(), new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					save();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) { }
			
			@Override
			public void keyPressed(KeyEvent e) { }
		});
        
        registerSelectRow(frame.getTablePanel().getTable().getSelectionModel(), new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setData();
			}
		});
    }
    
    public PaisTableModel getTableModel() {
		return tableModel;
	}

    @Override
    public void prepareAndOpenFrame() {
        loadPaises();
        addNewPais();
        showFrame();
    }
    
    private void addNewPais() {
    	long cantPais = service.addNewPais();
    	frame.getFormPanel().setNewPais(cantPais + 1);
    }

    private void loadPaises() {
        List<Pais> paises = service.findAll();
        tableModel.clear();
        tableModel.addEntities(paises);
    }
    
    private void findAll(String name) {
    	List<Pais> paises;
    	
    	if (name.isEmpty()) {
    		paises = service.findAll();
		} else {
			paises = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(paises);
    }

    private void save() {
        PaisPanel formPanel = frame.getFormPanel();
        Pais pais = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(pais);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(pais);
            frame.getFormPanel().clearForm();
            loadPaises();
            addNewPais();
        }
    }
    
    private void showFrame() {
        frame.setVisible(true);
        frame.getFormPanel().getTfNombre().requestFocus();
    }

    private void cleanInputs() {
    	frame.getFormPanel().clearForm();
    	addNewPais();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	Pais pais = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(pais);   
        }
    }

}
