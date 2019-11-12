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
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.domain.services.ListaPrecioService;
import py.com.prestosoftware.domain.validations.ListaPrecioValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.ListaPorcentajeFrame;
import py.com.prestosoftware.ui.forms.ListaPrecioPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.ListaPrecioTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class ListaPrecioController extends AbstractFrameController {

    private ListaPorcentajeFrame frame;
    private ListaPrecioTableModel tableModel;
    private ListaPrecioService service;
    private ListaPrecioValidator validator;
   
    @Autowired
    public ListaPrecioController(ListaPorcentajeFrame frame, ListaPrecioTableModel tableModel, 
    							 ListaPrecioService service, ListaPrecioValidator validator) {
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
        loadListaPrecios();
        showFrame();
        getMax();
    }

    private void loadListaPrecios() {
        List<ListaPrecio> lista = service.findAll();
        tableModel.clear();
        tableModel.addEntities(lista);
    }
    
    private void getMax() {
    	long max = service.getRowMax();
    	frame.getFormPanel().setNewListaPrecio(max + 1);
    }
    
    private void findAll(String name) {
    	List<ListaPrecio> lista;
    	
    	if (name.isEmpty()) {
    		lista = service.findAll();
		} else {
			lista = service.findByNombre(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(lista);
    }

    private void save() {
        ListaPrecioPanel formPanel = frame.getFormPanel();
        ListaPrecio listaPrecio = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(listaPrecio);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(listaPrecio);
            loadListaPrecios();
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
        	ListaPrecio listaPrecio = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(listaPrecio);   
        }
    }

}
