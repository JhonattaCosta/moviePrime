package com.moviesPrime.mapper;

import com.moviesPrime.controller.request.CategoryRequest;
import com.moviesPrime.controller.response.CategoryResponse;
import com.moviesPrime.entity.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {

    public  static Category toCategory(CategoryRequest categoryRequest){
        return Category
                .builder()
                .name(categoryRequest.name())
                .build();
    }

    public  static CategoryResponse toCategoryResponse(Category category){
        return CategoryResponse
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
