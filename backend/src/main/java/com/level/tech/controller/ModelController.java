package com.level.tech.controller;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.dto.request.KeyValueRequest;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.ModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/model")
public class ModelController {

    private final ModelService modelService;

    @PostMapping
    public ResponseEntity<KeyValueDTO> addModel(
            @RequestBody @Valid KeyValueRequest request
    ) {
        return new ResponseEntity<>(
                modelService.addModel(request.getId(), request.getName()), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<KeyValueDTO> updateModel(
            @RequestBody @Valid KeyValueRequest request
    ) {
        return new ResponseEntity<>(
                modelService.updateModel(request.getId(), request.getName()), HttpStatus.OK);
    }

    @GetMapping("/{modelId}")
    public ResponseEntity<KeyValueDTO> getModelById(
            @PathVariable(name = "modelId") Long modelId
    ) {
        return new ResponseEntity<>(
                modelService.getModel(modelId), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<KeyValueDTO>> getModelByProductId(
            @PathVariable(name = "productId") Long productId
    ) {
        return new ResponseEntity<>(
                modelService.getModelByProductId(productId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<KeyValueDTO>> getAllModels() {
        return new ResponseEntity<>(
                modelService.getModels(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteCategories(
            @RequestBody List<Long> modelIds
    ) {
        modelService.deleteModels(modelIds);
        return new ResponseEntity<>(
                new ResponseData("Models deleted successfully") , HttpStatus.OK);
    }

}
