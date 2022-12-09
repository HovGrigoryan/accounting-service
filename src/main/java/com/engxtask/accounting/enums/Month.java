package com.engxtask.accounting.enums;

import java.util.Arrays;

public enum Month {

    JANUARY("1"),
    FEBRUARY("2"),
    MARCH("3"),
    APRIL("4"),
    MAY("5"),
    JUNE("6"),
    JULY("7"),
    AUGUST("8"),
    SEPTEMBER("9"),
    OCTOBER("10"),
    NOVEMBER("11"),
    DECEMBER("12");

    public final String value;

    Month(String value) {
        this.value = value;
    }

    public static Month getEnum(String value) {
        return Arrays.stream(Month.values()).filter(month -> month.name().equals(value.toUpperCase())).findAny().orElseThrow(RuntimeException::new);
    }
}
