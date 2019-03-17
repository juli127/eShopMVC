package com.gmail.kramarenko104.model;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import java.util.Objects;

public class Product {

    private static Logger logger = Logger.getLogger(Product.class);
    private int id;
    private String name;
    private int category;
    private int price;
    private String description;
    private String image;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
