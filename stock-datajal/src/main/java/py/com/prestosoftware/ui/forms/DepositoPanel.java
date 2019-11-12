package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Deposito;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import py.com.prestosoftware.data.models.Empresa;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@Component
public class DepositoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
    private JCheckBox chActivo;
    private JTextField tfId;
    private JPanel panel;
    private JLabel lblCodigo;
    private JPanel panel_1;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    private JLabel label;
    private JLabel label_1;
    private JComboBox<Empresa> cbEmpresa;
    private JLabel label_2;
    
    private EmpresaComboBoxModel empresaComboBoxModel;

    @Autowired
    public DepositoPanel(EmpresaComboBoxModel empresaComboBoxModel) {
    	this.empresaComboBoxModel = empresaComboBoxModel;
    	
    	this.setSize(400, 240);
        
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
					cbEmpresa.requestFocus();
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
        panel.setLayout(new MigLayout("", "[79px][223px]", "[30px][30px][30px][30px]"));
        panel.add(lblCodigo, "cell 0 0,grow");
        panel.add(tfId, "cell 1 0,alignx left,growy");
        panel.add(lblNombre, "cell 0 1,growy");
        panel.add(tfNombre, "cell 1 1,grow");
        panel.add(lblActivo, "cell 0 3,grow");
        panel.add(chActivo, "cell 1 3,grow");
        
        label = new JLabel("Empresa:");
        panel.add(label, "cell 0 2,alignx left,growy");
        
        label_1 = new JLabel("*");
        label_1.setVerticalAlignment(SwingConstants.BOTTOM);
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setForeground(Color.RED);
        label_1.setFont(new Font("Dialog", Font.BOLD, 20));
        panel.add(label_1, "cell 0 1,alignx left,growy");
        
        cbEmpresa = new JComboBox<Empresa>(empresaComboBoxModel);
        cbEmpresa.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent e) {
        		chActivo.requestFocus();
        	}
        });
        panel.add(cbEmpresa, "cell 1 2,grow");
        
        label_2 = new JLabel("*");
        label_2.setVerticalAlignment(SwingConstants.BOTTOM);
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setForeground(Color.RED);
        label_2.setFont(new Font("Dialog", Font.BOLD, 20));
        panel.add(label_2, "cell 0 2,alignx right,growy");
        
        panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        panel_1.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        panel_1.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        panel_1.add(btnCerrar);
    }
    
    public void setFormValue(Deposito dep) {
    	tfId.setText(dep.getId() + "");
    	tfNombre.setText(dep.getNombre());
    	cbEmpresa.setSelectedItem(dep.getEmpresa());
    	chActivo.setSelected(dep.getActivo() == 1 ? true : false);
    }

    public Deposito getFormValue() {
    	Deposito dep = new Deposito();
        
        if (!tfId.getText().isEmpty())
        	dep.setId(Long.parseLong(tfId.getText()));
        
        dep.setNombre(tfNombre.getText());
        dep.setEmpresa((Empresa) cbEmpresa.getSelectedItem());
        dep.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return dep;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
        cbEmpresa.setSelectedIndex(0);
    	chActivo.setSelected(true);
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
