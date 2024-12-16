package com.level.tech.service;

import com.level.tech.dto.KeyValueDTO;

import java.util.List;

public interface CategoryService {

    KeyValueDTO addCategory(String name);

    KeyValueDTO updateCategory(Long id, String name);

    KeyValueDTO getCategory(Long id);

    List<KeyValueDTO> getCategories();

    void deleteCategory(Long id);

    void deleteCategories(List<Long> ids);

}
