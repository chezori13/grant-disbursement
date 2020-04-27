package sg.com.gov.grant.disbursement.domain.baseType;

public enum OccupationType {
    UNEMPLOYED("Unemployed"), STUDENT("Student"), EMPLOYED("Employed"), NA("NA");
    private String value;

    OccupationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static OccupationType fromString(String text) {
        if(null==text){
            throw new IllegalArgumentException(String.format(
                    "OccupationType cannot be empty. Options: %s, %s, %s, %s", UNEMPLOYED, STUDENT, EMPLOYED, NA));
        }

        switch (text.toUpperCase()) {
            case "UNEMPLOYED":
                return OccupationType.UNEMPLOYED;
            case "STUDENT":
                return OccupationType.STUDENT;
            case "EMPLOYED":
                return OccupationType.EMPLOYED;
            case "NA":
                return OccupationType.NA;
            default:
                throw new RuntimeException(String.format("Invalid OccupationType: %s", text));

        }
    }
}
