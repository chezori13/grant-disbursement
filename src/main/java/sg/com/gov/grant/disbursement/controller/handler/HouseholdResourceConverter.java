package sg.com.gov.grant.disbursement.controller.handler;

import org.springframework.stereotype.Component;
import sg.com.gov.grant.disbursement.domain.Household;
import sg.com.gov.grant.disbursement.domain.baseType.HousingType;
import sg.com.gov.grant.disbursement.resource.HouseholdResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Convert Resource Model to Entity Model and vice versa
 */
@Component
public class HouseholdResourceConverter {

    private final FamilyMemberResourceConverter familyMemberResourceConverter;

    public HouseholdResourceConverter(FamilyMemberResourceConverter familyMemberResourceConverter) {
        this.familyMemberResourceConverter = familyMemberResourceConverter;
    }

    /** convert entity to a resource */
    public HouseholdResource convertEntityToResource(Household household){
        HouseholdResource householdResource = new HouseholdResource();
        householdResource.setId(household.getId());
        householdResource.setHousingType(household.getHousingType().toString());
        householdResource.getFamilyMembers()
                .addAll(familyMemberResourceConverter.convertEntitiesToResources(household.getFamilyMembers()));

        return householdResource;
    }

    public Collection<HouseholdResource> convertEntitiesToResources(Collection<Household> familyMembers) {
        List<HouseholdResource> householdResources = new ArrayList<>();
        householdResources.addAll(
                familyMembers.stream()
                        .map( x -> convertEntityToResource(x) )
                        .collect(Collectors.toList())
        );
        return householdResources;
    }

    public Household convertResourceToEntity(HouseholdResource householdResource, Supplier<Household> entitySupplier){
        Household household = entitySupplier.get();
        household.setHousingType(HousingType.fromString(householdResource.getHousingType()));
        household.updateFamilyMembers(familyMemberResourceConverter.convertResourcesToEntities(householdResource.getFamilyMembers()));
        return household;
    }
}
