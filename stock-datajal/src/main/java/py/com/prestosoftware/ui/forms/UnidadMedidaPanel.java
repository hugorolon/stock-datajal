package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.UnidadMedida;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import java.util.ResourceBundle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

@Component
public class UnidadMedidaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
    private JCheckBox chActivo;
    private JTextField tfId;
    private JPanel pnlCrud;
    private JLabel lblCodigo;
    private JPanel pnlBotonera;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;

    public UnidadMedidaPanel() {
    	this.setSize(400, 200);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        
        pnlCrud = new JPanel();
        add(pnlCrud);
        
        lblCodigo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("UnidadMedidaPanel.lblCodigo.text"));
        
        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setColumns(10);
        
        JLabel lblNombre = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("UnidadMedidaPanel.lblNombre.text"));
        
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
        
        JLabel lblActivo = new JLabel(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("UnidadMedidaPanel.lblActivo.text"));
        
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
        GroupLayout gl_pnlCrud = new GroupLayout(pnlCrud);
        gl_pnlCrud.setHorizontalGroup(
        	gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCrud.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
        					.addGap(3)
        					.addComponent(tfId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
        					.addGap(3)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
        					.addGap(3)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))))
        );
        gl_pnlCrud.setVerticalGroup(
        	gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCrud.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(8)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(9)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
        );
        pnlCrud.setLayout(gl_pnlCrud);
        
        pnlBotonera = new JPanel();
        add(pnlBotonera, BorderLayout.SOUTH);
        
        btnGuardar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("UnidadMedidaPanel.btnGuardar.text")); //$NON-NLS-1$ //$NON-NLS-2$
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("UnidadMedidaPanel.btnCancelar.text")); //$NON-NLS-1$ //$NON-NLS-2$
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton(ResourceBundle.getBundle("py.com.prestosoftware.ui.forms.messages").getString("UnidadMedidaPanel.btnCerrar.text")); //$NON-NLS-1$ //$NON-NLS-2$
        pnlBotonera.add(btnCerrar);
    }
    
    public void setFormValue(UnidadMedida umedida) {
    	tfId.setText(umedida.getId() + "");
    	tfNombre.setText(umedida.getNombre());
    	chActivo.setSelected(umedida.getActivo() == 1 ? true : false);
    }

    public UnidadMedida getFormValue() {
    	UnidadMedida umedida = new UnidadMedida();
        
        if (!tfId.getText().isEmpty()) {
        	umedida.setId(Long.parseLong(tfId.getText()));
		}
        
        umedida.setNombre(tfNombre.getText());
        umedida.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return umedida;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
    	chActivo.setSelected(true);
    }

	public void setNewUm(long id) {
		tfId.setText(String.valueOf(id));
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
