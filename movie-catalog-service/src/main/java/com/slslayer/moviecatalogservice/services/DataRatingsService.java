package com.slslayer.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "getFallbackUserRatings")
    public UserRatings getUserRatings(String userId) {
        return restTemplate.getForObject("http://data-ratings-service/ratingsData/users/" + userId, UserRatings.class);
    }

    public UserRatings getFallbackUserRatings(String userId) {

        UserRatings userRatings = new UserRatings();
        userRatings.setUserRatings(Arrays.asList(new Rating("-1", -1)));

        return userRatings;
    }

}
