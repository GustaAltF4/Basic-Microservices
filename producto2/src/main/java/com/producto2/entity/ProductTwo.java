package com.producto2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTwo {

    private Integer id;
    private String nombre;
    private String description;
    private Double precio;
    private String imagenUrl;
    private Integer user_id;
}
