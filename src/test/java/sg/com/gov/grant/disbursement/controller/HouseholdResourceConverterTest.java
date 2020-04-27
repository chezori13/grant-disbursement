package sg.com.gov.grant.disbursement.controller;

import org.junit.jupiter.api.Test;
import sg.com.gov.grant.disbursement.domain.FamilyMember;
import sg.com.gov.grant.disbursement.domain.Household;
import sg.com.gov.grant.disbursement.domain.baseType.GenderType;
import sg.com.gov.grant.disbursement.domain.baseType.HousingType;
import sg.com.gov.grant.disbursement.domain.baseType.MaritalStatusType;
import sg.com.gov.grant.disbursement.domain.baseType.OccupationType;
import sg.com.gov.grant.disbursement.resource.FamilyMemberResource;
import sg.com.gov.grant.disbursement.resource.HouseholdResource;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HouseholdResourceConverterTest {

    private HouseholdResourceConverter householdResourceConverter = new HouseholdResourceConverter(new FamilyMemberResourceConverter());

    @Test
    void convertEntityToResource_shouldSucceed() {
        //Given
        Household household = new Household();
        household.setId(1L);
        household.setHousingType(HousingType.CONDOMINIUM);

        //When
        HouseholdResource householdResource = householdResourceConverter.convertEntityToResource(household);

        //Then
        assertEquals(household.getId(), householdResource.getId());
        assertEquals(household.getHousingType(), HousingType.fromString(householdResource.getHousingType()));
        assertEquals(0, householdResource.getFamilyMembers().size());
    }

    @Test
    void convertEntityToResource_withFamilyMember_shouldSucceed() {
        //Given
        FamilyMember familyMember = new FamilyMember();
        familyMember.setId(1L);
        familyMember.setName("Chester");
        familyMember.setAnnualIncome(10000L);
        familyMember.setOccupationType(OccupationType.EMPLOYED);
        familyMember.setMaritalStatus(MaritalStatusType.SINGLE);
        familyMember.setGender(GenderType.MALE);
        familyMember.setDob(LocalDate.of(1988, Month.MARCH, 29));

        Household household = new Household();
        household.setId(1L);
        household.setHousingType(HousingType.CONDOMINIUM);
        household.addFamilyMember(familyMember);

        //When
        HouseholdResource householdResource = householdResourceConverter.convertEntityToResource(household);

        //Then
        assertEquals(household.getId(), householdResource.getId());
        assertEquals(household.getHousingType(), HousingType.fromString(householdResource.getHousingType()));
        assertEquals(1, householdResource.getFamilyMembers().size());

        FamilyMemberResource familyMemberResource = householdResource.getFamilyMembers().get(0);
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
        HouseholdResource householdResource = new HouseholdResource();
        householdResource.setId(1L);
        householdResource.setHousingType("HDB");

        //When
        Household household = householdResourceConverter.convertResourceToEntity(householdResource, () -> new Household());

        //Then
        //if entity is new, id should be generated upon persist
        assertNull(household.getId());
        assertEquals(HousingType.fromString(householdResource.getHousingType()), household.getHousingType());
        assertEquals(0, household.getFamilyMembers().size());
    }
}