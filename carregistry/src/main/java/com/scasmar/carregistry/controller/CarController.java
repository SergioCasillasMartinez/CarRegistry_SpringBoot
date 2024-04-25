package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.controller.dto.CarRequest;
import com.scasmar.carregistry.controller.dto.CarResponse;
import com.scasmar.carregistry.controller.mapper.CarMapper;
import com.scasmar.carregistry.model.Car;
import com.scasmar.carregistry.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;

    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public CompletableFuture<?> getAllCars(){
        try{
            CompletableFuture<List<Car>> carsList = carService.getAllCars();
            List<CarResponse> carResponseList = carsList.get().stream().map(carMapper::toResponse).toList();

            return CompletableFuture.completedFuture(ResponseEntity.ok().body(carResponseList));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

    @GetMapping("/getModel/{model}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> getCarPrice(@PathVariable String model){
        List<Car> carsList = carService.getCarModel(model);
        List<CarResponse> carResponseList = carsList.stream().map(carMapper::toResponse).toList();

        return ResponseEntity.ok().body(carResponseList);
    }

    @GetMapping("/getCSV")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> getCSV() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "cars.csv");

        byte[] csvBytes = carService.carsCSV().getBytes();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> addCar(@RequestBody CarRequest carRequest){
        CarResponse carResponse = carMapper.toResponse(carService.addCar(carMapper.toModel(carRequest)));

        return ResponseEntity.ok().body(carResponse);
    }

    @PostMapping(value = "/addCSV", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> addCsv(@RequestParam(value = "carCSV") MultipartFile carCSV){
        try{
            if (carCSV.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
            }else if (carCSV.getOriginalFilename().contains(".csv")){
                carService.addCarsCSV(carCSV);

                return ResponseEntity.ok("File added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/addList")
    @PreAuthorize("hasRole('VENDOR')")
    public CompletableFuture<?> addCarList(@RequestBody List<CarRequest> carRequestList){
        try{
            List<Car> carList = carRequestList.stream().map(carMapper::toModel).toList();

            return carService.addCarList(carList).thenApply(ResponseEntity::ok);
        }catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.ok().body(e));
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> updateCar(@PathVariable int id, @RequestBody CarRequest carRequest){
        carService.updateCar(id, carMapper.toModel(carRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> deleteCar(@PathVariable int id){
        carService.deleteCar(id);
        return ResponseEntity.ok().body("Deleted Car with id: " + id);
    }

}
