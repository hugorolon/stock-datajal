package py.com.prestosoftware.ui.helpers;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class CellTextFieldSeleccion extends DefaultCellEditor implements TableCellRenderer  {
	private static final long serialVersionUID = 3562965297268488880L;
	private JComponent component = new JTextField();    
    private String value = ""; // valor de la celda
    
    /** Constructor de clase */
    public CellTextFieldSeleccion() {
        super( new JTextField() );
    }

    /** retorna valor de celda */
    @Override
    public Object getCellEditorValue() {
    	if(!((JTextField) component).getText().equals("")){
//			VariablesGlobales.esSeleccionado=true;
		}
        return ((JTextField)component).getText();        
    }

    /** Segun el valor de la celda selecciona/deseleciona el JCheckBox */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        String  texto = String.valueOf(value);
        ((JTextField) component).setText(texto);
        ((JTextField) component).setHorizontalAlignment(JTextField.CENTER);
        ((JTextField) component).addKeyListener(new java.awt.event.KeyAdapter() {   
			public void keyReleased(java.awt.event.KeyEvent e) {    
				if(!((JTextField) component).getText().equals("")){
//					VariablesGlobales.esSeleccionado=true;
        		}else{
//        			VariablesGlobales.esSeleccionado=false;
        		}
			}
		});
        ((JTextField) component).addFocusListener(new java.awt.event.FocusAdapter() {   
			public void focusGained(java.awt.event.FocusEvent e) {
				((JTextField) component).selectAll();
			}
			public void focusLost(java.awt.event.FocusEvent e) {
			}
		});
        return ((JTextField) component);     
    }

    /** cuando termina la manipulacion de la celda */
    @Override
    public boolean stopCellEditing() { 
        value = String.valueOf(getCellEditorValue());
        if(!value.equals("")){//al salir escribe la X
        	if(value.equals(".")){
//        		VariablesGlobales.salirGrilla=true;
        		value="";
        	}else{
//        		VariablesGlobales.salirGrilla=false;
        		value="X";
        	}
        }
        ((JTextField)component).setText(value);
        return super.stopCellEditing();
    }

    /** retorna componente */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	if (value == null){
             return null; 
         } 
    	return ((JTextField) component);
    }
}
