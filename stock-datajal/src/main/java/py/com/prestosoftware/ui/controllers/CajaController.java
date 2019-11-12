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
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.EmpresaService;
import py.com.prestosoftware.domain.validations.CajaValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.CajaFrame;
import py.com.prestosoftware.ui.forms.CajaPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.CajaTableModel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class CajaController extends AbstractFrameController {

    private CajaFrame frame;
    private CajaTableModel tableModel;
    private CajaService service;
    private CajaValidator validator;
    
    private EmpresaService empresaService;
    private EmpresaComboBoxModel empresaComboBoxModel;
   
    @Autowired
    public CajaController(CajaFrame frame, CajaTableModel tableModel, 
    		CajaService service, CajaValidator validator,
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
        loadCajas();
        loadEmpresas();
        showFrame();
        getMax();
    }

    private void loadCajas() {
        List<Caja> cajas = service.findAll();
        tableModel.clear();
        tableModel.addEntities(cajas);
    }
    
    private void loadEmpresas() {
        List<Empresa> empresas = empresaService.findAll();
        empresaComboBoxModel.clear();
        empresaComboBoxModel.addElements(empresas);
    }
    
    private void getMax() {
    	long max = service.getRowCount();
    	frame.getFormPanel().setNewCaja(max + 1);
    }
    
    private void findAll(String name) {
    	List<Caja> cajas;
    	
    	if (name.isEmpty()) {
    		cajas = service.findAll();
		} else {
			cajas = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(cajas);
    }

    private void save() {
        CajaPanel formPanel = frame.getFormPanel();
        Caja caja = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(caja);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(caja);
            loadCajas();
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
        	Caja caja = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(caja);   
        }
    }

}
