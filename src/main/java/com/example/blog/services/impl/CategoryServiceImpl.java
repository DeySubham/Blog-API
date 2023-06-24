package com.example.blog.services.impl;

import com.example.blog.entities.Category;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CategoryDto;
import com.example.blog.repositories.CategoryRepo;
import com.example.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category createdCategory = categoryRepo.save(dtoToCategory(categoryDto));
        return categoryToDto(createdCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = categoryRepo.save(category);
        return categoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        categoryRepo.delete(category);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(category -> categoryToDto(category)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
        return categoryToDto(category);
    }

    private CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
}
