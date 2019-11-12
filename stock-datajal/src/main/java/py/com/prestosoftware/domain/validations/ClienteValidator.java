package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Cliente;

import java.util.Optional;

@Component
public class ClienteValidator extends ValidationSupport implements Validator<Cliente> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Cliente client) {
        if (	isNullOrEmptyString(client.getNombre()) ||
                isNullOrEmptyString(client.getRazonSocial()) ||
                isNullOrEmptyString(client.getCiruc()) || 
                isNullValue(client.getEmpresa()) ||
                isNullOrEmptyString(client.getDireccion())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }

        return Optional.empty();
    }

}
