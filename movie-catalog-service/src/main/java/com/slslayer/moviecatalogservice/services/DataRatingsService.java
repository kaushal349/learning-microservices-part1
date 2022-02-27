package com.slslayer.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.slslayer.moviecatalogservice.models.Rating;
import com.slslayer.moviecatalogservice.models.UserRatings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
public class DataRatingsService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRatings",
            commandProperties = {
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="50"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }
    )
    public UserRatings getUserRatings(String userId) {
        return restTemplate.getForObject("http://data-ratings-service/ratingsData/users/" + userId, UserRatings.class);
    }

    public UserRatings getFallbackUserRatings(String userId) {

        UserRatings userRatings = new UserRatings();
        userRatings.setUserRatings(Arrays.asList(new Rating("-1", -1)));

        return userRatings;
    }

}
