package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.AjusteStock;

import java.util.Optional;

@Component
public class AjusteStockValidator extends ValidationSupport implements Validator<AjusteStock> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(AjusteStock t) {
        if (isNullValue(t.getDeposito().getId()) ||
            isNullValue(t.getUsuario().getId()) ||
            isNullValue(t.getFecha()) ||
            isEmptyList(t.getItems())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}
