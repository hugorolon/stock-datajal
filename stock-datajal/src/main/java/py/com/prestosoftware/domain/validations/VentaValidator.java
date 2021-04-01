package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Venta;
import java.util.Optional;

@Component
public class VentaValidator extends ValidationSupport implements Validator<Venta> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios o No tiene items cargados";

    @Override
    public Optional<ValidationError> validate(Venta venta) {
        if (isNullOrEmptyString(venta.getComprobante()) ||
                isNullValue(venta.getCliente().getId()) ||
                isNullValue(venta.getDeposito().getId()) ||
                isNullValue(venta.getVendedor().getId()) ||
                isNullOrEmptyString(venta.getCondicion() + "") ||
                isEmptyList(venta.getItems())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}
