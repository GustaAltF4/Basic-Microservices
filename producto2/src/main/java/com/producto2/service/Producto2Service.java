package com.producto2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.producto2.entity.ProductTwo;
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
import java.util.stream.Collectors;

@Service
public class Producto2Service {

    @Autowired
    private ObjectMapper mapper;
    @Value("${productos2.json.path}")
    private String jsonFilePath;

    public List<ProductTwo> obetenerTodos() throws IOException {
        Path path = Path.of(jsonFilePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
            Files.write(path, mapper.writeValueAsBytes(new ArrayList<ProductTwo>()),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return new ArrayList<>();
        }

        try (InputStream json = Files.newInputStream(path)) {
            return new ArrayList<>(List.of(mapper.readValue(json, ProductTwo[].class)));
        }

    }

    //guardar
    public void guardar(List<ProductTwo> productos) throws IOException {
        Path path = Path.of(jsonFilePath);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        Files.write(path, mapper.writeValueAsBytes(productos),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    //Crear
    public ProductTwo crearProducto(ProductTwo productNew) throws IOException {
        if (productNew.getId() == null ||
                productNew.getNombre() == null ||
                productNew.getPrecio() == null ||
                productNew.getDescription() == null ||
                productNew.getImagenUrl() == null) {
            return null;
        }
        List<ProductTwo> productos = obetenerTodos();

        Optional<ProductTwo> productExistente = productos.stream()
                .filter(product -> product.getId().equals(productNew.getId()))
                .findFirst();

        if (productExistente.isPresent()) {
            return null;
        }

        productos.add(productNew);
        guardar(productos);
        return productNew;
    }

    //buscar por id
    public Optional<ProductTwo> findById(Integer id) throws IOException {
        List<ProductTwo> productos = obetenerTodos();
        return productos.stream()
                .filter(product -> product.getId().equals(id)).findFirst();
    }

    //Actualizar
    public ProductTwo actualizarProducto(Integer id, ProductTwo productActualizado) throws IOException {
        if (productActualizado.getNombre() == null ||
                        productActualizado.getPrecio() == null ||
                        productActualizado.getDescription() == null ||
                        productActualizado.getImagenUrl() == null) {
            return null;
        }
        List<ProductTwo> productos = obetenerTodos();

        for (ProductTwo p : productos) {
            if (p.getId().equals(id)){
                p.setNombre(productActualizado.getNombre());
                p.setPrecio(productActualizado.getPrecio());
                p.setDescription(productActualizado.getDescription());
                p.setImagenUrl(productActualizado.getImagenUrl());
                guardar(productos);
                return p;
            }
        }
        return null;
    }

    //Eliminar
    public boolean eliminar(Integer id) throws IOException {
        List<ProductTwo> productos = obetenerTodos();

        Optional<ProductTwo> productoExistente = productos.stream()
                .filter(product -> product.getId().equals(id)).findFirst();

        if (productoExistente.isPresent()){
            productos.remove(productoExistente.get());
            guardar(productos);
            return true;
        }
        return false;
    }

    //PRODUCTO POR USUARIO ID
    public Optional<List<ProductTwo>> obtenerPorUsuarioId(Integer id) throws IOException {
        if (id == null) {
            return Optional.empty(); // Retorna un Optional vacío si el ID es null
        }
        List<ProductTwo> productos = obetenerTodos();
        List<ProductTwo> productosFiltrados = productos.stream()
                .filter(product -> product.getUser_id() != null && product.getUser_id().equals(id))
                //.collect(Collectors.toList());
                .toList();
        return productosFiltrados.isEmpty() ? Optional.empty() : Optional.of(productosFiltrados);
    }
}
