package com.gmail.kramarenko104.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/*
  id INT AUTO_INCREMENT,
  orderNumber INT,
  status VARCHAR(100),
  userId INT,
  productId INT,
  quantity INT,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES users(id),
  FOREIGN KEY (productId) REFERENCES products(id)
 */
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (nullable = false)
    private int orderNumber;
    @Column (nullable = false)
    private int userId;
    @Column
    private String status;
    @Column (nullable = false)
    private int productId;
    @Column (nullable = false)
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    ////////////////////////////////
    private int itemsCount;
    private int totalSum;
    private Map<Product, Integer> products;

    public Order() {
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }
    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", userId=" + userId +
                ", itemsCount=" + itemsCount +
                ", totalSum=" + totalSum +
                ", products=" + products +
                '}';
    }
}
