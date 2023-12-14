package com.moviestore.moviestoreapi.DAOs;

import DbConnection.SingletonConnection;
import com.moviestore.moviestoreapi.DTOs.DTOMovies;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOMovies {
    public static List<DTOMovies> ListMovies() {
        List<DTOMovies> movies = new ArrayList<DTOMovies>();
        ResultSet moviesDB;
        try(Connection con = SingletonConnection.GetDBConnection()){
            PreparedStatement query = con.prepareStatement("" +
                    "SELECT * FROM peliculas");

            moviesDB = query.executeQuery();

            while (moviesDB.next())
            {
                DTOMovies movie = new DTOMovies();

                movie.setId(moviesDB.getInt("id"));
                movie.setIsbn(moviesDB.getString("ISBN"));
                movie.setNombre(moviesDB.getString("nombre"));
                movie.setDescripcion(moviesDB.getString("descripcion"));
                movie.setUnidadesDisponibles(moviesDB.getInt("unidades_disponibles"));
                movie.setCategoria(moviesDB.getInt("categorias_id"));

                movies.add(movie);
            }
        }catch (Exception e){
            System.out.println("Problema al acceder al ejecutar el query intentalo denuevo");
        }

        return movies;
    }

    public static DTOMovies GetMovieById(Integer id) {
        DTOMovies movie = new DTOMovies();
        ResultSet moviesDB;
        try(Connection con = SingletonConnection.GetDBConnection()){
            PreparedStatement query = con.prepareStatement("" +
                    "SELECT * FROM peliculas WHERE id = ?");

            query.setInt(1,id);

            moviesDB = query.executeQuery();

            while (moviesDB.next())
            {

                movie.setId(moviesDB.getInt("id"));
                movie.setIsbn(moviesDB.getString("ISBN"));
                movie.setNombre(moviesDB.getString("nombre"));
                movie.setDescripcion(moviesDB.getString("descripcion"));
                movie.setUnidadesDisponibles(moviesDB.getInt("unidades_disponibles"));
                movie.setCategoria(moviesDB.getInt("categorias_id"));

                return movie;
            }
        }catch (Exception e){
            System.out.println("Problema al acceder al obtener pelicula por Id error: "+e);
        }

        return movie;
    }

    public static Boolean InsertMovie(DTOMovies movieInfo){
        try(Connection con = SingletonConnection.GetDBConnection()){
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO peliculas(ISBN,nombre,descripcion,unidades_disponibles,categorias_id) " +
                            "VALUES(?,?,?,?,?)"
            );

            statement.setString(1,movieInfo.getIsbn());
            statement.setString(2,movieInfo.getNombre());
            statement.setString(3,movieInfo.getDescripcion());
            statement.setInt(4,movieInfo.getUnidadesDisponibles());
            statement.setInt(5,movieInfo.getCategoria());

            statement.execute();

            return true;

        }catch (Exception e){
            System.out.println("Problema al insertar pelicula error "+e);
        }

        return false;
    }

    public static Boolean UpdateMovie(Integer movieId, DTOMovies movieInfo){
        try(Connection con = SingletonConnection.GetDBConnection()){
            PreparedStatement statement = con.prepareStatement(
                    "UPDATE peliculas " +
                            "SET ISBN = ?, " +
                            "nombre = ?, " +
                            "descripcion = ?, " +
                            "unidades_disponibles = ?, " +
                            "categorias_id = ? " +
                            "WHERE id = ?"
            );

            statement.setString(1,movieInfo.getIsbn());
            statement.setString(2,movieInfo.getNombre());
            statement.setString(3,movieInfo.getDescripcion());
            statement.setInt(4,movieInfo.getUnidadesDisponibles());
            statement.setInt(5,movieInfo.getCategoria());
            statement.setInt(6,movieId);

            statement.execute();

            return true;

        }catch (Exception e){
            System.out.println("Error al Actualizar pelicula intentalo de nuevo "+e);
        }
        return false;
    }

    public static Boolean DeleteMovie(Integer movieId){
        try(Connection con = SingletonConnection.GetDBConnection())
        {
            // Deshabilita las restricciones de clave foránea
            PreparedStatement disableFK = con.prepareStatement("SET FOREIGN_KEY_CHECKS=0;");
            disableFK.execute();

            // Elimina la película
            PreparedStatement deleteStatement = con.prepareStatement("DELETE FROM peliculas WHERE id = ?");
            deleteStatement.setInt(1, movieId);
            deleteStatement.execute();

            // Vuelve a habilitar las restricciones de clave foránea
            PreparedStatement enableFK = con.prepareStatement("SET FOREIGN_KEY_CHECKS=1;");
            enableFK.execute();

            return true;

        }catch (Exception e){
            System.out.println("Error al eliminar pelicula error "+e);
        }

        return false;
    }
}

