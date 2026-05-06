package com.user.managements.Repositories;

// Nơi để chúng ta chữa các hàm để lấy data về

import com.user.managements.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByName(String name);
}
