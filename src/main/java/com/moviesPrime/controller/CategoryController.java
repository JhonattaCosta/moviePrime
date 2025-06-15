package com.moviesPrime.controller;


import com.moviesPrime.controller.request.CategoryRequest;
import com.moviesPrime.controller.response.CategoryResponse;
import com.moviesPrime.entity.Category;
import com.moviesPrime.mapper.CategoryMapper;
import com.moviesPrime.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/moviesprime/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Listar Categorias", description = "Lista todas as categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Lista de categoria"),
            @ApiResponse(responseCode = "400", description = "Não foi possivel listar categorias")
    })
    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> getAllCategory(){
        return  ResponseEntity.ok(categoryService.findAll()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList());
    }

    @Operation(summary = "Listar Categorias por id", description = "Lista categorias pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Categoria listada por id"),
            @ApiResponse(responseCode = "400", description = "Não foi possivel encontra a categoria")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryId(@PathVariable Long id){
        return categoryService.findCategoryId(id)
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar Categoria", description = "sava a categoria no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "categoria salva"),
            @ApiResponse(responseCode = "400", description = "Não foi possivel salvar categoria")
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@Valid @RequestBody CategoryRequest request){
        Category savedCategory = categoryService.saveCategory(CategoryMapper.toCategory(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryMapper.toCategoryResponse(savedCategory));
    }

    @Operation(summary = "Atualizar Categoria", description = "atualiza a categoria no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "categoria atualizada por id"),
            @ApiResponse(responseCode = "400", description = "Não foi possivel atualizar categoria")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryRequest request){
        return categoryService.updateCategory(id, CategoryMapper.toCategory(request))
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deleta Categoria", description = "deleta a categoria no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleta a categoria por id"),
            @ApiResponse(responseCode = "400", description = "Não foi possivel deletar categoria")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryId(@PathVariable Long id){
        Optional<Category> optCategory = categoryService.findCategoryId(id);
        if(optCategory.isPresent()){
            categoryService.deleteCategoryId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }


}
