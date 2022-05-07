package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Proveedor;

import java.util.Optional;

@Component
public class ProveedorValidator extends ValidationSupport implements Validator<Proveedor> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Proveedor proveedor) {
        if (isNullOrEmptyString(proveedor.getNombre()) ||
                isNullOrEmptyString(proveedor.getRazonSocial()) ||
                isNullOrEmptyString(proveedor.getRuc()) ||
                isNullOrEmptyString(proveedor.getCelular()) ||
                isNullOrEmptyString(proveedor.getDireccion())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}
