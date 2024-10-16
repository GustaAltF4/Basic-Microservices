package com.usuario.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Producto1Dto {

    //private Integer id;
    private String nombre;
    private String description;
    private Double precio;
    private Integer user_id;


}
