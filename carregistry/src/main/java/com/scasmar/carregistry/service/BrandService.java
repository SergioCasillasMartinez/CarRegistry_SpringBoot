package com.scasmar.carregistry.service;

import com.scasmar.carregistry.model.Brand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BrandService {
    CompletableFuture<List<Brand>> getAllBrands();
    Brand getBrandById(int id);
    List<Brand> getBrandByCountry(String country);
    Brand addBrand(Brand brand);
    CompletableFuture<List<Brand>> addBrandList(List<Brand> brandList);
    Brand updateBrand(int id, Brand brand);
    void deleteBrand(int id);
}
