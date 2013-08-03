package uk.co.benjiweber.puppetsafe.serializer;

import java.lang.reflect.Field;

public class NonStaticDeclarationException extends RuntimeException {
    public NonStaticDeclarationException(Class<?> cls, Field field) {
        super("Did you mean to declare " + cls.getSimpleName() + "#" + field.getName() + " static?");
    }

}
