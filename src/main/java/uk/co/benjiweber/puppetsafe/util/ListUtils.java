package uk.co.benjiweber.puppetsafe.util;

public class ListUtils {
    public static <T> T coalesce(T... ts) {
        for (T t : ts)
            if (t != null)
                return t;

        return null;
    }
}
