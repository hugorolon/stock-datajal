package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Producto;

import java.util.Optional;

@Component
public class ProductoValidator extends ValidationSupport implements Validator<Producto> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Producto product) {
        if (isNullOrEmptyString(product.getDescripcion()) ||
                isNullOrEmptyString(product.getReferencia()) ||
                isNullOrEmptyString(product.getDescripcionFiscal()) ||
                isNullOrEmptyString(String.valueOf(product.getPeso())) ||
                isNullOrEmptyString(String.valueOf(product.getCantidadPorCaja()))) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}
