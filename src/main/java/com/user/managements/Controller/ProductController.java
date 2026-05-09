package com.user.managements.Controller;

import com.user.managements.Model.Product;
import com.user.managements.Model.ResponseObject;
import com.user.managements.Repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repository;

    @GetMapping("/list")
    List<Product> listProducts() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable long id) {
        Optional<Product> foundProduct = repository.findById(id);
        return foundProduct.isPresent()
                ? ResponseEntity.ok(new ResponseObject("ok", "Query successful", foundProduct))
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseObject("failed", "Cannot find product with id=" + id, ""));
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        if (repository.existsByName(newProduct.getName().trim())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseObject("failed", "Product name already taken", ""));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject("ok", "Insert successful", repository.save(newProduct)));
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@PathVariable long id, @RequestBody Product newProduct) {
        Product updatedProduct = repository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName().trim());
                    product.setDescription(newProduct.getDescription().trim());
                    product.setPrice(newProduct.getPrice());
                    product.setYear(newProduct.getYear());
                    product.setUrl(newProduct.getUrl());
                    return repository.save(product);
                }).orElseGet(() -> {
                    // Guard: ensure the new name is not taken before inserting
                    if (repository.existsByName(newProduct.getName().trim())) {
                        return null;
                    }
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });

        if (updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseObject("failed", "Product name already taken", ""));
        }
        return ResponseEntity.ok(new ResponseObject("ok", "Update successful", updatedProduct));
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", "Cannot find product with id=" + id, ""));
        }
        repository.deleteById(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Delete successful", ""));
    }
}
