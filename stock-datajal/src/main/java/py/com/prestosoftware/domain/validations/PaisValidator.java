package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.Pais;

import java.util.Optional;

@Component
public class PaisValidator extends ValidationSupport implements Validator<Pais> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Pais pais) {
        if (isNullOrEmptyString(pais.getNombre())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        
        return Optional.empty();
    }

}
