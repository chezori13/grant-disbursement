package sg.com.gov.grant.disbursement.persistence;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import sg.com.gov.grant.disbursement.domain.Household;

public interface HouseholdRepository extends CrudRepository<Household, Long>, JpaSpecificationExecutor<Household> {

}
