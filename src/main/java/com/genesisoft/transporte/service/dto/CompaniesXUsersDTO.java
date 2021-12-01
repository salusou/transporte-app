package com.genesisoft.transporte.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.CompaniesXUsers} entity.
 */
public class CompaniesXUsersDTO implements Serializable {

    private Long id;

    private CompaniesDTO companies;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompaniesDTO getCompanies() {
        return companies;
    }

    public void setCompanies(CompaniesDTO companies) {
        this.companies = companies;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompaniesXUsersDTO)) {
            return false;
        }

        CompaniesXUsersDTO companiesXUsersDTO = (CompaniesXUsersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companiesXUsersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompaniesXUsersDTO{" +
            "id=" + getId() +
            ", companies=" + getCompanies() +
            ", user=" + getUser() +
            "}";
    }
}
