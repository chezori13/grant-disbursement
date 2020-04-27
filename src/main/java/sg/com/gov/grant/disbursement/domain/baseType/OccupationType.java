package sg.com.gov.grant.disbursement.domain.baseType;

public enum OccupationType {
    UNEMPLOYED("Unemployed"), STUDENT("Student"), EMPLOYED("Employed");
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
            return null;
        }

        switch (text.toUpperCase()) {
            case "UNEMPLOYED":
                return OccupationType.UNEMPLOYED;
            case "STUDENT":
                return OccupationType.STUDENT;
            case "EMPLOYED":
                return OccupationType.EMPLOYED;
            default:
                throw new RuntimeException(String.format("Invalid OccupationType: %s", text));

        }
    }
}
