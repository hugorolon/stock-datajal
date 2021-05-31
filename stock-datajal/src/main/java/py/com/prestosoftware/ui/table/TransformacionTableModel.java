package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.TransformacionProductoDetalle;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class TransformacionTableModel extends DefaultTableModel<TransformacionProductoDetalle> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "CODIGO", "DESCRIPCION", "CANTIDAD" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	TransformacionProductoDetalle item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getProductoDestino().getId();
            case 1:
            	return item.getProductoDestino().getDescripcion();
            case 2:
            	return item.getCantidad();
            default:
                return "";
        }
    }
}
