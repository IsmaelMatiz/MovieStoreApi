package com.moviestore.moviestoreapi.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DTOMovies {
    private Integer id;
    private String isbn;
    private String nombre;
    private String descripcion;
    private Integer unidadesDisponibles;
    private Integer Categoria;
}
