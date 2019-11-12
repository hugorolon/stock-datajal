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
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.domain.services.GrupoService;
import py.com.prestosoftware.domain.services.ProductoService;
import py.com.prestosoftware.domain.services.TamanhoService;
import py.com.prestosoftware.domain.validations.GrupoValidator;
import py.com.prestosoftware.domain.validations.ValidationError;
import py.com.prestosoftware.ui.forms.GrupoFrame;
import py.com.prestosoftware.ui.forms.GrupoPanel;
import py.com.prestosoftware.ui.shared.AbstractFrameController;
import py.com.prestosoftware.ui.shared.TableSearchPanel;
import py.com.prestosoftware.ui.table.GrupoTableModel;
import py.com.prestosoftware.ui.table.TamanhoComboBoxModel;
import py.com.prestosoftware.util.Notifications;

@Controller
public class GrupoController extends AbstractFrameController {

	private GrupoFrame frame;
	private GrupoTableModel tableModel;
	private GrupoService service;
	private GrupoValidator validator;
	private TamanhoComboBoxModel tCboxModel;
	private TamanhoService tService;
	private ProductoService productoService;

	@Autowired
	public GrupoController(GrupoFrame frame, GrupoTableModel tableModel, GrupoService service, GrupoValidator validator,
			ProductoService productoService, TamanhoComboBoxModel tCboxModel, TamanhoService tService) {
		this.frame = frame;
		this.tableModel = tableModel;
		this.service = service;
		this.validator = validator;
		this.productoService = productoService;
		this.tCboxModel = tCboxModel;
		this.tService = tService;
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
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
		loadGrupos();
		loadTamanhos();
		showFrame();
		addNewGrupo();
	}

	private void loadTamanhos() {
		List<Tamanho> tamanhos = tService.findAll();
		tCboxModel.clear();
		tCboxModel.addElements(tamanhos);
	}

	private void loadGrupos() {
		List<Grupo> grupos = service.findAll();
		tableModel.clear();
		tableModel.addEntities(grupos);
	}

	private void addNewGrupo() {
		long id = service.getRowCount();
		frame.getFormPanel().setNewGrupo(id + 1);
	}

	private void findAll(String name) {
		List<Grupo> grupos;

		if (name.isEmpty())
			grupos = service.findAll();
		else
			grupos = service.findByNombre(name);

		tableModel.clear();
		tableModel.addEntities(grupos);
	}

	private void save() {
		GrupoPanel formPanel = frame.getFormPanel();
		Grupo grupo = formPanel.getFormValue();
		Optional<ValidationError> errors = validator.validate(grupo);
		if (errors.isPresent()) {
			ValidationError validationError = errors.get();
			Notifications.showFormValidationAlert(validationError.getMessage());
		} else {
			service.save(grupo);
			loadGrupos();
			cleanInputs();
			addNewGrupo();

			if (grupo.getPorcIncrementoPrecioA() != null || grupo.getPorcIncrementoPrecioA() > 0)
				updatePrecioVentaProduct(grupo);

			Notifications.showAlert("Grupo guardado correctamente.!");
		}
	}

	private void updatePrecioVentaProduct(Grupo grupo) {
		// consultar todos los productos por subrgrupo y incrementarle el porcentaje
		List<Producto> productos = productoService.getProductoByGrupo(grupo);

		if (productos.size() == 0) {
			return;
		}
		for (Producto e : productos) {
			if (e.getPrecioVentaA() != null)
				e.setPrecioVentaA(
						e.getPrecioVentaA() + ((grupo.getPorcIncrementoPrecioA() / 100) * e.getPrecioVentaA()));

			if (e.getPrecioVentaB() != null)
				e.setPrecioVentaB(
						e.getPrecioVentaB() + ((grupo.getPorcIncrementoPrecioB() / 100) * e.getPrecioVentaB()));

			if (e.getPrecioVentaC() != null)
				e.setPrecioVentaC(
						e.getPrecioVentaC() + ((grupo.getPorcIncrementoPrecioC() / 100) * e.getPrecioVentaC()));

			if (e.getPrecioVentaD() != null)
				e.setPrecioVentaD(
						e.getPrecioVentaD() + ((grupo.getPorcIncrementoPrecioD() / 100) * e.getPrecioVentaD()));

			if (e.getPrecioVentaE() != null)
				e.setPrecioVentaE(
						e.getPrecioVentaE() + ((grupo.getPorcIncrementoPrecioE() / 100) * e.getPrecioVentaE()));

			productoService.save(e);

		}
	}

	private void showFrame() {
		frame.setVisible(true);
		frame.getFormPanel().getTfNombre().requestFocus();
	}

	private void cleanInputs() {
		frame.getFormPanel().clearForm();
		frame.getFormPanel().getTfNombre().requestFocus();
		addNewGrupo();
	}

	private void closeWindow() {
		frame.closeWindow();
	}

	private void setData() {
		int selectedRow = frame.getTablePanel().getTable().getSelectedRow();

		if (selectedRow != -1) {
			Grupo grupo = tableModel.getEntityByRow(selectedRow);
			frame.getFormPanel().setFormValue(grupo);
		}
	}

}
