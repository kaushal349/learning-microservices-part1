package com.slslayer.movieinfoservice.resource;


import com.slslayer.movieinfoservice.models.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/movies")
public class MovieResource {

        @GetMapping("/{movieId}")
        public Movie getMovieInfo(@PathVariable("movieId") String movieId){
            return new Movie(movieId, "test name");
        }

}
