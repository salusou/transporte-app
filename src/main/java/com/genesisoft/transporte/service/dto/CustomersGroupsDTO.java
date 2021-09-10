package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.CustomersGroups} entity.
 */
@ApiModel(description = "Customers Groups\nThis class are the customers group.\n@author Samuel Souza")
public class CustomersGroupsDTO implements Serializable {

    private Long id;

    @NotNull
    private String groupName;

    private AffiliatesDTO affiliates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public AffiliatesDTO getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(AffiliatesDTO affiliates) {
        this.affiliates = affiliates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomersGroupsDTO)) {
            return false;
        }

        CustomersGroupsDTO customersGroupsDTO = (CustomersGroupsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, customersGroupsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomersGroupsDTO{" +
            "id=" + getId() +
            ", groupName='" + getGroupName() + "'" +
            ", affiliates=" + getAffiliates() +
            "}";
    }
}
