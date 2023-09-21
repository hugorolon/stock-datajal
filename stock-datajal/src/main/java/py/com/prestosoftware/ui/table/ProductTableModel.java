package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class ProductTableModel extends DefaultTableModel<Producto> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] {"ID","CODIGO", "CODIGO SEC", "DESCRIPCION", "SALDO", "COSTO", "PRECIO A" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto product = entities.get(rowIndex);
        
        Double dep01 = product.getDepO1() != null ? product.getDepO1():0;
    	Double dep02 = product.getDepO2() != null ? product.getDepO2():0;
    	
    	Double totalDep = dep01 + dep02 ;
        
        Double depBloq01 = product.getDepO1Bloq() != null ? product.getDepO1Bloq():0;
    	Double depBloq02 = product.getDepO2Bloq() != null ? product.getDepO2Bloq():0;
    	
    	Double precioA = product.getPrecioVentaA() != null ? product.getPrecioVentaA():0;
    	//Double precioB = product.getPrecioVentaB() != null ? product.getPrecioVentaB():0;
    	Double precioC = product.getPrecioVentaC() != null ? product.getPrecioVentaC():0;
    	
    	Double totalDepBloq = depBloq01 + depBloq02 ;
    	
    	Double salPend = product.getSalidaPend() != null ? product.getSalidaPend():0;
    	Double precioCosto = product.getPrecioCosto() != null ? product.getPrecioCosto():0;
    	

        switch (columnIndex) {
            case 0:
                return product.getId();
            case 1:
                return product.getCodigo();    
            case 2:
                return product.getCodigoSec();        
            case 3:
                return product.getDescripcion();
            case 4:      	
                return (Object) FormatearValor.doubleAString(totalDep - (totalDepBloq + salPend));
            case 5:
                return (Object) FormatearValor.doubleAString(precioCosto);
            case 6:
                return (Object) FormatearValor.doubleAString(precioA);
            default:
                return "";
        }
    }
}
