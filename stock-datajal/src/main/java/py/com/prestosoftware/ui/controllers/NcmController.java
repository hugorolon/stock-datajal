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
import py.com.prestosoftware.data.models.Ncm;
import py.com.prestosoftware.domain.services.NcmService;
import py.com.prestosoftware.domain.validations.NcmValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.NcmFrame;
import py.com.prestosoftware.ui.forms.NcmPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.NcmTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class NcmController extends AbstractFrameController {

    private NcmFrame frame;
    private NcmTableModel tableModel;
    private NcmService service;
    private NcmValidator validator;
   
    @Autowired
    public NcmController(NcmFrame frame, NcmTableModel tableModel, 
    					 NcmService service, NcmValidator validator) {
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
    
    public NcmTableModel getTableModel() {
		return tableModel;
	}

    @Override
    public void prepareAndOpenFrame() {
        loadNcms();
        showFrame();
        getRowCount();
    }

    private void loadNcms() {
        List<Ncm> ncms = service.findAll();
        tableModel.clear();
        tableModel.addEntities(ncms);
    }
    
    private void getRowCount() {
    	long id = service.getRoxMax();
    	frame.getFormPanel().setNewId(id + 1);
    }
    
    private void findAll(String name) {
    	List<Ncm> ncms;
    	
    	if (name.isEmpty()) {
    		ncms = service.findAll();
		} else {
			ncms = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(ncms);
    }

    private void save() {
        NcmPanel formPanel = frame.getFormPanel();
        Ncm ncm = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(ncm);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(ncm);
            loadNcms();
            cleanInputs();
            getRowCount();
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
        	Ncm ncm = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(ncm);   
        }
    }

}