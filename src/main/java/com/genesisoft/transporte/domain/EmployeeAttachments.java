package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Employee Attachments.\nThis class list all employees attachments.\n@author Samuel Souza
 */
@Entity
@Table(name = "employee_attachments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeAttachments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Attach Image will only receive the file.\n@type ImageBlob
     */
    @Lob
    @Column(name = "attach_image")
    private byte[] attachImage;

    @Column(name = "attach_image_content_type")
    private String attachImageContentType;

    /**
     * Id generate in cloudinary.\n@type String
     */
    @NotNull
    @Column(name = "attach_url", nullable = false)
    private String attachUrl;

    /**
     * description about the file attached.\n@type String
     */
    @NotNull
    @Column(name = "attach_description", nullable = false)
    private String attachDescription;

    /**
     * created date.\n@type String
     */
    @NotNull
    @Column(name = "attached_date", nullable = false)
    private ZonedDateTime attachedDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "employeeAttachments",
            "employeeComponentsData",
            "vehicleControls",
            "vehicleControlHistories",
            "housings",
            "companies",
            "affiliates",
            "cities",
            "positions",
        },
        allowSetters = true
    )
    private Employees employees;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeAttachments id(Long id) {
        this.id = id;
        return this;
    }

    public byte[] getAttachImage() {
        return this.attachImage;
    }

    public EmployeeAttachments attachImage(byte[] attachImage) {
        this.attachImage = attachImage;
        return this;
    }

    public void setAttachImage(byte[] attachImage) {
        this.attachImage = attachImage;
    }

    public String getAttachImageContentType() {
        return this.attachImageContentType;
    }

    public EmployeeAttachments attachImageContentType(String attachImageContentType) {
        this.attachImageContentType = attachImageContentType;
        return this;
    }

    public void setAttachImageContentType(String attachImageContentType) {
        this.attachImageContentType = attachImageContentType;
    }

    public String getAttachUrl() {
        return this.attachUrl;
    }

    public EmployeeAttachments attachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
        return this;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public String getAttachDescription() {
        return this.attachDescription;
    }

    public EmployeeAttachments attachDescription(String attachDescription) {
        this.attachDescription = attachDescription;
        return this;
    }

    public void setAttachDescription(String attachDescription) {
        this.attachDescription = attachDescription;
    }

    public ZonedDateTime getAttachedDate() {
        return this.attachedDate;
    }

    public EmployeeAttachments attachedDate(ZonedDateTime attachedDate) {
        this.attachedDate = attachedDate;
        return this;
    }

    public void setAttachedDate(ZonedDateTime attachedDate) {
        this.attachedDate = attachedDate;
    }

    public Employees getEmployees() {
        return this.employees;
    }

    public EmployeeAttachments employees(Employees employees) {
        this.setEmployees(employees);
        return this;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeAttachments)) {
            return false;
        }
        return id != null && id.equals(((EmployeeAttachments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeAttachments{" +
            "id=" + getId() +
            ", attachImage='" + getAttachImage() + "'" +
            ", attachImageContentType='" + getAttachImageContentType() + "'" +
            ", attachUrl='" + getAttachUrl() + "'" +
            ", attachDescription='" + getAttachDescription() + "'" +
            ", attachedDate='" + getAttachedDate() + "'" +
            "}";
    }
}
