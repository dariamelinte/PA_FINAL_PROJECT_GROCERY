package com.example.grocery.api.prediction;

import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {
    @Autowired
    StatsService statsService;
    @GetMapping("/product/{id}/{months}")
    public ResponseEntity<Response<double[]>> getForecast(@PathVariable String id, @PathVariable int months){
        return new ResponseEntity(statsService.prediction(id, months), HttpStatus.OK);
    }


    @GetMapping("/grocery/{id}/{months}")
    public ResponseEntity<Response<double[]>> getForecastForGrocery(@PathVariable String id, @PathVariable int months){
        return new ResponseEntity(statsService.predictionForGrocery(id, months), HttpStatus.OK);
    }
}
