package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Egreso;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class EgresoTableModel extends DefaultTableModel<Egreso> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "CODIGO", "DESCRIPCION", "ABREVIATURA" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Egreso item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getId();
            case 1:
            	return item.getEgrDescripcion();
            case 2:
            	return item.getEgrAbreviatura();
            default:
                return "";
        }
    }
}
