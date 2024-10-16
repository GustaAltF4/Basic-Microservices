package com.producto1.controller;

import com.producto1.Error.ErrorResponse;
import com.producto1.entity.ProductOne;
import com.producto1.service.Producto1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto1")
public class Producto1Controller {

    @Autowired
    private Producto1Service producto1Service;

    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            List<ProductOne> productos = producto1Service.obetenerTodos();
            if (productos.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse("No hay productos", 204));
            }
            return ResponseEntity.ok(productos);
        }catch (IOException e){
            System.out.println("erroes:"+ Arrays.toString(e.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al obtener los productos: "+e.getMessage(), 500));
        }

    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductOne productoNew) {
        try {
            ProductOne creado = producto1Service.crearProducto(productoNew);
            if (creado == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("El producto con el ID especificado ya existe o tiene los campos inválidados", 400));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al crear el producto: "+e.getMessage(), 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            Optional<ProductOne> productoOpt = producto1Service.findById(id);

            // Comprobar si el producto está presente
            if (productoOpt.isPresent()) {
                return ResponseEntity.ok(productoOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Producto no encontrado", 404));
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener el producto: " + e.getMessage(), 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody ProductOne productoActualizado) {
        try {
            ProductOne actualizado = producto1Service.actualizarProducto(id, productoActualizado);
            return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Error al actualizar el producto, revise los campos", 404));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al actualizar el producto: " + e.getMessage(), 500));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            boolean eliminado = producto1Service.eliminar(id);
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
            Optional<List<ProductOne>> productoOpt = producto1Service.obtenerPorUsuarioId(id);
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

