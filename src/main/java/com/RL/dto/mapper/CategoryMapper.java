package com.RL.dto.mapper;


import com.RL.domain.Category;
import com.RL.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);
    @Mapping(target="books",ignore=true)
    Category categoryDTOToCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> map(List<Category> category);

}