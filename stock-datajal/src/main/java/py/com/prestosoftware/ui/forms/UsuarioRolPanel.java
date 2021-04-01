package py.com.prestosoftware.ui.forms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Empresa;
import py.com.prestosoftware.data.models.Rol;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.data.models.UsuarioRol;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.ui.search.ClienteInterfaz;
import py.com.prestosoftware.ui.search.VendedorDialog;
import py.com.prestosoftware.ui.search.VendedorInterfaz;
import py.com.prestosoftware.ui.table.RolComboBoxModel;
import py.com.prestosoftware.ui.table.UsuarioComboBoxModel;
import py.com.prestosoftware.util.Borders;
import py.com.prestosoftware.util.Notifications;

import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.text.AbstractDocument;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Optional;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class UsuarioRolPanel extends JPanel implements VendedorInterfaz {

	private static final long serialVersionUID = 1L;
	private static final int USUARIO_CODE = 2;
	private JLabel lblRol;
    private JTextField tfId;
    private JPanel panel;
    private JLabel lblUsuario;
    private JPanel pnlBotonera;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnCerrar;
    private JComboBox<Rol> cbRol;
    private JTextField tfUsuarioID, tfUsuario;
    private RolComboBoxModel rolComboBoxModel;
    private Optional<Usuario> usuario;
    private VendedorDialog vendedorDialog;
    private UsuarioService usuarioService;
    
    @Autowired
    public UsuarioRolPanel(RolComboBoxModel rolComboBoxModel, UsuarioService usuarioService, VendedorDialog vendedorDialog) {
    	this.rolComboBoxModel = rolComboBoxModel;
    	this.usuarioService = usuarioService;
    	this.vendedorDialog =vendedorDialog;
    	this.setSize(400, 410);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 0));
        
        panel = new JPanel();
        add(panel, BorderLayout.NORTH);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{58, 223, 0};
        gbl_panel.rowHeights = new int[]{30, 59, 0, 31, 31, 0, 0, 0, 30, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        JLabel lbLid = new JLabel("Id:");
        GridBagConstraints gbc_lbLid = new GridBagConstraints();
        gbc_lbLid.anchor = GridBagConstraints.NORTH;
        gbc_lbLid.fill = GridBagConstraints.HORIZONTAL;
        gbc_lbLid.insets = new Insets(0, 0, 5, 5);
        gbc_lbLid.gridx = 0;
        gbc_lbLid.gridy = 1;
        panel.add(lbLid, gbc_lbLid);
        
        
        
        
        tfId = new JTextField();
        tfId.setEditable(false);
        tfId.setColumns(10);
        GridBagConstraints gbc_tfId = new GridBagConstraints();
        gbc_tfId.anchor = GridBagConstraints.WEST;
        gbc_tfId.fill = GridBagConstraints.VERTICAL;
        gbc_tfId.insets = new Insets(0, 0, 5, 0);
        gbc_tfId.gridx = 1;
        gbc_tfId.gridy = 1;
        panel.add(tfId, gbc_tfId);
        
        GridBagConstraints gbc_cbUsuario = new GridBagConstraints();
        gbc_cbUsuario.anchor = GridBagConstraints.WEST;
        gbc_cbUsuario.fill = GridBagConstraints.VERTICAL;
        gbc_cbUsuario.insets = new Insets(0, 0, 5, 0);
        gbc_cbUsuario.gridx = 1;
        gbc_cbUsuario.gridy = 2;
        
        JPanel pnlCliente = new JPanel();
        pnlCliente.setBounds(6, 18, 876, 79);
        GridBagConstraints gbc_pnlCliente = new GridBagConstraints();
        gbc_pnlCliente.anchor = GridBagConstraints.WEST;
        gbc_pnlCliente.fill = GridBagConstraints.VERTICAL;
        gbc_pnlCliente.gridheight = 2;
        gbc_pnlCliente.insets = new Insets(0, 0, 5, 0);
        gbc_pnlCliente.gridy = 2;
        gbc_pnlCliente.gridwidth = 2;
        gbc_pnlCliente.gridx = 0;
        panel.add(pnlCliente, gbc_pnlCliente);
        pnlCliente.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        lblUsuario = new JLabel("Usuario:");
        pnlCliente.add(lblUsuario);
        tfUsuarioID = new JTextField();
        tfUsuarioID.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	}
        });
        tfUsuarioID.setColumns(5);
        pnlCliente.add(tfUsuarioID);
        tfUsuarioID.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		tfUsuarioID.selectAll();
        	}
        });
        tfUsuarioID.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_F4) {
        			showDialog(USUARIO_CODE);
        		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			if (!tfUsuarioID.getText().isEmpty()) {
        				findUsuarioById(Long.valueOf(tfUsuarioID.getText()));
        			} else {
        				showDialog(USUARIO_CODE);
        			}
        		}
        	}

        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        
        tfUsuario = new JTextField();
        pnlCliente.add(tfUsuario);
        tfUsuario.setEditable(false);
        tfUsuario.setToolTipText("Nombre del Usuario");
        tfUsuario.setColumns(15);
        
        lblRol = new JLabel("Rol:");
        GridBagConstraints gbc_lblRol = new GridBagConstraints();
        gbc_lblRol.fill = GridBagConstraints.BOTH;
        gbc_lblRol.insets = new Insets(0, 0, 5, 5);
        gbc_lblRol.gridx = 0;
        gbc_lblRol.gridy = 4;
        panel.add(lblRol, gbc_lblRol);
        
        cbRol = new JComboBox<Rol>(rolComboBoxModel);
        cbRol.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			tfUsuarioID.requestFocus();
				}
        	}
        });
        
        GridBagConstraints gbc_cbRol = new GridBagConstraints();
        gbc_cbRol.fill = GridBagConstraints.BOTH;
        gbc_cbRol.insets = new Insets(0, 0, 5, 0);
        gbc_cbRol.gridx = 1;
        gbc_cbRol.gridy = 4;
        panel.add(cbRol, gbc_cbRol);
        GridBagConstraints gbc_lblVendedor = new GridBagConstraints();
        gbc_lblVendedor.insets = new Insets(0, 0, 5, 5);
        gbc_lblVendedor.gridx = 0;
        gbc_lblVendedor.gridy = 6;
        
        pnlBotonera = new JPanel();
        add(pnlBotonera, BorderLayout.SOUTH);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        pnlBotonera.add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        pnlBotonera.add(btnCancelar);
        
        btnCerrar = new JButton("Cerrar");
        pnlBotonera.add(btnCerrar);
    }
    
    public void setFormValue(UsuarioRol usuarioRol) {
    	tfId.setText(usuarioRol.getId() + "");
    	rolComboBoxModel.setSelectedItem(usuarioRol.getRol());
    	tfUsuarioID.setText(usuarioRol.getUsuario().getId().toString());
    	tfUsuario.setText(usuarioRol.getUsuario().getUsuario());
    	//usuComboBoxModel.setSelectedItem(usuarioRol.getUsuario());
    }

    public UsuarioRol getFormValue() {
    	UsuarioRol userRol = new UsuarioRol();
        
        if (!tfId.getText().isEmpty()) {
        	userRol.setId(Long.parseLong(tfId.getText()));
		}
        if(!tfUsuarioID.getText().isEmpty()) {
        	userRol.setUsuario(new Usuario());
        	userRol.getUsuario().setId(Long.parseLong(tfUsuarioID.getText()));
        }
        if(!tfUsuario.getText().isEmpty())
        	userRol.getUsuario().setUsuario(tfUsuario.getText());
        
        userRol.setRol(rolComboBoxModel.getSelectedItem());
        
        return userRol;
    }

    public void clearForm() {
    	tfId.setText("");
        cbRol.setSelectedIndex(0);
        tfUsuarioID.setText("");
        tfUsuario.setText("");
    }
    
    
    
    public JComboBox<Rol> getCbRol() {
		return cbRol;
	}

	public void setCbRol(JComboBox<Rol> cbRol) {
		this.cbRol = cbRol;
	}

	public JTextField getTfUsuarioID() {
		return tfUsuarioID;
	}

	public void setTfUsuarioID(JTextField tfUsuarioID) {
		this.tfUsuarioID = tfUsuarioID;
	}
	
	public JTextField getTfUsuario() {
		return tfUsuario;
	}

	public void setTfUsuario(JTextField tfUsuario) {
		this.tfUsuario = tfUsuario;
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
    
	public void setNewUsuarioRol(long id) {
		tfId.setText(String.valueOf(id));
	}
	
	private void findUsuarioById(Long id) {
		usuario = usuarioService.findById(id);
		if (usuario.isPresent()) {
			String nombre = usuario.get().getUsuario();
			tfUsuario.setText(nombre);				
		} else {
			tfUsuario.setText("");
			Notifications.showAlert("No existe Usuario con este codigo.!");
		}
	}
	
	private void showDialog(int code) {
		switch (code) {
			case USUARIO_CODE:
				vendedorDialog.setInterfaz(this);
				vendedorDialog.setVisible(true);
				break;
			default:
				break;
		}
	}

	@Override
	public void getEntity(Usuario usuario) {
		if (usuario != null) {
			tfUsuarioID.setText(String.valueOf(usuario.getId()));
			tfUsuario.setText(usuario.getUsuario());
			cbRol.requestFocus();
		}
	}
	
	public Optional<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(Optional<Usuario> usuario) {
		this.usuario = usuario;
	}

	
}