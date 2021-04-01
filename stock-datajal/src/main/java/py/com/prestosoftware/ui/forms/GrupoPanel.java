package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Grupo;
import py.com.prestosoftware.data.models.Tamanho;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.table.TamanhoComboBoxModel;
import py.com.prestosoftware.util.Borders;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.text.AbstractDocument;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import net.miginfocom.swing.MigLayout;

@Component
public class GrupoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre, tfId;
    private JCheckBox chActivo;
    private JPanel pnlBotonera;
    private JButton btnGuardar, btnCancelar, btnCerrar;
    private JLabel lblCodigo;
    private JLabel lblAutprecioA;
    private JTextField tfPorcPrecioA;
    private JLabel lblAutprecioB;
    private JTextField tfPorcPrecioB;
    private JLabel lblAutprecioC;
    private JTextField tfPorcPrecioC;
    private JLabel lblAutprecioD;
    private JTextField tfPorcPrecioD;
    private JLabel lblAutprecioE;
    private JTextField tfPorcPrecioE;
    private JLabel lblTipo;
    private JLabel lblTamao;
    private JLabel label;
    private JLabel label_1;
    private JLabel label_2;
    
    private JComboBox<String> cbTipo;
    private JComboBox<Tamanho> cbTamanho;
    
    private TamanhoComboBoxModel tamanhoCbModel;

    @Autowired
    public GrupoPanel(TamanhoComboBoxModel tamanhoCbModel) {
    	this.tamanhoCbModel = tamanhoCbModel;
    	this.setSize(446, 234);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        setLayout(new MigLayout("", "[83px][4px][29px][5px][31px][18px][16px][5px][33px][2px][9px][2px][56px][5px][90px]", "[27px][28px][26px][26px][26px][39px]"));
        
        lblCodigo = new JLabel("Codigo:");
        add(lblCodigo, "cell 0 0,alignx center,growy");
        
        tfId = new JTextField();
        add(tfId, "cell 2 0 3 1,growx,aligny bottom");
        tfId.setColumns(10);
        
        JLabel lblNombre = new JLabel("Nombre:");
        add(lblNombre, "cell 6 0 3 1,alignx left,growy");
        
        tfNombre = new JTextField();
        add(tfNombre, "cell 12 0 3 1,alignx left,aligny bottom");
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfNombre.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbTamanho.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
        lblAutprecioA = new JLabel("Aut/Precio A %");
        add(lblAutprecioA, "cell 0 2 3 1,alignx right,growy");
        
        tfPorcPrecioA = new JTextField();
        add(tfPorcPrecioA, "cell 4 2 3 1,growx,aligny top");
        tfPorcPrecioA.setColumns(10);
        
        lblAutprecioD = new JLabel("Aut/Precio D %");
        add(lblAutprecioD, "cell 8 2 5 1,grow");
        
        tfPorcPrecioD = new JTextField();
        add(tfPorcPrecioD, "cell 14 2,growx,aligny top");
        tfPorcPrecioD.setColumns(10);
        
        lblAutprecioB = new JLabel("Aut/Precio B %");
        add(lblAutprecioB, "cell 0 3 3 1,alignx right,growy");
        
        tfPorcPrecioB = new JTextField();
        add(tfPorcPrecioB, "cell 4 3 3 1,growx,aligny top");
        tfPorcPrecioB.setColumns(10);
        
        lblAutprecioE = new JLabel("Aut/Precio E %");
        add(lblAutprecioE, "cell 8 3 5 1,grow");
        
        tfPorcPrecioE = new JTextField();
        add(tfPorcPrecioE, "cell 14 3,growx,aligny top");
        tfPorcPrecioE.setColumns(10);
        
        lblAutprecioC = new JLabel("Aut/Precio C %");
        add(lblAutprecioC, "cell 0 4 3 1,alignx right,growy");
        
        tfPorcPrecioC = new JTextField();
        add(tfPorcPrecioC, "cell 4 4 3 1,growx,aligny top");
        tfPorcPrecioC.setColumns(10);
        
        JLabel lblActivo = new JLabel("Activo:");
        add(lblActivo, "cell 8 4 5 1,grow");
        
        chActivo = new JCheckBox();
        add(chActivo, "cell 14 4,grow");
        chActivo.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					btnGuardar.requestFocus();
				}
        	}
        });
        chActivo.setSelected(true);
        
        pnlBotonera = new JPanel();
        add(pnlBotonera, "cell 0 5 15 1,growx,aligny top");
        
        btnGuardar = new JButton("Guardar");
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        pnlBotonera.add(btnCerrar);
        
        cbTipo = new JComboBox<String>();
        cbTipo.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent e) {
        		tfPorcPrecioA.requestFocus();
        	}
        });
        cbTipo.setModel(new DefaultComboBoxModel<String>(new String[] {"MERCADERIA", "SERVICIO"}));
        add(cbTipo, "cell 12 1 3 1,alignx left,aligny bottom");
        
        lblTipo = new JLabel("Tipo:");
        add(lblTipo, "cell 6 1 3 1,grow");
        
        lblTamao = new JLabel("Tamaño:");
        add(lblTamao, "cell 0 1,alignx center,growy");
        
        cbTamanho = new JComboBox<Tamanho>(tamanhoCbModel);
        cbTamanho.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent e) {
        		cbTipo.requestFocus();
        	}
        });
        add(cbTamanho, "cell 2 1 3 1,growx,aligny bottom");
        
        label = new JLabel("*");
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.RED);
        label.setFont(new Font("Dialog", Font.BOLD, 20));
        add(label, "cell 10 0,alignx left,growy");
        
        label_1 = new JLabel("*");
        label_1.setVerticalAlignment(SwingConstants.BOTTOM);
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setForeground(Color.RED);
        label_1.setFont(new Font("Dialog", Font.BOLD, 20));
        add(label_1, "cell 10 1,alignx left,growy");
        
        label_2 = new JLabel("*");
        label_2.setVerticalAlignment(SwingConstants.BOTTOM);
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setForeground(Color.RED);
        label_2.setFont(new Font("Dialog", Font.BOLD, 20));
        add(label_2, "cell 0 1,alignx right,growy");
    }
    
    public void setFormValue(Grupo grupo) {
    	tfId.setText(grupo.getId() + "");
    	tfNombre.setText(grupo.getNombre());
    	cbTamanho.setSelectedItem(grupo.getTamanho());
    	tfPorcPrecioA.setText(FormatearValor.doubleAString(grupo.getPorcIncrementoPrecioA()));
    	tfPorcPrecioB.setText(FormatearValor.doubleAString(grupo.getPorcIncrementoPrecioB()));
    	tfPorcPrecioC.setText(FormatearValor.doubleAString(grupo.getPorcIncrementoPrecioC()));
    	tfPorcPrecioD.setText(FormatearValor.doubleAString(grupo.getPorcIncrementoPrecioD()));
    	tfPorcPrecioE.setText(FormatearValor.doubleAString(grupo.getPorcIncrementoPrecioE()));
    	chActivo.setSelected(grupo.getActivo() == 1 ? true : false);
    }

    public Grupo getFormValue() {
    	Grupo grupo = new Grupo();
        
        if (!tfId.getText().isEmpty()) 
        	grupo.setId(Long.parseLong(tfId.getText()));
        
        grupo.setNombre(tfNombre.getText());
        grupo.setTamanho((Tamanho) cbTamanho.getSelectedItem());
        grupo.setActivo(chActivo.isSelected() ? 1 : 0);
        
        if (!tfPorcPrecioA.getText().isEmpty()) {
        	grupo.setPorcIncrementoPrecioA(FormatearValor.stringADouble(tfPorcPrecioA.getText()));
        }
        if (!tfPorcPrecioB.getText().isEmpty()) {
        	grupo.setPorcIncrementoPrecioB(FormatearValor.stringADouble(tfPorcPrecioB.getText()));
        }
        if (!tfPorcPrecioC.getText().isEmpty()) {
        	grupo.setPorcIncrementoPrecioC(FormatearValor.stringADouble(tfPorcPrecioC.getText()));
        }
        if (!tfPorcPrecioD.getText().isEmpty()) {
        	grupo.setPorcIncrementoPrecioD(FormatearValor.stringADouble(tfPorcPrecioD.getText()));
        }
        if (!tfPorcPrecioE.getText().isEmpty()) {
        	grupo.setPorcIncrementoPrecioE(FormatearValor.stringADouble(tfPorcPrecioE.getText()));
        }
        return grupo;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
    	cbTamanho.setSelectedIndex(0);
    	cbTipo.setSelectedIndex(0);
    	tfPorcPrecioA.setText("");
    	tfPorcPrecioB.setText("");
    	tfPorcPrecioC.setText("");
    	tfPorcPrecioD.setText("");
    	tfPorcPrecioE.setText("");
    	chActivo.setSelected(false);
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

	public void setNewGrupo(long id) {
		tfId.setText(String.valueOf(id));
	}
}