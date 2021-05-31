package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.MovimientoIngreso;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class MovimientoIngresoTableModel extends DefaultTableModel<MovimientoIngreso> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "ID.", "FECHA", "CAJA", "DOCUMENTO", "ENTIDAD"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	MovimientoIngreso item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getMinNumero();
            case 1:
                return item.getFecha();
            case 2:
                return item.getMinCaja();
            case 3:
                return item.getMinDocumento();
            case 4:
                return item.getMinEntidad();    
            default:
                return "";
        }
    }
}
