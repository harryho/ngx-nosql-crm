package com.nnc.app.service.mapper;

import com.nnc.app.domain.*;
import com.nnc.app.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper <CategoryDTO, Category> {
    
    

}
