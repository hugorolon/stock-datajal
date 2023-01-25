package py.com.prestosoftware.ui.table;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.MovimientoIngreso;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class MovimientoIngresoTableModel extends DefaultTableModel<Object[]> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "ID.", "FECHA", "CAJA", "DOCUMENTO", "ENTIDAD", "TOTAL"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Object[] item = entities.get(rowIndex);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        switch (columnIndex) {
            case 0:
                return item[0].toString();
            case 1:
                return format.format(item[1]);
            case 2:
                return item[2].toString();
            case 3:
                return item[3].toString();
            case 4:
                return item[4].toString();
            case 5:
                return item[5];    
            default:
                return "";
        }
    }
}
