package com.scasmar.carregistry.service.impl;

import com.scasmar.carregistry.model.Brand;
import com.scasmar.carregistry.entity.BrandEntity;
import com.scasmar.carregistry.respository.BrandRepository;
import com.scasmar.carregistry.service.BrandService;
import com.scasmar.carregistry.service.converts.BrandConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandConverter brandConverter;

    @Override
    @Async
    public CompletableFuture<List<Brand>> getAllBrands() {
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        List<Brand> brandList = brandEntityList.stream().map(brandConverter::toBrand).toList();

        return CompletableFuture.completedFuture(brandList);
    }

    @Override
    public Brand getBrandById(int id){
        Optional<BrandEntity> brandEntityOptional = brandRepository.findById(id);
        if (brandEntityOptional.isPresent()){
            return brandConverter.toBrand(brandEntityOptional.get());
        } else {
            return null;
        }
    }

    @Override
    public List<Brand> getBrandByCountry(String country) {
        List<BrandEntity> brandEntityList = brandRepository.findByCountry(country);

        return brandEntityList.stream().map(brandConverter::toBrand).toList();
    }

    @Override
    public Brand addBrand(Brand brand) {
        BrandEntity brandEntity = brandRepository.save(brandConverter.toEntity(brand));
        return brandConverter.toBrand(brandEntity);
    }

    @Override
    @Async
    public CompletableFuture<List<Brand>> addBrandList(List<Brand> brandList) {
        List<BrandEntity> brandEntityList = brandList.stream().map(brandConverter::toEntity).toList();
        List<BrandEntity> savedBrandEntityList = brandRepository.saveAll(brandEntityList);
        List<Brand> savedBrandList = savedBrandEntityList.stream().map(brandConverter::toBrand).toList();

        return CompletableFuture.completedFuture(savedBrandList);
    }


    @Override
    public Brand updateBrand(int id, Brand brand) {
        Optional<BrandEntity> brandEntityOptional = brandRepository.findById(id);
        if (brandEntityOptional.isPresent()){
            BrandEntity brandEntity = brandConverter.toEntity(brand);
            return brandConverter.toBrand(brandEntity);
        }
        return null;
    }

    @Override
    public void deleteBrand(int id) {
        brandRepository.deleteById(id);
    }
}
