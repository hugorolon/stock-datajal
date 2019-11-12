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
import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.domain.services.TamanhoService;
import py.com.prestosoftware.domain.validations.TamanhoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.TamanhoFrame;
import py.com.prestosoftware.ui.forms.TamanhoPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.TamanhoTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class TamanhoController extends AbstractFrameController {

    private TamanhoFrame frame;
    private TamanhoTableModel tableModel;
    private TamanhoService service;
    private TamanhoValidator validator;
   
    @Autowired
    public TamanhoController(TamanhoFrame frame, TamanhoTableModel tableModel, 
    		TamanhoService service, TamanhoValidator validator) {
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
        loadTamanhos();
        showFrame();
        getMax();
    }

    private void loadTamanhos() {
        List<Tamanho> tamanhos = service.findAll();
        tableModel.clear();
        tableModel.addEntities(tamanhos);
    }
    
    private void getMax() {
    	long max = service.getRowCount();
    	frame.getFormPanel().setNewTamanho(max + 1);
    }
    
    private void findAll(String name) {
    	List<Tamanho> tamanhos;
    	
    	if (name.isEmpty()) {
    		tamanhos = service.findAll();
		} else {
			tamanhos = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(tamanhos);
    }

    private void save() {
        TamanhoPanel formPanel = frame.getFormPanel();
        Tamanho tamanho = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(tamanho);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(tamanho);
            loadTamanhos();
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
        	Tamanho tamanho = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(tamanho);   
        }
    }

}