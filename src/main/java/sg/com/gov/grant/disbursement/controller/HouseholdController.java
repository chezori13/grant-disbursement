package sg.com.gov.grant.disbursement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.com.gov.grant.disbursement.domain.Household;
import sg.com.gov.grant.disbursement.persistence.specification.HouseholdSpecification;
import sg.com.gov.grant.disbursement.resource.FamilyMemberResource;
import sg.com.gov.grant.disbursement.resource.HouseholdResource;
import sg.com.gov.grant.disbursement.service.HouseholdService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/household")
public class HouseholdController {

    @Autowired
    private HouseholdResourceConverter householdResourceConverter;

    @Autowired
    private FamilyMemberResourceConverter familyMemberResourceConverter;

    @Autowired
    private HouseholdService householdService;

    private RequestParamParser reqParamParser = new RequestParamParser();

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HouseholdResource createHousehold (@RequestBody HouseholdResource householdResource){
        Household household = householdResourceConverter.convertResourceToEntity(householdResource, () -> new Household());

        return householdResourceConverter.convertEntityToResource(householdService.createHousehold(household));
    }

    @GetMapping
    public List<HouseholdResource> getHouseholds(@RequestParam(required = false) String householdSize,
                                                 @RequestParam(required = false) String housingType,
                                                 @RequestParam(required = false) String totalIncome,
                                                 @RequestParam(required = false) String maritalStatus,
                                                 @RequestParam(required = false) String occupationType,
                                                 @RequestParam(required = false) String age){

        HouseholdSpecification householdSpecification =
                reqParamParser.createHouseholdSpecification(householdSize, housingType, totalIncome, maritalStatus, occupationType, age);

        return new ArrayList(householdResourceConverter.convertEntitiesToResources(householdService.findAll(householdSpecification)));

    }

    @GetMapping(value="/{id}")
    public HouseholdResource getHouseholdById(@PathVariable Long id){
        Household household = householdService.findById(id);

        if (null == household) {
            String message = String.format("No Household found with (id = %d)", id);
            throw new EntityNotFoundException(message);
        }
        return householdResourceConverter.convertEntityToResource(household);
    }

    @PutMapping(value = "/{id}/familyMember")
    @ResponseStatus(HttpStatus.CREATED)
    public HouseholdResource addFamilyMember(@PathVariable Long id, @RequestBody FamilyMemberResource familyMemberResource){

        Household updatedHousehold = householdService
                .addFamilyMember(id, familyMemberResourceConverter.convertResourceToEntity(familyMemberResource, null));

        return householdResourceConverter.convertEntityToResource(updatedHousehold);
    }


}
