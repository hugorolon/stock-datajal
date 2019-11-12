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
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Rol;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.domain.services.EmpresaService;
import py.com.prestosoftware.domain.services.RolService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.domain.validations.UsuarioValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.UsuarioFrame;
import py.com.prestosoftware.ui.forms.UsuarioPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.ui.table.RolComboBoxModel;
import py.com.prestosoftware.ui.table.UsuarioTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class UsuarioController extends AbstractFrameController {

    private UsuarioFrame frame;
    private UsuarioTableModel tableModel;
    private UsuarioService service;
    private UsuarioValidator validator;
    
    private RolService rolService;
    private EmpresaService empresaService;
    private RolComboBoxModel rolComboBoxModel;
    private EmpresaComboBoxModel empresaComboBoxModel;
   
    @Autowired
    public UsuarioController(UsuarioFrame frame, UsuarioTableModel tableModel, UsuarioService service, 
    		UsuarioValidator validator, RolService rolService, EmpresaService empresaService,
    		RolComboBoxModel rolComboBoxModel, EmpresaComboBoxModel empresaComboBoxModel) {
        this.frame = frame;
        this.tableModel = tableModel;
        this.service = service;
        this.validator = validator;
        this.rolService = rolService;
        this.empresaService = empresaService;
        this.rolComboBoxModel = rolComboBoxModel;
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
        loadUsuarios();
        loadRoles();
        loadEmpresas();
        showFrame();
        getMax();
    }

    private void loadUsuarios() {
        List<Usuario> usuarios = service.findAll();
        tableModel.clear();
        tableModel.addEntities(usuarios);
    }
    
    private void loadEmpresas() {
        List<Empresa> empresas = empresaService.findAll();
        empresaComboBoxModel.clear();
        empresaComboBoxModel.addElements(empresas);
    }
    
    private void loadRoles() {
        List<Rol> roles = rolService.findAll();
        rolComboBoxModel.clear();
        rolComboBoxModel.addElements(roles);
    }
    
    private void getMax() {
    	long max = service.getRowCount();
    	frame.getFormPanel().setNewUsuario(max + 1);
    }
    
    private void findAll(String name) {
    	List<Usuario> usuarios;
    	
    	if (name.isEmpty()) {
    		usuarios = service.findAll();
		} else {
			usuarios = service.findByUsuario(name);		
		}
    	
        tableModel.clear();
        tableModel.addEntities(usuarios);
    }

    private void save() {
        UsuarioPanel formPanel = frame.getFormPanel();
        Usuario usuario = formPanel.getFormValue();
        Optional<ValidationError> errors = validator.validate(usuario);
        if (errors.isPresent()) {
            ValidationError validationError = errors.get();
            Notifications.showFormValidationAlert(validationError.getMessage());
        } else {
            service.save(usuario);
           loadUsuarios();
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
        	Usuario usuario = tableModel.getEntityByRow(selectedRow);
        	frame.getFormPanel().setFormValue(usuario);   
        }
    }

}
