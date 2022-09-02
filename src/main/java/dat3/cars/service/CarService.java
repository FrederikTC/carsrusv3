package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CarService {

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    CarRepository carRepository;

    public List<CarResponse> getCars() {
        List<Car> cars = carRepository.findAll();

        List<CarResponse> response = cars.stream().map(car -> new CarResponse(car, false)).collect(Collectors.toList());

        return response;
    }

    public void deleteById(int Id) {
        carRepository.deleteById(Id);
    }

    public CarResponse addCar(CarRequest carRequest) {
        if (carRepository.existsById(carRequest.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car with this ID already exist");
        }

        Car newCar = CarRequest.getCarEntity(carRequest);
        newCar = carRepository.save(newCar);

        return new CarResponse(newCar, false);
    }

    public void editCar(CarRequest body, int Id) {
        Car car = carRepository.findById(Id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car with this username already exist"));

        if (body.getId() != (Id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change Id");
        }
        car.setId(body.getId());
        car.setModel(body.getModel());
        car.setBrand(body.getBrand());
        car.setBestDiscount(body.getBestDiscount());
        car.setPricePrDay(body.getPricePrDay());
    }

    public CarResponse findCarById(@PathVariable int Id) throws Exception {
        Car found = carRepository.findById(Id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Id"));
        return new CarResponse(found,false);

    }
}
