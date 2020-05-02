package sg.com.gov.grant.disbursement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sg.com.gov.grant.disbursement.domain.FamilyMember;
import sg.com.gov.grant.disbursement.domain.Household;
import sg.com.gov.grant.disbursement.domain.baseType.MaritalStatusType;
import sg.com.gov.grant.disbursement.domain.baseType.RelationshipType;
import sg.com.gov.grant.disbursement.persistence.FamilyMemberRepository;
import sg.com.gov.grant.disbursement.persistence.HouseholdRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    public Household createHousehold(Household household){
        return householdRepository.save(household);
    }

    public Household findById(Long id){
        return householdRepository.findById(id).orElse(null);
    }

    @Transactional
    public Household addFamilyMember(Long householdId, FamilyMember familyMember){
        Household household = findById(householdId);
        if(null == household){
            throw new EntityNotFoundException("Household not found: ID " + householdId);
        }
        if(MaritalStatusType.MARRIED == familyMember.getMaritalStatus()){
            searchAndLinkSpouse(householdId, familyMember);
        }
        familyMember.setHousehold(household);
        household.addFamilyMember(familyMember);
        return householdRepository.save(household);
    }

    private void searchAndLinkSpouse(Long householdId, FamilyMember familyMember) {
        familyMember.getFamilyRelationships().stream()
                .filter(x -> RelationshipType.MARRIED_TO == x.getRelationshipType())
                .forEach( familyRelationship -> {
                    FamilyMember existingSpouse = familyMemberRepository
                            .findByHouseholdIdAndNameLike(householdId, familyRelationship.getSpouseNamePlaceHolder());
                    if(null != existingSpouse){
                        familyRelationship.setFamilyMember1(existingSpouse);
                        familyMemberRepository.save(familyMember);
                        linkupDanglingSpouse(familyMember, existingSpouse);
                    }
                });
    }

    private void linkupDanglingSpouse(FamilyMember familyMember, FamilyMember existingSpouse) {
        //link up dangling spouse
        existingSpouse.getFamilyRelationships()
                .stream().filter(x -> RelationshipType.MARRIED_TO == x.getRelationshipType())
                .forEach( spouseRelationship -> {
                    if(null == spouseRelationship.getFamilyMember1()
                        && spouseRelationship.getSpouseNamePlaceHolder().equalsIgnoreCase(familyMember.getName())){
                        spouseRelationship.setFamilyMember1(familyMember);
                        familyMemberRepository.save(existingSpouse);
                    }

                });
    }

    public List<Household> findAll(Specification<Household> householdSpecification){
        return householdRepository.findAll(householdSpecification);
    }


}
