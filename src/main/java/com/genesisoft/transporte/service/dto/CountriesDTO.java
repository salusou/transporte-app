package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.Countries} entity.
 */
@ApiModel(
    description = "This table is the list of all countries of World.\nThis class is used to filter country of the company.\n@author Samuel Souza"
)
public class CountriesDTO implements Serializable {

    private Long id;

    /**
     * Country Name example Brazil\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Country Name example Brazil\n@type String", required = true)
    private String countryName;

    /**
     * This is a abbrev about country. Example: br\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "This is a abbrev about country. Example: br\n@type String", required = true)
    private String iso2;

    /**
     * To phone call. Example of Brazil: 55\n@type Integer
     */
    @ApiModelProperty(value = "To phone call. Example of Brazil: 55\n@type Integer")
    private Integer codeArea;

    /**
     * Language of this Country: Example: pt_br\n@type String
     */
    @Size(max = 6)
    @ApiModelProperty(value = "Language of this Country: Example: pt_br\n@type String")
    private String language;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public Integer getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(Integer codeArea) {
        this.codeArea = codeArea;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountriesDTO)) {
            return false;
        }

        CountriesDTO countriesDTO = (CountriesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, countriesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CountriesDTO{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", iso2='" + getIso2() + "'" +
            ", codeArea=" + getCodeArea() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
