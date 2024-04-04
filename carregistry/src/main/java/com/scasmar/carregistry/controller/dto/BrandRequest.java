package com.scasmar.carregistry.controller.dto;

import lombok.Data;

@Data
public class BrandRequest {
    private String name;
    private Integer warranty;
    private String country;
}
