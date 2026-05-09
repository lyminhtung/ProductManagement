package com.user.managements.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private long id;

    @Column(nullable = false, unique = true, length = 300)
    private String name;

    private String description;
    private Double price;
    private int year;
    private String url;

    @Transient
    private int age;

    // Custom computed property — not stored in DB
    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }

    public Product(String name, String description, Double price, int year, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.year = year;
        this.url = url;
    }
}
