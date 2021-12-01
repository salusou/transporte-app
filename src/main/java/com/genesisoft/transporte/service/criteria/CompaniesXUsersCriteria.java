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
 * Criteria class for the {@link com.genesisoft.transporte.domain.CompaniesXUsers} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.CompaniesXUsersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies-x-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompaniesXUsersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter companiesId;

    private LongFilter userId;

    public CompaniesXUsersCriteria() {}

    public CompaniesXUsersCriteria(CompaniesXUsersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.companiesId = other.companiesId == null ? null : other.companiesId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public CompaniesXUsersCriteria copy() {
        return new CompaniesXUsersCriteria(this);
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

    public LongFilter getCompaniesId() {
        return companiesId;
    }

    public LongFilter companiesId() {
        if (companiesId == null) {
            companiesId = new LongFilter();
        }
        return companiesId;
    }

    public void setCompaniesId(LongFilter companiesId) {
        this.companiesId = companiesId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompaniesXUsersCriteria that = (CompaniesXUsersCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(companiesId, that.companiesId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companiesId, userId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompaniesXUsersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (companiesId != null ? "companiesId=" + companiesId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }
}
