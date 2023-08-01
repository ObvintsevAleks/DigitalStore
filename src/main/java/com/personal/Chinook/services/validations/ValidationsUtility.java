package com.personal.Chinook.services.validations;

import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

public final class ValidationsUtility {

    private ValidationsUtility() {
        throw new AssertionError("Suppress default constructor for non-instantiability");
    }

    public static void checkNullFields(Object obj) {
        try {
            for (Field attribute : obj.getClass().getDeclaredFields()) {
                attribute.setAccessible(true);
                Object objValue = attribute.get(obj);

                if (attribute.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = attribute.getAnnotation(Column.class);

                    if(!columnAnnotation.nullable() && objValue == null)
                        throw new InvalidFieldException("ERROR, not all fields are present");
                }
            }
        } catch (IllegalAccessException illEx) {
            illEx.printStackTrace();
        }
    }

    public static void checkStringsContent(Object obj) {
        try {
            for (Field attribute : obj.getClass().getDeclaredFields()) {
                attribute.setAccessible(true);
                Object objValue = attribute.get(obj);

                if (objValue instanceof String) {
                    String validateString = (String) objValue;

                    //checks empty or blank fields
                    if(isEmptyString(validateString))
                        throw new InvalidFieldException("ERROR, found empty fields");

                    //checks jpa column length exceeding
                    checkExceedingLengths(attribute, validateString);

                    //checks special characters through regex
                    if(containsSpecialCharacters(validateString))
                        throw new InvalidFieldException("ERROR, fields cannot contain special characters");
                }
            }
        } catch (IllegalAccessException illEx) {
            illEx.printStackTrace();
        }
    }

    public static void checkNegativeIntegers(Object obj) {
        try {
            for (Field attribute : obj.getClass().getDeclaredFields()) {
                attribute.setAccessible(true);
                Object objValue = attribute.get(obj);

                if (objValue instanceof Integer && isNegativeNumber((Integer) objValue))
                    throw new InvalidFieldException("ERROR, negative numbers are not allowed");
            }
        } catch (IllegalAccessException illEx) {
            illEx.printStackTrace();
        }
    }

    private static void checkExceedingLengths(Field attribute, String str) {
        if (attribute.isAnnotationPresent(Column.class)) {
            Column columnAnnotation = attribute.getAnnotation(Column.class);

            if (str.length() > columnAnnotation.length()) {
                throw new InvalidFieldException(
                        "ERROR, field exceeds character limit: " +
                                columnAnnotation.name() +
                                " (" + columnAnnotation.length() + " )"
                );
            }
        }
    }

    public static boolean isEmptyString(String str) {
        if(str.isBlank() || str.isEmpty())
            return true;

        return false;
    }

    public static boolean isNegativeNumber(Integer value) {
        if(value < 0)
            return true;

        return false;
    }

    public static boolean containsSpecialCharacters(String str) {
        if (!(Pattern.matches("[a-zA-Z -]+", str)))
            return true;

        return false;
    }
}
