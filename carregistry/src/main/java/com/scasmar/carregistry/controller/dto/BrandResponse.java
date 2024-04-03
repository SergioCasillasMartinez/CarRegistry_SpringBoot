package com.scasmar.carregistry.controller.dto;

import lombok.Data;

@Data
public class BrandResponse {
    private Integer id;
    private String name;
    private Integer warranty;
    private String country;
}
