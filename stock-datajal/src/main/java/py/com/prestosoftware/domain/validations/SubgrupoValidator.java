package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Subgrupo;
import java.util.Optional;

@Component
public class SubgrupoValidator extends ValidationSupport implements Validator<Subgrupo> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Subgrupo grupo) {
        if (isNullOrEmptyString(grupo.getNombre())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        
        return Optional.empty();
    }

}
