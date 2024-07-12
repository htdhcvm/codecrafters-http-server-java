public enum CliParamsEnum {
    FILE_DIRECTORY("fileDirectory");

    private final String value;

    CliParamsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
