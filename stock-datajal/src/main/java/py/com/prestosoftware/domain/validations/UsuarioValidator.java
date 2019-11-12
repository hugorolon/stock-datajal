package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;
import py.com.prestosoftware.data.models.Usuario;
import java.util.Optional;

@Component
public class UsuarioValidator extends ValidationSupport implements Validator<Usuario> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(Usuario categoria) {
        if (isNullOrEmptyString(categoria.getUsuario())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        
        return Optional.empty();
    }

}
