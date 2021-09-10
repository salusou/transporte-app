package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.genesisoft.transporte.domain.enumeration.AttachmentType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Status Attachments\nThis class list all status for all screens.\n@author Samuel Souza
 */
@Entity
@Table(name = "status_attachments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StatusAttachments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "status_name", nullable = false)
    private String statusName;

    @Column(name = "status_descriptions")
    private String statusDescriptions;

    @Column(name = "status_obs")
    private String statusObs;

    @Enumerated(EnumType.STRING)
    @Column(name = "attachment_type")
    private AttachmentType attachmentType;

    @OneToMany(mappedBy = "statusAttachments")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customers", "statusAttachments" }, allowSetters = true)
    private Set<CustomerAttachments> customerAttachments = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "insurances",
            "positions",
            "costCenters",
            "administrativeFeesRanges",
            "customersGroups",
            "fees",
            "customers",
            "statusAttachments",
            "statuses",
            "parkings",
            "suppliers",
            "employees",
            "vehicleControls",
            "housings",
            "stateProvinces",
            "cities",
            "companies",
        },
        allowSetters = true
    )
    private Affiliates affiliates;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusAttachments id(Long id) {
        this.id = id;
        return this;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public StatusAttachments statusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusDescriptions() {
        return this.statusDescriptions;
    }

    public StatusAttachments statusDescriptions(String statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
        return this;
    }

    public void setStatusDescriptions(String statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
    }

    public String getStatusObs() {
        return this.statusObs;
    }

    public StatusAttachments statusObs(String statusObs) {
        this.statusObs = statusObs;
        return this;
    }

    public void setStatusObs(String statusObs) {
        this.statusObs = statusObs;
    }

    public AttachmentType getAttachmentType() {
        return this.attachmentType;
    }

    public StatusAttachments attachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
        return this;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Set<CustomerAttachments> getCustomerAttachments() {
        return this.customerAttachments;
    }

    public StatusAttachments customerAttachments(Set<CustomerAttachments> customerAttachments) {
        this.setCustomerAttachments(customerAttachments);
        return this;
    }

    public StatusAttachments addCustomerAttachments(CustomerAttachments customerAttachments) {
        this.customerAttachments.add(customerAttachments);
        customerAttachments.setStatusAttachments(this);
        return this;
    }

    public StatusAttachments removeCustomerAttachments(CustomerAttachments customerAttachments) {
        this.customerAttachments.remove(customerAttachments);
        customerAttachments.setStatusAttachments(null);
        return this;
    }

    public void setCustomerAttachments(Set<CustomerAttachments> customerAttachments) {
        if (this.customerAttachments != null) {
            this.customerAttachments.forEach(i -> i.setStatusAttachments(null));
        }
        if (customerAttachments != null) {
            customerAttachments.forEach(i -> i.setStatusAttachments(this));
        }
        this.customerAttachments = customerAttachments;
    }

    public Affiliates getAffiliates() {
        return this.affiliates;
    }

    public StatusAttachments affiliates(Affiliates affiliates) {
        this.setAffiliates(affiliates);
        return this;
    }

    public void setAffiliates(Affiliates affiliates) {
        this.affiliates = affiliates;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusAttachments)) {
            return false;
        }
        return id != null && id.equals(((StatusAttachments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusAttachments{" +
            "id=" + getId() +
            ", statusName='" + getStatusName() + "'" +
            ", statusDescriptions='" + getStatusDescriptions() + "'" +
            ", statusObs='" + getStatusObs() + "'" +
            ", attachmentType='" + getAttachmentType() + "'" +
            "}";
    }
}
