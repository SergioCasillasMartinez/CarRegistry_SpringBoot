package com.scasmar.carregistry.controller.dto;

import lombok.Data;

@Data
public class BrandRequest {
    private Integer id;
    private String name;
    private Integer warranty;
    private String country;
}
