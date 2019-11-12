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
import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.domain.services.CondicionPagoService;
import py.com.prestosoftware.domain.validations.CondicionPagoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.CondicionPagoFrame;
import py.com.prestosoftware.ui.forms.CondicionPagoPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.CondicionPagoTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class CondicionPagoController extends AbstractFrameController {

    private CondicionPagoFrame frame;
    private CondicionPagoTableModel tableModel;
    private CondicionPagoService service;
    private CondicionPagoValidator validator;
   
    @Autowired
    public CondicionPagoController(CondicionPagoFrame frame, CondicionPagoTableModel tableModel, 
    		CondicionPagoService service, CondicionPagoValidator validator) {
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
    
    public CondicionPagoTableModel getTableModel() {
		return tableModel;
	}

    @Override
    public void prepareAndOpenFrame() {
        showFrame();
        loadCondiciones();
        getRowCount();
    }

    private void loadCondiciones() {
        List<CondicionPago> condiciones = service.findAll();        
        tableModel.clear();
        tableModel.addEntities(condiciones);
    }
    
    private void getRowCount() {
    	long id = service.getRoxMax();
    	frame.getFormPanel().setNewCat(id + 1);
    }
    
    private void findAll(String name) {
    	List<CondicionPago> condiciones;
    	
    	if (name.isEmpty()) {
    		condiciones = service.findAll();
		} else {
			condiciones = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(condiciones);
    }

    private void save() {
        CondicionPagoPanel formPanel = frame.getFormPanel();
        CondicionPago c = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(c);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(c);
            loadCondiciones();
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
        	CondicionPago c = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(c);   
        }
    }

}
