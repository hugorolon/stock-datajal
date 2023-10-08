package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.MovimientoCaja;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class NotaLanzadaTableModel extends DefaultTableModel<MovimientoCaja> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[]{ "OPERACIÓN", "DOCUMENTO", "VALOR", "REFERENCIA O NOMBRE.", "SITUACIÓN" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	MovimientoCaja item = entities.get(rowIndex);

        switch (columnIndex) {
            case 0:
            	return item.getPlanCuentaId()==1?"VENTAS":item.getPlanCuentaId()==2?"COMPRAS":item.getPlanCuentaId()==3?"COBRO A CLIENTES":item.getPlanCuentaId()==4?"DEVOLUCION DE CLIENTES":item.getPlanCuentaId()==5?"DEVOLUCIÓN A PROVEEDORES":"PAGO A PROVEEDORES";
            case 1:	
                return item.getNotaNro();
            case 2:
                return FormatearValor.doubleAString(item.getNotaValor());
            case 3:
                return item.getNotaReferencia();
            case 4:
                return item.getSituacion();
                
            default:
                return "";
        }
    }
}
