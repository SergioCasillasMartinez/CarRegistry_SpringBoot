package com.scasmar.carregistry.service;

import com.scasmar.carregistry.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getAllBrands();
    Brand getBrandById(int id);
    List<Brand> getBrandByCountry(String country);
    Brand addBrand(Brand brand);
    Brand updateBrand(int id, Brand brand);
    void deleteBrand(int id);
}
