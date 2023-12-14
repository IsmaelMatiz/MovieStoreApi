package com.moviestore.moviestoreapi.Controllers;

import com.moviestore.moviestoreapi.DAOs.DAOMovies;
import com.moviestore.moviestoreapi.DTOs.DTOMovies;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/Movie")
public class MovieController {

    @GetMapping
    public ResponseEntity<List<DTOMovies>> GetAll(){
        return ResponseEntity.ok(DAOMovies.ListMovies());
    }

    @GetMapping("{id}")
    public ResponseEntity<DTOMovies> GetById(@PathVariable("id") int id){
        return ResponseEntity.ok(DAOMovies.GetMovieById(id));
    }

    @PostMapping
    public ResponseEntity<String> AddAMovie(@RequestBody DTOMovies movie){
        var isSucces = DAOMovies.InsertMovie(movie);
        return ResponseEntity.ok(isSucces ? "Pelicula Insertada exitosamente" : "Error al publicar Pelicula intente de nuevo y verifique formato");
    }

    @PutMapping
    public ResponseEntity<String> UpdateMovie(@RequestBody DTOMovies movie){
        var isSucces = DAOMovies.UpdateMovie(movie.getId(),movie);
        return ResponseEntity.ok(isSucces ? "Pelicula Actualizada exitosamente" : "Error al Actualizar Pelicula intente de nuevo y verifique formato");
    }

    @DeleteMapping("{id}")
    public  ResponseEntity DeleteMovie(@PathVariable Integer id){
        var isSucces = DAOMovies.DeleteMovie(id);
        return ResponseEntity.ok(isSucces ? "Pelicula borrada exitosamente" : "Error al borrar Pelicula intente de nuevo y verifique id");
    }
}
