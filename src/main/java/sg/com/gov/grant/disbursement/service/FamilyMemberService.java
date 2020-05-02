package sg.com.gov.grant.disbursement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.gov.grant.disbursement.domain.FamilyMember;
import sg.com.gov.grant.disbursement.domain.baseType.RelationshipType;
import sg.com.gov.grant.disbursement.persistence.FamilyMemberRepository;

import java.time.LocalDate;

@Service
public class FamilyMemberService {

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    public boolean isMemberExist(Long householdId, String name, LocalDate dob){
        if(null == familyMemberRepository.findByHouseholdIdAndNameAndDob(householdId, name, dob)){
            return false;
        }
        return true;
    }

    public void searchAndLinkSpouse(Long householdId, FamilyMember familyMember) {
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
}
