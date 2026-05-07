package com.user.managements.Model;

import jakarta.persistence.*;

import java.util.Calendar;

@Entity// Khai báo để biết đây là 1 thực thể
//POJO = Plain Object Java Object
@Table(name = "product")
public class Product {
    // đây là primary  key
    //để cấp cho nó 1 kiểu strategy kiểu thụ động có nghĩa là tự generate auto-increment
//    @GeneratedValue(strategy = GenerationType.AUTO)

    @Id
    @SequenceGenerator(
            // đặt quy tắc để tạo ra
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private long id;
    @Column(nullable = false,  unique = true, length = 300)
    private String name;
    private String description;
    private Double price;
    private int year;
    private String url;

    @Transient
    private int age;
    public int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }
    public Product() {
    }

    public Product( String name, String description, Double price, int year, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.year = year;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", year=" + year +
                ", url='" + url + '\'' +
                '}';
    }

}
