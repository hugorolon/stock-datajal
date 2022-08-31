package py.com.prestosoftware.ui.table;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.MovimientoEgreso;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class MovimientoEgresoTableModel extends DefaultTableModel<MovimientoEgreso> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "ID.", "FECHA", "CAJA", "DOCUMENTO", "ENTIDAD"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	MovimientoEgreso item = entities.get(rowIndex);
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        
        switch (columnIndex) {
            case 0:
                return item.getMegNumero();
            case 1:
            	return sdf.format(item.getFecha());
            case 2:
                return item.getMegCaja();
            case 3:
                return item.getMegDocumento();
            case 4:
                return item.getMegEntidad();    
            default:
                return "";
        }
    }
}
