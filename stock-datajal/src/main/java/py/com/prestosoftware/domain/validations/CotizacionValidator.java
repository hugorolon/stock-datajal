package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Cotizacion;

import java.util.Optional;

@Component
public class CotizacionValidator extends ValidationSupport implements Validator<Cotizacion> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Cotizacion cot) {
        if (isNullOrEmptyString(String.valueOf(cot.getValorCompra()))) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        
        return Optional.empty();
    }

}
