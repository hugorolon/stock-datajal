package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

@Component
public class EmpresaPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
    private JCheckBox chActivo;
    private JTextField tfId;
    private JTextField tfRepresentante;
    private JLabel lblRuc;
    private JTextField tfRuc;
    private JLabel lblDv;
    private JTextField tfRucDV;
    private JLabel lblTelefono;
    private JTextField tfTelefono;
    private JTextField tfDireccion;
    private JLabel lblDireccin;
    private JLabel lblCelular;
    private JTextField tfCelular;
    private JPanel panel;
    private JLabel lblCodigo;
    private JLabel lblRepresentante_1;
    private JPanel panel_1;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;

    public EmpresaPanel() {
    	this.setSize(530, 370);
        
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
					tfRepresentante.requestFocus();
				}
        	}
        });
        tfNombre.setColumns(10);
        
        tfRepresentante = new JTextField();
        ((AbstractDocument) tfRepresentante.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfRepresentante.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfRuc.requestFocus();
				}
        	}
        });
        tfRepresentante.setColumns(10);
        
        lblRuc = new JLabel("RUC:");
        
        tfRuc = new JTextField();
        tfRuc.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (!tfRuc.getText().isEmpty())
        				tfRucDV.setText(String.valueOf(Util.calculateRucDV(tfRuc.getText())));
					tfDireccion.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfRuc.setColumns(10);
        
        lblDv = new JLabel("DV:");
        
        tfRucDV = new JTextField();
        tfRucDV.setColumns(10);
        
        lblDireccin = new JLabel("Dirección:");
        
        tfDireccion = new JTextField();
        ((AbstractDocument) tfDireccion.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfDireccion.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfCelular.requestFocus();
				}
        	}
        });
        tfDireccion.setColumns(10);
        
        lblCelular = new JLabel("Celular:");
        
        tfCelular = new JTextField();
        tfCelular.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfTelefono.requestFocus();
				}
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCelular.setColumns(10);
        
        lblTelefono = new JLabel("Telefono:");
        
        tfTelefono = new JTextField();
        tfTelefono.addKeyListener(new KeyAdapter() {
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
        tfTelefono.setColumns(10);
        
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
        
        lblRepresentante_1 = new JLabel("Representante:");
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblRepresentante_1, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfRepresentante, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblRuc, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfRuc, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
        					.addGap(12)
        					.addComponent(lblDv, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
        					.addGap(5)
        					.addComponent(tfRucDV, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblDireccin, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfDireccion, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblCelular, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(tfCelular, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
        					.addGap(8)
        					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblTelefono, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
        						.addGroup(gl_panel.createSequentialGroup()
        							.addGap(75)
        							.addComponent(tfTelefono, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGap(10)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfId, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(10)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(10)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblRepresentante_1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfRepresentante, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(10)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblRuc, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfRuc, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblDv, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfRucDV, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(10)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblDireccin, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfDireccion, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(10)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblCelular, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfCelular, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblTelefono, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfTelefono, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        			.addGap(10)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(chActivo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
        );
        panel.setLayout(gl_panel);
        
        panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        panel_1.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        panel_1.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        panel_1.add(btnCerrar);
    }
    
    public void setFormValue(Empresa empresa) {
    	tfId.setText(empresa.getId() + "");
    	tfNombre.setText(empresa.getNombre());
    	tfRepresentante.setText(empresa.getRepresentante());
    	tfRuc.setText(empresa.getRuc());
    	tfRucDV.setText(empresa.getDvruc());
    	tfDireccion.setText(empresa.getDireccion());
    	tfTelefono.setText(empresa.getTelefono());
    	tfCelular.setText(empresa.getCelular());
    	chActivo.setSelected(empresa.getActivo() == 1 ? true : false);
    }

    public Empresa getFormValue() {
    	Empresa empresa = new Empresa();
        
        if (!tfId.getText().isEmpty()) {
        	empresa.setId(Long.parseLong(tfId.getText()));
		}
        
        empresa.setNombre(tfNombre.getText());
        empresa.setRepresentante(tfRepresentante.getText());
        empresa.setRuc(tfRuc.getText());
        empresa.setDvruc(tfRucDV.getText());
        empresa.setDireccion(tfDireccion.getText());
        empresa.setTelefono(tfTelefono.getText());
        empresa.setCelular(tfCelular.getText());
        empresa.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return empresa;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
        tfRepresentante.setText("");
        tfDireccion.setText("");
        tfRuc.setText("");
        tfRucDV.setText("");
        tfCelular.setText("");
        tfTelefono.setText("");
    	chActivo.setSelected(true);
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

	public void setNewEmpresa(long id) {
		tfId.setText(String.valueOf(id));
	}
    
}
