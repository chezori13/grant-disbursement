package sg.com.gov.grant.disbursement.persistence.specification;


import org.springframework.data.jpa.domain.Specification;
import sg.com.gov.grant.disbursement.domain.Household;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class HouseholdSpecification implements Specification<Household> {
    private List<SearchCriteria> searchCriteriaList;
    private List<Predicate> predicateList;

    public HouseholdSpecification() {
        this.searchCriteriaList = new ArrayList<>();
        this.predicateList = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        searchCriteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate
            (Root<Household> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        //add add criteria to predicateList
        searchCriteriaList.stream().forEach( criteria -> {
            if(BuilderOperator.SIZE == criteria.getBuilderOperator()){
                createSizePredicate(root, builder, criteria);
            }
            else{
                createNormalOrJoinPredicate(root, builder, criteria);
            }
        });

        query.distinct(true);
        return builder.and(predicateList.toArray(new Predicate[0]));
    }

    private void createSizePredicate(Root<Household> root, CriteriaBuilder builder, SearchCriteria criteria){
        if (criteria.getCriteriaOperator().equals(CriteriaOperator.GREATER_THAN)) {
            predicateList.add(builder.greaterThan(builder.size(root.get(criteria.getKey())), (Integer) criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.LESS_THAN)) {
            predicateList.add(builder.lessThan(builder.size(root.get(criteria.getKey())), (Integer) criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.GREATER_THAN_OR_EQUAL)) {
            predicateList.add(builder.greaterThanOrEqualTo(builder.size(root.get(criteria.getKey())), (Integer) criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.LESS_THAN_OR_EQUAL)) {
            predicateList.add(builder.lessThanOrEqualTo(builder.size(root.get(criteria.getKey())), (Integer) criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.EQUAL)) {
            predicateList.add(builder.equal(builder.size(root.get(criteria.getKey())), criteria.getValue()));
        }
    }

    private void createNormalOrJoinPredicate(Root<Household> root, CriteriaBuilder builder, SearchCriteria criteria) {
        String[] fieldSequences = criteria.getKey().split("\\.");
        Path fieldPath = getPath(root, fieldSequences[0]);
        for (int i = 1; i < fieldSequences.length; i++) {
            fieldPath = getPath(fieldPath, fieldSequences[i]);
        }
        if (criteria.getCriteriaOperator().equals(CriteriaOperator.GREATER_THAN)) {
            predicateList.add(builder.greaterThan(fieldPath, (Comparable) criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.LESS_THAN)) {
            predicateList.add(builder.lessThan(fieldPath, (Comparable) criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.GREATER_THAN_OR_EQUAL)) {
            predicateList.add(builder.greaterThanOrEqualTo(fieldPath, (Comparable) criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.LESS_THAN_OR_EQUAL)) {
            predicateList.add(builder.lessThanOrEqualTo(fieldPath, (Comparable) criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.NOT_EQUAL)) {
            predicateList.add(builder.notEqual(fieldPath, criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.EQUAL)) {
            predicateList.add(builder.equal(fieldPath, criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.LIKE)) {
            predicateList.add(builder.like(builder.lower(fieldPath),
                    "%" + criteria.getValue().toString().toLowerCase() + "%"));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.IN)) {
            predicateList.add(builder.in(fieldPath).value(criteria.getValue()));
        }
        else if (criteria.getCriteriaOperator().equals(CriteriaOperator.NOT_IN)) {
            predicateList.add(builder.not(fieldPath).in(criteria.getValue()));
        }
    }

    private static Path<?> getPath(Path<?> root, String fieldName) {

        Path<?> path = root.get(fieldName);

        if (Iterable.class.isAssignableFrom(path.getJavaType())) {
            return ((From<?, ?>) root).joinSet(fieldName, JoinType.LEFT);
        } else {
            return path;
        }

    }
}