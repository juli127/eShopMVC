package com.gmail.kramarenko104.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/*
entity 'orders_test':
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
@Table(name = "orders_test")
public class Order {

    private int id;
    private int orderNumber;
    private String status;
    private int userId;

    @Transient
    private int itemsCount;
    @Transient
    private int totalSum;

//    HOW  Map<Product, Integer> products should transfer Product -> field 'productId', Integer -> field 'quantity' ????
//    @ElementCollection
    @OneToMany(mappedBy = "order")
    @MapKey(name = "id")
    private Map<Product, Integer> products;

    ////////////////////////////////
    public Order() {
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column (nullable = false)
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

    @Column (nullable = false)
    public int getUserId() {
        return userId;
    }

    @Column
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


    //    @Column (nullable = false)
//    private int productId;
//
//    @Column (nullable = false)
//    private int quantity;

    //    public int getProductId() {
//        return productId;
//    }
//
//    public void setProductId(int productId) {
//        this.productId = productId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }

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
