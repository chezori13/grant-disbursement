package sg.com.gov.grant.disbursement.controller;

import sg.com.gov.grant.disbursement.domain.baseType.HousingType;
import sg.com.gov.grant.disbursement.domain.baseType.MaritalStatusType;
import sg.com.gov.grant.disbursement.domain.baseType.OccupationType;
import sg.com.gov.grant.disbursement.persistence.specification.BuilderOperator;
import sg.com.gov.grant.disbursement.persistence.specification.CriteriaOperator;
import sg.com.gov.grant.disbursement.persistence.specification.HouseholdSpecification;
import sg.com.gov.grant.disbursement.persistence.specification.SearchCriteria;

public class RequestParamParser {

    public HouseholdSpecification createHouseholdSpecification
            (String householdSize, String housingType, String totalIncome, String maritalStatus, String occupationType, String age) {

        HouseholdSpecification householdSpecification = new HouseholdSpecification();
        if(null != householdSize) {
            householdSpecification
                    .add(new SearchCriteria("familyMembers", BuilderOperator.SIZE,
                            getOperatorFromReq(householdSize),
                            Integer.parseInt(getValueFromReq(householdSize))));
        }
        if(null != housingType) {
            householdSpecification
                    .add(new SearchCriteria("housingType",
                            getOperatorFromReq(householdSize),
                            HousingType.fromString(getValueFromReq(householdSize))));
        }
        if(null != totalIncome) {
            householdSpecification
                    .add(new SearchCriteria("totalAnnualIncome",
                            getOperatorFromReq(totalIncome),
                            getValueFromReq(totalIncome)));
        }
        if(null != maritalStatus) {
            householdSpecification
                    .add(new SearchCriteria("familyMembers.maritalStatus",
                            getOperatorFromReq(maritalStatus),
                            MaritalStatusType.fromString(getValueFromReq(maritalStatus))));
        }
        if(null != occupationType) {
            householdSpecification
                    .add(new SearchCriteria("familyMembers.occupationType",
                            getOperatorFromReq(occupationType),
                            OccupationType.fromString(getValueFromReq(occupationType))));
        }
        if(null != age) {
            householdSpecification
                    .add(new SearchCriteria("familyMembers.age",
                            getOperatorFromReq(age),
                            getValueFromReq(age)));
        }

        return householdSpecification;
    }

    private CriteriaOperator getOperatorFromReq(String param){
        String[] splitedValue = param.split("\\:");
        if(2 == splitedValue.length){
            return CriteriaOperator.fromString(splitedValue[0]);
        }
        else if(1 == splitedValue.length){
            return CriteriaOperator.EQUAL;
        }
        else {
            throw new RuntimeException(String.format("Invalid search param value: %s. Example gt:100", param));
        }
    }

    private String getValueFromReq(String param){
        String[] splitedValue = param.split("\\:");
        if(2 == splitedValue.length){
            return splitedValue[1];
        }
        else if(1 == splitedValue.length){
            return splitedValue[0];
        }
        else {
            throw new RuntimeException(String.format("Invalid search param value: %s. Example gt:100", param));
        }
    }
}
