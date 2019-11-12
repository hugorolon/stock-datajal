package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Deposito;

import java.util.Optional;

@Component
public class DepositoValidator extends ValidationSupport implements Validator<Deposito> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Deposito dep) {
        if (isNullOrEmptyString(dep.getNombre())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        
        return Optional.empty();
    }

}
