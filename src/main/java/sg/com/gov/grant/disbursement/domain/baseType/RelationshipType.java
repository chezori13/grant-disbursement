package sg.com.gov.grant.disbursement.domain.baseType;

public enum RelationshipType {
    MARRIED_TO("Married to"), PARENT_TO ("Parent to"), CHILD_OF ("Child of");

    private String value;

    RelationshipType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public static RelationshipType fromString(String text) {
        if(null==text){
            return null;
        }

        switch (text.toUpperCase()) {
            case "MARRIED_TO":
            case "MARRIED":
                return RelationshipType.MARRIED_TO;
            case "PARENT_TO":
            case "PARENT":
                return RelationshipType.PARENT_TO;
            case "CHILD_OF":
            case "CHILD":
                return RelationshipType.CHILD_OF;
            default:
                throw new RuntimeException(String.format("Invalid RelationshipType: %s", text));

        }
    }
}
