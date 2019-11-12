package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.TransferenciaProducto;
import java.util.Optional;

@Component
public class TransferenciaProductoValidator extends ValidationSupport implements Validator<TransferenciaProducto> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(TransferenciaProducto t) {
        if (isNullOrEmptyString(t.getComprobante()) ||
            isNullValue(t.getDepositoOrigen().getId()) ||
            isNullValue(t.getDepositoDestino().getId()) ||
            isNullValue(t.getFecha()) ||
            isEmptyList(t.getItems())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}
