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
        return new String[] {"COD", "DESCRIPCION", "REF", "SALDO", "BLOQ.", "SAL. PEND.", "ENT. PEND." };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto product = entities.get(rowIndex);
        
        Double dep01 = product.getDepO1() != null ? product.getDepO1():0;
    	Double dep02 = product.getDepO2() != null ? product.getDepO2():0;
    	Double dep03 = product.getDepO3() != null ? product.getDepO3():0;
    	Double dep04 = product.getDepO4() != null ? product.getDepO4():0;
    	Double dep05 = product.getDepO5() != null ? product.getDepO5():0;  
    	
    	Double totalDep = dep01 + dep02 + dep03 + dep04 + dep05;
        
        Double depBloq01 = product.getDepO1Bloq() != null ? product.getDepO1Bloq():0;
    	Double depBloq02 = product.getDepO2Bloq() != null ? product.getDepO2Bloq():0;
    	Double depBloq03 = product.getDepO3Bloq() != null ? product.getDepO3Bloq():0;
    	Double depBloq04 = product.getDepO4Bloq() != null ? product.getDepO4Bloq():0;
    	Double depBloq05 = product.getDepO5Bloq() != null ? product.getDepO5Bloq():0;
    	
    	Double totalDepBloq = depBloq01 + depBloq02 + depBloq03 + depBloq04 + depBloq05;
    	
    	Double salPend = product.getSalidaPend() != null ? product.getSalidaPend():0;
    	Double entPend = product.getEntPendiente() != null ? product.getEntPendiente():0;

        switch (columnIndex) {
            case 0:
                return product.getId();
            case 1:
                return product.getDescripcion();
            case 2:
                return product.getReferencia();
            case 3:      	
                return (Object) FormatearValor.doubleAString(totalDep - (totalDepBloq + salPend));
            case 4:
                return (Object) FormatearValor.doubleAString(totalDepBloq);
            case 5:
                return (Object) FormatearValor.doubleAString(salPend);
            case 6:
                return (Object) FormatearValor.doubleAString(entPend);
            default:
                return "";
        }
    }
}
