package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import py.com.prestosoftware.data.models.Empresa;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import net.miginfocom.swing.MigLayout;

@Component
public class CajaPanel extends JPanel {

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
    private JLabel label;
    private JLabel label_1;
    private JLabel label_2;
    
    private JComboBox<Empresa> cbEmpresas;
    
    private EmpresaComboBoxModel empresaComboBoxModel;

    @Autowired
    public CajaPanel(EmpresaComboBoxModel empresaComboBoxModel) {
    	this.empresaComboBoxModel = empresaComboBoxModel;
    	
    	this.setSize(400, 220);
        
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
        chActivo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        chActivo.setSelected(true);
        pnlCrud.setLayout(new MigLayout("", "[58px][3px][18px][223px]", "[30px][30px][30px][30px]"));
        pnlCrud.add(lblCodigo, "cell 0 0,grow");
        pnlCrud.add(tfId, "cell 3 0,alignx left,growy");
        pnlCrud.add(lblNombre, "cell 0 1,grow");
        pnlCrud.add(tfNombre, "cell 3 1,grow");
        pnlCrud.add(lblActivo, "cell 0 3,grow");
        pnlCrud.add(chActivo, "cell 3 3,alignx left,growy");
        
        label = new JLabel("*");
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.RED);
        label.setFont(new Font("Dialog", Font.BOLD, 20));
        pnlCrud.add(label, "cell 2 2,grow");
        
        cbEmpresas = new JComboBox<Empresa>(empresaComboBoxModel);
        cbEmpresas.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent e) {
        		chActivo.requestFocus();
        	}
        });
        pnlCrud.add(cbEmpresas, "cell 3 2,grow");
        
        label_1 = new JLabel("Empresa:");
        pnlCrud.add(label_1, "cell 0 2 3 1,alignx left,growy");
        
        label_2 = new JLabel("*");
        label_2.setVerticalAlignment(SwingConstants.BOTTOM);
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setForeground(Color.RED);
        label_2.setFont(new Font("Dialog", Font.BOLD, 20));
        pnlCrud.add(label_2, "cell 2 1,grow");
        
        pnlBotonera = new JPanel();
        add(pnlBotonera, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        pnlBotonera.add(btnCerrar);
    }
    
    public void setFormValue(Caja caja) {
    	tfId.setText(caja.getId() + "");
    	tfNombre.setText(caja.getNombre());
    	cbEmpresas.setSelectedItem(caja.getEmpresa());
    	chActivo.setSelected(caja.getActivo() == 1 ? true : false);
    }

    public Caja getFormValue() {
    	Caja caja = new Caja();
        
        if (!tfId.getText().isEmpty()) 
        	caja.setId(Long.parseLong(tfId.getText()));
        
        caja.setNombre(tfNombre.getText());
        caja.setEmpresa((Empresa) cbEmpresas.getSelectedItem());
        caja.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return caja;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
        cbEmpresas.setSelectedIndex(0);
    	chActivo.setSelected(false);
    }

	public void setNewCaja(long id) {
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
