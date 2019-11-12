package py.com.prestosoftware.ui.transactions;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.AperturaCierreCaja;
import py.com.prestosoftware.data.models.Caja;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.domain.services.CajaService;
import py.com.prestosoftware.domain.services.MovimientoCajaService;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.helpers.GlobalVars;
import py.com.prestosoftware.ui.helpers.Util;
import py.com.prestosoftware.domain.services.AperturaCierreCajaService;
import py.com.prestosoftware.util.Notifications;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.util.Date;
import java.util.Optional;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class AperturaCierrePanel extends JDialog {

	private static final long serialVersionUID = 1L;
    
	private JTextField tfCajaID;
	private JTextField tfCaja;
	private JButton btnGuardar;
	
	private CajaService cajaService;
	private AperturaCierreCajaService movService;
	private MovimientoCajaService cajaMovService;
	
	private int tipoMov;

    @Autowired
    public AperturaCierrePanel(CajaService cajaService, AperturaCierreCajaService movService, MovimientoCajaService cajaMovService) {
    	this.cajaService = cajaService;
    	this.movService = movService;
    	this.cajaMovService = cajaMovService;
    	
    	this.setSize(500, 207);
        getContentPane().setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        
        setPanelUp();
        initComponents();
    }

    private void setPanelUp() {
        //setBorder(Borders.createEmptyBorder());
    }

    private void initComponents() {
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Movimiento de Caja", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBounds(6, 6, 488, 171);
        getContentPane().add(panel_3);
        panel_3.setLayout(null);
        
        JPanel pnlCliente = new JPanel();
        pnlCliente.setBounds(6, 18, 476, 145);
        panel_3.add(pnlCliente);
        pnlCliente.setLayout(null);
        
        JLabel lblCondicin = new JLabel("CAJA NUMERO:");
        lblCondicin.setBounds(53, 6, 95, 30);
        pnlCliente.add(lblCondicin);
        
        tfCajaID = new JTextField();
        tfCajaID.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			findCajaById();
				} 
        	}
        	@Override
        	public void keyTyped(KeyEvent e) {
        		Util.validateNumero(e);
        	}
        });
        tfCajaID.setBounds(151, 6, 137, 30);
        pnlCliente.add(tfCajaID);
        
        btnGuardar = new JButton("CONFIRMAR");
        btnGuardar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addMovimientoCaja();
				}
        	}
        });
        btnGuardar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		addMovimientoCaja();
        	}
        });
        btnGuardar.setBounds(110, 90, 117, 40);
        pnlCliente.add(btnGuardar);
        
        JButton btnCancelar = new JButton("ESC / CANCELAR");
        btnCancelar.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		clearForm();
        		dispose();
        	}
        });
        btnCancelar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clearForm();
        		dispose();
        	}
        });
        btnCancelar.setBounds(239, 90, 117, 40);
        pnlCliente.add(btnCancelar);
        
        tfCaja = new JTextField();
        tfCaja.setEditable(false);
        tfCaja.setBounds(151, 48, 262, 30);
        pnlCliente.add(tfCaja);
        
        JLabel lblCaja = new JLabel("CAJA:");
        lblCaja.setBounds(53, 48, 95, 30);
        pnlCliente.add(lblCaja);
    }
    
    public int getTipoMov() {
		return tipoMov;
	}
    
    public void setTipoMov(int tipoMov) {
		this.tipoMov = tipoMov;
	}
    
    private void findCajaById() {
    	Long cajaId = tfCajaID.getText().isEmpty() ? 0 : Long.valueOf(tfCajaID.getText());
    	Optional<Caja> caja = cajaService.findById(cajaId);
    	
    	if (caja.isPresent()) {
    		tfCajaID.setEnabled(false);
			tfCaja.setText(caja.get().getNombre());
			btnGuardar.requestFocus();
		} else {
			Notifications.showAlert("No existe caja con el codigo Informado.!");
		}
    }
    
    private void addMovimientoCaja() {
    	Long cajaId = tfCajaID.getText().isEmpty() ? 0 : Long.valueOf(tfCajaID.getText());
    	Date fecha = new Date();
    	String situacion = "PAGADO";
    	
    	Optional<AperturaCierreCaja> aperturaCierreCaja = movService.findByCajaAndFechaApertura(new Caja(cajaId), fecha);
    	AperturaCierreCaja aperCierre = null;
    	
    	if (getTipoMov() == 1) { //APERTURA
    		if (!aperturaCierreCaja.isPresent()) { 
    			Optional<AperturaCierreCaja> apertura = movService.getInfoAperturaCierreCaja();
    			aperCierre = new AperturaCierreCaja();
    			
    			//refactorar
    			if (apertura.isPresent()) {
    				AperturaCierreCaja ap = apertura.get();
    				aperCierre.setMontoApertura(ap.getMontoCierre() != null ? ap.getMontoCierre():0);
    				aperCierre.setMontoAperturaM1(ap.getMontoCierreM1() != null ? ap.getMontoCierreM1():0);
    				aperCierre.setMontoAperturaM2(ap.getMontoCierreM2() != null ? ap.getMontoCierreM2():0);
    				aperCierre.setMontoAperturaM3(ap.getMontoCierreM3() != null ? ap.getMontoCierreM3():0);
    				aperCierre.setMontoAperturaM4(ap.getMontoCierreM4() != null ? ap.getMontoCierreM4():0);
    				aperCierre.setMontoAperturaM5(ap.getMontoCierreM5() != null ? ap.getMontoCierreM5():0);
				} else {
					aperCierre.setMontoApertura(0.0D);
    				aperCierre.setMontoAperturaM1(0.0D);
    				aperCierre.setMontoAperturaM2(0.0D);
    				aperCierre.setMontoAperturaM3(0.0D);
    				aperCierre.setMontoAperturaM4(0.0D);
    				aperCierre.setMontoAperturaM5(0.0D);
				}
    			
    			aperCierre.setUsuario(GlobalVars.USER_ID);
        		aperCierre.setCaja(new Caja(cajaId));
        		aperCierre.setHoraApertura(fecha);
        		aperCierre.setFechaApertura(fecha);
        		aperCierre.setActivo(1);

        		movService.save(aperCierre);
        		
        		Notifications.showAlert("Se abrio correctamente la CAJA");
    		} else {
    			Date apert = aperturaCierreCaja.get().getFechaApertura() != null ? aperturaCierreCaja.get().getFechaApertura(): new Date(); 
    			Notifications.showAlert("La CAJA ya se encuentra abierta en la fecha: " + Fechas.formatoDDMMAAAA(apert));
    		}
		} else { // CIERRE
			if (aperturaCierreCaja.isPresent()) {
				Optional<MovimientoCaja> movCaja = cajaMovService.getTotalsMovCaja(new Caja(cajaId), fecha, situacion);
				
				if (movCaja.isPresent()) {	
					MovimientoCaja mCaja = movCaja.get();
					
					aperCierre = aperturaCierreCaja.get();
					aperCierre.setHoraCierre(fecha);
					aperCierre.setFechaCierre(fecha);
					
					//verificar los valores del cierre
					aperCierre.setMontoCierre(mCaja.getNotaValor() != null ? mCaja.getNotaValor():0);
					aperCierre.setMontoCierreM1(mCaja.getValorM01() != null ? mCaja.getValorM01():0);
					aperCierre.setMontoCierreM2(mCaja.getValorM02() != null ? mCaja.getValorM02():0);
					aperCierre.setMontoCierreM3(mCaja.getValorM03() != null ? mCaja.getValorM03():0);
					aperCierre.setMontoCierreM4(mCaja.getValorM04() != null ? mCaja.getValorM04():0);
					aperCierre.setMontoCierreM5(mCaja.getValorM05() != null ? mCaja.getValorM04():0);
					
					movService.save(aperCierre);
					
					Notifications.showAlert("Se cerro correctamente la CAJA.!");
				} else {
					Notifications.showAlert("No se puede cerrar la caja sin movimiento.!");
				}
			} else {
				Notifications.showAlert("CAJA no esta abierta.");
			}
		}
    }
    
    private void clearForm() {
    	tfCajaID.setText("");
    	tfCaja.setText("");
    	tfCajaID.setEnabled(true);
    }
    
}