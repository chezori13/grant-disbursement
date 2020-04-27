package sg.com.gov.grant.disbursement.persistence;

import org.springframework.data.repository.CrudRepository;
import sg.com.gov.grant.disbursement.domain.FamilyMember;

public interface FamilyMemberRepository extends CrudRepository<FamilyMember, Long>{

    FamilyMember findByHouseholdIdAndNameLike(Long hostId, String name);

}