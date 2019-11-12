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
import py.com.prestosoftware.data.models.Color;
import py.com.prestosoftware.domain.services.ColorService;
import py.com.prestosoftware.domain.validations.ColorValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.ColorFrame;
import py.com.prestosoftware.ui.forms.ColorPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.ColorTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class ColorController extends AbstractFrameController {

    private ColorFrame frame;
    private ColorTableModel tableModel;
    private ColorService service;
    private ColorValidator validator;
   
    @Autowired
    public ColorController(ColorFrame frame, ColorTableModel tableModel, 
    		ColorService service, ColorValidator validator) {
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
    	loadColores();
        showFrame();
        addNewColor();
    }

    private void loadColores() {
        List<Color> colores = service.findAll();
        tableModel.clear();
        tableModel.addEntities(colores);
    }
    
    private void addNewColor() {
    	long max = service.getRowCount();
    	frame.getFormPanel().setNewColor(max + 1);
    }
    
    private void findAll(String name) {
    	List<Color> colores;
    	
    	if (name.isEmpty()) {
    		colores = service.findAll();
		} else {
			colores = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(colores);
    }

    private void save() {
        ColorPanel formPanel = frame.getFormPanel();
        Color color = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(color);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(color);
            loadColores();
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
    	addNewColor();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	Color color = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(color);   
        }
    }

}
