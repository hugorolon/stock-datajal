package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Presupuesto;

import java.util.Optional;

@Component
public class PresupuestoValidator extends ValidationSupport implements Validator<Presupuesto> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Presupuesto p) {
        if (isNullValue(p.getCliente().getId()) ||
            isNullValue(p.getVendedor().getId()) ||
            isEmptyList(p.getItems())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}
