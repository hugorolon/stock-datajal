package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Devolucion;

import java.util.Optional;

@Component
public class DevolucionValidator extends ValidationSupport implements Validator<Devolucion> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Devolucion d) {
        if (isNullOrEmptyString(d.getComprobante()) ||
                isNullValue(d.getTipo()) ||
                isNullOrEmptyString(d.getCredito() + "") ||
                isEmptyList(d.getItems())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}
