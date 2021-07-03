package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;

import java.util.Optional;

@Component
public class ProductoValidator extends ValidationSupport implements Validator<Producto> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campo Obligatorio";

    @Override
    public Optional<ValidationError> validate(Producto product) {
    	String msg="";
        if (isNullOrEmptyString(product.getDescripcion())) {
        	msg="Descripción de producto es ";
        	return Optional.of(new ValidationError(msg+REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        else if (isNullOrEmptyString(product.getReferencia())) {
        	msg="Referencia de producto es ";
        	return Optional.of(new ValidationError(msg+REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }else if (isNullOrEmptyString(product.getDescripcionFiscal())) {
        	msg="Descripción Fiscal es ";
        	return Optional.of(new ValidationError(msg+REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }else if (isNullOrEmptyString(String.valueOf(product.getPeso()))) {
        	msg="Peso es ";
        	return Optional.of(new ValidationError(msg+REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }else if (isNullOrEmptyString(String.valueOf(product.getCantidadPorCaja()))){
        	msg="Cantidad por Caja es ";
        	return Optional.of(new ValidationError(msg+REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
                 
            
        

        return Optional.empty();
    }

}
