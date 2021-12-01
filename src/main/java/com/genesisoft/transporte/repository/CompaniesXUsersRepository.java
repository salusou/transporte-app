package com.genesisoft.transporte.repository;

import com.genesisoft.transporte.domain.CompaniesXUsers;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompaniesXUsers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompaniesXUsersRepository extends JpaRepository<CompaniesXUsers, Long>, JpaSpecificationExecutor<CompaniesXUsers> {
    @Query("select companiesXUsers from CompaniesXUsers companiesXUsers where companiesXUsers.user.login = ?#{principal.username}")
    List<CompaniesXUsers> findByUserIsCurrentUser();
}
