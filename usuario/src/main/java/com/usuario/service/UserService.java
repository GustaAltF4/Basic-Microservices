package com.usuario.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuario.clients.Producto1FeignClient;
import com.usuario.clients.Producto2FeignClient;
import com.usuario.dto.Producto1Dto;
import com.usuario.dto.Producto2Dto;
import com.usuario.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private ObjectMapper mapper;
    @Value("${user.json.path}")
    private String jsonFilePath;

    @Autowired
    private Producto2FeignClient producto2FeignClient;
    @Autowired
    private Producto1FeignClient producto1FeignClient;

    public List<User> obetenerTodos() throws IOException {
        Path path = Path.of(jsonFilePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
            Files.write(path, mapper.writeValueAsBytes(new ArrayList<User>()),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return new ArrayList<>();
        }

        try (InputStream json = Files.newInputStream(path)) {
            return new ArrayList<>(List.of(mapper.readValue(json, User[].class)));
        }

    }

    //guardar
    public void guardar(List<User> user) throws IOException {
        Path path = Path.of(jsonFilePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        Files.write(path, mapper.writeValueAsBytes(user),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    //Crear
    public User crearUser(User userNew) throws IOException {
        if (userNew.getId() == null ||
                userNew.getNombre() == null ||
                userNew.getEmail() == null ) {
            return null;
        }
        List<User> usuarios = obetenerTodos();

        Optional<User> productExistente = usuarios.stream()
                .filter(product -> product.getId().equals(userNew.getId()))
                .findFirst();

        if (productExistente.isPresent()) {
            return null;
        }

        usuarios.add(userNew);
        guardar(usuarios);
        return userNew;
    }

    //buscar por id
    public Optional<User> findById(Integer id) throws IOException {
        List<User> productos = obetenerTodos();
        return productos.stream()
                .filter(product -> product.getId().equals(id)).findFirst();
    }

    //Actualizar
    public User actualizarUser(Integer id, User userActualizado) throws IOException {
        if (userActualizado.getNombre() == null ||
                userActualizado.getEmail() == null) {
            return null;
        }
        List<User> users = obetenerTodos();

        for (User u : users) {
            if (u.getId().equals(id)){
                u.setNombre(userActualizado.getNombre());
                u.setEmail(userActualizado.getEmail());

                guardar(users);
                return u;
            }
        }
        return null;
    }

    //Eliminar
    public boolean eliminar(Integer id) throws IOException {
        List<User> users = obetenerTodos();

        Optional<User> userExistente = users.stream()
                .filter(product -> product.getId().equals(id)).findFirst();

        if (userExistente.isPresent()){
            users.remove(userExistente.get());
            guardar(users);
            return true;
        }
        return false;
    }

    //--------------MÉTODOS DE PRODUCTOS--------------

    public List<Producto2Dto> obtenerProductos2(Integer user_id) {
        return producto2FeignClient.obtenerProductos2(user_id);
    }
    public List<Producto1Dto> obtenerProductos1(Integer user_id) {
        return producto1FeignClient.obtenerProductos1(user_id);
    }
    public Map<String, List<?>> obtenerTodos(Integer user_id) {
        List<Producto1Dto> productos1 = producto1FeignClient.obtenerProductos1(user_id);
        List<Producto2Dto> productos2 = producto2FeignClient.obtenerProductos2(user_id);

        Map<String, List<?>> result = new HashMap<>();
        result.put("All-Producto1 del user" + user_id , productos1);
        result.put("All-Producto2 del user" + user_id, productos2);

        return result;
    }



}
