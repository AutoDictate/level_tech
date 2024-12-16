package com.level.tech.service;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.entity.Category;
import com.level.tech.exception.AlreadyExistsException;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.CategoryMapper;
import com.level.tech.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public KeyValueDTO addCategory(final String name) {
        if (categoryRepository.existsByName(name)) {
            throw new AlreadyExistsException("Category already exists");
        }
        var savedCategory = categoryMapper.addCategory(name);
        return categoryMapper.categoryDTO(savedCategory);
    }

    @Override
    public KeyValueDTO updateCategory(final Long id, final String name) {
        var savedCategory = categoryMapper.updateCategory(id, name);
        return categoryMapper.categoryDTO(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public KeyValueDTO getCategory(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::categoryDTO)
                .orElseThrow(()-> new EntityNotFoundException("Category not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeyValueDTO> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryDTO)
                .toList();
    }

    @Override
    public void deleteCategory(final Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }

    @Override
    public void deleteCategories(final List<Long> categoryIds) {
        categoryIds.forEach(this::deleteCategory);
    }
}
