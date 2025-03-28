package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Moneda;

import java.util.Optional;

@Component
public class MonedaValidator extends ValidationSupport implements Validator<Moneda> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Moneda moneda) {
        if (isNullOrEmptyString(moneda.getNombre())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        
        return Optional.empty();
    }

}
