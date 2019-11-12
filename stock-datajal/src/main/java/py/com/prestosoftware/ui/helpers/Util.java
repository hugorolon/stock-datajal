package py.com.prestosoftware.ui.helpers;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;

import py.com.prestosoftware.util.Notifications;

public class Util {

	public static void validateNumero(KeyEvent e) {
		char a=e.getKeyChar();
		if (!(a>=KeyEvent.VK_0 && a<=KeyEvent.VK_9 && a != KeyEvent.VK_COMMA) && a != '.' && a != '(' && a != ')' ) {
			e.consume();								
		}		
	}
	
	public static void numeroYComaDecimal(KeyEvent e) {
		char a=e.getKeyChar();
		if (!(a>=KeyEvent.VK_0 && a<=KeyEvent.VK_9) && a!=',' && a!='.') {
			e.consume();								
		}		
	}
	
	public static void goTo(JTextField to, JTextField go) {
		to.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        			go.requestFocus();
        		}
        	}
        });	
	}
	
	public static void ocultarColumna(JTable table, Integer columna) {
		table.getColumnModel().getColumn(columna).setMaxWidth(0);
		table.getColumnModel().getColumn(columna).setMinWidth(0);
		table.getTableHeader().getColumnModel().getColumn(columna).setMaxWidth(0);
		table.getTableHeader().getColumnModel().getColumn(columna).setMinWidth(0);
	}
	
	public static void isNumber(JComponent to) {
		to.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyTyped(KeyEvent e) {
        		validateNumero(e);
        	}
        });	
	}
	
	public static boolean validateEmpty(JTextField txt, String msg) {
		boolean result = true;
		
		if (txt.getText().isEmpty()) {
			Notifications.showAlert(msg);
			result = false;
		}
		
		return result;
	}
	
	public static void setupScreen(Window window) {
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = window.getSize(); 
		window.setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);
	}
	
	public static int calculateRucDV(String ruc) {
		return DigitoVerificador.calcular(ruc, 11);
	}
	
	//para asignar tipo de columnas
	public static void tipoColumna(JTable jTable, int cantColumn, int [] columns) {		
		for (int i = 0; i <= cantColumn; i++) {
			int j = columns[i];
	        jTable.getColumnModel().getColumn( j ).setCellEditor(new CellTextFieldDecimalMoneda());
	        jTable.getColumnModel().getColumn( j ).setCellRenderer(new CellRendererTextFieldSeleccion());
		}
	}
	
	// para asignar tamanhos de columnas
	public static void tamanhoTablaFijo(JTable jTable, int cantColumn, int [] columns, int [] Tamanhocolumns) {
		for (int i = 0; i <= cantColumn; i++) {
			int j = columns[i];
			
			int k = Tamanhocolumns[i];
			
	        jTable.getColumnModel().getColumn( j ).setMinWidth(k);
	        jTable.getColumnModel().getColumn( j ).setMaxWidth(k);
		}
	}

	public static void forzarFocoGrilla(JTable jTable, int fila, int columna) {
		jTable.changeSelection(fila, columna, false, false);
        jTable.editCellAt(fila, columna);
        jTable.getEditorComponent().requestFocusInWindow();
	}
	
	public static void setAlturaFila(JTable jTable) {
		jTable.setRowHeight(20);
	}
	
	public static String puntoDecimalAComa(String texto) { 
		return texto.substring(0,texto.length()-1)+",";
	}
	
	public static void puntoDecimal(KeyEvent e){
		if (e.getKeyCode()==110) {
			GlobalVars.esPuntoDecimal=true;
		}
	}
	
	public static String cantidadDosDecimales(String valorRecibido,Integer decimalesPermitidos){
		if (!valorRecibido.equals("")) {
			BigDecimal monto=BigDecimal.ZERO;
			monto=FormatearValor.stringABigDecimal(valorRecibido);
			monto=monto.setScale(decimalesPermitidos,RoundingMode.HALF_UP);
			valorRecibido=FormatearValor.bigDecimalAStringDosDecimales(monto);
		}
		return valorRecibido;
	}
	
	public static String cantidadTresDecimales(String valorRecibido,Integer decimalesPermitidos){
		if(!valorRecibido.equals("")){
			BigDecimal monto=BigDecimal.ZERO;
			monto=FormatearValor.stringABigDecimal(valorRecibido);
			monto=monto.setScale(decimalesPermitidos,RoundingMode.HALF_UP);
			valorRecibido=FormatearValor.bigDecimalAStringTresDecimales(monto);
		}
		return valorRecibido;
	}
	
}
