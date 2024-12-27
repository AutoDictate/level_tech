package com.level.tech.controller;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.dto.request.KeyValueRequest;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<KeyValueDTO> addProduct(
            @RequestBody @Valid KeyValueRequest request
    ) {
        return new ResponseEntity<>(
                productService.addProduct(request.getId(), request.getName()), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<KeyValueDTO> updateProduct(
            @RequestBody @Valid KeyValueRequest request
    ) {
        return new ResponseEntity<>(
                productService.updateProduct(request.getId(), request.getName()), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<KeyValueDTO> getProductById(
            @PathVariable(name = "productId") Long productId
    ) {
        return new ResponseEntity<>(
                productService.getProduct(productId), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/category")
    public ResponseEntity<List<KeyValueDTO>> getProductsByCategoryId(
            @PathVariable(name = "categoryId") Long categoryId
    ) {
        return new ResponseEntity<>(
                productService.getProductByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<KeyValueDTO>> getAllProducts() {
        return new ResponseEntity<>(
                productService.getProducts(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteProducts(
            @RequestBody List<Long> productIds
    ) {
        productService.deleteProducts(productIds);
        return new ResponseEntity<>(
                new ResponseData("Products deleted successfully") , HttpStatus.OK);
    }

}
