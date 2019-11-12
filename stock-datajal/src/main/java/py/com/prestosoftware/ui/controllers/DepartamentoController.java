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
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.data.models.Pais;
import py.com.prestosoftware.domain.services.DepartamentoService;
import py.com.prestosoftware.domain.services.PaisService;
import py.com.prestosoftware.domain.validations.DepartamentoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.DepartamentoFrame;
import py.com.prestosoftware.ui.forms.DepartamentoPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.DepartamentoTableModel;
import py.com.prestosoftware.ui.table.PaisComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class DepartamentoController extends AbstractFrameController {

    private DepartamentoFrame frame;
    private DepartamentoTableModel tableModel;
    private DepartamentoValidator validator;
    private PaisComboBoxModel paisComboModel;
    private DepartamentoService service;
    private PaisService paisService;
   
    @Autowired
    public DepartamentoController(DepartamentoFrame frame, DepartamentoTableModel tableModel,  
    		DepartamentoService service, PaisService paisService, 
    		DepartamentoValidator validator, PaisComboBoxModel paisComboModel) {
        this.frame = frame;
        this.tableModel = tableModel;
        this.service = service;
        this.paisService = paisService;
        this.validator = validator;
        this.paisComboModel = paisComboModel;
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
    
    public DepartamentoTableModel getTableModel() {
		return tableModel;
	}

    @Override
    public void prepareAndOpenFrame() {
        loadDepartamentos();
        loadPaises();
        showFrame();
        addNewDep();
    }

    private void loadDepartamentos() {
        List<Departamento> departamentos = service.findAll();
        tableModel.clear();
        tableModel.addEntities(departamentos);
    }
    
    private void loadPaises() {
    	List<Pais> paises = paisService.findAll();
    	paisComboModel.clear();
    	paisComboModel.addElements(paises);
    }
    
    private void addNewDep() {
    	long depId = service.addNewDep();
    	frame.getFormPanel().setNewDep(depId + 1);
    }
    
    private void findAll(String name) {
    	List<Departamento> departamentos;
    	
    	if (name.isEmpty()) {
    		departamentos = service.findAll();
		} else {
			departamentos = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(departamentos);
    }

    private void save() {
        DepartamentoPanel formPanel = frame.getFormPanel();
        Departamento dep = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(dep);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(dep);
            tableModel.fireTableDataChanged();
            frame.getFormPanel().clearForm();
            loadDepartamentos();
            addNewDep(); 
        }
    }
    
    private void showFrame() {
        frame.setVisible(true);
        frame.getFormPanel().getTfNombre().requestFocus();
    }

    private void cleanInputs() {
    	frame.getFormPanel().clearForm();
    	frame.getFormPanel().getTfNombre().requestFocus();
    	addNewDep();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	Departamento dep = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(dep);   
        }
    }

}