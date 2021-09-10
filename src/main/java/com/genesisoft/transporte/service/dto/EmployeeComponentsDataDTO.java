package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.EmployeeComponentsData} entity.
 */
@ApiModel(description = "Employee Components Data.\nThis class is to save grid template per user.\n@author Samuel Souza")
public class EmployeeComponentsDataDTO implements Serializable {

    private Long id;

    /**
     * Grid Information details.\n@type Text
     */
    @ApiModelProperty(value = "Grid Information details.\n@type Text")
    private String dataInfo;

    private EmployeesDTO employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    public EmployeesDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeesDTO employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeComponentsDataDTO)) {
            return false;
        }

        EmployeeComponentsDataDTO employeeComponentsDataDTO = (EmployeeComponentsDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeComponentsDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeComponentsDataDTO{" +
            "id=" + getId() +
            ", dataInfo='" + getDataInfo() + "'" +
            ", employee=" + getEmployee() +
            "}";
    }
}
