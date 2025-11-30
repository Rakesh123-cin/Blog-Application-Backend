package com.rakesh.blog_application.service.impl;

import com.rakesh.blog_application.entity.Category;
import com.rakesh.blog_application.exception.ResourceNotFoundException;
import com.rakesh.blog_application.payload.CategoryDto;
import com.rakesh.blog_application.repository.CategoryRepository;
import com.rakesh.blog_application.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto,Category.class);
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory,CategoryDto.class);
    }
    public CategoryDto getCategory(Long categoryId)
    {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","Id",categoryId));
        return modelMapper.map(category,CategoryDto.class);
    }
    public List<CategoryDto> getAllCategories()
    {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category->modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
    }
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto)
    {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","Id",categoryId));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return modelMapper.map(categoryRepository.save(category),CategoryDto.class);
    }
    public void deleteCategory(Long categoryId)
    {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","Id",categoryId));
        categoryRepository.delete(category);
    }
}
