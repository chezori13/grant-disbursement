package sg.com.gov.grant.disbursement.domain.baseType;

public enum HousingType {
    LANDED("Landed"), CONDOMINIUM("Condominium"), HDB("HDB");

    private String value;

    HousingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static HousingType fromString(String text) {
        if(null==text){
            return null;
        }

        switch (text.toUpperCase()) {
            case "LANDED":
                return HousingType.LANDED;
            case "CONDOMINIUM":
            case "CONDO":
                return HousingType.CONDOMINIUM;
            case "HDB":
                return HousingType.HDB;
            default:
                throw new RuntimeException(String.format("Invalid HouseholdType: %s", text));

        }
    }
}
