package com.user.managements.Controller;

import com.user.managements.Model.Product;
import com.user.managements.Model.ResponseObject;
import com.user.managements.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {
    //DI = Dependency Injection
    @Autowired // giúp cho cái đối tượng repo sẽ được tạo ra ngay khi app chúng ta được tạo
    private ProductRepository repository;
    @GetMapping("/list")
    List<Product> listProducts(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable long id){
        Optional<Product> foundProduct = repository.findById(id);
        return foundProduct.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Query Successful",foundProduct)
        ) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Not Found","Cannot find the products with id " + id,"")
        );
    }
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct){

        List<Product> foundProducts = repository.findByName(newProduct.getName().trim());
        if(foundProducts.size() > 0){
            return  ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed","Product name already taken", "")
            );
        }
        return  ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Insert Successful", repository.save(newProduct))
        );
    }
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@PathVariable long id, @RequestBody Product newProduct){
        Product updatedProduct = repository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName().trim());
                    product.setDescription(newProduct.getDescription().trim());
                    product.setPrice(newProduct.getPrice());
                    product.setYear(newProduct.getYear());
                    product.setUrl(newProduct.getUrl());
                    return repository.save(product);
                }).orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update Successful", updatedProduct)
        );
    }
    @DeleteMapping("delete/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable long id){
        boolean exists = repository.existsById(id);
        if(exists){
            repository.deleteById(id);
            return  ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "delete successful", "")
            );
        }
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Not Found","Cannot find the products with id " + id,"")
            );
    }

}
