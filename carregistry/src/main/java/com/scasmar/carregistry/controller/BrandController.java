package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.controller.dto.BrandRequest;
import com.scasmar.carregistry.controller.dto.BrandResponse;
import com.scasmar.carregistry.controller.mapper.BrandMapper;
import com.scasmar.carregistry.model.Brand;
import com.scasmar.carregistry.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandMapper brandMapper;
    
    @GetMapping("/allBrands")
    public ResponseEntity<?> getAllBrands(){
        List<Brand> brandList = brandService.getAllBrands();
        List<BrandResponse> brandResponseList = new ArrayList<>();
        brandList.forEach(brand -> brandResponseList.add(brandMapper.toResponse(brand)));
        return ResponseEntity.ok().body(brandResponseList);
    }

    @GetMapping("/brandId/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable int id){
        BrandResponse brandResponse = brandMapper.toResponse(brandService.getBrandById(id));
        return ResponseEntity.ok().body(brandResponse);
    }

    @GetMapping("/brandCountry/{country}")
    public ResponseEntity<?> getBrandByCountry(@PathVariable String country){
        List<Brand> brandList = brandService.getBrandByCountry(country);
        List<BrandResponse> brandResponseList = new ArrayList<>();
        brandList.forEach(brand -> brandResponseList.add(brandMapper.toResponse(brand)));
        return ResponseEntity.ok().body(brandResponseList);
    }

    @PostMapping("/addBrand")
    public ResponseEntity<?> addBrand(@RequestBody BrandRequest brandRequest){
        BrandResponse brandResponse = brandMapper.toResponse(brandService.addBrand(brandMapper.toModel(brandRequest)));
        return ResponseEntity.ok().body(brandResponse);
    }

    @PutMapping("/updateBrand/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable int id, @RequestBody BrandRequest brandRequest){
        brandService.updateBrand(id, brandMapper.toModel(brandRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteBrand/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable int id){
        brandService.deleteBrand(id);
        return ResponseEntity.ok().body("Deleted Brand with id: " + id);
    }
}
