package ru.study;

public class StringUtils {
    public static boolean isEmptyOrNull(String s) {
        return s == null || s.trim().length() == 0;
    }
}
