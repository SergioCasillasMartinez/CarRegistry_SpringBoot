package com.scasmar.carregistry.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    private int id;
    private String name;
    private int warranty;
    private String country;
}
