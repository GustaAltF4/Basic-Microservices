package com.usuario.controller;

import com.usuario.dto.Producto1Dto;
import com.usuario.dto.Producto2Dto;
import com.usuario.entity.User;
import com.usuario.error.ErrorResponse;
import com.usuario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<User> users = userService.obetenerTodos();
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse("No hay registros", 204));
            }
            return ResponseEntity.ok(users);

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al obtener los registros: "+e.getMessage(), 500));
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUser(@RequestBody User userNew) {
        try{
            User user = userService.crearUser(userNew);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("El usuario con el ID especificado ya existe o tiene los campos inválidados", 400));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(user);

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al crear el usuario: "+e.getMessage(), 500));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try{
            Optional<User> userOpt = userService.findById(id);
            if (userOpt.isPresent()) {
                return ResponseEntity.ok(userOpt.get());
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Usuario no encontrado", 404));
            }

        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al obtener el usuario: "+e.getMessage(), 500));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody User userActualizado) {
        try{
            User actualizado = userService.actualizarUser(id, userActualizado);
            return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Error al actualizar el usuario, revise los campos", 404));
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al actualizar el usuario: "+e.getMessage(), 500));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            boolean eliminado = userService.eliminar(id);
            return eliminado ? ResponseEntity.ok(new ErrorResponse("Usuario eliminado", 200)) : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Usuario no encontrado", 404));
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error al eliminar el usuario: "+e.getMessage(), 500));
        }
    }

    //ENDPOINTS DE PRODUCTOS
    @GetMapping("/{id}/producto2")
    public ResponseEntity<?> findProd2ByUser(@PathVariable Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("El ID del usuario no puede ser 0", 400));
            }

            List<Producto2Dto> productos2 = userService.obtenerProductos2(id);
            if (productos2!=null) {
                return ResponseEntity.ok(productos2);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Usuario no encontrado", 404));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener los productos: el servicio puede no estar disponible en este momento. "+e.getMessage(), 500));
        }

    }

    @GetMapping("/{id}/producto1")
    public ResponseEntity<?> findProd1ByUser(@PathVariable Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("El ID del usuario no puede ser 0", 400));
            }

            List<Producto1Dto> productos1 = userService.obtenerProductos1(id);
            if (productos1 !=null) {
                return ResponseEntity.ok(productos1);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Usuario no encontrado", 404));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener los productos: el servicio puede no estar disponible en este momento. "+e.getMessage(), 500));
        }

    }

    @GetMapping("/{id}/all")
    public ResponseEntity<?> findAllByUser(@PathVariable Integer id) {
        try {
            if (id <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("El ID del usuario no puede ser 0", 400));
            }

            Map<String, List<?>> productos1 = userService.obtenerTodos(id);
            if (productos1 !=null) {
                return ResponseEntity.ok(productos1);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Usuario no encontrado", 404));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al obtener los productos: el servicio puede no estar disponible en este momento. "+e.getMessage(), 500));
        }

    }
}
