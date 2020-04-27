package sg.com.gov.grant.disbursement.persistence.specification;

import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.query.criteria.internal.predicate.LikePredicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import sg.com.gov.grant.disbursement.domain.Household;
import sg.com.gov.grant.disbursement.domain.baseType.HousingType;

import javax.persistence.criteria.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HouseholdSpecificationTest {
    private HouseholdSpecification householdSpecification;

    @Autowired
    private TestEntityManager entityManager;

    @Mock
    private Root<Household> root;

    private CriteriaQuery<?> query;

    private CriteriaBuilder criteriaBuilder;

    @BeforeEach
    void setEachUp() {
        householdSpecification = new HouseholdSpecification();
        criteriaBuilder = entityManager.getEntityManager().getCriteriaBuilder();
        query = criteriaBuilder.createQuery(Household.class);
        root = query.from(Household.class);
    }

    @Test
    void toPredicate_shouldSucceed() {
        //Given
        householdSpecification.add(new SearchCriteria("familyMembers.name", CriteriaOperator.LIKE, "Chester"));
        householdSpecification.add(new SearchCriteria("housingType", CriteriaOperator.EQUAL, HousingType.HDB));
        householdSpecification.add(new SearchCriteria("familyMembers.age", CriteriaOperator.LESS_THAN_OR_EQUAL, 100));
        householdSpecification.add(new SearchCriteria("familyMembers.age", CriteriaOperator.GREATER_THAN_OR_EQUAL, 10));
        householdSpecification.add(new SearchCriteria("totalAnnualIncome", CriteriaOperator.LESS_THAN, 100000));
        householdSpecification.add(new SearchCriteria("totalAnnualIncome", CriteriaOperator.GREATER_THAN, 50000));

        //When
        Predicate predicate = householdSpecification.toPredicate(root, query, criteriaBuilder);

        //Then
        assertNotNull(predicate);
        assertEquals(Predicate.BooleanOperator.AND, predicate.getOperator());
        List<Expression<Boolean>> expressions = predicate.getExpressions();
        assertEquals(6, expressions.size());

        assertTrue(expressions.get(0) instanceof LikePredicate);
        assertEquals(ComparisonPredicate.ComparisonOperator.EQUAL,((ComparisonPredicate)expressions.get(1)).getComparisonOperator());
        assertEquals(ComparisonPredicate.ComparisonOperator.LESS_THAN_OR_EQUAL,((ComparisonPredicate)expressions.get(2)).getComparisonOperator());
        assertEquals(ComparisonPredicate.ComparisonOperator.GREATER_THAN_OR_EQUAL,((ComparisonPredicate)expressions.get(3)).getComparisonOperator());
        assertEquals(ComparisonPredicate.ComparisonOperator.LESS_THAN,((ComparisonPredicate)expressions.get(4)).getComparisonOperator());
        assertEquals(ComparisonPredicate.ComparisonOperator.GREATER_THAN,((ComparisonPredicate)expressions.get(5)).getComparisonOperator());
    }

    @Test
    void toPredicate_withWrongClassFieldName_shouldFail(){
        //Given
        householdSpecification.add(new SearchCriteria("wrongFieldName", CriteriaOperator.LIKE, "Chester"));

        //Then
        assertThrows(IllegalArgumentException.class, () -> {
            householdSpecification.toPredicate(root, query, criteriaBuilder);
        });

    }

    @Test
    void toPredicate_withoutSearchCriteria_shouldReturnEmptyPredicateList(){
        //Given
        //not inserting SearchCriteria

        //When
        Predicate predicate = householdSpecification.toPredicate(root, query, criteriaBuilder);

        //Then
        assertEquals(0, predicate.getExpressions().size());
    }
}