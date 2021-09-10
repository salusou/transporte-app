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
 * Criteria class for the {@link com.genesisoft.transporte.domain.StateProvinces} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.StateProvincesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /state-provinces?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StateProvincesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter stateName;

    private StringFilter abbreviation;

    private LongFilter citiesId;

    private LongFilter companiesId;

    private LongFilter affiliatesId;

    private LongFilter toInsurancesId;

    private LongFilter forInsurancesId;

    private LongFilter countriesId;

    public StateProvincesCriteria() {}

    public StateProvincesCriteria(StateProvincesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stateName = other.stateName == null ? null : other.stateName.copy();
        this.abbreviation = other.abbreviation == null ? null : other.abbreviation.copy();
        this.citiesId = other.citiesId == null ? null : other.citiesId.copy();
        this.companiesId = other.companiesId == null ? null : other.companiesId.copy();
        this.affiliatesId = other.affiliatesId == null ? null : other.affiliatesId.copy();
        this.toInsurancesId = other.toInsurancesId == null ? null : other.toInsurancesId.copy();
        this.forInsurancesId = other.forInsurancesId == null ? null : other.forInsurancesId.copy();
        this.countriesId = other.countriesId == null ? null : other.countriesId.copy();
    }

    @Override
    public StateProvincesCriteria copy() {
        return new StateProvincesCriteria(this);
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

    public StringFilter getStateName() {
        return stateName;
    }

    public StringFilter stateName() {
        if (stateName == null) {
            stateName = new StringFilter();
        }
        return stateName;
    }

    public void setStateName(StringFilter stateName) {
        this.stateName = stateName;
    }

    public StringFilter getAbbreviation() {
        return abbreviation;
    }

    public StringFilter abbreviation() {
        if (abbreviation == null) {
            abbreviation = new StringFilter();
        }
        return abbreviation;
    }

    public void setAbbreviation(StringFilter abbreviation) {
        this.abbreviation = abbreviation;
    }

    public LongFilter getCitiesId() {
        return citiesId;
    }

    public LongFilter citiesId() {
        if (citiesId == null) {
            citiesId = new LongFilter();
        }
        return citiesId;
    }

    public void setCitiesId(LongFilter citiesId) {
        this.citiesId = citiesId;
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

    public LongFilter getAffiliatesId() {
        return affiliatesId;
    }

    public LongFilter affiliatesId() {
        if (affiliatesId == null) {
            affiliatesId = new LongFilter();
        }
        return affiliatesId;
    }

    public void setAffiliatesId(LongFilter affiliatesId) {
        this.affiliatesId = affiliatesId;
    }

    public LongFilter getToInsurancesId() {
        return toInsurancesId;
    }

    public LongFilter toInsurancesId() {
        if (toInsurancesId == null) {
            toInsurancesId = new LongFilter();
        }
        return toInsurancesId;
    }

    public void setToInsurancesId(LongFilter toInsurancesId) {
        this.toInsurancesId = toInsurancesId;
    }

    public LongFilter getForInsurancesId() {
        return forInsurancesId;
    }

    public LongFilter forInsurancesId() {
        if (forInsurancesId == null) {
            forInsurancesId = new LongFilter();
        }
        return forInsurancesId;
    }

    public void setForInsurancesId(LongFilter forInsurancesId) {
        this.forInsurancesId = forInsurancesId;
    }

    public LongFilter getCountriesId() {
        return countriesId;
    }

    public LongFilter countriesId() {
        if (countriesId == null) {
            countriesId = new LongFilter();
        }
        return countriesId;
    }

    public void setCountriesId(LongFilter countriesId) {
        this.countriesId = countriesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StateProvincesCriteria that = (StateProvincesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(stateName, that.stateName) &&
            Objects.equals(abbreviation, that.abbreviation) &&
            Objects.equals(citiesId, that.citiesId) &&
            Objects.equals(companiesId, that.companiesId) &&
            Objects.equals(affiliatesId, that.affiliatesId) &&
            Objects.equals(toInsurancesId, that.toInsurancesId) &&
            Objects.equals(forInsurancesId, that.forInsurancesId) &&
            Objects.equals(countriesId, that.countriesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stateName, abbreviation, citiesId, companiesId, affiliatesId, toInsurancesId, forInsurancesId, countriesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StateProvincesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (stateName != null ? "stateName=" + stateName + ", " : "") +
            (abbreviation != null ? "abbreviation=" + abbreviation + ", " : "") +
            (citiesId != null ? "citiesId=" + citiesId + ", " : "") +
            (companiesId != null ? "companiesId=" + companiesId + ", " : "") +
            (affiliatesId != null ? "affiliatesId=" + affiliatesId + ", " : "") +
            (toInsurancesId != null ? "toInsurancesId=" + toInsurancesId + ", " : "") +
            (forInsurancesId != null ? "forInsurancesId=" + forInsurancesId + ", " : "") +
            (countriesId != null ? "countriesId=" + countriesId + ", " : "") +
            "}";
    }
}
