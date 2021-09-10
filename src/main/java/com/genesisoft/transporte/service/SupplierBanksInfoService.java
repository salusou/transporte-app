package com.genesisoft.transporte.service;

import com.genesisoft.transporte.domain.SupplierBanksInfo;
import com.genesisoft.transporte.repository.SupplierBanksInfoRepository;
import com.genesisoft.transporte.service.dto.SupplierBanksInfoDTO;
import com.genesisoft.transporte.service.mapper.SupplierBanksInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SupplierBanksInfo}.
 */
@Service
@Transactional
public class SupplierBanksInfoService {

    private final Logger log = LoggerFactory.getLogger(SupplierBanksInfoService.class);

    private final SupplierBanksInfoRepository supplierBanksInfoRepository;

    private final SupplierBanksInfoMapper supplierBanksInfoMapper;

    public SupplierBanksInfoService(
        SupplierBanksInfoRepository supplierBanksInfoRepository,
        SupplierBanksInfoMapper supplierBanksInfoMapper
    ) {
        this.supplierBanksInfoRepository = supplierBanksInfoRepository;
        this.supplierBanksInfoMapper = supplierBanksInfoMapper;
    }

    /**
     * Save a supplierBanksInfo.
     *
     * @param supplierBanksInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public SupplierBanksInfoDTO save(SupplierBanksInfoDTO supplierBanksInfoDTO) {
        log.debug("Request to save SupplierBanksInfo : {}", supplierBanksInfoDTO);
        SupplierBanksInfo supplierBanksInfo = supplierBanksInfoMapper.toEntity(supplierBanksInfoDTO);
        supplierBanksInfo = supplierBanksInfoRepository.save(supplierBanksInfo);
        return supplierBanksInfoMapper.toDto(supplierBanksInfo);
    }

    /**
     * Partially update a supplierBanksInfo.
     *
     * @param supplierBanksInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SupplierBanksInfoDTO> partialUpdate(SupplierBanksInfoDTO supplierBanksInfoDTO) {
        log.debug("Request to partially update SupplierBanksInfo : {}", supplierBanksInfoDTO);

        return supplierBanksInfoRepository
            .findById(supplierBanksInfoDTO.getId())
            .map(
                existingSupplierBanksInfo -> {
                    supplierBanksInfoMapper.partialUpdate(existingSupplierBanksInfo, supplierBanksInfoDTO);

                    return existingSupplierBanksInfo;
                }
            )
            .map(supplierBanksInfoRepository::save)
            .map(supplierBanksInfoMapper::toDto);
    }

    /**
     * Get all the supplierBanksInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierBanksInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplierBanksInfos");
        return supplierBanksInfoRepository.findAll(pageable).map(supplierBanksInfoMapper::toDto);
    }

    /**
     * Get one supplierBanksInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SupplierBanksInfoDTO> findOne(Long id) {
        log.debug("Request to get SupplierBanksInfo : {}", id);
        return supplierBanksInfoRepository.findById(id).map(supplierBanksInfoMapper::toDto);
    }

    /**
     * Delete the supplierBanksInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplierBanksInfo : {}", id);
        supplierBanksInfoRepository.deleteById(id);
    }
}
