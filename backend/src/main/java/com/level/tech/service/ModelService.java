package com.level.tech.service;

import com.level.tech.dto.KeyValueDTO;

import java.util.List;

public interface ModelService {

    KeyValueDTO addModel(final Long productId, final String name);

    KeyValueDTO updateModel(final Long modelId, final String name);

    KeyValueDTO getModel(final Long modelId);

    List<KeyValueDTO> getModels();

    List<KeyValueDTO> getModelByProductId(Long productId);

    void deleteModel(Long id);

    void deleteModels(List<Long> modelIds);

}
