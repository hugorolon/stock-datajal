package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.PlanCuenta;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.text.AbstractDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import net.miginfocom.swing.MigLayout;

@Component
public class PlanCuentaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
    private JCheckBox chActivo;
    private JPanel pnlCrud, pnlBotonera;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    private JLabel lblCuenta;
    private JTextField tfCuenta;
    private JLabel lblTipo;
    private JComboBox<String> cbTipo;
    private JLabel lblRecibo;
    private JComboBox<String> cbRecibo;

    public PlanCuentaPanel() {
    	this.setSize(449, 220);
        
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
        
        JLabel lblNombre = new JLabel("Nombre:");
        
        tfNombre = new JTextField();
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfNombre.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			//tfCuenta.setText(tfId.getText());
					chActivo.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
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
        
        lblCuenta = new JLabel("Operacion:");
        
        tfCuenta = new JTextField();
        tfCuenta.setEditable(false);
        tfCuenta.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbTipo.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        
        lblTipo = new JLabel("Tipo:");
        
        cbTipo = new JComboBox<String>();
        cbTipo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
        	}
        });
        cbTipo.setModel(new DefaultComboBoxModel<String>(new String[] {"E", "S"}));
        pnlCrud.setLayout(new MigLayout("", "[66px][59px][47px][42px][15px][104px]", "[30px][32px][32px][32px]"));
        pnlCrud.add(lblActivo, "cell 3 3,alignx left,growy");
        pnlCrud.add(chActivo, "cell 5 3,alignx left,growy");
        pnlCrud.add(lblNombre, "cell 0 1,grow");
        pnlCrud.add(tfNombre, "cell 1 1 5 1,grow");
        pnlCrud.add(lblCuenta, "cell 0 0,alignx left,growy");
        pnlCrud.add(tfCuenta, "cell 1 0,grow");
        pnlCrud.add(lblTipo, "cell 0 2,grow");
        pnlCrud.add(cbTipo, "cell 1 2,grow");
        
        lblRecibo = new JLabel("Recibo:");
        pnlCrud.add(lblRecibo, "cell 0 3,grow");
        
        cbRecibo = new JComboBox<String>();
        cbRecibo.setModel(new DefaultComboBoxModel<String>(new String[] {"SI", "NO"}));
        pnlCrud.add(cbRecibo, "cell 1 3,grow");
        
        pnlBotonera = new JPanel();
        add(pnlBotonera, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        pnlBotonera.add(btnCerrar);
    }
    
    public void setFormValue(PlanCuenta cuenta) {
    	//tfId.setText(cuenta.getId() + "");
    	tfNombre.setText(cuenta.getNombre());
    	tfCuenta.setText(cuenta.getCuenta());
    	cbTipo.setSelectedItem(cuenta.getTipo());
    	cbRecibo.setSelectedItem(cuenta.getRecibo());
    	chActivo.setSelected(cuenta.getActivo() == 1 ? true : false);
    }

    public PlanCuenta getFormValue() {
    	PlanCuenta cuenta = new PlanCuenta();
        
//        if (!tfId.getText().isEmpty()) {
//        	cuenta.setId(Long.parseLong(tfId.getText()));
//		}
        
        cuenta.setNombre(tfNombre.getText());
        cuenta.setCuenta(tfCuenta.getText());
        cuenta.setTipo(String.valueOf(cbTipo.getSelectedItem()));
        cuenta.setRecibo((String)cbRecibo.getSelectedItem());
        cuenta.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return cuenta;
    }

    public void clearForm() {
    	//tfId.setText("");
        tfNombre.setText("");
        tfCuenta.setText("");
        cbRecibo.setSelectedIndex(0);
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

	public void setNewPlanCuenta(long id) {
		tfCuenta.setText(String.valueOf(id));
	}
}
