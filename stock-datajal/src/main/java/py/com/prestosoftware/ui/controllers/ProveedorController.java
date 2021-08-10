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
import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.CiudadService;
import py.com.prestosoftware.domain.services.EmpresaService;
import py.com.prestosoftware.domain.services.ProveedorService;
import py.com.prestosoftware.domain.validations.ProveedorValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.ProveedorPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.table.CiudadComboBoxModel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.ui.table.ProveedorTableModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class ProveedorController extends AbstractFrameController {

	private ProveedorPanel proveedorFrame;
	private ProveedorTableModel proveedorTableModel;
	private ProveedorService proveedorService;
	private ProveedorValidator proveedorValidator;

	private CiudadService ciudadService;
	private EmpresaService empresaService;
	private CiudadComboBoxModel ciudadComboBoxModel;
	private EmpresaComboBoxModel empresaComboBoxModel;

	@Autowired
	public ProveedorController(ProveedorPanel proveedorFrame, ProveedorTableModel proveedorTableModel,
			ProveedorService proveedorService, ProveedorValidator proveedorValidator, CiudadService ciudadService,
			EmpresaService empresaService, CiudadComboBoxModel ciudadComboBoxModel,
			EmpresaComboBoxModel empresaComboBoxModel) {
		this.proveedorFrame = proveedorFrame;
		this.proveedorTableModel = proveedorTableModel;
		this.proveedorService = proveedorService;
		this.proveedorValidator = proveedorValidator;
		this.ciudadService = ciudadService;
		this.empresaService = empresaService;
		this.ciudadComboBoxModel = ciudadComboBoxModel;
		this.empresaComboBoxModel = empresaComboBoxModel;
	}

	@PostConstruct
	private void prepareListeners() {
		registerAction(proveedorFrame.getBtnGuardar(), (e) -> saveProveedor());
		registerAction(proveedorFrame.getBtnCancelar(), (e) -> cleanInputs());
		registerAction(proveedorFrame.getBtnCerrar(), (e) -> closeWindow());
		registerAction(proveedorFrame.getBtnNuevo(), (e) -> cleanInputs());
		registerAction(proveedorFrame.getBtnBuscar(), (e) -> findProveedores(proveedorFrame.getTfBuscador().getText()));
		registerKeyEvent(proveedorFrame.getTfBuscador(), new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String name = proveedorFrame.getTfBuscador().getText();
					findProveedores(name);
				}
			}
		});

		registerSelectRow(proveedorFrame.getTbProveedor().getSelectionModel(), new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setData();
			}
		});

		registerButtonEvent(proveedorFrame.getBtnGuardar(), new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					saveProveedor();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		registerButtonEvent(proveedorFrame.getBtnNuevo(), new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addNewProveedor();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	@Override
	public void prepareAndOpenFrame() {
		loadProveedores();
		loadCiudades();
		loadEmpresas();
		showProveedorFrame();
		// addNewProveedor();
	}

	private void loadProveedores() {
		List<Proveedor> proveedores = proveedorService.findAll();
		proveedorTableModel.clear();
		proveedorTableModel.addEntities(proveedores);
	}

	private void loadCiudades() {
		List<Ciudad> ciudades = ciudadService.findAll();
		ciudadComboBoxModel.clear();
		ciudadComboBoxModel.addElements(ciudades);
	}

	private void loadEmpresas() {
		List<Empresa> empresas = empresaService.findAll();
		empresaComboBoxModel.clear();
		empresaComboBoxModel.addElements(empresas);
	}

	public void addNewProveedor() {
		long Id = proveedorService.addNewProveedor();
		proveedorFrame.setNewProveedor(Id + 1);
	}

	private void findProveedores(String name) {
		List<Proveedor> proveedores;

		if (name.isEmpty()) {
			proveedores = proveedorService.findAll();
		} else {
			proveedores = proveedorService.findByNombre(name);
		}

		proveedorTableModel.clear();
		proveedorTableModel.addEntities(proveedores);
	}

	private void showProveedorFrame() {
		proveedorFrame.setVisible(true);
		proveedorFrame.getTfNombre().requestFocus();
	}

	private void saveProveedor() {
		Proveedor proveedor = proveedorFrame.getProveedorFrom();
		Optional<ValidationError> errors = proveedorValidator.validate(proveedor);
		if (errors.isPresent()) {
			ValidationError validationError = errors.get();
			Notifications.showFormValidationAlert(validationError.getMessage());
		} else {
			proveedorService.save(proveedor);
			cleanInputs();
			loadProveedores();
		}
	}

	private void cleanInputs() {
		proveedorFrame.clearForm();
		proveedorFrame.getTfNombre().requestFocus();
		addNewProveedor();
	}

	private void closeWindow() {
		proveedorFrame.dispose();
	}

	private void setData() {
		int[] selectedRow = proveedorFrame.getTbProveedor().getSelectedRows();
		if (selectedRow.length > 0) {
			Long selectedId = (Long) proveedorFrame.getTbProveedor().getValueAt(selectedRow[0], 0);

			Proveedor proveedor = proveedorTableModel.getEntities().stream()
					.filter(customer -> customer.getId().equals(selectedId.longValue())).findAny().orElse(null);
			proveedorFrame.setProveedorForm(proveedor);
//        int selectedRow = proveedorFrame.getTbProveedor().getSelectedRow();

//        if (selectedRow != -1) {
//        	Proveedor proveedor = proveedorTableModel.getEntityByRow(selectedRow);
//        	proveedorFrame.setProveedorForm(proveedor);   
//        }
		}
	}

}
