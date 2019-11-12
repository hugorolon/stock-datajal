package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Impuesto;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class ImpuestoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
    private JCheckBox chActivo;
    private JTextField tfId;
 
    private JLabel lblPorcentaje;
    private JTextField tfPorcentaje;
    private JLabel lblCodigo;
    private JPanel pnlCrud;
    private JPanel panel;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;

    public ImpuestoPanel() {
    	this.setSize(400, 218);
        
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
					tfPorcentaje.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
        lblPorcentaje = new JLabel("Porcentaje:");
        
        tfPorcentaje = new JTextField();
        tfPorcentaje.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfPorcentaje.setColumns(10);
        
        JLabel lblActivo = new JLabel("Activo:");
        
        chActivo = new JCheckBox();
        chActivo.setSelected(true);
        GroupLayout gl_pnlCrud = new GroupLayout(pnlCrud);
        gl_pnlCrud.setHorizontalGroup(
        	gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCrud.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblPorcentaje, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfPorcentaje, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pnlCrud.createSequentialGroup()
        					.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))))
        );
        gl_pnlCrud.setVerticalGroup(
        	gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pnlCrud.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(1)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(1)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblPorcentaje, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfPorcentaje, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(5)
        			.addGroup(gl_pnlCrud.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
        );
        pnlCrud.setLayout(gl_pnlCrud);
        
        panel = new JPanel();
        add(panel, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	}
        });
        panel.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        panel.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        panel.add(btnCerrar);
    }
    
    public void setFormValue(Impuesto impuesto) {
    	tfId.setText(impuesto.getId() + "");
    	tfNombre.setText(impuesto.getNombre());
    	tfPorcentaje.setText(String.valueOf(impuesto.getPorcentaje()));
    	chActivo.setSelected(impuesto.getActivo() == 1 ? true : false);
    }

    public Impuesto getFormValue() {
    	Impuesto impuesto = new Impuesto();
        
        if (!tfId.getText().isEmpty()) {
        	impuesto.setId(Long.parseLong(tfId.getText()));
		}
        
        impuesto.setNombre(tfNombre.getText());
        impuesto.setPorcentaje(Double.valueOf(tfPorcentaje.getText()));
        impuesto.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return impuesto;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
        tfPorcentaje.setText("");
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

	public void setNewImpuesto(long id) {
		tfId.setText(String.valueOf(id));
	}
    
}