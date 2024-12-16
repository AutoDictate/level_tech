package com.level.tech.controller;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<KeyValueDTO> addCategory(
            @RequestBody String name
    ) {
        return new ResponseEntity<>(
                categoryService.addCategory(name), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<KeyValueDTO> updateCategory(
            @RequestParam(name = "categoryId") Long categoryId,
            @RequestParam(name = "name") String name
    ) {
        return new ResponseEntity<>(
                categoryService.updateCategory(categoryId, name), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<KeyValueDTO> getCategoryById(
            @PathVariable(name = "categoryId") Long categoryId
    ) {
        return new ResponseEntity<>(
                categoryService.getCategory(categoryId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<KeyValueDTO>> getAllCategory() {
        return new ResponseEntity<>(
                categoryService.getCategories(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteCategories(
            @RequestBody List<Long> categoryIds
    ) {
        categoryService.deleteCategories(categoryIds);
        return new ResponseEntity<>(
                new ResponseData("Categories deleted successfully") , HttpStatus.OK);
    }

}
