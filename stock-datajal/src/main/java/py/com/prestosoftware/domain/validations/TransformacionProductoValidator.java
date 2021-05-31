package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.TransformacionProducto;
import java.util.Optional;

@Component
public class TransformacionProductoValidator extends ValidationSupport implements Validator<TransformacionProducto> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(TransformacionProducto t) {
        if (isNullOrEmptyString(t.getSituacion().toString()) ||
            isNullValue(t.getDepositoOrigen().getId()) ||
            isNullValue(t.getDepositoDestino().getId()) ||
            isNullValue(t.getFecha()) ||
            isEmptyList(t.getItems())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}
