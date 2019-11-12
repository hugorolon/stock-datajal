package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.util.Borders;
import py.com.prestosoftware.util.Notifications;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.text.AbstractDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.miginfocom.swing.MigLayout;

@Component
public class CondicionPagoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre, tfId;
    private JCheckBox chActivo;
    private JPanel pnelCrud, pnlBotonera;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    private JLabel lblCodigo;
    private JLabel lblCantDia;
    private JTextField tfCantDia;

    public CondicionPagoPanel() {
    	this.setSize(400, 215);	
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        
        pnelCrud = new JPanel();
        add(pnelCrud);
        
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
        pnelCrud.setLayout(new MigLayout("", "[67px][223px]", "[30px][30px][30px][30px]"));
        
        lblCodigo = new JLabel("Codigo:");
        pnelCrud.add(lblCodigo, "cell 0 0,grow");
        pnelCrud.add(tfId, "cell 1 0,alignx left,growy");
        pnelCrud.add(lblNombre, "cell 0 1,grow");
        pnelCrud.add(tfNombre, "cell 1 1,grow");
        pnelCrud.add(lblActivo, "cell 0 3,grow");
        pnelCrud.add(chActivo, "cell 1 3,alignx left,growy");
        
        lblCantDia = new JLabel("Cant. Dia:");
        pnelCrud.add(lblCantDia, "cell 0 2,grow");
        
        tfCantDia = new JTextField();
        tfCantDia.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (tfCantDia.getText().isEmpty()) {
						Notifications.showAlert("Cantidad de Dia es obligatorio.!");
						tfCantDia.selectAll();
						tfCantDia.requestFocus();
					}
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCantDia.setColumns(10);
        pnelCrud.add(tfCantDia, "cell 1 2,alignx left,growy");
        
        pnlBotonera = new JPanel();
        add(pnlBotonera, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        pnlBotonera.add(btnCerrar);
    }
    
    public void setFormValue(CondicionPago c) {
    	tfId.setText(c.getId() + "");
    	tfNombre.setText(c.getNombre());
    	tfCantDia.setText(String.valueOf(c.getCantDia()));
    	chActivo.setSelected(c.getActivo() == 1 ? true : false);
    }

    public CondicionPago getFormValue() {
    	CondicionPago c = new CondicionPago();
        
        if (!tfId.getText().isEmpty()) {
        	c.setId(Long.parseLong(tfId.getText()));
		}
        
        c.setNombre(tfNombre.getText());
        c.setCantDia(Integer.parseInt(tfCantDia.getText()));
        c.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return c;
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

	public void setNewCat(long id) {
		tfId.setText(String.valueOf(id));
	}
}
