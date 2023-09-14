package py.com.prestosoftware.ui.main;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.data.models.Usuario;
import py.com.prestosoftware.domain.services.MonedaService;
import py.com.prestosoftware.domain.services.UsuarioRolService;
import py.com.prestosoftware.domain.services.UsuarioService;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.UppercaseDocumentFilter;
import py.com.prestosoftware.util.Notifications;

@Component
public class LoginForm extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JTextField tfUsuario = null;
	private JPasswordField tfClave = null;
	private JButton bAceptar = null;
	private JButton bCerrar = null;
	private JLabel lblUsuario;
	private JLabel lblClave;
	
	private MonedaService monedaService;
	private UsuarioService usuarioService;
	private UsuarioRolService usuarioRolService;
	private MainFrame mainMenuFrame;

	@Autowired
	public LoginForm(MonedaService monedaService, UsuarioService usuarioService,UsuarioRolService usuarioRolService, MainFrame mainMenuFrame) {
		super();
		
		this.monedaService = monedaService;
		this.usuarioService = usuarioService;
		this.mainMenuFrame = mainMenuFrame;
		this.usuarioRolService=usuarioRolService;
		initialize();
	}
	
	private void initialize() {
		this.setSize(796, 498);
		//this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/resources/static/favicon.ico")));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\adrian5\\stock-datajal\\stock-datajal\\src\\main\\resources\\static\\favicon.ico"));
		this.setContentPane(getJContentPane());
		this.setTitle("LOGIN - STK");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.exit(0);
			}
		});
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblClave = new JLabel();
			lblClave.setBounds(new Rectangle(24, 57, 72, 24));
			lblClave.setText("CLAVE:");
			lblUsuario = new JLabel();
			lblUsuario.setBounds(new Rectangle(24, 18, 72, 24));
			lblUsuario.setText("USUARIO:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getTfUsuario(), null);
			jContentPane.add(getTfClave(), null);
			jContentPane.add(getBAceptar(), null);
			jContentPane.add(getBCerrar(), null);
			jContentPane.add(lblUsuario, null);
			jContentPane.add(lblClave, null);
		}
		return jContentPane;
	}

	private JTextField getTfUsuario() {
		if (tfUsuario == null) {
			tfUsuario = new JTextField();
			tfUsuario.setBounds(new Rectangle(106, 15, 216, 30));
			((AbstractDocument) tfUsuario.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
			tfUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode()==KeyEvent.VK_ENTER) {
						tfClave.requestFocus();
					} else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
						tfClave.requestFocus();
					}
				}
			});
			tfUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					eventoTeclado(e);
				}
			});
			tfUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					tfUsuario.selectAll();
				}
			});
		}
		return tfUsuario;
	}

	private JPasswordField getTfClave() {
		if (tfClave == null) {
			tfClave = new JPasswordField();
			tfClave.setBounds(new Rectangle(106, 56, 216, 30));
			((AbstractDocument) tfClave.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
			tfClave.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode()==KeyEvent.VK_ENTER){
						aceptar();
					} else if (e.getKeyCode()==KeyEvent.VK_UP) {
						tfUsuario.requestFocus();
					}
				}
			});
			tfClave.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusGained(java.awt.event.FocusEvent e) {
					tfClave.selectAll();
				}
			});
		}
		return tfClave;
	}

	private JButton getBAceptar() {
		if (bAceptar == null) {
			bAceptar = new JButton();
			bAceptar.setBounds(new Rectangle(106, 97, 103, 36));
			bAceptar.setText("Aceptar");
			bAceptar.setMnemonic(KeyEvent.VK_A);
			bAceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					aceptar();
				}
			});
			bAceptar.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						aceptar();
					}
				}
			});
		}
		return bAceptar;
	}

	private JButton getBCerrar() {
		if (bCerrar == null) {
			bCerrar = new JButton();
			bCerrar.setBounds(new Rectangle(219, 97, 103, 36));
			bCerrar.setText("Cerrar");
			bCerrar.setMnemonic(KeyEvent.VK_C);
			bCerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
			bCerrar.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						cerrar();
					}
				}
			});
		}
		return bCerrar;
	}
	
	private void aceptar() {
		if (validateInputs()) {
			Optional<Usuario> user = usuarioService.login(tfUsuario.getText(), tfClave.getText());
			
			if (user.isPresent()) {
				setGlobalConfig(user.get());
				setMonedaConfig();
				setUsuarioConfig();
				showMenu();
			} else {
				Notifications.showAlert("Usuario o clave incorrectos. Vuelva a intentarlo.!");
				tfUsuario.requestFocus();
			}
			
		}
	}
	
	private void showMenu() {
		dispose();
		if (usuarioRolService!=null&&!usuarioRolService.hasRole(Long.valueOf(GlobalVars.USER_ID), "VENTAS CON DESCUENTO TOTAL")) {
			mainMenuFrame.getMnuCompra().setEnabled(false);
			mainMenuFrame.getBtnCompras().setEnabled(false);
			mainMenuFrame.getMnuMercaderia().setEnabled(false);
			mainMenuFrame.getMnuMovCuentaARecibirCobroCliente().setEnabled(false);
			mainMenuFrame.getMnuMovCuentaAPagarPagoProveedor().setEnabled(false);
			mainMenuFrame.getMnuMovCajaIngreso().setVisible(false);
			mainMenuFrame.getMnuMovCajaEgreso().setVisible(false);
			mainMenuFrame.getMnuRelatorios().setVisible(false);
		}
		mainMenuFrame.setVisible(true);
	}
	
	private void setUsuarioConfig() {
		mainMenuFrame.loadInfoUser();
	}
	
	private void setGlobalConfig(Usuario u ) {
		GlobalVars.USER_ID 		= u.getId();
		GlobalVars.USER 		= u.getUsuario();
		GlobalVars.EMPRESA_ID 	= u.getEmpresa().getId();
		GlobalVars.EMPRESA 		= u.getEmpresa().getNombre();
		GlobalVars.EMPRESA_DIR 	= u.getEmpresa().getDireccion();
		GlobalVars.EMPRESA_TEL 	= u.getEmpresa().getCelular();
		GlobalVars.EMPRESA_RUC 	= u.getEmpresa().getRuc();
	}
	
	private void setMonedaConfig() {
		Optional<Moneda> moneda = monedaService.findMonedaBase(1);
		
		if (moneda.isPresent())
			GlobalVars.BASE_MONEDA_ID = moneda.get().getId();
		else 
			GlobalVars.BASE_MONEDA_ID = 1L;
	}
	
	private boolean validateInputs() {
		if (tfUsuario.getText().isEmpty()) {
			Notifications.showAlert("El usuario es obligatorio");
			tfUsuario.requestFocus();
			return false;
		} else if (tfClave.getText().isEmpty()) {
			Notifications.showAlert("La clave es obligatorio");
			tfClave.requestFocus();
			return false;
		}
		
		return true;
	}
	
	private void cerrar() {
		System.exit(0);
	}
	
	private void eventoTeclado(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			dispose();
		}
	}
	
}  