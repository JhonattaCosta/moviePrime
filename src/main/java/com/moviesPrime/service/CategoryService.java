package com.moviesPrime.service;

import com.moviesPrime.entity.Category;
import com.moviesPrime.repository.CategoryRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> findAll(){
        return repository.findAll();
    }

    public Category saveCategory(Category category){
        return repository.save(category);
    }

    public Optional<Category> findCategoryId(Long id){
        return repository.findById(id);
    }

    public Optional<Category> updateCategory (Long id, Category updatecategory){
        Optional<Category> optCategory = repository.findById(id);
        if (optCategory.isPresent()){
            Category category = optCategory.get();
            category.setName(updatecategory.getName());

            repository.save(category);
            return Optional.of(category);
        }

        return Optional.empty();
    }

    public void deleteCategoryId(Long id){
        repository.deleteById(id);
    }

}
