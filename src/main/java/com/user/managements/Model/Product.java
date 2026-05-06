package com.user.managements.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity// Khai báo để biết đây là 1 thực thể
//POJO = Plain Object Java Object
public class Product {
    // đây là primary  key
    @GeneratedValue(strategy = GenerationType.AUTO)//để cấp cho nó 1 kiểu strategy kiểu thụ động có nghĩa là tự generate auto-increment
    @Id
    private long id;
    private String name;
    private String description;
    private Double price;
    private int year;
    private String url;
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
