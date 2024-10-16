package com.usuario.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Producto2Dto {

    //private Integer id;
    private String nombre;
    private String description;
    private Double precio;
    private String imagenUrl;
    private Integer user_id;
}
