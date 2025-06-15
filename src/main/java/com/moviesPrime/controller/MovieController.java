package com.moviesPrime.controller;

import com.moviesPrime.controller.request.MovieRequest;
import com.moviesPrime.controller.response.MovieResponse;
import com.moviesPrime.entity.Movie;
import com.moviesPrime.mapper.MovieMapper;
import com.moviesPrime.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("moviesprime/movie")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "Recurso responsavel pelo gerenciamento de filmes")
public class MovieController {
    private final MovieService movieService;


    @Operation(summary = "Salvar filme", description = "esse metodo salva um filme",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Filme salvo com sucesso",
                    content = @Content(schema = @Schema(implementation = MovieRequest.class))),
            @ApiResponse(responseCode = "400", description = "Não foi possivel salvar novo filme")
    })
    @PostMapping
    public ResponseEntity<MovieResponse> saveMovie(@Valid @RequestBody MovieRequest request) {
        Movie savedMovie = movieService.saveMovie(MovieMapper.toMovie(request));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(savedMovie));
    }

    @Operation(summary = "Listar Filmes", description = "Lista todos os filmes cadastrados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Filmes listados",
                    content = @Content(schema = @Schema(implementation = MovieResponse.class))),
            @ApiResponse(responseCode = "400",description = "Não foi possivel encontrar os filmes")
    })
    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAllMovies(){
        return ResponseEntity.ok(movieService.findAllMovies()
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());

    }

    @Operation(summary = "Listar um Filme", description = "Lista filme por ID",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Filme listado por ID",
                    content = @Content(schema = @Schema(implementation = MovieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Filme não foi encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findMovieId(@PathVariable Long id){
        return movieService.findMovieId(id)
                .map( movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Procurar filme por categoria", description = "Lista filmes pela categoria cadastrada",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filmes listados pela categoria",
                    content = @Content(schema = @Schema(implementation = MovieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Os filmes/filme não foi encontrado")
    })
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> findByCategory(@RequestParam Long category){
        return ResponseEntity.ok(movieService.findByCategory(category)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());

    }

    @Operation(summary = "Update filme", description = "Atualiza o filme escolhido",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizar filmes selecionados por ID",
                    content = @Content(schema = @Schema(implementation = MovieRequest.class))),
            @ApiResponse(responseCode = "400", description = "Não foi possivel realizar o update")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovieId(@PathVariable Long id, @Valid  @RequestBody MovieRequest request){
        return movieService.updateMovieId(id, MovieMapper.toMovie(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar filme", description = "Deleta o filme",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleta o filme por id",
                    content = @Content(schema = @Schema(implementation = MovieResponse.class))),
            @ApiResponse(responseCode = "400", description = "Não foi possivel deletar")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovieId (@PathVariable Long id){
        Optional<Movie> optMovie = movieService.findMovieId(id);
        if(optMovie.isPresent()){
            movieService.deleteMovieId(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        }
        return ResponseEntity.notFound().build();
    }
}
