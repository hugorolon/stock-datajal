package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.shared.FormBtnPanel;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.text.AbstractDocument;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class TamanhoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
    private JCheckBox chActivo;
    private JTextField tfId;
 
    private FormBtnPanel formBtnPanel;
    private JPanel panel;
    private JLabel lblCodigo;
    private JPanel panel_1;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;

    @Autowired
    public TamanhoPanel(FormBtnPanel formBtnPanel) {
    	this.formBtnPanel = formBtnPanel;
    	this.setSize(400, 200);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        add(formBtnPanel, BorderLayout.SOUTH);
        setLayout(new BorderLayout(0, 0));
        
        panel = new JPanel();
        add(panel);
        
        lblCodigo = new JLabel("Codigo:");
        
        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setColumns(10);
        
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
        chActivo.setSelected(true);
        chActivo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(12)
        					.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(12)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(12)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(5)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(4)
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
    
    public void setFormValue(Tamanho tamanho) {
    	tfId.setText(tamanho.getId() + "");
    	tfNombre.setText(tamanho.getNombre());
    	chActivo.setSelected(tamanho.getActivo() == 1 ? true : false);
    }

    public Tamanho getFormValue() {
    	Tamanho tamanho = new Tamanho();
        
        if (!tfId.getText().isEmpty()) {
        	tamanho.setId(Long.parseLong(tfId.getText()));
		}
        
        tamanho.setNombre(tfNombre.getText());
        tamanho.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return tamanho;
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

	public void setNewTamanho(long id) {
		tfId.setText(String.valueOf(id));
	}
    
}
