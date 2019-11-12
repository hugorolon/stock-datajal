package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.CuentaCliente;
import py.com.prestosoftware.ui.helpers.Fechas;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class CuentaClienteTableModel extends DefaultTableModel<CuentaCliente> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] { "DOC.", "FECHA VENC.", "TOTAL NOTA", "TOTAL PAGADO", "SALDO", "VALOR A PAGAR" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {    	
    	CuentaCliente c = entities.get(rowIndex);
   
    	Double total 	= c.getValorTotal() != null ? c.getValorTotal():0;
    	Double pagado 	= c.getValorPagado() != null ? c.getValorPagado():0;

        switch (columnIndex) {
	        case 0:
	            return c.getDocumento();
            case 1:
                return Fechas.dateUtilAStringDDMMAAAA(c.getVencimiento());
            case 2:
                return total;
            case 3:
                return pagado;
            case 4:
                return total - pagado;
            case 5:
                return 0;     
        default:
            throw new IndexOutOfBoundsException();
        }
    }
}
