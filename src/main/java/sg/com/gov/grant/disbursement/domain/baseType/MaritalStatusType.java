package sg.com.gov.grant.disbursement.domain.baseType;

public enum MaritalStatusType {
    SINGLE("Single"), MARRIED("Married"), DIVORCED("Divorced");

    private String value;

    MaritalStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static MaritalStatusType fromString(String text) {
        if(null==text){
            throw new IllegalArgumentException(String.format(
                    "MaritalStatus cannot be empty. Options: %s, %s, %s, %s", SINGLE, MARRIED, DIVORCED));
        }

        switch (text.toUpperCase()) {
            case "SINGLE":
                return MaritalStatusType.SINGLE;
            case "MARRIED":
                return MaritalStatusType.MARRIED;
            case "DIVORCED":
                return MaritalStatusType.DIVORCED;
            default:
                throw new RuntimeException(String.format("Invalid MaritalStatusType: %s", text));

        }
    }


}
