package com.scasmar.carregistry.service.converters;

import com.scasmar.carregistry.entity.BrandEntity;
import com.scasmar.carregistry.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandConverterTest {

    @Mock
    private BrandConverter brandConverter;

    @Test
    void test_toBrand(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1);
        brandEntity.setName("Brand");
        brandEntity.setWarranty(2);
        brandEntity.setCountry("Country");

        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Brand");
        brand.setWarranty(2);
        brand.setCountry("Country");

        when(brandConverter.toBrand(brandEntity)).thenReturn(brand);

        Brand result = brandConverter.toBrand(brandEntity);

        assertEquals(result.getId(), brand.getId());
        assertEquals(result.getName(), brand.getName());
        assertEquals(result.getWarranty(), brand.getWarranty());
        assertEquals(result.getCountry(), brand.getCountry());
    }

    @Test
    void test_toEntity(){
        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Brand");
        brand.setWarranty(2);
        brand.setCountry("Country");

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(1);
        brandEntity.setName("Brand");
        brandEntity.setWarranty(2);
        brandEntity.setCountry("Country");

        when(brandConverter.toEntity(brand)).thenReturn(brandEntity);

        BrandEntity result = brandConverter.toEntity(brand);

        assertEquals(result.getId(), brandEntity.getId());
        assertEquals(result.getName(), brandEntity.getName());
        assertEquals(result.getWarranty(), brandEntity.getWarranty());
        assertEquals(result.getCountry(), brandEntity.getCountry());
    }
}
