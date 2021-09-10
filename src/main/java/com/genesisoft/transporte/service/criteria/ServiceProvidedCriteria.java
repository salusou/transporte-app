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
 * Criteria class for the {@link com.genesisoft.transporte.domain.ServiceProvided} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.ServiceProvidedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-provideds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceProvidedCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serviceName;

    private LongFilter suppliersId;

    public ServiceProvidedCriteria() {}

    public ServiceProvidedCriteria(ServiceProvidedCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.serviceName = other.serviceName == null ? null : other.serviceName.copy();
        this.suppliersId = other.suppliersId == null ? null : other.suppliersId.copy();
    }

    @Override
    public ServiceProvidedCriteria copy() {
        return new ServiceProvidedCriteria(this);
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

    public StringFilter getServiceName() {
        return serviceName;
    }

    public StringFilter serviceName() {
        if (serviceName == null) {
            serviceName = new StringFilter();
        }
        return serviceName;
    }

    public void setServiceName(StringFilter serviceName) {
        this.serviceName = serviceName;
    }

    public LongFilter getSuppliersId() {
        return suppliersId;
    }

    public LongFilter suppliersId() {
        if (suppliersId == null) {
            suppliersId = new LongFilter();
        }
        return suppliersId;
    }

    public void setSuppliersId(LongFilter suppliersId) {
        this.suppliersId = suppliersId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceProvidedCriteria that = (ServiceProvidedCriteria) o;
        return (
            Objects.equals(id, that.id) && Objects.equals(serviceName, that.serviceName) && Objects.equals(suppliersId, that.suppliersId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceName, suppliersId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceProvidedCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (serviceName != null ? "serviceName=" + serviceName + ", " : "") +
            (suppliersId != null ? "suppliersId=" + suppliersId + ", " : "") +
            "}";
    }
}
