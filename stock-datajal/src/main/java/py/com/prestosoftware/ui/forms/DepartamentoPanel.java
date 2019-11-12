package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.data.models.Pais;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.table.PaisComboBoxModel;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.text.AbstractDocument;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class DepartamentoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField tfNombre, tfDepartamentoId;
	private JCheckBox chActivo;
	private JLabel lblPais;
	private JPanel panel, panel_1;
	private JButton btnGuardar, btnCancelar, btnCerrar;

	private PaisComboBoxModel paisComboBoxModel;
	private JComboBox<Pais> cbPaises;
	private JLabel lblCodigo;

	@Autowired
	public DepartamentoPanel(PaisComboBoxModel paisComboBoxModel) {
		this.paisComboBoxModel = paisComboBoxModel;
		this.setSize(400, 245);

		setPanelUp();
		initComponents();
	}

	private void setPanelUp() {
		setBorder(Borders.createEmptyBorder());
	}

	private void initComponents() {
		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		add(panel);

		JLabel lblNombre = new JLabel("Nombre:");

		tfNombre = new JTextField();
		((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		tfNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbPaises.requestFocus();
				}
			}
		});
		tfNombre.setColumns(10);

		lblPais = new JLabel("Pais:");

		cbPaises = new JComboBox<Pais>(paisComboBoxModel);
		cbPaises.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
			}
		});

		JLabel lblActivo = new JLabel("Activo:");

		chActivo = new JCheckBox();
		chActivo.setSelected(true);
		chActivo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
			}
		});

		tfDepartamentoId = new JTextField();
		tfDepartamentoId.setEditable(false);
		tfDepartamentoId.setColumns(10);

		lblCodigo = new JLabel("Codigo:");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(6)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE).addGap(16)
						.addComponent(tfDepartamentoId, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addGap(16)
								.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblPais, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addGap(16)
								.addComponent(cbPaises, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addGap(16).addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 127,
										GroupLayout.PREFERRED_SIZE)))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(4)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfDepartamentoId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(12)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(12)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPais, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbPaises, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(12)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))));
		panel.setLayout(gl_panel);

		panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);

		btnGuardar = new JButton("Guardar");
		panel_1.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		panel_1.add(btnCancelar);

		btnCerrar = new JButton("Cerrar");
		panel_1.add(btnCerrar);
	}

	public void setFormValue(Departamento dep) {
		tfDepartamentoId.setText(dep.getId() + "");
		tfNombre.setText(dep.getNombre());
		chActivo.setSelected(dep.getActivo() == 1 ? true : false);
		paisComboBoxModel.setSelectedItem(dep.getPais());
	}

	public Departamento getFormValue() {
		Departamento dep = new Departamento();

		if (!tfDepartamentoId.getText().isEmpty()) {
			dep.setId(Long.parseLong(tfDepartamentoId.getText()));
		}

		dep.setNombre(tfNombre.getText());
		dep.setPais(paisComboBoxModel.getSelectedItem());
		dep.setActivo(chActivo.isSelected() ? 1 : 0);

		return dep;
	}

	public void clearForm() {
		tfDepartamentoId.setText("");
		tfNombre.setText("");
		cbPaises.setSelectedIndex(0);
		chActivo.setSelected(true);
	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public JButton getBtnCerrar() {
		return btnCerrar;
	}

	public JTextField getTfNombre() {
		return tfNombre;
	}

	public void setNewDep(long depId) {
		tfDepartamentoId.setText(String.valueOf(depId));
	}

}
