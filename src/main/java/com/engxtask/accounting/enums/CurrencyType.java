package com.engxtask.accounting.enums;

public enum CurrencyType {

    USD("US"),
    GEL("GEORGIA");

    public final String value;
    CurrencyType(String v) {
        value = v;
    }
}
