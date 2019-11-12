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
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.domain.services.CiudadService;
import py.com.prestosoftware.domain.services.DepartamentoService;
import py.com.prestosoftware.domain.validations.CiudadValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.CiudadFrame;
import py.com.prestosoftware.ui.forms.CiudadPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.CiudadTableModel;
import py.com.prestosoftware.ui.table.DepartamentoComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class CiudadController extends AbstractFrameController {

    private CiudadFrame frame;
    private CiudadTableModel tableModel;
    private CiudadValidator validator;
    private CiudadService service;
    private DepartamentoComboBoxModel depComboModel;
    private DepartamentoService depService;
   
    @Autowired
    public CiudadController(CiudadFrame frame, CiudadTableModel tableModel, 
    						CiudadService service, CiudadValidator validator,
    						DepartamentoService depService, DepartamentoComboBoxModel depComboModel) {
        this.frame = frame;
        this.tableModel = tableModel;
        this.service = service;
        this.validator = validator;
        this.depService = depService;
        this.depComboModel = depComboModel;
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
    
    public CiudadTableModel getTableModel() {
		return tableModel;
	}

    @Override
    public void prepareAndOpenFrame() {
        loadCiudades();
        loadDeps();
        showFrame();
        addNewCiudad();
    }

    private void loadCiudades() {
    	List<Ciudad> ciudades = service.findAll();
        tableModel.clear();
        tableModel.addEntities(ciudades);
    }
    
    private void loadDeps() {
    	List<Departamento> deps = depService.findAll();
    	depComboModel.clear();
    	depComboModel.addElements(deps);
    }

    private void addNewCiudad() {
    	long ciudadId = service.addNewCiudad();
    	frame.getFormPanel().setNewCotizacion(ciudadId + 1);
    }
   
    private void findAll(String name) {
    	List<Ciudad> ciudades;
    	
    	if (name.isEmpty()) {
    		ciudades = service.findAll();
		} else {
			ciudades = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(ciudades);
    }

    private void showFrame() {
        frame.setVisible(true);
        frame.getFormPanel().getTfNombre().requestFocus();
    }

    private void save() {
        CiudadPanel formPanel = frame.getFormPanel();
        Ciudad ciudad = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(ciudad);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(ciudad);
            tableModel.fireTableDataChanged();
            cleanInputs();
            loadCiudades();
        }
    }

    private void cleanInputs() {
    	frame.getFormPanel().clearForm();
    	frame.getFormPanel().getTfNombre().requestFocus();
    	addNewCiudad();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	Ciudad ciudad = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(ciudad);   
        }
    }

}
