package com.scasmar.carregistry.controller;

import com.scasmar.carregistry.controller.dto.BrandRequest;
import com.scasmar.carregistry.controller.dto.BrandResponse;
import com.scasmar.carregistry.controller.mapper.BrandMapper;
import com.scasmar.carregistry.model.Brand;
import com.scasmar.carregistry.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;
    private final BrandMapper brandMapper;
    
    @GetMapping("/findAll")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public CompletableFuture<ResponseEntity<List<BrandResponse>>> getAllBrands(){
        try{
            CompletableFuture<List<Brand>> brandList = brandService.getAllBrands();
            List<BrandResponse> brandResponseList = brandList.get().stream().map(brandMapper::toResponse).toList();

            return CompletableFuture.completedFuture(ResponseEntity.ok().body(brandResponseList));
        } catch (ExecutionException | InterruptedException e){
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable int id){
        try{
            BrandResponse brandResponse = brandMapper.toResponse(brandService.getBrandById(id));

            return ResponseEntity.ok().body(brandResponse);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getByCountry/{country}")
    @PreAuthorize("hasAnyRole('CLIENT','VENDOR')")
    public ResponseEntity<List<BrandResponse>> getBrandByCountry(@PathVariable String country){
        try{
            List<Brand> brandList = brandService.getBrandByCountry(country);
            List<BrandResponse> brandResponseList = new ArrayList<>();
            brandList.forEach(brand -> brandResponseList.add(brandMapper.toResponse(brand)));

            return ResponseEntity.ok().body(brandResponseList);
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<BrandResponse> addBrand(@RequestBody BrandRequest brandRequest){
        try {
            BrandResponse brandResponse = brandMapper.toResponse(brandService.addBrand(brandMapper.toModel(brandRequest)));

            return ResponseEntity.ok().body(brandResponse);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/addList")
    @PreAuthorize("hasRole('VENDOR')")
    public CompletableFuture<ResponseEntity<List<Brand>>> addBrandList(@RequestBody List<BrandRequest> brandRequestList){
        try {
            List<Brand> brandList = brandRequestList.stream().map(brandMapper::toModel).toList();

            return CompletableFuture.completedFuture(ResponseEntity.ok().body(brandList));
        } catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<BrandService> updateBrand(@PathVariable int id, @RequestBody BrandRequest brandRequest){
        try{
            brandService.updateBrand(id, brandMapper.toModel(brandRequest));

            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('VENDOR')")
    public ResponseEntity<String> deleteBrand(@PathVariable int id){
        brandService.deleteBrand(id);

        return ResponseEntity.ok().body("Deleted Brand with id: " + id);
    }
}
