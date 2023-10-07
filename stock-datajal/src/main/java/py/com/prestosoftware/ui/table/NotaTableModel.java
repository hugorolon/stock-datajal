package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.viewmodel.Nota;

@Component
public class NotaTableModel extends DefaultTableModel<Nota> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "OPERACION", "DOCUMENTO", "REF.", "VALOR"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Nota item = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return item.getNota();
            case 1:
                return item.getNotaNro();
            case 2:
                return item.getNotaRef();
            case 3:
                return (Object)item.getNotaValor();
            default:
                return "";
        }
    }
}
