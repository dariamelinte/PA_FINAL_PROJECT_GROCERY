package com.example.grocery.api.prediction;

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
    @GetMapping("/product/{id}")
    public ResponseEntity<double[]> getForecast(@PathVariable String id){
        return new ResponseEntity(statsService.prediction(id, 3), HttpStatus.OK);
    }


    @GetMapping("/grocery/{id}")
    public ResponseEntity<double[]> getForecastForGrocery(@PathVariable String id){
        return new ResponseEntity(statsService.predictionForGrocery(id, 3), HttpStatus.OK);
    }
}
