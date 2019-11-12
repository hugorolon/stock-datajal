package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.util.Borders;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@Component
public class MonedaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
    private JCheckBox chEsBase;
    private JTextField tfId;
    private JPanel panel;
    private JLabel lblCodigo;
    private JPanel panel_1;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    private JLabel lblActivo_1;
    private JCheckBox chActivo;
    private JLabel lblSigla;
    private JTextField tfSigla;
    private JTextField tfMascara;
    private JLabel lblMascara;
    private JLabel lblOperacion;
    private JComboBox<String> tfOperacion;

    public MonedaPanel() {
    	this.setSize(450, 270);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        
        panel = new JPanel();
        
        lblCodigo = new JLabel("CODIGO:");
        lblCodigo.setBounds(6, 3, 67, 30);
        
        tfId = new JTextField();
        tfId.setBounds(85, 3, 99, 30);
        tfId.setEditable(false);
        tfId.setColumns(10);
        
        JLabel lblNombre = new JLabel("NOMBRE:");
        lblNombre.setBounds(6, 36, 67, 30);
        
        tfNombre = new JTextField();
        tfNombre.setBounds(85, 36, 165, 30);
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfNombre.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfSigla.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
        JLabel lblActivo = new JLabel("ES BASE:");
        lblActivo.setBounds(6, 168, 67, 30);
        
        chEsBase = new JCheckBox();
        chEsBase.setBounds(85, 168, 67, 30);
        chEsBase.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
        	}
        });
        
        lblActivo_1 = new JLabel("ACTIVO");
        lblActivo_1.setBounds(162, 167, 67, 30);
        
        chActivo = new JCheckBox();
        chActivo.setBounds(241, 167, 67, 30);
        chActivo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        chActivo.setSelected(true);
        
        lblSigla = new JLabel("SIGLA:");
        lblSigla.setBounds(6, 69, 67, 30);
        
        tfSigla = new JTextField();
        tfSigla.setBounds(85, 69, 67, 30);
        tfSigla.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		if (tfSigla.getText().length() >= 4 ) {
        			e.consume();
        		}     
        	}
        });
        
        panel_1 = new JPanel();
        
        btnGuardar = new JButton("Guardar");
        panel_1.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        panel_1.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        panel_1.add(btnCerrar);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(50, Short.MAX_VALUE))
        		.addComponent(panel, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        panel.setLayout(null);
        panel.add(lblCodigo);
        panel.add(tfId);
        panel.add(lblNombre);
        panel.add(tfNombre);
        panel.add(lblSigla);
        panel.add(tfSigla);
        panel.add(lblActivo);
        panel.add(chEsBase);
        panel.add(lblActivo_1);
        panel.add(chActivo);
        
        tfMascara = new JTextField();
        tfMascara.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfOperacion.requestFocus();
				}
        	}
        });
        tfMascara.setBounds(85, 102, 67, 30);
        panel.add(tfMascara);
        
        lblMascara = new JLabel("MASCARA:");
        lblMascara.setBounds(6, 102, 67, 30);
        panel.add(lblMascara);
        
        lblOperacion = new JLabel("OPERACIÓN");
        lblOperacion.setBounds(6, 135, 74, 30);
        panel.add(lblOperacion);
        
        tfOperacion = new JComboBox<String>();
        tfOperacion.setModel(new DefaultComboBoxModel<String>(new String[] {"*", "/"}));
        tfOperacion.setBounds(85, 135, 67, 30);
        panel.add(tfOperacion);
        setLayout(groupLayout);
    }
    
    public void setFormValue(Moneda mon) {
    	tfId.setText(mon.getId() + "");
    	tfNombre.setText(mon.getNombre());
    	chEsBase.setSelected(mon.getEsBase() == 1 ? true : false);
    	chActivo.setSelected(mon.getActivo() == 1 ? true : false);
    }

    public Moneda getFormValue() {
    	Moneda mon = new Moneda();
        
        if (!tfId.getText().isEmpty()) {
        	mon.setId(Long.parseLong(tfId.getText()));
		}
        
        mon.setNombre(tfNombre.getText());
        mon.setSigla(tfSigla.getText());
        mon.setMascara(tfMascara.getText());
        mon.setOperacion(tfOperacion.getSelectedItem().toString());
        mon.setEsBase(chEsBase.isSelected() ? 1 : 0);
        mon.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return mon;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
    	chEsBase.setSelected(true);
    }

	public void setNewDeposito(long id) {
		tfId.setText(String.valueOf(id));
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
}
