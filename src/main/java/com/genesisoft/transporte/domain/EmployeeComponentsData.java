package com.genesisoft.transporte.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Employee Components Data.\nThis class is to save grid template per user.\n@author Samuel Souza
 */
@Entity
@Table(name = "employee_components_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeComponentsData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Grid Information details.\n@type Text
     */
    @Column(name = "data_info")
    private String dataInfo;

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
    private Employees employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeComponentsData id(Long id) {
        this.id = id;
        return this;
    }

    public String getDataInfo() {
        return this.dataInfo;
    }

    public EmployeeComponentsData dataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
        return this;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    public Employees getEmployee() {
        return this.employee;
    }

    public EmployeeComponentsData employee(Employees employees) {
        this.setEmployee(employees);
        return this;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeComponentsData)) {
            return false;
        }
        return id != null && id.equals(((EmployeeComponentsData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeComponentsData{" +
            "id=" + getId() +
            ", dataInfo='" + getDataInfo() + "'" +
            "}";
    }
}
