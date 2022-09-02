package dat3.cars.api;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cars")
public class CarController {
    CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // Security
    @PostMapping // same as above when you are using @RestController
    public CarResponse addCar(@RequestBody CarRequest carRequest) {
        return carService.addCar(carRequest);
    }

    // Security
    @PutMapping("/{Id}")
    public ResponseEntity<Boolean> editCar(@RequestBody CarRequest body, @PathVariable int Id) {
        carService.editCar(body, Id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // Security ADMIN
    @GetMapping
    public List<CarResponse> getCars() {

        return carService.getCars();

    }

    // Security
    @DeleteMapping("/{Id}")
    public void deleteCarById(@PathVariable int Id) {
        carService.deleteById(Id);
    }

}


