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
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.domain.services.MonedaService;
import py.com.prestosoftware.domain.validations.MonedaValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.MonedaFrame;
import py.com.prestosoftware.ui.forms.MonedaPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.MonedaTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class MonedaController extends AbstractFrameController {

    private MonedaFrame frame;
    private MonedaTableModel tableModel;
    private MonedaService service;
    private MonedaValidator validator;
   
    @Autowired
    public MonedaController(MonedaFrame frame, MonedaTableModel tableModel, 
    		MonedaService service, MonedaValidator validator) {
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

    @Override
    public void prepareAndOpenFrame() {
        loadMonedas();
        showFrame();
        getMax();
    }

    private void loadMonedas() {
        List<Moneda> monedas = service.findAll();
        tableModel.clear();
        tableModel.addEntities(monedas);
    }
    
    private void getMax() {
    	long max = service.getRowCount();
    	frame.getFormPanel().setNewDeposito(max + 1);
    }
    
    private void findAll(String name) {
    	List<Moneda> monedas;
    	
    	if (name.isEmpty()) {
    		monedas = service.findAll();
		} else {
			monedas = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(monedas);
    }

    private void save() {
//        Optional<Moneda> m = service.findMonedaBase(1);
        
//        if (m.isPresent()) {
//        	Notifications.showAlert("moneda Base encontrado para " + m.get().getNombre()
//        			+ " Debes deshabilitar la moneda y luego cambiar la base.!");
//		} else {
			MonedaPanel formPanel = frame.getFormPanel();
	        Moneda mon = formPanel.getFormValue();
	        Optional<ValidationError> errors = validator.validate(mon);
			
			if (errors.isPresent()) {
	            ValidationError validationError = errors.get();
	            Notifications.showFormValidationAlert(validationError.getMessage());
	        } else {
	            service.save(mon);
	            loadMonedas();
	            cleanInputs();
	        }
//		} 
    }
    
    private void showFrame() {
        frame.setVisible(true);
        frame.getFormPanel().getTfNombre().requestFocus();
    }

    private void cleanInputs() {
    	frame.getFormPanel().clearForm();
    	frame.getFormPanel().getTfNombre().requestFocus();
    	getMax();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	Moneda mon = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(mon);   
        }
    }

}
