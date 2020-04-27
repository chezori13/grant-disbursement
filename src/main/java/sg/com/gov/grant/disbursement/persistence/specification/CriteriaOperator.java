package sg.com.gov.grant.disbursement.persistence.specification;

public enum CriteriaOperator {
    NOT_IN, IN, NOT_EQUAL, EQUAL, GREATER_THAN, GREATER_THAN_OR_EQUAL, LESS_THAN, LESS_THAN_OR_EQUAL, LIKE;

    public static CriteriaOperator fromString(String text) {
        switch (text) {
            case "notIn":
            case "not_in":
                return CriteriaOperator.NOT_IN;
            case "in":
                return CriteriaOperator.IN;
            case "notEq":
            case "not_equal":
                return CriteriaOperator.NOT_EQUAL;
            case "eq":
            case "equal":
                return CriteriaOperator.EQUAL;
            case ">":
            case "gt":
                return CriteriaOperator.GREATER_THAN;
            case "gte":
            case "ge":
                return CriteriaOperator.GREATER_THAN_OR_EQUAL;
            case "<":
            case "lt":
                return CriteriaOperator.LESS_THAN;
            case "lte":
            case "le":
                return CriteriaOperator.LESS_THAN_OR_EQUAL;
            case "like":
                return CriteriaOperator.LIKE;
            default:
                throw new RuntimeException(String.format("Invalid search operator: %s", text));

        }
    }

}