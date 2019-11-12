package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Pais;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.miginfocom.swing.MigLayout;

@Component
public class PaisPanel extends JPanel {

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

    public PaisPanel() {
    	this.setSize(600, 100);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        setLayout(new MigLayout("", "[488px]", "[81px]"));
        
        panel = new JPanel();
        add(panel, "cell 0 0,growx,aligny top");
        
        JLabel lblNombre = new JLabel("Nombre:");
        
        tfNombre = new JTextField();
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfNombre.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				} 
//        		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
//					dispose();
//				}
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
        panel.setLayout(new MigLayout("", "[37px][10px][63px][10px][41px][2px][172px][5px][37px][10px][21px]", "[30px][33px]"));
        
        lblCodigo = new JLabel("Codigo:");
        panel.add(lblCodigo, "cell 0 0,alignx left,growy");
        panel.add(tfCodigo, "cell 2 0,grow");
        panel.add(lblNombre, "cell 4 0,alignx left,growy");
        panel.add(tfNombre, "cell 6 0,grow");
        panel.add(lblActivo, "cell 8 0,grow");
        panel.add(chActivo, "cell 10 0,alignx left,growy");
        
        panel_1 = new JPanel();
        panel.add(panel_1, "cell 0 1 11 1,growx,aligny top");
        
        btnGuardar = new JButton("Guardar");
        panel_1.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        panel_1.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        panel_1.add(btnCerrar); 
    }
    
    public void setNewPais(long paisId) {
    	tfCodigo.setText(String.valueOf(paisId));
    }
    
    public void setFormValue(Pais pais) {
    	tfCodigo.setText(pais.getId() + "");
    	tfNombre.setText(pais.getNombre());
    	chActivo.setSelected(pais.getActivo() == 1 ? true : false);
    }

    public Pais getFormValue() {
    	Pais pais = new Pais();
        
        if (!tfCodigo.getText().isEmpty()) {
        	pais.setId(Long.parseLong(tfCodigo.getText()));
		}
        
        pais.setNombre(tfNombre.getText());
        pais.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return pais;
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
