package com.slslayer.moviecatalogservice.resource;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.slslayer.moviecatalogservice.models.CatalogItem;
import com.slslayer.moviecatalogservice.models.Movie;
import com.slslayer.moviecatalogservice.models.Rating;
import com.slslayer.moviecatalogservice.models.UserRatings;
import com.slslayer.moviecatalogservice.services.DataRatingsService;
import com.slslayer.moviecatalogservice.services.MovieInfoService;
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
    private MovieInfoService movieInfoService;

    @Autowired
    private DataRatingsService dataRatingsService;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        UserRatings userRatings = dataRatingsService.getUserRatings(userId);

        return userRatings.getUserRatings().stream().map(rating ->{
            Movie movie = movieInfoService.getMovie(rating);
            return new CatalogItem(movie.getName(), "Test Description", rating.getRating());
        }).collect(Collectors.toList());
    }

}
