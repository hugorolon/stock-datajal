package py.com.prestosoftware.ui.helpers;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class CellRendererTextFieldDecimalMoneda extends JTextField implements TableCellRenderer {
	private static final long serialVersionUID = 5683986241472012383L;
	
	private JComponent component = new JTextField();

    /** Constructor de clase */
    public CellRendererTextFieldDecimalMoneda() {
        setOpaque(true);
    }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	  ((JTextField) component).setHorizontalAlignment(JTextField.RIGHT);//si se quiere universal
	  String texto = String.valueOf(value);
      ( (JTextField) component).setText(texto);
	  return ( (JTextField) component);
  }

}
