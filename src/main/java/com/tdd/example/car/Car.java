package com.tdd.example.car;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Car {
    private Long id;
    private String make;
    private String model;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        // Using only make and model since there is no DB.  This is just an example
        if (this == o) {
            return true;
        }

        if (!(o instanceof Car)) {
            return false;
        }

        final Car car = (Car) o;
        return Objects.equals(getMake(), car.getMake()) &&
                Objects.equals(getModel(), car.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMake(), getModel());
    }
}
