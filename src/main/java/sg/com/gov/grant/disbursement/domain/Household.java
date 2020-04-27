package sg.com.gov.grant.disbursement.domain;

import org.hibernate.annotations.Formula;
import sg.com.gov.grant.disbursement.domain.baseType.HousingType;

import javax.persistence.*;
import java.util.*;

@Entity
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private HousingType housingType;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    private Set<FamilyMember> familyMembers = new HashSet<>();

    //TODO lazy load
    @Formula(value="(select sum(f.annual_income) from family_member f where f.household_id = id)")
    private Long totalAnnualIncome;

    public Long getTotalAnnualIncome() {
        return totalAnnualIncome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HousingType getHousingType() {
        return housingType;
    }

    public void setHousingType(HousingType housingType) {
        this.housingType = housingType;
    }

    public Collection<FamilyMember> getFamilyMembers() {
        return Collections.unmodifiableSet(this.familyMembers);
    }

    public void addFamilyMember(FamilyMember familyMember) { this.familyMembers.add(familyMember); }

    public void removeFamilyMembers(Collection<Long> familyMembersToRemove) {
        this.familyMembers
                .removeIf(familyMember -> familyMembersToRemove
                        .stream()
                        .anyMatch(id -> id == familyMember.getId()));
    }

    public void addFamilyMembers(Collection<FamilyMember> familyMembersToAdd) {
        this.familyMembers.addAll(familyMembersToAdd);
    }

    public void updateFamilyMembers(Collection<FamilyMember> familyMembersToUpdate) {
        // add new or different items
        familyMembersToUpdate.stream()
                .filter(x->!this.familyMembers.contains(x))
                .forEach( x -> this.familyMembers.add(x) )
        ;
        // remove deleted items
        this.familyMembers.removeIf(x->!familyMembersToUpdate.contains(x));
    }
}
