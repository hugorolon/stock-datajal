package py.com.prestosoftware.ui.main;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import java.awt.Font;
import javax.swing.SwingConstants;

//import negocio.entidad.ConfiguracionGeneralEntidad;
//import negocio.entidad.VentaEntidad;
//import negocio.sesion.ConfiguracionGeneralSesion;
//import negocio.sesion.VentaSesion;
//import negocio.utilidad.CrearArchivo;
//import negocio.utilidad.DetallesMaquina;
//import negocio.utilidad.EncrypterPassPhrase;
//import negocio.utilidad.Fechas;
//import negocio.utilidad.FormatearValor;
//import negocio.utilidad.LeerArchivo;
//import negocio.utilidad.RegistroSO;
//import negocio.utilidad.Temas;
//import negocio.utilidad.VariablesGlobales;

public class SplashPanel extends JFrame{

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel jLabel = null;

	private JProgressBar jProgressBar = null;
	
	private Timer timer=null; 
	
//	private static String horas=null;
	
	private Integer contadorTimer=0;

	private JLabel jLabel1 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setBounds(new Rectangle(2, 269, 503, 27));
			jProgressBar.setFont(new Font("Tahoma", Font.BOLD, 12));
	        jProgressBar.setForeground(new java.awt.Color(102, 189, 255));
			jProgressBar.setDoubleBuffered(true);
	        jProgressBar.setStringPainted(true);
		}
		return jProgressBar;
	}


//	private static void abrirSistema() {
//		try {
//			//DatosConfiguracionSesion.cargar();
//			String tema=LeerArchivo.temaSeleccionado(System.getProperty("user.dir").replace("\\", "/")+"/"+VariablesGlobales.temaArchivo);
//			if(tema!=null){
//				Temas.apariencia(tema);
//			}
//			new Inicio().setVisible(true);
//		} catch (Exception e) {
//		}
//	}

	public SplashPanel() {
		initialize();
	}

	private void initialize() {
		this.setSize(521, 336);
		this.setContentPane(getJContentPane());
		this.setUndecorated(true);
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = this.getSize(); 
		this.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
		eventoTimer();
	}


	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel12 = new JLabel();
			jLabel12.setBounds(new Rectangle(129, 175, 256, 93));
			//jLabel12.setIcon(new ImageIcon(getClass().getResource("/presentacion/imagenes/mysql_logo.png")));
			jLabel12.setText("");
			jLabel12.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel11 = new JLabel();
			jLabel11.setBounds(new Rectangle(383, 175, 122, 93));
			//jLabel11.setIcon(new ImageIcon(getClass().getResource("/presentacion/imagenes/servicios2.png")));
			jLabel11.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel11.setText("");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(2, 175, 125, 93));
			//jLabel1.setIcon(new ImageIcon(getClass().getResource("/presentacion/imagenes/java.png")));
			jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel1.setText("");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(1, 1, 520, 172));
			//jLabel.setIcon(new ImageIcon(getClass().getResource("/presentacion/imagenes/logo_compy.png")));
			jLabel.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJProgressBar(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel11, null);
			jContentPane.add(jLabel12, null);
		}
		return jContentPane;
	}

	private void eventoTimer() {
		timer = new Timer(5,new ActionListener()
        {
            public void actionPerformed(ActionEvent o)
            {
                //contador
                contadorTimer+=1;
                //al progresbar se le pasa como parametro el contador
                jProgressBar.setValue(contadorTimer);
                //cuando llega a 100 pone un stop
                terminar();
            }
        });
        timer.start();
	}
	
    public void terminar() {
        if (jProgressBar.getPercentComplete()==1.0) {
            timer.stop();   
            this.dispose();
            //new LoginForm().setVisible(true);	
//            if (horas==null) {
//            	new LoginForm().setVisible(true);	
//            } else {
//            	new LoginForm(horas).setVisible(true);
//            }
        }        
    }
    
//    private static void verificarPeriodoPrueba() {
//			try {
//				String cantidadMinutosArchivo=LeerArchivo.cantidadMinutos(System.getProperty("user.dir").replace("\\", "/")+"/"+VariablesGlobales.tiempoArchivo);
//				if(!cantidadMinutosArchivo.equals("")){
//					Double cantidadHorasArchivo=Integer.parseInt(cantidadMinutosArchivo) / 60d;
//					if(VariablesGlobales.direccionConfiguracionBD!=null){
//						//DatosConfiguracionSesion.cargar();
//						String cantidadMinutosBD=ConfiguracionGeneralSesion.consultarCantidadMinutos();
//						if(cantidadMinutosBD==null){
//							cantidadMinutosBD="KOBAAfZr2UE=";//CERO
//						}
//						cantidadMinutosBD=EncrypterPassPhrase.UsingPassPhraseDecrypted(cantidadMinutosBD);
//						Double cantidadHorasBD=Integer.valueOf(cantidadMinutosBD) / 60d;
//						if(cantidadHorasArchivo<cantidadHorasBD){
//							CrearArchivo.guardarCantidadMinutos(Integer.valueOf(cantidadMinutosBD));
//							cantidadMinutosArchivo=String.valueOf(cantidadMinutosBD);
//							cantidadHorasArchivo=cantidadHorasBD;
//						}
//					}
//					String sistemaOperativo=System.getProperty("os.name");
//					if(sistemaOperativo.startsWith("Windows")){
//						String cantidadMinutosSO=RegistroSO.leerRegistro();
//						if(cantidadMinutosSO!=null && !cantidadMinutosSO.equals("")){
//							int minutosSO=Integer.valueOf(EncrypterPassPhrase.UsingPassPhraseDecrypted(cantidadMinutosSO));
//							if(Integer.valueOf(cantidadMinutosArchivo)<minutosSO){
//								cantidadHorasArchivo=minutosSO / 60d;
//								CrearArchivo.guardarCantidadMinutos(minutosSO);
//							}
//						}
//					}
//					if(cantidadHorasArchivo<15){
//						horas=FormatearValor.doubleAString(15-cantidadHorasArchivo);
//					}else{
//						horas="0";
//					}
//					VariablesGlobales.cantidadHoras=cantidadHorasArchivo;
//					
//				}else if(VariablesGlobales.direccionConfiguracionBD!=null){
//						//DatosConfiguracionSesion.cargar();
//						String cantidadMinutosBD=ConfiguracionGeneralSesion.consultarCantidadMinutos();//cambiar por variable global
//						Integer minutosBD=0;
//						Double cantidadHorasBD=0d;
//						if(!cantidadMinutosBD.equals("")){
//							cantidadHorasBD=Integer.valueOf(EncrypterPassPhrase.UsingPassPhraseDecrypted(cantidadMinutosBD)) / 60d;
//							minutosBD=Integer.valueOf(EncrypterPassPhrase.UsingPassPhraseDecrypted(cantidadMinutosBD));
//						}
//						String sistemaOperativo=System.getProperty("os.name");
//						if(sistemaOperativo.startsWith("Windows")){
//							String cantidadMinutosSO=RegistroSO.leerRegistro();
//							if(cantidadMinutosSO!=null && !cantidadMinutosSO.equals("")){
//								Integer minutosSO=Integer.valueOf(EncrypterPassPhrase.UsingPassPhraseDecrypted(cantidadMinutosSO));
//								if(minutosBD<minutosSO){
//									cantidadHorasBD=minutosSO / 60d;
//									CrearArchivo.guardarCantidadMinutos(minutosSO);
//									ConfiguracionGeneralSesion.actualizarCantidadMinutos(cantidadMinutosSO);
//								}
//							}
//						}
//						VariablesGlobales.cantidadHoras=cantidadHorasBD;
//						if(VariablesGlobales.cantidadHoras<15){
//							horas=FormatearValor.doubleAString(15-VariablesGlobales.cantidadHoras);
//						}else{
//							horas="0";
//						}
//						
//				}else{
//					String sistemaOperativo=System.getProperty("os.name");
//					if(sistemaOperativo.startsWith("Windows")){
//						String cantidadMinutosSO=RegistroSO.leerRegistro();
//						if(cantidadMinutosSO!=null && !cantidadMinutosSO.equals("")){
//							Integer minutosSO=Integer.valueOf(EncrypterPassPhrase.UsingPassPhraseDecrypted(cantidadMinutosSO));
//							CrearArchivo.guardarCantidadMinutos(minutosSO);
//							VariablesGlobales.cantidadHoras=minutosSO / 60d;
//							if(VariablesGlobales.cantidadHoras<15){
//								horas=FormatearValor.doubleAString(15-VariablesGlobales.cantidadHoras);
//							}else{
//								horas="0";
//							}
//						}else{
//							CrearArchivo.guardarCantidadMinutos(0);
//							RegistroSO.guardar();
//							horas="15";
//						}
//					}else{
//						CrearArchivo.guardarCantidadMinutos(0);
//						horas="15";
//					}
//				}
//			} catch (NumberFormatException e) {
//				e.printStackTrace();
//			} catch (HeadlessException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	}
    
//	private static void cargarVariablesGlobales() {
//		String separador=System.getProperty("file.separator");
//		//carga los datos basicos del sistema
//		VariablesGlobales.direccionMac=DetallesMaquina.direccionMac();
//		VariablesGlobales.configuracionBDArchivo="ConfiguracionBD.ini";
//		VariablesGlobales.direccionConfiguracionBD=System.getProperty("user.dir")+separador+VariablesGlobales.configuracionBDArchivo;
//		VariablesGlobales.descripcionBd="mysql";
//		VariablesGlobales.driverBd="org.gjt.mm.mysql.Driver";
//		VariablesGlobales.serialArchivo="Serial.ini";
//		VariablesGlobales.tiempoArchivo="Tiempo.ini";
//		VariablesGlobales.temaArchivo="Tema.ini";
//		LeerArchivo.leerConfiguracionBD(VariablesGlobales.direccionConfiguracionBD);
//		try {
//			//consulta la configuracion general
//			ConfiguracionGeneralEntidad ent=ConfiguracionGeneralSesion.consultar();
//			if(ent!=null) {
//				VariablesGlobales.moraPermitidaCreditoDias=ent.getMoraPermitidaDias();
//				VariablesGlobales.cantidadMinutos=Integer.valueOf(EncrypterPassPhrase.UsingPassPhraseDecrypted(ent.getCantidadMinutos()));
//				VariablesGlobales.limiteDetalleVenta=ent.getLimiteDetalleVenta();
//				VariablesGlobales.limiteDetallePresupuesto=ent.getLimiteDetallePresupuesto();
//				VariablesGlobales.validezDiasPresupuesto=ent.getValidezDiasPresupuesto();
//				VariablesGlobales.limiteDetalleCobro=ent.getLimiteDetalleCobro();
//				VariablesGlobales.rutaBackup=ent.getRutaBackup();
//				VariablesGlobales.horaManhanaBackup=ent.getHoraManhanaBackup();
//				VariablesGlobales.horaTardeBackup=ent.getHoraTardeBackup();
//				VariablesGlobales.terminacionReporte=ent.getTerminacionReporte();
//			}
//			//consulta la ultima venta de la maquina local en el cual se ejecuta la aplicacion
//			VentaEntidad ent2=VentaSesion.consultarVentaPorMac(VariablesGlobales.direccionMac);
//			if(ent2!=null){
//				VariablesGlobales.sucursalCodigo=ent2.getComprobanteSucursal();
//				VariablesGlobales.puntoVenta=ent2.getComprobantePunto();
//			}
//		} catch (HeadlessException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
    
//	private static void consultarFechaBackup() {
//		try {
//			Date ultimaFechaBackup=ConfiguracionGeneralSesion.consultarFechaBackup();
//			Integer diferenciaDias=Fechas.diferenciaFechasDias(Fechas.formatoDDMMAAAA(ultimaFechaBackup), Fechas.formatoDDMMAAAA(new Date()));
//			if(diferenciaDias>=2){
//				JOptionPane.showMessageDialog(null, "La ultima copia de seguridad realizada con exito fue hace ("+diferenciaDias+") dias atras.\n" +
//						"Consulte con el administrador de sistemas");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) {
//		cargarVariablesGlobales();
//		if(DetallesMaquina.verificarSerial()==true){
//			consultarFechaBackup();
//			abrirSistema();
//		} else {
//			verificarPeriodoPrueba();
//			Reloj.start();
//			consultarFechaBackup();
//			abrirSistema();
//		}
	}
	
}
