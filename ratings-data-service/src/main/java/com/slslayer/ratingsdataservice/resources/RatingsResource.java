package com.slslayer.ratingsdataservice.resources;

import com.slslayer.ratingsdataservice.models.Rating;
import com.slslayer.ratingsdataservice.models.UserRatings;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsData")
public class RatingsResource {

    private List<Rating> ratings = Arrays.asList(new Rating("1234", 5), new Rating("4567", 3));

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 4);
    }

    @GetMapping("/users/{userId}")
    public UserRatings getUserRatings(@PathVariable("userId") String userId){
        UserRatings userRatings = new UserRatings();
        userRatings.setUserRatings(ratings);
        return userRatings;
    }
}
