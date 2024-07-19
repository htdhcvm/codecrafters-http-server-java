package io.codecrafters.common;

public enum EncodingHeaders {
    GZIP("gzip");

    private final String value;

    EncodingHeaders(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static boolean containsByValue(String value) {
        for (EncodingHeaders c : EncodingHeaders.values()) {
            if (c.value.equals(value)) {
                return true;
            }
        }

        return false;
    }
}
