package com.moviesPrime.service;

import com.moviesPrime.entity.Category;
import com.moviesPrime.repository.CategoryRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    @Test
    public void testGetAllCategory() {
        List<Category> categoriesfake = Arrays.asList(
                new Category(1L, "Ação"),
                new Category(2L, "Romance")
        );

        Mockito.when(repository.findAll()).thenReturn(categoriesfake);

        List<Category> categories = service.findAll();

        assertEquals(2, categories.size());

    }

    @Test
    public void testSaveCategory() {
        Category categoryfake = new Category(1L, "Ação");

        Mockito.when(repository.save(categoryfake)).thenReturn(categoryfake);

        Category savedCategory = service.saveCategory(categoryfake);

        assertEquals("Ação", savedCategory.getName());
    }

    @Test
    public void testFindCategoryId() {
        Optional<Category> categoryfake = Optional.of(new Category(1L, "Ação"));

        Mockito.when(repository.findById(1L)).thenReturn(categoryfake);

        Optional<Category> findCategoryId = service.findCategoryId(1L);

        assertTrue(findCategoryId.isPresent());
        assertEquals(categoryfake,findCategoryId);
    }

    @Test
    public void testUpdateCategory() {
        Category existingCategory = new Category(1L, "Ficção");
        Category categoryUpdate = new Category(1L, "Romance");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existingCategory));
        Mockito.when(repository.save(existingCategory)).thenReturn(existingCategory);

        Optional<Category> result = service.updateCategory(1L, categoryUpdate);

        assertTrue(result.isPresent());
        assertEquals("Romance", result.get().getName());

    }

    @Test
    public void testDeleteCategory(){
        Category categoryFake = new Category(1L, "Ação");
        service.deleteCategoryId(1L);
        Mockito.verify(repository).deleteById(1L);
    }

}
