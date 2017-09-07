package com.nnc.app.service;

import com.nnc.app.service.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Category.
 */
public interface CategoryService {

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    CategoryDTO save(CategoryDTO categoryDTO);

    /**
     *  Get all the categories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CategoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" category.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CategoryDTO findOne(String id);

    /**
     *  Delete the "id" category.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
