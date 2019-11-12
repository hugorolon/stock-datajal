package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Impuesto;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ImpuestoTableModel extends DefaultTableModel<Impuesto> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{
                "Nombre", "Porcentaje", "Activo"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Impuesto impuesto = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return impuesto.getNombre();
            case 1:
                return impuesto.getPorcentaje();
            case 2:
                return impuesto.getActivo();
            default:
                return "";
        }
    }
}
