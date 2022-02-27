package com.slslayer.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.slslayer.moviecatalogservice.models.Movie;
import com.slslayer.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    @Autowired
    private RestTemplate restTemplate;

    // implement bulkhead pattern
    @HystrixCommand(fallbackMethod = "getFallbackMovie",
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="20"), // max threads for this bucket is 20
                    @HystrixProperty(name="maxQueueSize", value="10") // can have 10 more extra requests in waiting queue
            }
    )
    public Movie getMovie(Rating rating) {
        return restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
    }

    public Movie getFallbackMovie(Rating rating) {
        return new Movie(rating.getMovieId(),"no movie found");
    }
}
