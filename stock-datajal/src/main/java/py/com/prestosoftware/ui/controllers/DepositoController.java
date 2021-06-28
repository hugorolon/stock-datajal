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
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.domain.services.DepositoService;
import py.com.prestosoftware.domain.services.EmpresaService;
import py.com.prestosoftware.domain.validations.DepositoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.DepositoFrame;
import py.com.prestosoftware.ui.forms.DepositoPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.DepositoTableModel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class DepositoController extends AbstractFrameController {

    private DepositoFrame frame;
    private DepositoTableModel tableModel;
    private DepositoService service;
    private DepositoValidator validator;
    
    private EmpresaService empresaService;
    private EmpresaComboBoxModel empresaComboBoxModel;
   
    @Autowired
    public DepositoController(DepositoFrame frame, DepositoTableModel tableModel, 
    	DepositoService service, DepositoValidator validator,
    	EmpresaService empresaService, EmpresaComboBoxModel empresaComboBoxModel) {
        this.frame = frame;
        this.tableModel = tableModel;
        this.service = service;
        this.validator = validator;
        this.empresaService = empresaService;
        this.empresaComboBoxModel = empresaComboBoxModel;
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
        loadDepositos();
       // loadEmpresas();
        showFrame();
        getMax();
    }

    private void loadDepositos() {
        List<Deposito> depositos = service.findAll();
        tableModel.clear();
        tableModel.addEntities(depositos);
    }
    
    private void loadEmpresas() {
        List<Empresa> empresas = empresaService.findAll();
        empresaComboBoxModel.clear();
        empresaComboBoxModel.addElements(empresas);
    }
    
    private void getMax() {
    	long max = service.getRowCount();
    	frame.getFormPanel().setNewDeposito(max + 1);
    }
    
    private void findAll(String name) {
    	List<Deposito> depositos;
    	
    	if (name.isEmpty()) {
    		depositos = service.findAll();
		} else {
			depositos = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(depositos);
    }

    private void save() {
        DepositoPanel formPanel = frame.getFormPanel();
        Deposito dep = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(dep);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(dep);
            loadDepositos();
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
    	getMax();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	Deposito dep = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(dep);   
        }
    }

}
