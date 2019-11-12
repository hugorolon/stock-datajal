package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Pedido;

import java.util.Optional;

@Component
public class PedidoValidator extends ValidationSupport implements Validator<Pedido> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Pedido p) {
        if (isNullOrEmptyString(p.getSituacion()) ||
                isNullValue(p.getProveedor().getId()) ||
                isNullValue(p.getFecha()) ||
                isNullOrEmptyString(p.getCondicion() + "") ||
                isEmptyList(p.getItems())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}