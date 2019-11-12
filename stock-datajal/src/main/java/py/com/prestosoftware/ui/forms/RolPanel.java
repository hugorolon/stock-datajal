package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Categoria;
import py.com.prestosoftware.data.models.Rol;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.text.AbstractDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@Component
public class RolPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre, tfId;
    private JCheckBox chActivo;
    private JPanel panel, panel_1;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    private JLabel lblCodigo;

    public RolPanel() {
    	this.setSize(400, 215);
        
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
					chActivo.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setColumns(10);
        
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
        
        JLabel lblActivo = new JLabel("Activo:");
        
        lblCodigo = new JLabel("Codigo:");
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(12)
        					.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(12)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(12)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(9)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
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
    
    public void setFormValue(Rol rol) {
    	tfId.setText(rol.getId() + "");
    	tfNombre.setText(rol.getNombre());
    	chActivo.setSelected(rol.getActivo() == 1 ? true : false);
    }

    public Rol getFormValue() {
    	Rol rol = new Rol();
        
        if (!tfId.getText().isEmpty()) {
        	rol.setId(Long.parseLong(tfId.getText()));
		}
        
        rol.setNombre(tfNombre.getText());
        rol.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return rol;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
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

	public void setNewRol(long id) {
		tfId.setText(String.valueOf(id));
	}
    
}
