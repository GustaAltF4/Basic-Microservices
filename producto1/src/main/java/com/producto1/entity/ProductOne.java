package com.producto1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOne {


    private Integer id;
    private String nombre;
    private String description;
    private Double precio;
    private Integer user_id;


}
