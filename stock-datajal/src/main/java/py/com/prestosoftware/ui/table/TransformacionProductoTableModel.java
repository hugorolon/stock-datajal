package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.TransformacionProducto;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class TransformacionProductoTableModel extends DefaultTableModel<TransformacionProducto> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "ID", "FECHA", "PRODUCTO ORIGEN", "CANTIDAD", "SITUACION" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	TransformacionProducto tp = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return tp.getId();
            case 1:
                return tp.getFecha();
            case 2:
            	return tp.getProductoOrigen().getId()+" - "+tp.getProductoOrigen().getDescripcion();
            case 3:
            	return tp.getCantidad();	
            case 4:
            	return tp.getSituacion() == 1 ? "VIGENTE":"ELIMINADO";
            default:
                return "";
        }
    }
}
