package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Ncm;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@Component
public class NcmPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblDescripcion;
	private JTextField tfNombre, tfDescripcion, tfId;
    private JCheckBox chActivo;
    private JPanel panel, panel_1;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    private JLabel lblCodigo;

    public NcmPanel() {
    	this.setSize(400, 233);
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
					tfDescripcion.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
        tfDescripcion = new JTextField();
        tfDescripcion.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
        	}
        });
        ((AbstractDocument) tfDescripcion.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfDescripcion.setColumns(10);
        
        lblDescripcion = new JLabel("Descripción:");
        
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
        
        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setColumns(10);
        
        lblCodigo = new JLabel("Codigo:");
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblDescripcion, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
        			.addGap(1)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfDescripcion, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(4)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(4)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblDescripcion, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfDescripcion, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(3)
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
    
    public void setFormValue(Ncm ncm) {
    	tfId.setText(ncm.getId() + "");
    	tfNombre.setText(ncm.getNombre());
    	tfDescripcion.setText(ncm.getDescripcion());
    	chActivo.setSelected(ncm.getActivo() == 1 ? true : false);
    }

    public Ncm getFormValue() {
    	Ncm ncm = new Ncm();
        
        if (!tfId.getText().isEmpty()) {
        	ncm.setId(Long.parseLong(tfId.getText()));
		}
        
        ncm.setNombre(tfNombre.getText());
        ncm.setDescripcion(tfDescripcion.getText());
        ncm.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return ncm;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
        tfDescripcion.setText("");
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

	public void setNewId(long id) {
		tfId.setText(String.valueOf(id));
	}
    
}