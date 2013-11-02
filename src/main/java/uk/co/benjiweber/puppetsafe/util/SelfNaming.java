package uk.co.benjiweber.puppetsafe.util;

import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.util.Set;

public abstract class SelfNaming {
    private Set<Field> candidates = Sets.newHashSet();

    public SelfNaming() {
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        StackTraceElement element = stackTrace[2];
        try {
            Class<?> clazz = Class.forName(element.getClassName());
            for (Field field : clazz.getDeclaredFields()) {
                if (SelfNaming.class.isAssignableFrom(field.getType())) {
                    candidates.add(field);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public String toString() {
        for (Field field : candidates) {
            try {
                if (field.get(null) == this) return field.getName();
            } catch (IllegalAccessException e) {
                // Don't care, will throw below.
            }
        }
        throw new IllegalStateException("Cannot find field name");
    }
}
