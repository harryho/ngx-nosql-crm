package com.nnc.app.service.impl;

import com.nnc.app.service.CategoryService;
import com.nnc.app.domain.Category;
import com.nnc.app.repository.CategoryRepository;
import com.nnc.app.service.dto.CategoryDTO;
import com.nnc.app.service.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Category.
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    /**
     * Save a category.
     *
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        log.debug("Request to save Category : {}", categoryDTO);
        Category category = categoryMapper.toEntity(categoryDTO);
        category = categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    /**
     *  Get all the categories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<CategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable)
            .map(categoryMapper::toDto);
    }

    /**
     *  Get one category by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public CategoryDTO findOne(String id) {
        log.debug("Request to get Category : {}", id);
        Category category = categoryRepository.findOne(id);
        return categoryMapper.toDto(category);
    }

    /**
     *  Delete the  category by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.delete(id);
    }
}
