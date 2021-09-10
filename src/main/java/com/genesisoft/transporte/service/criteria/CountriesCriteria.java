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
 * Criteria class for the {@link com.genesisoft.transporte.domain.Countries} entity. This class is used
 * in {@link com.genesisoft.transporte.web.rest.CountriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /countries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter countryName;

    private StringFilter iso2;

    private IntegerFilter codeArea;

    private StringFilter language;

    private LongFilter stateProvincesId;

    public CountriesCriteria() {}

    public CountriesCriteria(CountriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.countryName = other.countryName == null ? null : other.countryName.copy();
        this.iso2 = other.iso2 == null ? null : other.iso2.copy();
        this.codeArea = other.codeArea == null ? null : other.codeArea.copy();
        this.language = other.language == null ? null : other.language.copy();
        this.stateProvincesId = other.stateProvincesId == null ? null : other.stateProvincesId.copy();
    }

    @Override
    public CountriesCriteria copy() {
        return new CountriesCriteria(this);
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

    public StringFilter getCountryName() {
        return countryName;
    }

    public StringFilter countryName() {
        if (countryName == null) {
            countryName = new StringFilter();
        }
        return countryName;
    }

    public void setCountryName(StringFilter countryName) {
        this.countryName = countryName;
    }

    public StringFilter getIso2() {
        return iso2;
    }

    public StringFilter iso2() {
        if (iso2 == null) {
            iso2 = new StringFilter();
        }
        return iso2;
    }

    public void setIso2(StringFilter iso2) {
        this.iso2 = iso2;
    }

    public IntegerFilter getCodeArea() {
        return codeArea;
    }

    public IntegerFilter codeArea() {
        if (codeArea == null) {
            codeArea = new IntegerFilter();
        }
        return codeArea;
    }

    public void setCodeArea(IntegerFilter codeArea) {
        this.codeArea = codeArea;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public StringFilter language() {
        if (language == null) {
            language = new StringFilter();
        }
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
    }

    public LongFilter getStateProvincesId() {
        return stateProvincesId;
    }

    public LongFilter stateProvincesId() {
        if (stateProvincesId == null) {
            stateProvincesId = new LongFilter();
        }
        return stateProvincesId;
    }

    public void setStateProvincesId(LongFilter stateProvincesId) {
        this.stateProvincesId = stateProvincesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CountriesCriteria that = (CountriesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(countryName, that.countryName) &&
            Objects.equals(iso2, that.iso2) &&
            Objects.equals(codeArea, that.codeArea) &&
            Objects.equals(language, that.language) &&
            Objects.equals(stateProvincesId, that.stateProvincesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryName, iso2, codeArea, language, stateProvincesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountriesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (countryName != null ? "countryName=" + countryName + ", " : "") +
            (iso2 != null ? "iso2=" + iso2 + ", " : "") +
            (codeArea != null ? "codeArea=" + codeArea + ", " : "") +
            (language != null ? "language=" + language + ", " : "") +
            (stateProvincesId != null ? "stateProvincesId=" + stateProvincesId + ", " : "") +
            "}";
    }
}
