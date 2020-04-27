package sg.com.gov.grant.disbursement.domain;

import sg.com.gov.grant.disbursement.domain.baseType.RelationshipType;

import javax.persistence.*;

@Entity
public class FamilyRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RelationshipType relationshipType;

    @ManyToOne
    @JoinColumn(name = "family_member_id", nullable = false)
    private FamilyMember familyMember;

    @ManyToOne
    @JoinColumn(name = "family_member_1_id")
    private FamilyMember familyMember1;

    @Column
    private String spouseNamePlaceHolder;

    public FamilyRelationship() {
    }

    public FamilyRelationship(FamilyMember familyMember, RelationshipType relationshipType, String spouseNamePlaceHolder) {
        this.familyMember = familyMember;
        this.relationshipType = relationshipType;
        this.spouseNamePlaceHolder = spouseNamePlaceHolder;
    }

    public FamilyRelationship(FamilyMember familyMember, RelationshipType relationshipType, FamilyMember familyMember1) {
        this.familyMember = familyMember;
        this.relationshipType = relationshipType;
        this.familyMember1 = familyMember1;
        this.spouseNamePlaceHolder = familyMember1.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }

    public FamilyMember getFamilyMember1() {
        return familyMember1;
    }

    public void setFamilyMember1(FamilyMember familyMember1) {
        this.familyMember1 = familyMember1;
    }

    public String getSpouseNamePlaceHolder() {
        return spouseNamePlaceHolder;
    }

    public void setSpouseNamePlaceHolder(String spouseNamePlaceHolder) {
        this.spouseNamePlaceHolder = spouseNamePlaceHolder;
    }
}