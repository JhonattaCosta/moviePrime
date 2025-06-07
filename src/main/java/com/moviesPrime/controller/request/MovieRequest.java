package com.moviesPrime.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(@Schema(type = "String", description = "Titulo do Filme")
                           @NotEmpty(message = "Titulo do filme é obrigatório!") String title,
                           @Schema(type = "String", description = "Descrição do filme")
                           String description,
                           @Schema(type = "LocalDate", description = "Data de lançamento do filme")
                           @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
                           LocalDate releaseDate,
                           @Schema(type = "double", description = "Nota do filme")
                           double rating,
                           List<Long> categories,
                           List<Long> streamings
                           ) {
}
