package com.level.tech.mapper;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.entity.Category;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryMapper {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category addCategory(final String name) {
        Category category = new Category(name);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(final Long id, final String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Category not found"));
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public KeyValueDTO categoryDTO(final Category category) {
        return new KeyValueDTO(category.getId(), category.getName());
    }

}
