package com.producto2.controller;

import com.producto2.entity.ProductTwo;
import com.producto2.error.ErrorResponse;
import com.producto2.service.Producto2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto2")
public class Producto2Controller {

    @Autowired
    private Producto2Service producto2Service;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ProductTwo> productos = producto2Service.obetenerTodos();
            if (productos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse("No hay productos", 204));
            }
            return ResponseEntity.ok(productos);

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al obtener los productos: "+e.getMessage(), 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductTwo productoNew) {
        try{
            ProductTwo producto = producto2Service.crearProducto(productoNew);
            if (producto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("El producto con el ID especificado ya existe o tiene los campos inválidados", 400));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(producto);

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al crear el producto: "+e.getMessage(), 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try{
            Optional<ProductTwo> productoOpt = producto2Service.findById(id);
            if (productoOpt.isPresent()) {
                return ResponseEntity.ok(productoOpt.get());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Producto no encontrado", 404));
            }

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al obtener el producto: "+e.getMessage(), 500));
        }
    }

    @GetMapping("{id}/imagen")
    public ResponseEntity<?> obtenerImagen(@PathVariable Integer id) {
        try {
            Optional<ProductTwo> productoOpt = producto2Service.findById(id);
            if (productoOpt.isPresent()) {
               String imagenURL = productoOpt.get().getImagenUrl();

               return ResponseEntity.status(HttpStatus.FOUND)
                       .header(HttpHeaders.LOCATION, imagenURL)
                       .build();

            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Producto no encontrado", 404));
            }
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al obtener el producto: "+e.getMessage(), 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody ProductTwo productoActualizado) {
        try{
            ProductTwo actualizado = producto2Service.actualizarProducto(id, productoActualizado);
            return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Error al actualizar el producto, revise los campos", 404));
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al actualizar el producto: "+e.getMessage(), 500));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            boolean eliminado = producto2Service.eliminar(id);
            return eliminado ? ResponseEntity.ok(new ErrorResponse("Producto eliminado", 200)) : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Producto no encontrado", 404));
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al eliminar el producto: "+e.getMessage(), 500));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable Integer id) {
        try{
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body(new ErrorResponse("ID de usuario no válido", 400));
            }
            Optional<List<ProductTwo>> productoOpt = producto2Service.obtenerPorUsuarioId(id);
            if (productoOpt.isPresent()) {
                return ResponseEntity.ok(productoOpt.get());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("El usuario no existe o no tiene productos", 404));
            }
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al obtener el producto: "+e.getMessage(), 500));
        }
    }

}
