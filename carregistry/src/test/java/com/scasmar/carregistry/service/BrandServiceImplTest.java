package com.scasmar.carregistry.service;

import com.scasmar.carregistry.entity.BrandEntity;
import com.scasmar.carregistry.model.Brand;
import com.scasmar.carregistry.respository.BrandRepository;
import com.scasmar.carregistry.service.converters.BrandConverter;
import com.scasmar.carregistry.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {
    @InjectMocks
    private BrandServiceImpl brandService;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private BrandConverter brandConverter;

    @Test
    void test_getAllBrand() throws ExecutionException, InterruptedException {
        List<BrandEntity> brandEntityList = new ArrayList<>();
        when(brandRepository.findAll()).thenReturn(brandEntityList);

        List<Brand> brandList = new ArrayList<>();
        when(brandConverter.toBrand(any())).thenReturn(new Brand());

        CompletableFuture<List<Brand>> result = brandService.getAllBrands();

        assertEquals(brandList, result.get());
    }

    @Test
    void test_getBrandById(){
        final int BRAND_ID = 2;
        BrandEntity brandEntity = new BrandEntity();
        Brand brand = new Brand();

        when(brandRepository.findById(BRAND_ID)).thenReturn(Optional.of(brandEntity));
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandService.getBrandById(BRAND_ID);

        assertEquals(result, brand);
    }

    @Test
    void test_getBrandByCountry(){
        final String BRAND_COUNTRY = "DEU";
        BrandEntity brandEntity = new BrandEntity();
        Brand brand = new Brand();

        List<BrandEntity> resultWhen= new ArrayList<>();
        resultWhen.add(brandEntity);

        when(brandRepository.findByCountry(BRAND_COUNTRY)).thenReturn(resultWhen);
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        List<Brand> result = brandService.getBrandByCountry(BRAND_COUNTRY);

        List<Brand> resultService = new ArrayList<>();
        resultService.add(brand);

        assertEquals(result, resultService);
    }

    @Test
    void test_addBrand(){
        Brand brand = new Brand();
        BrandEntity brandEntity = new BrandEntity();

        when(brandConverter.toEntity(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandService.addBrand(brand);
        assertEquals(result, brand);
    }

    @Test
    void test_addBrandList(){

    }

    @Test
    void test_updateBrand_existBrand(){
        final int BRAND_ID = 2;
        Brand brand = new Brand();
        BrandEntity brandEntity = new BrandEntity();

        when(brandRepository.findById(BRAND_ID)).thenReturn(Optional.of(brandEntity));
        when(brandConverter.toEntity(brand)).thenReturn(brandEntity);
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandService.updateBrand(BRAND_ID, brand);

        assertEquals(result, brand);
    }

    @Test
    void test_updateBrand_noExistBrand(){
        final int BRAND_ID = 99;
        Brand brand = new Brand();
        BrandEntity brandEntity = new BrandEntity();

        when(brandRepository.findById(BRAND_ID)).thenReturn(Optional.of(brandEntity));
        when(brandConverter.toEntity(brand)).thenReturn(brandEntity);
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandService.updateBrand(BRAND_ID, brand);

        assertEquals(result, brand);
    }

    @Test
    void test_deleteBrand(){
        final int BRAND_ID = 2;

        brandService.deleteBrand(BRAND_ID);
    }
}
