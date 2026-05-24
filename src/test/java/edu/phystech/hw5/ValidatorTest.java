package edu.phystech.hw5;

import edu.phystech.hw5.annotation.validation.NotBlank;
import edu.phystech.hw5.annotation.validation.Size;
import edu.phystech.hw5.exception.ValidationException;
import edu.phystech.hw5.service.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * @author kzlv4natoly
 */
public class ValidatorTest {

    private Validator validator = object -> {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getType() != String.class) {
                continue;
            }
            field.setAccessible(true);
            String value;
            try {
                value = (String) field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            NotBlank notBlank = field.getAnnotation(NotBlank.class);
            if (notBlank != null && (value == null || value.isEmpty())) {
                throw new ValidationException(notBlank.message());
            }

            Size size = field.getAnnotation(Size.class);
            if (size != null) {
                int length = value == null ? 0 : value.length();
                if (length < size.min() || length > size.max()) {
                    throw new ValidationException(size.message());
                }
            }
        }
    };

    @Test
    void notBlankWorks() {
        class Example {

            @NotBlank
            private final String x;

            @NotBlank(message = "This is a very important field and it can't be empty!")
            private final String y;

            Example(String x, String y) {
                this.x = x;
                this.y = y;
            }
        }

        Assertions.assertDoesNotThrow(() -> validator.validate(new Example("123", "567")));
        ValidationException exception =
                Assertions.assertThrows(ValidationException.class, () -> validator.validate(new Example("11", "")));
        Assertions.assertEquals("This is a very important field and it can't be empty!", exception.getMessage());
    }

    @Test
    void sizeWorks() {
        class Example {
            @Size(max = 52, message = "Long live Saint Petersburg!")
            private final String x;

            @Size(min = 5, max = 11)
            private final String y;

            Example(String x, String y) {
                this.x = x;
                this.y = y;
            }
        }

        Assertions.assertDoesNotThrow(() -> validator.validate(new Example("123", "567765")));
        ValidationException exception =
                Assertions.assertThrows(ValidationException.class, () -> validator.validate(new Example("", "")));
        Assertions.assertEquals("Long live Saint Petersburg!", exception.getMessage());
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(new Example("", "0000000000000")));
    }

}
