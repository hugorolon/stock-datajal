package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Cotizacion;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CotPDVTableModel extends DefaultTableModel<Cotizacion> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{"SIGLA", "COMPRA", "VENTA"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Cotizacion cot = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return cot.getMoneda().getSigla();
            case 1:
                return (Object)FormatearValor.doubleAString(cot.getValorCompra());
            case 2:
                return (Object)FormatearValor.doubleAString(cot.getValorVenta());
                
            default:
                return "";
        }
    }
}
