package com.tdd.example.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
public class CarController {

    private Set<Car> cars = new HashSet<>();

    @RequestMapping(value = "/car", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<?> putCar(@RequestBody(required = true) Car car) {
        // Add to DB not set this is just an example
        return cars.add(car) ? new ResponseEntity<>(HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
