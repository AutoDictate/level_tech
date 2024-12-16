package com.level.tech.service;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.entity.Model;
import com.level.tech.entity.Product;
import com.level.tech.exception.AlreadyExistsException;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.ModelMapper;
import com.level.tech.repository.ModelRepository;
import com.level.tech.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ProductRepository productRepository;

    private final ModelRepository modelRepository;

    private final ModelMapper modelMapper;

    @Override
    public KeyValueDTO addModel(final Long productId, final String name) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException("Product not found"));

        if (modelRepository.existsByNameAndProduct(name, product)) {
            throw new AlreadyExistsException("Model already added");
        }
        var savedModel = modelMapper.addModel(product, name);
        return modelMapper.toModelDTO(savedModel);
    }

    @Override
    public KeyValueDTO updateModel(final Long modelId, final String name) {
        var savedModel = modelMapper.updateModel(modelId, name);
        return modelMapper.toModelDTO(savedModel);
    }

    @Override
    public KeyValueDTO getModel(Long modelId) {
        return modelRepository.findById(modelId)
                .map(modelMapper::toModelDTO)
                .orElseThrow(()-> new EntityNotFoundException("Model not found"));
    }

    @Override
    public List<KeyValueDTO> getModels() {
        return modelRepository.findAll()
                .stream()
                .map(modelMapper::toModelDTO)
                .toList();
    }

    @Override
    public List<KeyValueDTO> getModelByProductId(final Long productId) {
        return modelRepository.findAllByProductId(productId)
                .stream()
                .map(modelMapper::toModelDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deleteModel(final Long modelId) {
        Model model =  modelRepository.findById(modelId)
                .orElseThrow(()-> new EntityNotFoundException("Model not found"));
        modelRepository.delete(model);
    }

    @Override
    public void deleteModels(final List<Long> modelIds) {
        modelIds.forEach(this::deleteModel);
    }
}
