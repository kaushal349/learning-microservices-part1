package com.slslayer.moviecatalogservice.resource;

import com.slslayer.moviecatalogservice.models.CatalogItem;
import com.slslayer.moviecatalogservice.models.Movie;
import com.slslayer.moviecatalogservice.models.Rating;
import com.slslayer.moviecatalogservice.models.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/catalog")
public class MovieCatalogService {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        UserRatings userRatings = restTemplate.getForObject("http://data-ratings-service/ratingsData/users/" + userId, UserRatings.class);

        System.out.println("Hello world");
        return userRatings.getUserRatings().stream().map(rating ->{
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test Description", rating.getRating());
        }).collect(Collectors.toList());
    }
}
