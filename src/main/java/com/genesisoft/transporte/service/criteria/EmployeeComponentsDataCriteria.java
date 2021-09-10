package com.genesisoft.transporte.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.genesisoft.transporte.domain.EmployeeComponentsData} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.EmployeeComponentsDataResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-components-data?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeComponentsDataCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dataInfo;

    private LongFilter employeeId;

    public EmployeeComponentsDataCriteria() {}

    public EmployeeComponentsDataCriteria(EmployeeComponentsDataCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dataInfo = other.dataInfo == null ? null : other.dataInfo.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
    }

    @Override
    public EmployeeComponentsDataCriteria copy() {
        return new EmployeeComponentsDataCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDataInfo() {
        return dataInfo;
    }

    public StringFilter dataInfo() {
        if (dataInfo == null) {
            dataInfo = new StringFilter();
        }
        return dataInfo;
    }

    public void setDataInfo(StringFilter dataInfo) {
        this.dataInfo = dataInfo;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeComponentsDataCriteria that = (EmployeeComponentsDataCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(dataInfo, that.dataInfo) && Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataInfo, employeeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeComponentsDataCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dataInfo != null ? "dataInfo=" + dataInfo + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }
}
