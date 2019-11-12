package py.com.prestosoftware.domain.validations;

import java.util.List;

import org.apache.logging.log4j.util.Strings;

public abstract class ValidationSupport {

    boolean isNullOrEmptyString(String value) {
        return Strings.isBlank(value);
    }

    boolean isNullValue(Object value) {
        return value == null;
    }

    boolean isValueGreaterThanZero(long value) {
        return value > 0;
    }

    boolean isValueGreaterThanZero(double value) {
        return value > 0;
    }
    
    boolean isValueGreaterThanZero(int value) {
        return value > 0;
    }
    
    boolean isEmptyList(List<?> value) {
        return value.isEmpty();
    }

}
