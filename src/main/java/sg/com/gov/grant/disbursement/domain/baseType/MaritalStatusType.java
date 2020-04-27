package sg.com.gov.grant.disbursement.domain.baseType;

public enum MaritalStatusType {
    SINGLE("Single"), MARRIED("Married"), DIVORCED("Divorced"), SEPARATED("Separated");

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
            return null;
        }

        switch (text.toUpperCase()) {
            case "SINGLE":
                return MaritalStatusType.SINGLE;
            case "MARRIED":
                return MaritalStatusType.MARRIED;
            case "DIVORCED":
                return MaritalStatusType.DIVORCED;
            case "SEPARATED":
                return MaritalStatusType.SEPARATED;
            default:
                throw new RuntimeException(String.format("Invalid MaritalStatusType: %s", text));

        }
    }


}
