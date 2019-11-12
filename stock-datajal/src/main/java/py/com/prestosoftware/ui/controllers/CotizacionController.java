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
import py.com.prestosoftware.data.models.Cotizacion;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.domain.services.CotizacionService;
import py.com.prestosoftware.domain.services.MonedaService;
import py.com.prestosoftware.domain.validations.CotizacionValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.CotizacionFrame;
import py.com.prestosoftware.ui.forms.CotizacionPanel;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.CotizacionTableModel;
import py.com.prestosoftware.ui.table.MonedaComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class CotizacionController extends AbstractFrameController {

    private CotizacionFrame frame;
    private CotizacionTableModel tableModel;
    private CotizacionValidator validator;
    private CotizacionService service;
    private MonedaComboBoxModel monedaComboBoxModel;
    private MonedaService monedaService;
   
    @Autowired
    public CotizacionController(CotizacionFrame frame, CotizacionTableModel tableModel, 
    		CotizacionService service, CotizacionValidator validator,
    		MonedaService monedaService, MonedaComboBoxModel monedaComboBoxModel) {
        this.frame = frame;
        this.tableModel = tableModel;
        this.service = service;
        this.validator = validator;
        this.monedaService = monedaService;
        this.monedaComboBoxModel = monedaComboBoxModel;
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
        
        registerKeyEvent(frame.getFormPanel().getTfId(), new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validateNumero(e);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {	
					findCotizacionById(Long.valueOf(frame.getFormPanel().getTfId().getText()));
				}
				
			}
		});
    }

    @Override
    public void prepareAndOpenFrame() {
        loadCotizaciones();
        loadMonedas();
        showFrame();
        addNewCotizacion();
    }

    private void loadCotizaciones() {
    	List<Cotizacion> cotizaciones = service.findAll();
        tableModel.clear();
        tableModel.addEntities(cotizaciones);
    }
    
    private void loadMonedas() {
    	List<Moneda> monedas = monedaService.findAll();
    	monedaComboBoxModel.clear();
    	monedaComboBoxModel.addElements(monedas);
    }

    private void addNewCotizacion() {
    	long max = service.getRowCount();
    	frame.getFormPanel().setNewCotizacion(max + 1);
    }
    
    private void findCotizacionById(Long id) {
    	Optional<Cotizacion> cotizacion = service.findById(id);
    	
    	if (cotizacion.isPresent()) {
    		Cotizacion c = cotizacion.get();
			frame.getFormPanel().setData(c);
		} else {
			frame.getFormPanel().getTfFecha().requestFocus();
		}
    }
   
    private void findAll(String name) {
    	List<Cotizacion> cotizaciones = null;
    	
    	if (name.isEmpty()) {
    		cotizaciones = service.findAll();
		}
    	
        tableModel.clear();
        tableModel.addEntities(cotizaciones);
    }

    private void showFrame() {
        frame.setVisible(true);
        frame.getFormPanel().getTfId().requestFocus();
    }

    private void save() {
        CotizacionPanel formPanel = frame.getFormPanel();
        Cotizacion cotizacion = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(cotizacion);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(cotizacion);
            tableModel.fireTableDataChanged();
            cleanInputs();
            loadCotizaciones();
        }
    }

    private void cleanInputs() {
    	frame.getFormPanel().clearForm();
    	frame.getFormPanel().getTfId().requestFocus();
    	addNewCotizacion();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	Cotizacion cot = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(cot);   
        }
    }

}
