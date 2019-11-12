package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Rol;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.table.EmpresaComboBoxModel;
import py.com.prestosoftware.ui.table.RolComboBoxModel;
import py.com.prestosoftware.util.Borders;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.text.AbstractDocument;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

@Component
public class UsuarioPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField tfNombre;
	private JPasswordField tfClave;
    private JLabel lblClave;
    private JLabel lblRol;
    private JCheckBox chActivo;
    private JTextField tfId;
    private JPanel panel;
    private JLabel lblCodigo;
    private JLabel lblEmpresa;
    private JPanel pnlBotonera;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    private JComboBox<Rol> cbRol;
    private JComboBox<Empresa> cbEmpresa;
    
    private RolComboBoxModel rolComboBoxModel;
    private EmpresaComboBoxModel empresaComboBoxModel;
    
    @Autowired
    public UsuarioPanel(RolComboBoxModel rolComboBoxModel, EmpresaComboBoxModel empresaComboBoxModel) {
    	this.rolComboBoxModel = rolComboBoxModel;
    	this.empresaComboBoxModel = empresaComboBoxModel;
    	this.setSize(400, 285);
        
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
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{58, 223, 0};
        gbl_panel.rowHeights = new int[]{30, 59, 31, 31, 30, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        lblCodigo = new JLabel("Codigo:");
        GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
        gbc_lblCodigo.fill = GridBagConstraints.BOTH;
        gbc_lblCodigo.insets = new Insets(0, 0, 5, 5);
        gbc_lblCodigo.gridx = 0;
        gbc_lblCodigo.gridy = 0;
        panel.add(lblCodigo, gbc_lblCodigo);
        
        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setColumns(10);
        GridBagConstraints gbc_tfId = new GridBagConstraints();
        gbc_tfId.anchor = GridBagConstraints.WEST;
        gbc_tfId.fill = GridBagConstraints.VERTICAL;
        gbc_tfId.insets = new Insets(0, 0, 5, 0);
        gbc_tfId.gridx = 1;
        gbc_tfId.gridy = 0;
        panel.add(tfId, gbc_tfId);
        
        JLabel lblNombre = new JLabel("Usuario:");
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.anchor = GridBagConstraints.NORTH;
        gbc_lblNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblNombre.gridx = 0;
        gbc_lblNombre.gridy = 1;
        panel.add(lblNombre, gbc_lblNombre);
        
        lblClave = new JLabel("Clave:");
        GridBagConstraints gbc_lblClave = new GridBagConstraints();
        gbc_lblClave.anchor = GridBagConstraints.SOUTH;
        gbc_lblClave.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblClave.insets = new Insets(0, 0, 5, 5);
        gbc_lblClave.gridx = 0;
        gbc_lblClave.gridy = 1;
        panel.add(lblClave, gbc_lblClave);
        
        tfNombre = new JTextField();
        ((AbstractDocument) tfNombre.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        tfNombre.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfClave.requestFocus();
				}
        	}
        });
        
        tfClave = new JPasswordField();
        tfClave.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbRol.requestFocus();
				}
        	}
        });
        tfClave.setColumns(10);
        GridBagConstraints gbc_tfClave = new GridBagConstraints();
        gbc_tfClave.anchor = GridBagConstraints.SOUTH;
        gbc_tfClave.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfClave.insets = new Insets(0, 0, 5, 0);
        gbc_tfClave.gridx = 1;
        gbc_tfClave.gridy = 1;
        panel.add(tfClave, gbc_tfClave);
        tfNombre.setColumns(10);
        GridBagConstraints gbc_tfNombre = new GridBagConstraints();
        gbc_tfNombre.anchor = GridBagConstraints.NORTH;
        gbc_tfNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfNombre.insets = new Insets(0, 0, 5, 0);
        gbc_tfNombre.gridx = 1;
        gbc_tfNombre.gridy = 1;
        panel.add(tfNombre, gbc_tfNombre);
        
        cbEmpresa = new JComboBox<Empresa>(empresaComboBoxModel);
        cbEmpresa.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cbRol.requestFocus();
				}
        	}
        });
        
        lblEmpresa = new JLabel("Empresa:");
        GridBagConstraints gbc_lblEmpresa = new GridBagConstraints();
        gbc_lblEmpresa.fill = GridBagConstraints.BOTH;
        gbc_lblEmpresa.insets = new Insets(0, 0, 5, 5);
        gbc_lblEmpresa.gridx = 0;
        gbc_lblEmpresa.gridy = 2;
        panel.add(lblEmpresa, gbc_lblEmpresa);
        GridBagConstraints gbc_cbEmpresa = new GridBagConstraints();
        gbc_cbEmpresa.fill = GridBagConstraints.BOTH;
        gbc_cbEmpresa.insets = new Insets(0, 0, 5, 0);
        gbc_cbEmpresa.gridx = 1;
        gbc_cbEmpresa.gridy = 2;
        panel.add(cbEmpresa, gbc_cbEmpresa);
        
        lblRol = new JLabel("Rol:");
        GridBagConstraints gbc_lblRol = new GridBagConstraints();
        gbc_lblRol.fill = GridBagConstraints.BOTH;
        gbc_lblRol.insets = new Insets(0, 0, 5, 5);
        gbc_lblRol.gridx = 0;
        gbc_lblRol.gridy = 3;
        panel.add(lblRol, gbc_lblRol);
        
        cbRol = new JComboBox<Rol>(rolComboBoxModel);
        cbRol.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					chActivo.requestFocus();
				}
        	}
        });
        GridBagConstraints gbc_cbRol = new GridBagConstraints();
        gbc_cbRol.fill = GridBagConstraints.BOTH;
        gbc_cbRol.insets = new Insets(0, 0, 5, 0);
        gbc_cbRol.gridx = 1;
        gbc_cbRol.gridy = 3;
        panel.add(cbRol, gbc_cbRol);
        
        JLabel lblActivo = new JLabel("Activo:");
        GridBagConstraints gbc_lblActivo = new GridBagConstraints();
        gbc_lblActivo.fill = GridBagConstraints.BOTH;
        gbc_lblActivo.insets = new Insets(0, 0, 0, 5);
        gbc_lblActivo.gridx = 0;
        gbc_lblActivo.gridy = 4;
        panel.add(lblActivo, gbc_lblActivo);
        
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
        GridBagConstraints gbc_chActivo = new GridBagConstraints();
        gbc_chActivo.anchor = GridBagConstraints.WEST;
        gbc_chActivo.fill = GridBagConstraints.VERTICAL;
        gbc_chActivo.gridx = 1;
        gbc_chActivo.gridy = 4;
        panel.add(chActivo, gbc_chActivo);
        
        pnlBotonera = new JPanel();
        add(pnlBotonera, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        pnlBotonera.add(btnCerrar);
    }
    
    public void setFormValue(Usuario usuario) {
    	tfId.setText(usuario.getId() + "");
    	tfNombre.setText(usuario.getUsuario());
    	tfClave.setText(usuario.getClave());
    	rolComboBoxModel.setSelectedItem(usuario.getRol());
    	empresaComboBoxModel.setSelectedItem(usuario.getEmpresa());
    	chActivo.setSelected(usuario.getActivo() == 1 ? true : false);
    }

    public Usuario getFormValue() {
    	Usuario user = new Usuario();
        
        if (!tfId.getText().isEmpty()) {
        	user.setId(Long.parseLong(tfId.getText()));
		}
        
        user.setUsuario(tfNombre.getText());
        //user.setClave(tfClave.getPassword().toString());
        String passText = new String(tfClave.getPassword());
        user.setClave(passText);
        user.setRol(rolComboBoxModel.getSelectedItem());
        user.setEmpresa(empresaComboBoxModel.getSelectedItem());
        user.setActivo(chActivo.isSelected() ? 1 : 0);
        
        return user;
    }

    public void clearForm() {
    	tfId.setText("");
        tfNombre.setText("");
        tfClave.setText("");
        cbRol.setSelectedIndex(0);
        cbEmpresa.setSelectedIndex(0);
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

	public void setNewUsuario(long id) {
		tfId.setText(String.valueOf(id));
	}
}