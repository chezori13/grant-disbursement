package sg.com.gov.grant.disbursement.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sg.com.gov.grant.disbursement.controller.handler.FamilyMemberResourceConverter;
import sg.com.gov.grant.disbursement.controller.handler.HouseholdResourceConverter;
import sg.com.gov.grant.disbursement.controller.handler.RequestParamParser;
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

    @ApiOperation(value = "Create a household")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HouseholdResource createHousehold (@RequestBody HouseholdResource householdResource){
        Household household = householdResourceConverter.convertResourceToEntity(householdResource, () -> new Household());

        return householdResourceConverter.convertEntityToResource(householdService.createHousehold(household));
    }

    @ApiOperation(value = "List all / Search households")
    @GetMapping
    public List<HouseholdResource> getHouseholds(
            @ApiParam(value = "Example: gt:5 , 5, lt:10", required = false) @RequestParam(required = false) String householdSize,
            @ApiParam(value = "Options: Landed, Condo, HDB", required = false) @RequestParam(required = false) String housingType,
            @ApiParam(value = "Example: ge:20000, 20000, le:20000", required = false) @RequestParam(required = false) String totalIncome,
            @ApiParam(value = "Options: Single, Married, Divorced", required = false) @RequestParam(required = false) String maritalStatus,
            @ApiParam(value = "Options: Unemployed, Employed, Student", required = false) @RequestParam(required = false) String occupationType,
            @ApiParam(value = "Example: ge:5, 15, le:50", required = false) @RequestParam(required = false) String age){

        HouseholdSpecification householdSpecification =
                reqParamParser.createHouseholdSpecification(householdSize, housingType, totalIncome, maritalStatus, occupationType, age);

        return new ArrayList(householdResourceConverter.convertEntitiesToResources(householdService.findAll(householdSpecification)));

    }

    @ApiOperation(value = "Retrieve a household by id")
    @GetMapping(value="/{id}")
    public HouseholdResource getHouseholdById(@PathVariable Long id){
        Household household = householdService.findById(id);

        if (null == household) {
            String message = String.format("No Household found with (id = %d)", id);
            throw new EntityNotFoundException(message);
        }
        return householdResourceConverter.convertEntityToResource(household);
    }

    @ApiOperation(value = "Add a new family member to household")
    @PutMapping(value = "/{id}/familyMember")
    @ResponseStatus(HttpStatus.CREATED)
    public HouseholdResource addFamilyMember(@PathVariable Long id, @RequestBody FamilyMemberResource familyMemberResource){

        Household updatedHousehold = householdService
                .addFamilyMember(id, familyMemberResourceConverter.convertResourceToEntity(familyMemberResource, null));

        return householdResourceConverter.convertEntityToResource(updatedHousehold);
    }


}
