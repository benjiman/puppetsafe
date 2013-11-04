package uk.co.benjiweber.puppetsafe.util;

import com.google.common.collect.Sets;
import uk.co.benjiweber.puppetsafe.facts.WrapsPuppetable;

import java.lang.reflect.Field;
import java.util.Set;

public abstract class SelfNaming {
    private Set<Field> candidates = Sets.newHashSet();

    public SelfNaming() {
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();

        for (int depth = 1; depth < stackTrace.length; depth++) {
            StackTraceElement element = stackTrace[depth];
            try {
                Class<?> clazz = Class.forName(element.getClassName());
                for (Field field : clazz.getDeclaredFields()) {
                    if (SelfNaming.class.isAssignableFrom(field.getType()) || WrapsPuppetable.class.isAssignableFrom(field.getType())) {
                        candidates.add(field);
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override public String toString() {
        return getName();
    }

    public String getName() {
        for (Field field : candidates) {
            try {
                field.setAccessible(true);
                Object value = field.get(null);
                if (value == this) return field.getName();
                if (value instanceof WrapsPuppetable && ((WrapsPuppetable)value).wraps(this)) return field.getName();
            } catch (IllegalAccessException e) {
                // Don't care, will throw below.
            }
        }
        throw new IllegalStateException("Cannot find field name");
    }
}
