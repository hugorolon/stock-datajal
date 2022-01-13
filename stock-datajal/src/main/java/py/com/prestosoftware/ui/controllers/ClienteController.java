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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Cliente;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.data.models.Proveedor;
import py.com.prestosoftware.domain.services.CiudadService;
import py.com.prestosoftware.domain.services.ClienteService;
import py.com.prestosoftware.domain.services.EmpresaService;
import py.com.prestosoftware.domain.services.ListaPrecioService;
import py.com.prestosoftware.domain.validations.ClienteValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.ClientePanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.table.CiudadComboBoxModel;
import py.com.prestosoftware.ui.table.ClientTableModel;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.ui.table.ListaPrecioComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class ClienteController extends AbstractFrameController {

	private ClientePanel clientFrame;
	private ClientTableModel clientTableModel;
	private ClienteService clientService;
	private ClienteValidator clientValidator;

	private CiudadService ciudadService;
	private EmpresaService empresaService;
	private ListaPrecioService listaPrecioService;
	private CiudadComboBoxModel ciudadComboBoxModel;
	private EmpresaComboBoxModel empresaComboBoxModel;
	private ListaPrecioComboBoxModel listaPrecioComboBoxModel;

	@Autowired
	public ClienteController(ClientePanel clientFrame, ClientTableModel clientTableModel, ClienteService clientService,
			ClienteValidator clientValidator, CiudadService ciudadService, EmpresaService empresaService,
			ListaPrecioService listaPrecioService, CiudadComboBoxModel ciudadComboBoxModel,
			EmpresaComboBoxModel empresaComboBoxModel, ListaPrecioComboBoxModel listaPrecioComboBoxModel) {
		this.clientFrame = clientFrame;
		this.clientTableModel = clientTableModel;
		this.clientService = clientService;
		this.clientValidator = clientValidator;
		this.ciudadService = ciudadService;
		this.empresaService = empresaService;
		this.listaPrecioService = listaPrecioService;
		this.ciudadComboBoxModel = ciudadComboBoxModel;
		this.empresaComboBoxModel = empresaComboBoxModel;
		this.listaPrecioComboBoxModel = listaPrecioComboBoxModel;
	}

	@PostConstruct
	private void prepareListeners() {
		registerAction(clientFrame.getBtnGuardar(), (e) -> saveClient());
		registerAction(clientFrame.getBtnCancelar(), (e) -> cleanInputs());
		registerAction(clientFrame.getBtnCerrar(), (e) -> closeWindow());
		registerAction(clientFrame.getBtnNuevo(), (e) -> cleanInputs());

		registerAction(clientFrame.getBtnBuscar(), (e) -> findClients(clientFrame.getTfBuscador().getText()));
		registerKeyEvent(clientFrame.getTfBuscador(), new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String name = clientFrame.getTfBuscador().getText();
					findClients(name);
				}
			}
		});

		registerSelectRow(clientFrame.getTbCliente().getSelectionModel(), new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setData();
			}
		});

		registerButtonEvent(clientFrame.getBtnGuardar(), new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					saveClient();
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

	public ClientTableModel getClientTableModel() {
		return clientTableModel;
	}

	@Override
	public void prepareAndOpenFrame() {
		loadClients();
		loadCiudades();
		loadEmpresas();
		loadListaPrecios();
		showClientsFrame();
	}

	public void addNewCliente() {
		long id = clientService.addNewClient();
		clientFrame.setNewClient(id + 1);
	}

	private void loadClients() {
		List<Cliente> clientes = clientService.findAll();
		clientTableModel.clear();
		clientTableModel.addEntities(clientes);
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

	private void loadListaPrecios() {
		List<ListaPrecio> listaPrecios = listaPrecioService.findAll();
		listaPrecioComboBoxModel.clear();
		listaPrecioComboBoxModel.addElements(listaPrecios);
	}

	private void findClients(String name) {
		List<Cliente> clientes;

		if (name.isEmpty()) {
			clientes = clientService.findAll();
		} else {
			clientes = clientService.findByNombre(name);
		}

		clientTableModel.clear();
		clientTableModel.addEntities(clientes);
	}

	private void showClientsFrame() {
		clientFrame.setVisible(true);
		clientFrame.getTfNombre().requestFocus();
	}

	private void saveClient() {
		Cliente client = clientFrame.getClientFromForm();
		Optional<ValidationError> errors = clientValidator.validate(client);
		if (errors.isPresent()) {
			ValidationError validationError = errors.get();
			Notifications.showFormValidationAlert(validationError.getMessage());
		} else {
			try {
				clientService.save(client);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int i= 10 /0 ;
			System.out.println("div cero");
			cleanInputs();
			loadClients();
		}
	}

	private void cleanInputs() {
		clientFrame.clearForm();
		clientFrame.getTfNombre().requestFocus();
		addNewCliente();
	}

	private void closeWindow() {
		clientFrame.dispose();
	}

	private void setData() {
		int[] selectedRow = clientFrame.getTbCliente().getSelectedRows();
		if (selectedRow.length > 0) {
			Long selectedId = (Long) clientFrame.getTbCliente().getValueAt(selectedRow[0], 0);

			Cliente cliente = clientTableModel.getEntities().stream()
					.filter(customer -> customer.getId().equals(selectedId.longValue())).findAny().orElse(null);

			clientFrame.setClientForm(cliente);
		}
	}

}
