package sg.com.gov.grant.disbursement.controller;

import org.junit.jupiter.api.Test;
import sg.com.gov.grant.disbursement.domain.FamilyMember;
import sg.com.gov.grant.disbursement.domain.baseType.GenderType;
import sg.com.gov.grant.disbursement.domain.baseType.MaritalStatusType;
import sg.com.gov.grant.disbursement.domain.baseType.OccupationType;
import sg.com.gov.grant.disbursement.resource.FamilyMemberResource;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FamilyMemberResourceConverterTest {
    private FamilyMemberResourceConverter familyMemberResourceConverter = new FamilyMemberResourceConverter();

    @Test
    void convertEntityToResource_shouldSucceed() {
        //Given
        FamilyMember familyMember = new FamilyMember();
        familyMember.setId(1L);
        familyMember.setName("Chester");
        familyMember.setAnnualIncome(10000L);
        familyMember.setOccupationType(OccupationType.EMPLOYED);
        familyMember.setMaritalStatus(MaritalStatusType.SINGLE);
        familyMember.setGender(GenderType.MALE);
        familyMember.setDob(LocalDate.of(1988, Month.MARCH, 29));

        //When
        FamilyMemberResource familyMemberResource = familyMemberResourceConverter.convertEntityToResource(familyMember);

        //Then
        assertEquals(familyMember.getId(), familyMemberResource.getId());
        assertEquals(familyMember.getName(), familyMemberResource.getName());
        assertEquals(familyMember.getAnnualIncome(), familyMemberResource.getAnnualIncome());
        assertEquals(familyMember.getOccupationType(), OccupationType.fromString(familyMemberResource.getOccupationType()));
        assertEquals(familyMember.getMaritalStatus(), MaritalStatusType.fromString(familyMemberResource.getMaritalStatus()));
        assertEquals(familyMember.getGender(), GenderType.fromString(familyMemberResource.getGender()));
        assertEquals(familyMember.getDob(), familyMemberResource.getDob());
    }


    @Test
    void convertResourceToEntity_shouldSucceed() {
        //Given
        FamilyMemberResource familyMemberResource = new FamilyMemberResource();
        familyMemberResource.setName("Chester");
        familyMemberResource.setAnnualIncome(10000L);
        familyMemberResource.setOccupationType("Employed");
        familyMemberResource.setMaritalStatus("Married");
        familyMemberResource.setSpouse("Black Widow");
        familyMemberResource.setGender("Male");
        familyMemberResource.setDob(LocalDate.of(1988, Month.MARCH, 29));

        //When
        FamilyMember familyMember = familyMemberResourceConverter.convertResourceToEntity(familyMemberResource, () -> new FamilyMember());
        assertEquals(familyMemberResource.getId(),familyMember.getId());
        assertEquals(familyMemberResource.getName(), familyMember.getName());
        assertEquals(familyMemberResource.getAnnualIncome(), familyMember.getAnnualIncome());
        assertEquals(OccupationType.fromString(familyMemberResource.getOccupationType()), familyMember.getOccupationType());
        assertEquals(MaritalStatusType.fromString(familyMemberResource.getMaritalStatus()), familyMember.getMaritalStatus());
        assertEquals(GenderType.fromString(familyMemberResource.getGender()),familyMember.getGender());
        assertEquals(familyMemberResource.getDob(), familyMember.getDob());
        assertEquals(familyMemberResource.getSpouse(), familyMember.getFamilyRelationships().get(0).getSpouseNamePlaceHolder());

    }

    @Test
    void convertResourceToEntity_marriedStatusWithoutSpouse_shouldFail() {
        //Given
        FamilyMemberResource familyMemberResource = new FamilyMemberResource();
        familyMemberResource.setName("Chester");
        familyMemberResource.setAnnualIncome(10000L);
        familyMemberResource.setOccupationType("Employed");
        familyMemberResource.setMaritalStatus("Married");
        familyMemberResource.setGender("Male");
        familyMemberResource.setDob(LocalDate.of(1988, Month.MARCH, 29));

        //Then
        assertThrows(IllegalArgumentException.class, () -> {
            familyMemberResourceConverter.convertResourceToEntity(familyMemberResource, () -> new FamilyMember());
        });

    }
}