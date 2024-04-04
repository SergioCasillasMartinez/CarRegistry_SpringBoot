package com.scasmar.carregistry.service.impl;

import com.scasmar.carregistry.model.Brand;
import com.scasmar.carregistry.entity.BrandEntity;
import com.scasmar.carregistry.respository.BrandRepository;
import com.scasmar.carregistry.service.BrandService;
import com.scasmar.carregistry.service.converts.BrandConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandConverter brandConverter;

    @Override
    public List<Brand> getAllBrands() {
        List<BrandEntity> brandEntityList = brandRepository.findAll();

        return brandEntityList.stream().map(brandConverter::toBrand).toList();
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
