package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Ciudad;
import py.com.prestosoftware.data.models.Departamento;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.search.CiudadInterfaz;
import py.com.prestosoftware.ui.table.DepartamentoComboBoxModel;
import py.com.prestosoftware.util.Borders;

@Component
public class CiudadPanel extends JPanel implements CiudadInterfaz{

	private static final long serialVersionUID = 1L;
	
	private JLabel lblCodigo, lblDepartamento;
	private JTextField tfCiudadId, tfNombre;
    private JCheckBox chActivo;
    private JPanel panel, panel_1;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    private JComboBox<Departamento> cbDep;
    private CiudadInterfaz interfaz;
    
    private DepartamentoComboBoxModel depComboBoxModel;

    @Autowired
    public CiudadPanel(DepartamentoComboBoxModel depComboBox) {
    	this.depComboBoxModel = depComboBox;
    	this.setSize(400, 250);
        
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
					cbDep.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
        chActivo = new JCheckBox();
        chActivo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        chActivo.setSelected(true);
        
        JLabel lblActivo = new JLabel("Activo:");
        
        tfCiudadId = new JTextField();
        tfCiudadId.setEditable(false);
        
        lblDepartamento = new JLabel("Departamento:");
        
        cbDep = new JComboBox<Departamento>(depComboBoxModel);
        cbDep.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
        	}
        });
        
        lblCodigo = new JLabel("Codigo:");
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfCiudadId, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblDepartamento, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(cbDep, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(9)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfCiudadId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblDepartamento, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(cbDep, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
        );
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
    
    public void setFormValue(Ciudad ciudad) {
    	tfCiudadId.setText(ciudad.getId() + "");
    	tfNombre.setText(ciudad.getNombre());
    	chActivo.setSelected(ciudad.getActivo() == 1 ? true : false);
    	depComboBoxModel.setSelectedItem(ciudad.getDepartamento());
    }

    public Ciudad getFormValue() {
    	Ciudad ciudad = new Ciudad();
        
        if (!tfCiudadId.getText().isEmpty()) {
        	ciudad.setId(Long.parseLong(tfCiudadId.getText()));
		}
        
        ciudad.setNombre(tfNombre.getText());
        ciudad.setDepartamento(depComboBoxModel.getSelectedItem());
        ciudad.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return ciudad;
    }

    public void clearForm() {
    	tfCiudadId.setText("");
        tfNombre.setText("");
        cbDep.setSelectedIndex(0);
    	chActivo.setSelected(true);
    }
    
    
    
    public CiudadInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(CiudadInterfaz interfaz) {
		this.interfaz = interfaz;
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

	public void setNewCotizacion(long ciudadId) {
		tfCiudadId.setText(String.valueOf(ciudadId));
	}
	
	public JTextField getTfNombre() {
		return tfNombre;
	}

	@Override
	public void getEntity(Ciudad ciudad) {
		// TODO Auto-generated method stub
		
	}
	
}