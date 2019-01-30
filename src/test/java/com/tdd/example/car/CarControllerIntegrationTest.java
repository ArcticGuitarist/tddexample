package com.tdd.example.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void putReturnsAddedCarInformation() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            final Car car = new Car();
            car.setMake("Test Make Content is returned");
            car.setModel("Test Model Content is returned");
            mvc.perform(MockMvcRequestBuilders.put("/car")
                    .content(objectMapper.writeValueAsString(car))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(result -> {
                        String contentAsString = result.getResponse().getContentAsString();
                        Assert.assertNotNull(contentAsString);
                        Car readValue = objectMapper.readValue(contentAsString, Car.class);
                        Assert.assertEquals(car.getMake(), readValue.getMake());
                        Assert.assertEquals(car.getModel(), readValue.getModel());
                    });
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void putNewCarReturns201() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            final Car car = new Car();
            car.setMake("Test Make");
            car.setModel("Test Model");
            mvc.perform(MockMvcRequestBuilders.put("/car")
                    .content(objectMapper.writeValueAsString(car))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void putDuplicateCarsReturns409() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            final Car car = new Car();
            car.setMake("Test Make2");
            car.setModel("Test Model2");
            mvc.perform(MockMvcRequestBuilders.put("/car")
                    .content(objectMapper.writeValueAsString(car))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            mvc.perform(MockMvcRequestBuilders.put("/car")
                    .content(objectMapper.writeValueAsString(car))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isConflict());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void putNoCarReturns400() {
        try {
            mvc.perform(MockMvcRequestBuilders.put("/car")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getCarForNoMakeCarReturns204() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("/car")
                    .param("make", "Missing Make")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getCarByMakeReturnsCar() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            final Car car = new Car();
            car.setMake("Test Make for Getting Car");
            car.setModel("Test Model for Getting Car");
            mvc.perform(MockMvcRequestBuilders.put("/car")
                    .content(objectMapper.writeValueAsString(car))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            mvc.perform(MockMvcRequestBuilders.get("/car")
                    .param("make", car.getMake())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(car)));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getCarForNoModelCarReturns204() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("/car")
                    .param("model", "Missing model")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getCarByModelReturnsCar() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            final Car car = new Car();
            car.setMake("Test Make for Getting Car2");
            car.setModel("Test Model for Getting Car2");
            mvc.perform(MockMvcRequestBuilders.put("/car")
                    .content(objectMapper.writeValueAsString(car))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            mvc.perform(MockMvcRequestBuilders.get("/car")
                    .param("model", car.getModel())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(car)));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getWithoutMakeAndModelReturns400() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("/car")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getCarByModelAndMakeReturnsCar() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            final Car car = new Car();
            car.setMake("Test Make for Getting Car3");
            car.setModel("Test Model for Getting Car3");
            mvc.perform(MockMvcRequestBuilders.put("/car")
                    .content(objectMapper.writeValueAsString(car))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            mvc.perform(MockMvcRequestBuilders.get("/car")
                    .param("make", car.getMake())
                    .param("model", car.getModel())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(car)));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void getCarByModelAndMakeReturnsCarTrimsLeadingAndTrailingWhitespace() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            final Car car = new Car();
            car.setMake("Test Make for Getting Car4");
            car.setModel("Test Model for Getting Car4");
            mvc.perform(MockMvcRequestBuilders.put("/car")
                    .content(objectMapper.writeValueAsString(car))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());

            mvc.perform(MockMvcRequestBuilders.get("/car")
                    .param("make",  "     " + car.getMake() + "     ")
                    .param("model",  "     " + car.getModel() + "     ")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(car)));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }
}
