package py.com.prestosoftware.ui.table;

import java.util.Date;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.ui.shared.DefaultTableModel;
import py.com.prestosoftware.ui.viewmodel.DetalleAPagarProveedorView;
import py.com.prestosoftware.ui.viewmodel.DetalleCobroClienteView;

@Component
public class DetalleCuentaProveedorTableModel extends DefaultTableModel<DetalleAPagarProveedorView> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] {"Operación", "Boleta", "Fecha", "Nro. Documento", "Vencimiento","Monto Cuota", "Pagado", "Saldo","Cobrar"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	DetalleAPagarProveedorView item = entities.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return item.getNombre_ingreso();
            case 1:
                return item.getCap_boleta();    
            case 2:
                return item.getCap_fecha1();
            case 3:
                return item.getIcp_documento();    
            case 4:
                return item.getIcp_vencimiento1();
            case 5:
                return item.getCap_monto1();
            case 6:
                return item.getPagado();    
            case 7:
                return item.getIcp_monto1();
            case 8:
                return item.getCobro();    
            default:
            	return "";
        }
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        DetalleAPagarProveedorView row = entities.get(rowIndex);
        if(0 == columnIndex) {
            row.setNombre_ingreso((String) aValue);
        }
        else if(1 == columnIndex) {
            row.setCap_boleta((String) aValue);
        }
        else if(2 == columnIndex) {
            row.setCap_fecha1((Date) aValue);
        }
        else if(3 == columnIndex) {
            row.setIcp_documento((String) aValue);
        }
        else if(4 == columnIndex) {
            row.setIcp_vencimiento1((Date) aValue);
        }
        else if(5 == columnIndex) {
            row.setCap_monto1((Double) aValue);
        }
        else if(6 == columnIndex) {
            row.setPagado((Double) aValue);
        }
        else if(7 == columnIndex) {
            row.setIcp_monto1((Double) aValue);
        }
        else if(8 == columnIndex) {
            row.setCobro(Double.valueOf(aValue.toString().equalsIgnoreCase("")?"0":aValue.toString()));
        }
    }
    
}
