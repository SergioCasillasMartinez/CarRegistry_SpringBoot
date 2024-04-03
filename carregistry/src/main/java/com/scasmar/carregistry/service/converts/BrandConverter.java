package com.scasmar.carregistry.service.converts;

import com.scasmar.carregistry.model.Brand;
import com.scasmar.carregistry.entity.BrandEntity;
import org.springframework.stereotype.Component;

@Component
public class BrandConverter {
    public Brand toBrand(BrandEntity entity){
        Brand brand = new Brand();
        brand.setId(entity.getId());
        brand.setName(entity.getName());
        brand.setWarranty(entity.getWarranty());
        brand.setCountry(entity.getCountry());

        return brand;
    }

    public BrandEntity toEntity(Brand entity){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setId(entity.getId());
        brandEntity.setName(entity.getName());
        brandEntity.setWarranty(entity.getWarranty());
        brandEntity.setCountry(entity.getCountry());

        return brandEntity;
    }
}
