package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.ListaPrecio;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.shared.FormBtnPanel;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@Component
public class ListaPrecioPanel extends JPanel {

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

    @Autowired
    public ListaPrecioPanel(FormBtnPanel formBtnPanel) {
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
        GroupLayout gl_pnlCrud = new GroupLayout(pnlCrud);
        gl_pnlCrud.setHorizontalGroup(
        	gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCrud.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
        					.addGap(2)
        					.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
        					.addGap(2)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
        					.addGap(2)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))))
        );
        gl_pnlCrud.setVerticalGroup(
        	gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCrud.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(12)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
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
    
    public void setFormValue(ListaPrecio lista) {
    	tfId.setText(lista.getId() + "");
    	tfNombre.setText(lista.getNombre());
    	chActivo.setSelected(lista.getActivo() == 1 ? true : false);
    }

    public ListaPrecio getFormValue() {
    	ListaPrecio listaPrecio = new ListaPrecio();
        
        if (!tfId.getText().isEmpty()) {
        	listaPrecio.setId(Long.parseLong(tfId.getText()));
		}
        
        listaPrecio.setNombre(tfNombre.getText());
        listaPrecio.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return listaPrecio;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
    	chActivo.setSelected(true);
    }

	public void setNewListaPrecio(long id) {
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
