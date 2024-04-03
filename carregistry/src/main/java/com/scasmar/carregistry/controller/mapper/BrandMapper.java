package com.scasmar.carregistry.controller.mapper;

import com.scasmar.carregistry.controller.dto.BrandRequest;
import com.scasmar.carregistry.controller.dto.BrandResponse;
import com.scasmar.carregistry.model.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    public Brand toModel(BrandRequest model){
        Brand brand = new Brand();
        brand.setId(model.getId());
        brand.setName(model.getName());
        brand.setWarranty(model.getWarranty());
        brand.setCountry(model.getCountry());

        return brand;
    }

    public BrandResponse toResponse(Brand entity){
        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(entity.getId());
        brandResponse.setName(entity.getName());
        brandResponse.setWarranty(entity.getWarranty());
        brandResponse.setCountry(entity.getCountry());

        return brandResponse;
    }
}
