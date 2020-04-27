package sg.com.gov.grant.disbursement.domain.baseType;

public enum GenderType {
    MALE("Male"), FEMALE("Female");
    private String value;

    GenderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static GenderType fromString(String text) {
        if(null==text){
            return null;
        }

        switch (text.toUpperCase()) {
            case "MALE":
            case "M":
                return GenderType.MALE;
            case "FEMALE":
            case "F":
                return GenderType.FEMALE;
            default:
                throw new RuntimeException(String.format("Invalid GenderType: %s", text));

        }
    }
}
