package com.gmail.kramarenko104.model;

import javax.persistence.*;

/*
  productId BIGINT AUTO_INCREMENT,
  name VARCHAR(100) UNIQUE NOT NULL,
  category INT,
  price INT,
  description VARCHAR(300),
  image VARCHAR(100),
  PRIMARY KEY (id),
  FOREIGN KEY (category) REFERENCES categories(id)
 */

@Entity
@Table(name = "products")
@Access(value=AccessType.FIELD)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(nullable = false)
    private int category;

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "varchar(300)")
    private String description;

    @Column(columnDefinition = "varchar(100)")
    private String image;

    public Product() {
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "{\"productId\":" + productId + ",\"name\":\"" + name + "\",\"price\":" + price + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (productId != product.productId) return false;
        if (category != product.category) return false;
        if (price != product.price) return false;
        if (!name.equals(product.name)) return false;
        return description != null ? description.equals(product.description) : product.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (productId ^ (productId >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + category;
        result = 31 * result + price;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
