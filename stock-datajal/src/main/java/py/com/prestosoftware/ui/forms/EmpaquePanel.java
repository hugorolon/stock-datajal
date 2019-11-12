package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Empaque;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class EmpaquePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
    private JCheckBox chActivo;
    private JTextField tfCodigo;
 
    private JPanel panel;
    private JPanel panel_1;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    private JLabel lblCodigo;

    public EmpaquePanel() {
    	this.setSize(400, 220);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        
        panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        
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
        
        JLabel lblActivo = new JLabel("Activo:");
        
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
        
        tfCodigo = new JTextField();
        tfCodigo.setEditable(false);
        tfCodigo.setColumns(10);
        
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
        					.addComponent(tfCodigo, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(12)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(12)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(16)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
        );
        panel.setLayout(gl_panel);
        
        panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	}
        });
        panel_1.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        panel_1.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        panel_1.add(btnCerrar); 
    }
    
    public void setNewPais(long paisId) {
    	tfCodigo.setText(String.valueOf(paisId));
    }
    
    public void setFormValue(Empaque emp) {
    	tfCodigo.setText(emp.getId() + "");
    	tfNombre.setText(emp.getNombre());
    	chActivo.setSelected(emp.getActivo() == 1 ? true : false);
    }

    public Empaque getFormValue() {
    	Empaque emp = new Empaque();
        
        if (!tfCodigo.getText().isEmpty()) {
        	emp.setId(Long.parseLong(tfCodigo.getText()));
		}
        
        emp.setNombre(tfNombre.getText());
        emp.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return emp;
    }

    public void clearForm() {
    	tfCodigo.setText("");
        tfNombre.setText("");
    	chActivo.setSelected(true);
    	tfNombre.requestFocus();
    }
    
    public JTextField getTfNombre() {
		return tfNombre;
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
}
