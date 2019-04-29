package com.gmail.kramarenko104.model;

import javax.persistence.*;
import java.util.Objects;

/*
  id INT AUTO_INCREMENT,
  name VARCHAR(100) UNIQUE NOT NULL,
  category INT,
  price INT,
  description VARCHAR(300),
  image VARCHAR(100),
  PRIMARY KEY (id),
  FOREIGN KEY (category) REFERENCES categories(id)
 */

@Entity
@Table(name="products_test")
public class Product {

    private int id;
    private String name;
    private int category;
    private int price;
    private String description;
    private String image;

    public Product() {
    }

    public Product(int id, String name, int category, int price, String description, String image) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    @Column(unique = true, nullable = false, columnDefinition = "varchar(100)")
    public String getName() {
        return name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(columnDefinition = "varchar(300)")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column (nullable = false)
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column (nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Column (columnDefinition = "varchar(100)")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId() == product.getId() &&
                getCategory() == product.getCategory() &&
                getPrice() == product.getPrice() &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getDescription(), product.getDescription()) &&
                Objects.equals(getImage(), product.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCategory(), getPrice(), getDescription(), getImage());
    }

    @Override
    public String toString() {
        return "{\"productId\":" + id + ",\"name\":\"" + name + "\",\"price\":" + price + "}";
    }
}
