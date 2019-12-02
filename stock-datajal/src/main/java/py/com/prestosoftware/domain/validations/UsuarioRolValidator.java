package py.com.prestosoftware.domain.validations;

import org.springframework.stereotype.Component;

import py.com.prestosoftware.data.models.UsuarioRol;

import java.util.Optional;

@Component
public class UsuarioRolValidator extends ValidationSupport implements Validator<UsuarioRol> {

    private static final String REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA = "Campos Obligatorios";

    @Override
    public Optional<ValidationError> validate(UsuarioRol categoria) {
        if (isNullOrEmptyString(categoria.getUsuario().getUsuario())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        if (isNullOrEmptyString(categoria.getRol().getNombre())) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        
        return Optional.empty();
    }

}
