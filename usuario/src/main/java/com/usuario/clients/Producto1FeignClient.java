package com.usuario.clients;

import com.usuario.dto.Producto1Dto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "producto1")
public interface Producto1FeignClient {

    @GetMapping("/producto1/user/{user_id}")
    public List<Producto1Dto> obtenerProductos1(@PathVariable("user_id") Integer user_id);
}
