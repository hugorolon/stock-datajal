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

import py.com.prestosoftware.data.models.Rol;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.models.UsuarioRol;
import py.com.prestosoftware.domain.services.RolService;
import py.com.prestosoftware.domain.services.UsuarioRolService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.domain.validations.UsuarioRolValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.UsuarioRolFrame;
import py.com.prestosoftware.ui.forms.UsuarioRolPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.RolComboBoxModel;
import py.com.prestosoftware.ui.table.UsuarioComboBoxModel;
import py.com.prestosoftware.ui.table.UsuarioRolTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class UsuarioRolController extends AbstractFrameController {

    private UsuarioRolFrame frame;
    private UsuarioRolTableModel tableModel;
    private UsuarioRolService service;
    private UsuarioRolValidator validator;
    
    private RolService rolService;
    private UsuarioService usuarioService;
    private RolComboBoxModel rolComboBoxModel;
    private UsuarioComboBoxModel usuComboBoxModel;
   
    @Autowired
    public UsuarioRolController(UsuarioRolFrame frame, UsuarioRolTableModel tableModel, UsuarioRolService service, 
    		UsuarioRolValidator validator, RolService rolService,  UsuarioService usuarioService, RolComboBoxModel rolComboBoxModel, UsuarioComboBoxModel usuComboBoxModel) {
        this.frame = frame;
        this.tableModel = tableModel;
        this.service = service;
        this.validator = validator;
        this.rolService = rolService;
        this.usuarioService = usuarioService;
        this.rolComboBoxModel = rolComboBoxModel;
        this.usuComboBoxModel= usuComboBoxModel;
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
        loadUsuarioRoles();
        loadRoles();
        loadUsuarios();
        showFrame();
        getMax();
    }

    private void loadUsuarioRoles() {
        List<UsuarioRol> usuarioRoles = service.findAll();
        tableModel.clear();
        tableModel.addEntities(usuarioRoles);
    }
    
    private void loadRoles() {
        List<Rol> roles = rolService.findAll();
        rolComboBoxModel.clear();
        rolComboBoxModel.addElements(roles);
    }
    
    private void loadUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        usuComboBoxModel.clear();
       // usuComboBoxModel.addElements(usuarios);
    }
    private void getMax() {
    	long max = service.getRowCount();
    	frame.getFormPanel().setNewUsuarioRol(max + 1);
    }
    
    private void findAll(String name) {
    	List<UsuarioRol> usuarioRoles;
    	
    	if (name.isEmpty()) {
    		usuarioRoles = service.findAll();
		} else {
			usuarioRoles = service.findByUsuarioRol(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(usuarioRoles);
    }

    private void save() {
        UsuarioRolPanel formPanel = frame.getFormPanel();
        UsuarioRol usuarioRol = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(usuarioRol);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(usuarioRol);
           loadUsuarioRoles();
           cleanInputs();
        }
    }
    
    private void showFrame() {
        frame.setVisible(true);
        //frame.getFormPanel().getCbUsu().requestFocus();
    }

    private void cleanInputs() {
    	frame.getFormPanel().clearForm();
    	//frame.getFormPanel().getCbUsu().requestFocus();
    	getMax();
    }
    
    private void closeWindow() {
    	frame.closeWindow();
    }
    
    private void setData() {
        int selectedRow = frame.getTablePanel().getTable().getSelectedRow();
        
        if (selectedRow != -1) {
        	UsuarioRol usuarioRol = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(usuarioRol);   
        }
    }

}
