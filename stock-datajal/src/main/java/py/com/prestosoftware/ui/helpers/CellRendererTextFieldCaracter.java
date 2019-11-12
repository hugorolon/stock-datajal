package py.com.prestosoftware.ui.helpers;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class CellRendererTextFieldCaracter extends JTextField implements TableCellRenderer {
	private static final long serialVersionUID = 5683986241472012383L;
    /** Constructor de clase */
    public CellRendererTextFieldCaracter() {
        setOpaque(true);
    }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	  JTextField tfFecha=new JTextField();
	  tfFecha.setHorizontalAlignment(JTextField.LEFT);//si se quiere universal
	  String texto = String.valueOf(value);
	  tfFecha.setText(texto);
	  return tfFecha;
  }
}
