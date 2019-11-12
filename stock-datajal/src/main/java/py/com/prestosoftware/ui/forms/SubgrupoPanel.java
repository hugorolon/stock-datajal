package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Subgrupo;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.table.GrupoComboBoxModel;
import py.com.prestosoftware.util.Borders;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.text.AbstractDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import javax.swing.DefaultComboBoxModel;

@Component
public class SubgrupoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre, tfId;
    private JPanel pnlBotonera;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    private JLabel lblCodigo, label;
    private JCheckBox chActivo;
    private JComboBox<Grupo> cbGrupo;
    
    private GrupoComboBoxModel grupoComboBoxModel;
    private JLabel label_1;
    private JTextField tfPorcPrecioA;
    private JLabel label_2;
    private JTextField tfPorcPrecioB;
    private JLabel label_3;
    private JTextField tfPorcPrecioC;
    private JLabel label_4;
    private JTextField tfPorcPrecioD;
    private JTextField tfPorcPrecioE;
    private JLabel label_5;
    private JComboBox<String> cbTipo;
    private JLabel label_7;

    @Autowired
    public SubgrupoPanel(GrupoComboBoxModel grupoComboBoxModel) {
    	this.grupoComboBoxModel = grupoComboBoxModel;
    	this.setSize(462, 249);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        setLayout(new MigLayout("", "[121px][5px][105px][12px][76px][3px][26px][21px][91px]", "[26px][26px][27px][26px][26px][26px][39px]"));
        
        lblCodigo = new JLabel("Codigo:");
        add(lblCodigo, "cell 0 0,alignx left,growy");
        
        tfId = new JTextField();
        add(tfId, "cell 2 0 3 1,growx,aligny top");
        tfId.setEditable(false);
        tfId.setColumns(10);
        
        JLabel lblNombre = new JLabel("Nombre:");
        add(lblNombre, "cell 0 1,alignx left,growy");
        
        tfNombre = new JTextField();
        add(tfNombre, "cell 2 1 7 1,growx,aligny top");
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfNombre.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbGrupo.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
        JLabel lblActivo = new JLabel("Grupo");
        add(lblActivo, "cell 0 2,alignx left,growy");
        
        cbGrupo = new JComboBox<Grupo>(grupoComboBoxModel);
        add(cbGrupo, "cell 2 2,growx,aligny top");
        cbGrupo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			tfPorcPrecioA.requestFocus();
				}	
        	}
        });
        
        label_1 = new JLabel("Aut / Precio A %");
        add(label_1, "cell 0 3,alignx right,growy");
        
        tfPorcPrecioA = new JTextField();
        add(tfPorcPrecioA, "cell 2 3,growx,aligny top");
        tfPorcPrecioA.setColumns(10);
        
        label_4 = new JLabel("Aut / Precio D %");
        add(label_4, "cell 4 3 3 1,grow");
        
        tfPorcPrecioD = new JTextField();
        add(tfPorcPrecioD, "cell 8 3,growx,aligny top");
        tfPorcPrecioD.setColumns(10);
        
        label_2 = new JLabel("Aut / Precio B %");
        add(label_2, "cell 0 4,alignx right,growy");
        
        tfPorcPrecioB = new JTextField();
        add(tfPorcPrecioB, "cell 2 4,growx,aligny top");
        tfPorcPrecioB.setColumns(10);
        
        label_5 = new JLabel("Aut / Precio E %");
        add(label_5, "cell 4 4 3 1,grow");
        
        label = new JLabel("Activo:");
        add(label, "cell 4 5 3 1,growx,aligny bottom");
        
        tfPorcPrecioE = new JTextField();
        add(tfPorcPrecioE, "cell 8 4,growx,aligny top");
        tfPorcPrecioE.setColumns(10);
        
        chActivo = new JCheckBox();
        add(chActivo, "cell 8 5,alignx left,aligny bottom");
        chActivo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        chActivo.setSelected(true);
        
        label_3 = new JLabel("Aut / Precio C %");
        add(label_3, "cell 0 5,alignx right,growy");
        
        tfPorcPrecioC = new JTextField();
        add(tfPorcPrecioC, "cell 2 5,growx,aligny top");
        tfPorcPrecioC.setColumns(10);
        
        pnlBotonera = new JPanel();
        add(pnlBotonera, "cell 0 6 9 1,growx,aligny top");
        
        btnGuardar = new JButton("Guardar");
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        pnlBotonera.add(btnCerrar);
        
        cbTipo = new JComboBox<String>();
        cbTipo.setModel(new DefaultComboBoxModel(new String[] {"M", "S"}));
        add(cbTipo, "cell 6 2 3 1,growx,aligny top");
        
        label_7 = new JLabel("Tipo:");
        add(label_7, "cell 4 2,grow");
    }
    
    public void setFormValue(Subgrupo subgrupo) {
    	tfId.setText(subgrupo.getId() + "");
    	tfNombre.setText(subgrupo.getNombre());
    	tfPorcPrecioA.setText(subgrupo.getPorcentajePrecioA() != null ? FormatearValor.doubleAString(subgrupo.getPorcentajePrecioA()) : "");
    	tfPorcPrecioB.setText(subgrupo.getPorcentajePrecioB() != null ? FormatearValor.doubleAString(subgrupo.getPorcentajePrecioB()) : "");
    	tfPorcPrecioC.setText(subgrupo.getPorcentajePrecioC() != null ? FormatearValor.doubleAString(subgrupo.getPorcentajePrecioC()) : "");
    	tfPorcPrecioD.setText(subgrupo.getPorcentajePrecioD() != null ? FormatearValor.doubleAString(subgrupo.getPorcentajePrecioD()) : "");
    	tfPorcPrecioE.setText(subgrupo.getPorcentajePrecioE() != null ? FormatearValor.doubleAString(subgrupo.getPorcentajePrecioE()) : "");
    	chActivo.setSelected(subgrupo.getActivo() == 1 ? true : false);
    }

    public Subgrupo getFormValue() {
    	Subgrupo subgrupo = new Subgrupo();
        
        if (!tfId.getText().isEmpty()) {
        	subgrupo.setId(Long.parseLong(tfId.getText()));
		}
        
        subgrupo.setNombre(tfNombre.getText());
        subgrupo.setGrupo(grupoComboBoxModel.getSelectedItem());
        subgrupo.setActivo(chActivo.isSelected() ? 1 : 0);
        subgrupo.setTipo((String)cbTipo.getSelectedItem());
        
        //TODO Cargar el config y verificar si esta permitido la operacion
        
        if (!tfPorcPrecioA.getText().isEmpty()) {
        	subgrupo.setPorcentajePrecioA(FormatearValor.stringADouble(tfPorcPrecioA.getText()));
        	subgrupo.setPorcentajePrecioB(FormatearValor.stringADouble(tfPorcPrecioB.getText()));
        	subgrupo.setPorcentajePrecioC(FormatearValor.stringADouble(tfPorcPrecioC.getText()));
        	subgrupo.setPorcentajePrecioD(FormatearValor.stringADouble(tfPorcPrecioD.getText()));
        	subgrupo.setPorcentajePrecioE(FormatearValor.stringADouble(tfPorcPrecioE.getText()));
		}
        
        return subgrupo;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
        cbGrupo.setSelectedIndex(0);
    	chActivo.setSelected(true);
    	cbTipo.setSelectedIndex(0);
    	
    	tfPorcPrecioA.setText("");
    	tfPorcPrecioB.setText("");
    	tfPorcPrecioC.setText("");
    	tfPorcPrecioD.setText("");
    	tfPorcPrecioE.setText("");
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

	public void setNewSubGrupo(long id) {
		tfId.setText(String.valueOf(id));
	}
}