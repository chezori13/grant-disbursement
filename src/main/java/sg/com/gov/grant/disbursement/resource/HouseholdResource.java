package sg.com.gov.grant.disbursement.resource;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class HouseholdResource {
    private Long id;

    @NotNull
    private String housingType;

    private List<FamilyMemberResource> familyMembers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHousingType() {
        return housingType;
    }

    public void setHousingType(String housingType) {
        this.housingType = housingType;
    }

    public List<FamilyMemberResource> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(List<FamilyMemberResource> familyMembers) {
        this.familyMembers = familyMembers;
    }
}
