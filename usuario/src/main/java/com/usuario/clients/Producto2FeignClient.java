package com.usuario.clients;

import com.usuario.dto.Producto2Dto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="producto2")
public interface Producto2FeignClient {

    @GetMapping("/producto2/user/{user_id}")
    public List<Producto2Dto> obtenerProductos2(@PathVariable("user_id") Integer user_id);
}
