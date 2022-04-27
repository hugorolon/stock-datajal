package py.com.prestosoftware.ui.forms;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Marca;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.search.MarcaInterfaz;
import py.com.prestosoftware.ui.search.ProveedorInterfaz;
import py.com.prestosoftware.ui.shared.FormBtnPanel;
import py.com.prestosoftware.util.Borders;

@Component
public class MarcaPanel extends JPanel implements MarcaInterfaz{

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
    private JCheckBox chActivo;
    private JTextField tfId;
 
    private FormBtnPanel formBtnPanel;
    private JPanel pnlCrud;
    private JLabel lblCodigo;
    private JPanel pnlBotonera;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    private MarcaInterfaz interfaz;

    @Autowired
    public MarcaPanel(FormBtnPanel formBtnPanel) {
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
        
        pnlCrud = new JPanel();
        add(pnlCrud);
        
        lblCodigo = new JLabel("Codigo:");
        
        tfId = new JTextField();
        tfId.setEnabled(false);
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
        		btnGuardar.requestFocus();
        	}
        });
        GroupLayout gl_pnlCrud = new GroupLayout(pnlCrud);
        gl_pnlCrud.setHorizontalGroup(
        	gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCrud.createSequentialGroup()
        			.addGap(5)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(13)
        					.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(13)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
        					.addGap(13)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))))
        );
        gl_pnlCrud.setVerticalGroup(
        	gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCrud.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(4)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addGap(1)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
        			.addGap(4)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addGap(1)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))))
        );
        pnlCrud.setLayout(gl_pnlCrud);
        
        pnlBotonera = new JPanel();
        add(pnlBotonera, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        pnlBotonera.add(btnCerrar);
    }
    
    public MarcaInterfaz getInterfaz() {
		return interfaz;
	}

	public void setInterfaz(MarcaInterfaz interfaz) {
		this.interfaz = interfaz;
	}
    
    public void setFormValue(Marca marca) {
    	tfId.setText(marca.getId() + "");
    	tfNombre.setText(marca.getNombre());
    	chActivo.setSelected(marca.getActivo() == 1 ? true : false);
    }

    public Marca getFormValue() {
    	Marca marca = new Marca();
        
        if (!tfId.getText().isEmpty()) {
        	marca.setId(Long.parseLong(tfId.getText()));
		}
        
        marca.setNombre(tfNombre.getText());
        marca.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return marca;
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

	public void setNewMarca(long id) {
		tfId.setText(String.valueOf(id));
	}

	@Override
	public void getEntity(Marca marca) {
		// TODO Auto-generated method stub
		
	}
}
