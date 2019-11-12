package py.com.prestosoftware.domain.validations;

import java.util.Optional;

interface Validator <K> {

    Optional<ValidationError> validate(K k);

}
