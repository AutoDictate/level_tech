package com.level.tech.mapper;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.entity.Model;
import com.level.tech.entity.Product;
import com.level.tech.exception.AlreadyExistsException;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.ModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ModelMapper {

    private final ModelRepository modelRepository;

    @Transactional
    public Model addModel(final Product product, final String name) {
        if (modelRepository.existsByNameAndProduct(name, product)) {
            throw new AlreadyExistsException("Model already exists for this product");
        }
        Model model = new Model(name, product);
        return modelRepository.save(model);
    }

    @Transactional
    public Model updateModel(final Long modelId, final String name) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(()-> new EntityNotFoundException("Model not found for the product"));
        model.setName(name);
        return modelRepository.save(model);
    }

    @Transactional(readOnly = true)
    public KeyValueDTO toModelDTO(final Model model) {
        return new KeyValueDTO(
                model.getId(),
                model.getName()
        );
    }

}
