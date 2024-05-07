package com.scasmar.carregistry.service.impl;

import com.scasmar.carregistry.entity.BrandEntity;
import com.scasmar.carregistry.model.Car;
import com.scasmar.carregistry.entity.CarEntity;
import com.scasmar.carregistry.respository.BrandRepository;
import com.scasmar.carregistry.respository.CarRepository;
import com.scasmar.carregistry.service.CarService;
import com.scasmar.carregistry.service.converters.BrandConverter;
import com.scasmar.carregistry.service.converters.CarConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarConverter carConverter;
    private final BrandRepository brandRepository;
    private final BrandConverter brandConverter;
    private final String[] HEADERS = {"brand", "model", "millage", "price", "year", "description", "colour", "fuelType", "numDoor"};

    @Override
    @Async
    public CompletableFuture<List<Car>> getAllCars(){
        List<CarEntity> carEntityList = carRepository.findAll();
        List<Car> carList = carEntityList.stream().map(carConverter::toCar).toList();

        return CompletableFuture.completedFuture(carList);
    }

    @Override
    public List<Car> getCarModel(String model) {
        List<CarEntity> carEntityList = carRepository.findByModel(model);

        return carEntityList.stream().map(carConverter::toCar).toList();
    }

    @Override
    public Car addCar(Car car) { // If the brand does not exist the car does not add
        Optional<BrandEntity> brandEntity = brandRepository.findByName(car.getBrand().getName());
        if (brandEntity.isPresent()){
            CarEntity carEntity = carConverter.toEntity(car);
            carEntity.setBrandEntity(brandEntity.get());

            return carConverter.toCar(carRepository.save(carEntity));
        } else {
            return null;
        }
    }

    @Override
    @Async
    public CompletableFuture<List<Car>> addCarList(List<Car> carList){
        List<CarEntity> carEntityList = new ArrayList<>();
        carList.forEach(car ->{
            Optional<BrandEntity> brandEntity = brandRepository.findByName(car.getBrand().getName());
            if(brandEntity.isPresent()){
                CarEntity carEntity = carConverter.toEntity(car);
                carEntity.setBrandEntity(brandEntity.get());
                carEntityList.add(carEntity);
            }
        });
        List<CarEntity> savedCarEntityList = carRepository.saveAll(carEntityList);
        List<Car> savedCarList = savedCarEntityList.stream().map(carConverter::toCar).toList();

        return CompletableFuture.completedFuture(savedCarList);
    }

    @Override
    public Car updateCar(int id, Car car) {
        Optional<CarEntity> carEntityOptional = carRepository.findById(id);
        if(carEntityOptional.isPresent()){
            CarEntity carEntity = carConverter.toEntity(car);
            carEntity.setId(id);

            return carConverter.toCar(carRepository.save(carEntity));
        }else {
            return null;
        }
    }

    @Override
    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> addCarsCSV(MultipartFile file) {
        List<Car> carList = new ArrayList<>();

        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))){
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            Iterable<CSVRecord> csvRecordIterable = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecordIterable){
                Car car = new Car();
                Optional<BrandEntity> brandEntityOptional = brandRepository.findByName(csvRecord.get("brand"));

                brandEntityOptional.ifPresent(brandEntity -> car.setBrand(brandConverter.toBrand(brandEntity)));

                car.setModel(csvRecord.get("model"));
                car.setMillage(Integer.parseInt(csvRecord.get("millage")));
                car.setPrice(Double.parseDouble(csvRecord.get("price")));
                car.setYear(Integer.parseInt(csvRecord.get("year")));
                car.setDescription(csvRecord.get("description"));
                car.setColour(csvRecord.get("colour"));
                car.setFuelType(csvRecord.get("fuelType"));

                carList.add(car);
            }

            carRepository.saveAll(carList.stream().map(carConverter::toEntity).toList());

            return carList;
        }catch (IOException | NumberFormatException e){
            throw new RuntimeException("Failed to read the CSV file", e);
        }
    }

    @Override
    public String carsCSV() {
        List<Car> carList = carRepository.findAll().stream().map(carConverter::toCar).toList();
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(Arrays.toString(HEADERS)).append("\n");
        for (Car car : carList){
            csvContent.append(car.getId()).append(",")
                    .append(car.getBrand()).append(",")
                    .append(car.getModel()).append(",")
                    .append(car.getMillage()).append(",")
                    .append(car.getYear()).append(",")
                    .append(car.getDescription()).append(",")
                    .append(car.getColour()).append(",")
                    .append(car.getDescription()).append(",")
                    .append(car.getNumDoors()).append("\n");
        }

        return csvContent.toString();
    }
}
