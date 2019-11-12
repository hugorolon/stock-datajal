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
import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.domain.services.MarcaService;
import py.com.prestosoftware.domain.validations.MarcaValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.MarcaFrame;
import py.com.prestosoftware.ui.forms.MarcaPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.MarcaTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class MarcaController extends AbstractFrameController {

    private MarcaFrame frame;
    private MarcaTableModel tableModel;
    private MarcaService service;
    private MarcaValidator validator;
   
    @Autowired
    public MarcaController(MarcaFrame frame, MarcaTableModel tableModel, 
    		MarcaService service, MarcaValidator validator) {
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
        loadMarcas();
        showFrame();
        getRowCount();
    }

    private void loadMarcas() {
        List<Marca> marcas = service.findAll();
        tableModel.clear();
        tableModel.addEntities(marcas);
    }
    
    private void getRowCount() {
    	long max = service.getRowMax();
    	frame.getFormPanel().setNewMarca(max + 1);
    }
    
    private void findAll(String name) {
    	List<Marca> marcas;
    	
    	if (name.isEmpty()) {
    		marcas = service.findAll();
		} else {
			marcas = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(marcas);
    }

    private void save() {
        MarcaPanel formPanel = frame.getFormPanel();
        Marca marca = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(marca);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(marca);
            loadMarcas();
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
        	Marca marca = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(marca);   
        }
    }

}
