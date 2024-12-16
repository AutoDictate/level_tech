package com.level.tech.mapper;

import com.level.tech.dto.MasterProductDTO;
import com.level.tech.dto.request.MasterProductRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Category;
import com.level.tech.entity.MasterProduct;
import com.level.tech.entity.Model;
import com.level.tech.entity.Product;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.CategoryRepository;
import com.level.tech.repository.MasterProductRepository;
import com.level.tech.repository.ModelRepository;
import com.level.tech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterProductMapper {

    private final MasterProductRepository masterProductRepository;

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final CategoryMapper categoryMapper;

    private final ProductMapper productMapper;

    private final ModelMapper modelMapper;

    private final ModelRepository modelRepository;

    @Transactional
    public MasterProduct addMasterProduct(final MasterProductRequest request) {
        Category category = categoryRepository.findByName(request.getCategory())
                .orElseGet(()-> categoryMapper.addCategory(request.getCategory()));

        Product product = productRepository.findByProductNameAndCategory(request.getProduct(), category)
                .orElseGet(()-> productMapper.addProduct(category, request.getProduct()));

        if (!product.getCategory().getId().equals(category.getId())) {
            throw new IllegalArgumentException("Product does not belong to the specified Category");
        }

        Model model = modelRepository.findByNameAndProduct(request.getModel(), product)
                .orElseGet(()-> modelMapper.addModel(product, request.getModel()));

        if (!model.getProduct().getId().equals(product.getId())) {
            throw new IllegalArgumentException("Model does not belong to the specified Product");
        }

        MasterProduct masterProduct = new MasterProduct();
        masterProduct.setCategory(category);
        masterProduct.setProduct(product);
        masterProduct.setModel(model);
        masterProduct.setTax(request.getTax());
        masterProduct.setHsnAcs(request.getHsnAcs());
        masterProduct.setDescription(request.getDescription());

        return masterProductRepository.save(masterProduct);
    }

    @Transactional
    public MasterProduct updateMasterProduct(final Long id, final MasterProductRequest request) {
        MasterProduct masterProduct = masterProductRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Master Product not found"));

        Category category = categoryRepository.findByName(request.getCategory())
                .orElseGet(()-> categoryMapper.addCategory(request.getCategory()));

        Product product = productRepository.findByProductNameAndCategory(request.getProduct(), category)
                .orElseGet(()-> productMapper.addProduct(category, request.getProduct()));

        if (!product.getCategory().getId().equals(category.getId())) {
            throw new IllegalArgumentException("Product does not belong to the specified Category");
        }

        Model model = modelRepository.findByNameAndProduct(request.getModel(), product)
                .orElseGet(()-> modelMapper.addModel(product, request.getModel()));

        if (!model.getProduct().getId().equals(product.getId())) {
            throw new IllegalArgumentException("Model does not belong to the specified Product");
        }

        masterProduct.setCategory(category);
        masterProduct.setProduct(product);
        masterProduct.setModel(model);
        masterProduct.setTax(request.getTax());
        masterProduct.setHsnAcs(request.getHsnAcs());
        masterProduct.setDescription(request.getDescription());

        return masterProductRepository.save(masterProduct);
    }

    public MasterProductDTO toDTO(MasterProduct product) {
        MasterProductDTO dto = new MasterProductDTO();
        dto.setId(product.getId());
        dto.setCategory(categoryMapper.categoryDTO(product.getCategory()));
        dto.setProduct(productMapper.productDTO(product.getProduct()));
        dto.setModel(modelMapper.toModelDTO(product.getModel()));
        dto.setDescription(product.getDescription());
        dto.setTax(product.getTax());
        dto.setHsnAcs(product.getHsnAcs());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setCreatedBy(product.getCreatedBy());
        dto.setLastModifiedAt(product.getLastModifiedAt());

        return dto;
    }

    public PagedResponseDTO masterProductResponseDTO(
            final List<MasterProductDTO> allMasterProducts,
            final Integer pageNo,
            final Page<MasterProduct> masterProductList
    ) {
        return new PagedResponseDTO(
                allMasterProducts,
                pageNo + 1,
                masterProductList.getTotalElements(),
                masterProductList.getTotalPages()
        );
    }

}
