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
import py.com.prestosoftware.data.models.PlanCuenta;
import py.com.prestosoftware.domain.services.PlanCuentaService;
import py.com.prestosoftware.domain.validations.PlanCuentaValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.PlanCuentaFrame;
import py.com.prestosoftware.ui.forms.PlanCuentaPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.PlanCuentaTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class PlanCuentaController extends AbstractFrameController {

    private PlanCuentaFrame frame;
    private PlanCuentaTableModel tableModel;
    private PlanCuentaService service;
    private PlanCuentaValidator validator;
   
    @Autowired
    public PlanCuentaController(PlanCuentaFrame frame, PlanCuentaTableModel tableModel, 
    		PlanCuentaService service, PlanCuentaValidator validator) {
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
    
    public PlanCuentaTableModel getTableModel() {
		return tableModel;
	}

    @Override
    public void prepareAndOpenFrame() {
        loadPlanCuentas();
        showFrame();
        getRowCount();
    }

    private void loadPlanCuentas() {
        List<PlanCuenta> cuentas = service.findAll();
        tableModel.clear();
        tableModel.addEntities(cuentas);
    }
    
    private void getRowCount() {
    	long id = service.getRoxMax();
    	frame.getFormPanel().setNewPlanCuenta(id + 1);
    }
    
    private void findAll(String name) {
    	List<PlanCuenta> cuentas;
    	
    	if (name.isEmpty()) {
    		cuentas = service.findAll();
		} else {
			cuentas = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(cuentas);
    }

    private void save() {
        PlanCuentaPanel formPanel = frame.getFormPanel();
        PlanCuenta cuenta = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(cuenta);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(cuenta);
            loadPlanCuentas();
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
        	PlanCuenta cuenta = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(cuenta);   
        }
    }

}
