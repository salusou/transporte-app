package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.*; // for static metamodels
import com.genesisoft.transporte.domain.SupplierBanksInfo;
import com.genesisoft.transporte.repository.SupplierBanksInfoRepository;
import com.genesisoft.transporte.service.criteria.SupplierBanksInfoCriteria;
import com.genesisoft.transporte.service.dto.SupplierBanksInfoDTO;
import com.genesisoft.transporte.service.mapper.SupplierBanksInfoMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link SupplierBanksInfo} entities in the database.
 * The main input is a {@link SupplierBanksInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierBanksInfoDTO} or a {@link Page} of {@link SupplierBanksInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierBanksInfoQueryService extends QueryService<SupplierBanksInfo> {

    private final Logger log = LoggerFactory.getLogger(SupplierBanksInfoQueryService.class);

    private final SupplierBanksInfoRepository supplierBanksInfoRepository;

    private final SupplierBanksInfoMapper supplierBanksInfoMapper;

    public SupplierBanksInfoQueryService(
        SupplierBanksInfoRepository supplierBanksInfoRepository,
        SupplierBanksInfoMapper supplierBanksInfoMapper
    ) {
        this.supplierBanksInfoRepository = supplierBanksInfoRepository;
        this.supplierBanksInfoMapper = supplierBanksInfoMapper;
    }

    /**
     * Return a {@link List} of {@link SupplierBanksInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierBanksInfoDTO> findByCriteria(SupplierBanksInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierBanksInfo> specification = createSpecification(criteria);
        return supplierBanksInfoMapper.toDto(supplierBanksInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplierBanksInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierBanksInfoDTO> findByCriteria(SupplierBanksInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierBanksInfo> specification = createSpecification(criteria);
        return supplierBanksInfoRepository.findAll(specification, page).map(supplierBanksInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierBanksInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierBanksInfo> specification = createSpecification(criteria);
        return supplierBanksInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierBanksInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupplierBanksInfo> createSpecification(SupplierBanksInfoCriteria criteria) {
        Specification<SupplierBanksInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SupplierBanksInfo_.id));
            }
            if (criteria.getSupplierBankCode() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSupplierBankCode(), SupplierBanksInfo_.supplierBankCode));
            }
            if (criteria.getSupplierBankName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSupplierBankName(), SupplierBanksInfo_.supplierBankName));
            }
            if (criteria.getSupplierBankBranchCode() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSupplierBankBranchCode(), SupplierBanksInfo_.supplierBankBranchCode)
                    );
            }
            if (criteria.getSupplierBankAccountNumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSupplierBankAccountNumber(), SupplierBanksInfo_.supplierBankAccountNumber)
                    );
            }
            if (criteria.getSupplierBankUserName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSupplierBankUserName(), SupplierBanksInfo_.supplierBankUserName)
                    );
            }
            if (criteria.getSupplierBankPixKey() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSupplierBankPixKey(), SupplierBanksInfo_.supplierBankPixKey));
            }
            if (criteria.getSupplierBankUserNumber() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getSupplierBankUserNumber(), SupplierBanksInfo_.supplierBankUserNumber)
                    );
            }
            if (criteria.getSuppliersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSuppliersId(),
                            root -> root.join(SupplierBanksInfo_.suppliers, JoinType.LEFT).get(Suppliers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
