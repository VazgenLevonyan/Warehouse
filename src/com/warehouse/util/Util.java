package com.warehouse.util;

import com.warehouse.exceptions.ResourceManagementException;

import java.util.Objects;

public class Util {

    private static final String DEFAULT_MESSAGE = " Object must not be null";

    public static void validateInputs(String name, String description, String icon, int maxCapacity) {
        if (name == null || name.trim().isEmpty()) {
            throw new ResourceManagementException("Name cannot be null or empty");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new ResourceManagementException("Description cannot be null or empty");
        }
        if (icon == null || icon.trim().isEmpty()) {
            throw new ResourceManagementException("Icon cannot be null or empty");
        }
        validateInteger(maxCapacity);
    }

    public static void validateString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ResourceManagementException("String cannot be null or empty.");
        }
    }

    public static void validateInteger(int value) {

        if (value <= 0) {
            throw new ResourceManagementException("Integer value must be positive.");
        }
    }

    @SafeVarargs
    public static <T> void checkNotNull(T... objects) {
        for (T obj : objects) {
            Objects.requireNonNull(obj, "Object" + DEFAULT_MESSAGE);
        }
    }
}