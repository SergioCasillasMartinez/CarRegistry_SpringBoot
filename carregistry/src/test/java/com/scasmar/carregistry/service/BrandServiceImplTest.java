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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {
    @InjectMocks
    private BrandServiceImpl brandService;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private BrandConverter brandConverter;

    @Test
    void test_getBrandByCountry(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setCountry("DEU");
        Brand brand = new Brand();

        List<BrandEntity> resultWhen= new ArrayList<>();
        resultWhen.add(brandEntity);

        when(brandRepository.findByCountry("DEU")).thenReturn(resultWhen);
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        List<Brand> result = brandService.getBrandByCountry("DEU");

        List<Brand> resultService = new ArrayList<>();
        resultService.add(brand);

        assertEquals(result, resultService);
    }

    @Test
    void test_getBrandById(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(2);
        Brand brand = new Brand();

        when(brandRepository.findById(2)).thenReturn(Optional.of(brandEntity));
        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandService.getBrandById(2);

        assertEquals(result, brand);
    }


}
