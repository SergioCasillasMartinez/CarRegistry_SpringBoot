package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.controller.dto.BrandRequest;
import com.scasmar.carregistry.controller.dto.BrandResponse;
import com.scasmar.carregistry.controller.mapper.BrandMapper;
import com.scasmar.carregistry.model.Brand;
import com.scasmar.carregistry.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandMapper brandMapper;
    
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public CompletableFuture<?> getAllBrands(){
        try{
            CompletableFuture<List<Brand>> brandList = brandService.getAllBrands();
            List<BrandResponse> brandResponseList = brandList.get().stream().map(brandMapper::toResponse).toList();
            return CompletableFuture.completedFuture(ResponseEntity.ok().body(brandResponseList));
        } catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> getBrandById(@PathVariable int id){
        BrandResponse brandResponse = brandMapper.toResponse(brandService.getBrandById(id));
        return ResponseEntity.ok().body(brandResponse);
    }

    @GetMapping("/{country}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<?> getBrandByCountry(@PathVariable String country){
        List<Brand> brandList = brandService.getBrandByCountry(country);
        List<BrandResponse> brandResponseList = new ArrayList<>();
        brandList.forEach(brand -> brandResponseList.add(brandMapper.toResponse(brand)));
        return ResponseEntity.ok().body(brandResponseList);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> addBrand(@RequestBody BrandRequest brandRequest){
        BrandResponse brandResponse = brandMapper.toResponse(brandService.addBrand(brandMapper.toModel(brandRequest)));
        return ResponseEntity.ok().body(brandResponse);
    }

    @PostMapping("/addList")
    @PreAuthorize("hasRole('VENDOR')")
    public CompletableFuture<?> addBrandList(@RequestBody List<BrandRequest> brandRequestList){
        try {
            List<Brand> brandList = brandRequestList.stream().map(brandMapper::toModel).toList();

            return CompletableFuture.completedFuture(ResponseEntity.ok().body(brandList));
        } catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> updateBrand(@PathVariable int id, @RequestBody BrandRequest brandRequest){
        brandService.updateBrand(id, brandMapper.toModel(brandRequest));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<?> deleteBrand(@PathVariable int id){
        brandService.deleteBrand(id);
        return ResponseEntity.ok().body("Deleted Brand with id: " + id);
    }
}
