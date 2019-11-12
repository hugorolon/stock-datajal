package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.CondicionPago;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CondicionPagoTableModel extends DefaultTableModel<CondicionPago> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{ "Nombre", "Cant. Dia", "Activo" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	CondicionPago c = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return c.getNombre();
            case 1:
                return c.getCantDia();
            case 2:
                return c.getActivo();
            default:
                return "";
        }
    }
}
