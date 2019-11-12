package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Moneda;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class MonedaTableModel extends DefaultTableModel<Moneda> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "Codigo", "Sigla", "Cent. Plural", "Operacion", "Mascara", "Activo" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Moneda mon = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return mon.getId();
            case 1:
                return mon.getSigla();
//            case 2:
//                return mon.getCentPlural();
            case 3:
                return mon.getOperacion();
            case 4:
                return mon.getMascara();
            case 5:
                return mon.getActivo();
            default:
                return "";
        }
    }
}
