package com.level.tech.service;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.entity.Category;
import com.level.tech.entity.Product;
import com.level.tech.exception.AlreadyExistsException;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.ProductMapper;
import com.level.tech.repository.CategoryRepository;
import com.level.tech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public KeyValueDTO addProduct(final Long categoryId, final String name) {
        if (productRepository.existsByName(name)) {
            throw new AlreadyExistsException("Category already exists");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new EntityNotFoundException("Category not found"));

        var savedProduct = productMapper.addProduct(category, name);
        return productMapper.productDTO(savedProduct);
    }

    @Override
    public KeyValueDTO updateProduct(final Long id, final String name) {
        var savedProduct = productMapper.updateProduct(id, name);
        return productMapper.productDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public KeyValueDTO getProduct(Long id) {
        return productRepository.findById(id)
                .map(productMapper::productDTO)
                .orElseThrow(()-> new EntityNotFoundException("Category not found"));
    }

    @Override
    public List<KeyValueDTO> getProductByCategoryId(final Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::productDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeyValueDTO> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productDTO)
                .toList();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Category not found"));
        productRepository.delete(product);
    }

    @Override
    public void deleteProducts(List<Long> productIds) {
        productIds.forEach(this::deleteProduct);
    }
}
