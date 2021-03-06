package com.itescia.itbadge.service.impl;

import com.itescia.itbadge.service.BadgeageService;
import com.itescia.itbadge.domain.Badgeage;
import com.itescia.itbadge.repository.BadgeageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Badgeage.
 */
@Service
@Transactional
public class BadgeageServiceImpl implements BadgeageService {

    private final Logger log = LoggerFactory.getLogger(BadgeageServiceImpl.class);

    private final BadgeageRepository badgeageRepository;

    public BadgeageServiceImpl(BadgeageRepository badgeageRepository) {
        this.badgeageRepository = badgeageRepository;
    }

    /**
     * Save a badgeage.
     *
     * @param badgeage the entity to save
     * @return the persisted entity
     */
    @Override
    public Badgeage save(Badgeage badgeage) {
        log.debug("Request to save Badgeage : {}", badgeage);        return badgeageRepository.save(badgeage);
    }

    /**
     * Get all the badgeages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Badgeage> findAll(Pageable pageable) {
        log.debug("Request to get all Badgeages");
        return badgeageRepository.findAll(pageable);
    }


    /**
     * Get one badgeage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Badgeage> findOne(Long id) {
        log.debug("Request to get Badgeage : {}", id);
        return badgeageRepository.findById(id);
    }

    /**
     * Delete the badgeage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Badgeage : {}", id);
        badgeageRepository.deleteById(id);
    }
}
