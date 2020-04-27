package sg.com.gov.grant.disbursement.persistence.specification;

public class SearchCriteria {
    private String key;
    private CriteriaOperator criteriaOperator;
    private BuilderOperator builderOperator;
    private Object value;

    public SearchCriteria(String key, CriteriaOperator criteriaOperator, Object value) {
        this.key = key;
        this.criteriaOperator = criteriaOperator;
        this.value = value;
    }

    public SearchCriteria(String key, BuilderOperator builderOperator, CriteriaOperator criteriaOperator, Object value) {
        this.key = key;
        this.criteriaOperator = criteriaOperator;
        this.builderOperator = builderOperator;
        this.value = value;
    }

    public String getKey() {
        return key;
    }


    public CriteriaOperator getCriteriaOperator() {
        return criteriaOperator;
    }


    public Object getValue() {
        return value;
    }

    public BuilderOperator getBuilderOperator() {
        return builderOperator;
    }

    public void setBuilderOperator(BuilderOperator builderOperator) {
        this.builderOperator = builderOperator;
    }
}
