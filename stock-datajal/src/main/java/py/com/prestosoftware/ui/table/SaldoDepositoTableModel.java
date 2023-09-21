package py.com.prestosoftware.ui.table;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;
import py.com.prestosoftware.ui.helpers.FormatearValor;
import py.com.prestosoftware.ui.shared.DefaultTableModel;

@Component
public class SaldoDepositoTableModel extends DefaultTableModel<Producto> {

	private static final long serialVersionUID = 1L;
	
	@Override
    public String[] getColumnLabels() {
        return new String[] {"COD", "DESCRIPCION", "REF", "SALDO", "BLOQ." };
//        		"DEP 01", "DEP 02", "DEP 03","DEP 04", "DEP 05" };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto product = entities.get(rowIndex);
        
        Double dep01 = product.getDepO1() != null ? product.getDepO1():0;
    	Double dep02 = product.getDepO2() != null ? product.getDepO2():0;
    	
    	Double totalDep = dep01 + dep02 ;
        
        Double depBloq01 = product.getDepO1Bloq() != null ? product.getDepO1Bloq():0;
    	Double depBloq02 = product.getDepO2Bloq() != null ? product.getDepO2Bloq():0;
    	
    	Double totalDepBloq = depBloq01 + depBloq02 ;

        switch (columnIndex) {
            case 0:
                return product.getId();
            case 1:
                return product.getDescripcion();
            case 2:
                return product.getReferencia();
            case 3:      	
                return (Object) FormatearValor.doubleAString(totalDep - totalDepBloq);
            case 4:
                return (Object) FormatearValor.doubleAString(totalDepBloq);
//            case 5:
//                return (Object) FormatearValor.doubleAString(product.getDepO1() != null ? product.getDepO1():0);
//            case 6:
//                return (Object) FormatearValor.doubleAString(product.getDepO2() != null ? product.getDepO2():0);
//            case 7:
//                return (Object) FormatearValor.doubleAString(product.getDepO3() != null ? product.getDepO3():0);
//            case 8:
//                return (Object) FormatearValor.doubleAString(product.getDepO4() != null ? product.getDepO4():0);
//            case 9:
//                return (Object) FormatearValor.doubleAString(product.getDepO5() != null ? product.getDepO5():0);
            default:
                return "";
        }
    }
}
