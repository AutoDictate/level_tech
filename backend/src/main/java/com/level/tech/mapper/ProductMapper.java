package com.level.tech.mapper;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.entity.Category;
import com.level.tech.entity.Product;
import com.level.tech.exception.AlreadyExistsException;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.CategoryRepository;
import com.level.tech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Transactional
    public Product addProduct(final Category category, final String name) {

        if (productRepository.findByProductNameAndCategory(name, category).isPresent()) {
            throw new AlreadyExistsException("Product already added in this category");
        }
        Product product = new Product(name, category);
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(final Long id, final String name) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));
        product.setName(name);
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public KeyValueDTO productDTO(final Product product) {
        return new KeyValueDTO(product.getId(), product.getName());
    }

}
