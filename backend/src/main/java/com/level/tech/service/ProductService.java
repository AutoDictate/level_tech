package com.level.tech.service;

import com.level.tech.dto.KeyValueDTO;

import java.util.List;

public interface ProductService {

    KeyValueDTO addProduct(Long categoryId, String name);

    KeyValueDTO updateProduct(Long id, String name);

    KeyValueDTO getProduct(Long id);

    List<KeyValueDTO> getProductByCategoryId(Long categoryId);

    List<KeyValueDTO> getProducts();

    void deleteProduct(Long id);

    void deleteProducts(List<Long> ids);

}
