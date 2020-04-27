package sg.com.gov.grant.disbursement.controller;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sg.com.gov.grant.disbursement.domain.FamilyMember;
import sg.com.gov.grant.disbursement.domain.FamilyRelationship;
import sg.com.gov.grant.disbursement.domain.baseType.GenderType;
import sg.com.gov.grant.disbursement.domain.baseType.MaritalStatusType;
import sg.com.gov.grant.disbursement.domain.baseType.OccupationType;
import sg.com.gov.grant.disbursement.domain.baseType.RelationshipType;
import sg.com.gov.grant.disbursement.resource.FamilyMemberResource;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Convert Resource Model to Entity Model and vice versa
 */
@Component
public class FamilyMemberResourceConverter {
    /** convert entity to a resource */
    public FamilyMemberResource convertEntityToResource(FamilyMember familyMember){
        FamilyMemberResource familyMemberResource = new FamilyMemberResource();
        familyMemberResource.setId(familyMember.getId());
        familyMemberResource.setName(familyMember.getName());
        familyMemberResource.setGender(familyMember.getGender().toString());
        familyMemberResource.setMaritalStatus(familyMember.getMaritalStatus().toString());
        //set SpouseName
        if(MaritalStatusType.MARRIED == familyMember.getMaritalStatus()) {
            familyMember.getFamilyRelationships().stream()
                    .filter(x -> RelationshipType.MARRIED_TO == x.getRelationshipType())
                    .forEach(marriedRelationship -> familyMemberResource.setSpouse(marriedRelationship.getSpouseNamePlaceHolder())
            );
        }
        familyMemberResource.setOccupationType(familyMember.getOccupationType().toString());
        familyMemberResource.setAnnualIncome(familyMember.getAnnualIncome());
        familyMemberResource.setDob(familyMember.getDob());

        return familyMemberResource;
    }

    public Collection<FamilyMemberResource> convertEntitiesToResources(Collection<FamilyMember> familyMembers) {
        List<FamilyMemberResource> familyMemberResources = new ArrayList<>();
        familyMemberResources.addAll(
                familyMembers.stream()
                        .map( x -> convertEntityToResource(x) )
                        .collect(Collectors.toList())
        );
        return familyMemberResources;
    }

    /** convert resource to an entity*/
    public FamilyMember convertResourceToEntity(FamilyMemberResource familyMemberResource, Supplier<FamilyMember> entitySupplier){
        FamilyMember familyMember;
        if(null == entitySupplier){
            familyMember = new FamilyMember();
            familyMember.setId(familyMemberResource.getId());
        }
        else{
            familyMember = entitySupplier.get();
        }

        familyMember.setName(familyMemberResource.getName());
        familyMember.setGender(GenderType.fromString(familyMemberResource.getGender()));
        familyMember.setMaritalStatus(MaritalStatusType.fromString(familyMemberResource.getMaritalStatus()));

        if(MaritalStatusType.MARRIED == familyMember.getMaritalStatus()) {
            if(StringUtils.isEmpty(familyMemberResource.getSpouse())){
                throw new IllegalArgumentException("Spouse name cannot be empty if MaritalStatus is "+MaritalStatusType.MARRIED);
            }
            boolean containsMarried = false;
            for (FamilyRelationship familyRelationship :familyMember.getFamilyRelationships()) {
                if(RelationshipType.MARRIED_TO == familyRelationship.getRelationshipType()){
                    containsMarried = true;
                    familyRelationship.setSpouseNamePlaceHolder(familyMemberResource.getSpouse());
                }
            }

            if(!containsMarried){
                FamilyRelationship marriedRelationship =
                        new FamilyRelationship(familyMember, RelationshipType.MARRIED_TO, familyMemberResource.getSpouse());
                familyMember.getFamilyRelationships().add(marriedRelationship);

            }

        }
        familyMember.setOccupationType(OccupationType.fromString(familyMemberResource.getOccupationType()));
        familyMember.setAnnualIncome(familyMemberResource.getAnnualIncome());
        familyMember.setDob(familyMemberResource.getDob());

        return familyMember;
    }

    public Collection<FamilyMember> convertResourcesToEntities(Collection<FamilyMemberResource> familyMemberResources){
        ArrayList<FamilyMember> familyMembers = new ArrayList<>();
        familyMembers.addAll(
                familyMemberResources.stream()
                        .map( x -> convertResourceToEntity(x, null))
                        .collect(Collectors.toList())
        );
        return familyMembers;
    }

}
