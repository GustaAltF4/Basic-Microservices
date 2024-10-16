package com.producto1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.producto1.entity.ProductOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Producto1Service {

    @Autowired
    private ObjectMapper mapper;
    @Value("${productos.json.path}")
    private String jsonFilePath;


    public List<ProductOne> obetenerTodos() throws IOException {
        Path path = Path.of(jsonFilePath);
        System.out.println("Ruta " + path.toString());
        if (!Files.exists(path)) {
            Files.createFile(path);
            Files.write(path, mapper.writeValueAsBytes(new ArrayList<ProductOne>()),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return new ArrayList<>(); // Retornar una lista vacía si se crea el archivo
        }
        // Leer el archivo y devolver la lista de productos
        try (InputStream json = Files.newInputStream(path)) {
            return new ArrayList<>(List.of(mapper.readValue(json, ProductOne[].class)));
        }
    }

    //Guardar
    public void guardar(List<ProductOne> productos) throws IOException {
        Path path = Path.of(jsonFilePath);

        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        Files.write(path, mapper.writeValueAsBytes(productos),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

    }

    //Crear
    public ProductOne crearProducto(ProductOne productoNew) throws IOException {
        if (productoNew.getId() == null
                || productoNew.getNombre() == null
                || productoNew.getPrecio() == null
                || productoNew.getDescription() == null) {
            return null;
        }
        List<ProductOne> productos = obetenerTodos();

        Optional<ProductOne> productoExistente = productos.stream()
                .filter(product -> product.getId().equals(productoNew.getId()))
                .findFirst();

        if (productoExistente.isPresent()) {
            return null;
        }

        productos.add(productoNew);
        guardar(productos);
        return productoNew;
    }

    //buscar por id
    public Optional<ProductOne> findById(Integer id) throws IOException {
        List<ProductOne> productos = obetenerTodos();
        return productos.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    //Actualizar
    public ProductOne actualizarProducto(Integer id, ProductOne productoActualizado) throws IOException {
        if (productoActualizado.getNombre() == null ||
                productoActualizado.getPrecio() == null ||
                productoActualizado.getDescription() == null) {
            return null;
        }
        List<ProductOne> productos = obetenerTodos();
        for (ProductOne p : productos) {
            if (p.getId().equals(id)) {
                p.setNombre(productoActualizado.getNombre());
                p.setPrecio(productoActualizado.getPrecio());
                p.setDescription(productoActualizado.getDescription());
                guardar(productos);
                return p;
            }
        }
        return null;
    }

    //Eliminar
    public boolean eliminar(Integer id) throws IOException {
        List<ProductOne> productos = obetenerTodos();

        Optional<ProductOne> productoExistente = productos.stream()
                .filter(product -> product.getId().equals(id)).findFirst();

        if (productoExistente.isPresent()) {
            productos.remove(productoExistente.get());
            guardar(productos);
            return true;
        }

        return false;
    }

    //PRODUCTO POR USUARIO ID
    public Optional<List<ProductOne>> obtenerPorUsuarioId(Integer id) throws IOException {
        if (id == null) {
            return Optional.empty(); // Retorna un Optional vacío si el ID es null
        }
        List<ProductOne> productos = obetenerTodos();
        List<ProductOne> productosFiltrados = productos.stream()
                .filter(product -> product.getUser_id() != null && product.getUser_id().equals(id))
                //.collect(Collectors.toList());
                .toList();
        return productosFiltrados.isEmpty() ? Optional.empty() : Optional.of(productosFiltrados);
    }
}
