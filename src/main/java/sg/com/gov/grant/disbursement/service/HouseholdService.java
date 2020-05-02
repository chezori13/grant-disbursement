package sg.com.gov.grant.disbursement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sg.com.gov.grant.disbursement.domain.FamilyMember;
import sg.com.gov.grant.disbursement.domain.Household;
import sg.com.gov.grant.disbursement.domain.baseType.MaritalStatusType;
import sg.com.gov.grant.disbursement.persistence.HouseholdRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private FamilyMemberService familyMemberService;

    public List<Household> findAll(Specification<Household> householdSpecification){
        return householdRepository.findAll(householdSpecification);
    }

    public Household createHousehold(Household household){
        return householdRepository.save(household);
    }

    public Household findById(Long id){
        return householdRepository.findById(id).orElse(null);
    }

    @Transactional
    public Household addFamilyMember(Long householdId, FamilyMember familyMember){
        Household household = findById(householdId);
        familyMember.setHousehold(household);
        if(null == household){
            throw new EntityNotFoundException("Household not found: ID " + householdId);
        }
        if(familyMemberService.isMemberExist(householdId, familyMember.getName(), familyMember.getDob())){
            throw new IllegalArgumentException("FamilyMember already exist: " + familyMember.getName());
        }
        if(MaritalStatusType.MARRIED == familyMember.getMaritalStatus()){
            familyMemberService.searchAndLinkSpouse(householdId, familyMember);
        }
        household.addFamilyMember(familyMember);
        return householdRepository.save(household);
    }

}
