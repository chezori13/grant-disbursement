package sg.com.gov.grant.disbursement.domain;

import org.hibernate.annotations.Formula;
import sg.com.gov.grant.disbursement.domain.baseType.GenderType;
import sg.com.gov.grant.disbursement.domain.baseType.MaritalStatusType;
import sg.com.gov.grant.disbursement.domain.baseType.OccupationType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
public class FamilyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    @Column
    private MaritalStatusType maritalStatus;

    @ManyToMany(mappedBy = "familyMember", cascade = CascadeType.ALL)
    private List<FamilyRelationship> familyRelationships = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column
    private OccupationType occupationType;

    @Column
    private Long annualIncome;

    @Column
    private LocalDate dob;

    //TODO lazy load
    @Formula(value = "( DATE_PART('year', NOW()) - DATE_PART('year', dob) )")
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public MaritalStatusType getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusType maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public List<FamilyRelationship> getFamilyRelationships() {
        return familyRelationships;
    }

    public void setFamilyRelationships(List<FamilyRelationship> familyRelationships) {
        this.familyRelationships = familyRelationships;
    }

    public OccupationType getOccupationType() {
        return occupationType;
    }

    public void setOccupationType(OccupationType occupationType) {
        this.occupationType = occupationType;
    }

    public Long getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(Long annualIncome) {
        this.annualIncome = annualIncome;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
