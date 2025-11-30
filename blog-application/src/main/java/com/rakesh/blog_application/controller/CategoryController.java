package com.rakesh.blog_application.controller;

import com.rakesh.blog_application.payload.CategoryDto;
import com.rakesh.blog_application.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name="CRUD REST APIs for Category resource")
public class CategoryController {
    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService=categoryService;
    }
    // add category Rest api
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create a category",
            description = "Create Category REST API is used to create a new category by admin"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Category created successfully"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        return new ResponseEntity<>(categoryService.addCategory(categoryDto),HttpStatus.CREATED);
    }
    // get category by id rest api
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get a category",
            description = "Get Category REST API is used to get a category by Id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category retrieved successfully"
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId)
    {
        return new ResponseEntity<>(categoryService.getCategory(categoryId),HttpStatus.OK);
    }
    // get all categories Rest api
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get all categories",
            description = "Get All Categories REST API is used to get all categories from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Categories retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }
    // update category
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Update a category",
            description = "Update Category REST API is used to update a category by Id by Admin"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category updated successfully"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(value = "id") Long categoryId, @Valid @RequestBody CategoryDto categoryDto )
    {
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, categoryDto),HttpStatus.OK);
    }
    // delete category
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Delete a category",
            description = "Delete Category REST API is used to delete a category by Id by Admin"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Category deleted successfully"
    )
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId)
    {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted successfully",HttpStatus.OK);
    }

}
