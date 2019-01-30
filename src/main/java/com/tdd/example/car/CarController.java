package com.tdd.example.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RestController
public class CarController {

    private Set<Car> cars = new HashSet<>();

    @RequestMapping(value = "/car", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<?> putCar(@RequestBody(required = true) Car car) {
        // Add to DB not set this is just an example
        return cars.add(car) ? new ResponseEntity<>("Added Data", HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/car", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getCar(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model) {
        // Get from DB not set this is just an example
        if (make != null) {
            return cars.stream()
                    .filter(car -> Objects.equals(car.getMake(), make))
                    .findFirst()
                    .<ResponseEntity<?>>map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } else if (model != null) {
            return cars.stream()
                    .filter(car -> Objects.equals(car.getModel(), model))
                    .findFirst()
                    .<ResponseEntity<?>>map(car -> new ResponseEntity<>(car, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
