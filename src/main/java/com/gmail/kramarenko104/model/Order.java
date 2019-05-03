package com.gmail.kramarenko104.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/*
entity 'orders_test':
  orderId BIGINT AUTO_INCREMENT,
  orderNumber BIGINT,
  status VARCHAR(100),
  userId BIGINT,
  productId BIGINT,
  quantity INT,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES users(id),
  FOREIGN KEY (productId) REFERENCES products(id)
 */

//@Entity
@Table(name = "orders_test")
@Access(value=AccessType.FIELD)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, updatable = false)
    private long orderId;

    @Column (nullable = false, updatable = false)
    private long userId;

    @Column (nullable = false, updatable = false)
    private long orderNumber;

    @Column
    private String status;

    @Transient
    private int itemsCount;

    @Transient
    private int totalSum;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "orderId") )
    @MapKeyJoinColumn(name = "productId")
    @Column(name="quantity")
    private Map<Product, Integer> products;

    public Order() {
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    public long getOrderId() {
        return orderId;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (orderNumber != order.orderNumber) return false;
        if (userId != order.userId) return false;
        if (itemsCount != order.itemsCount) return false;
        if (totalSum != order.totalSum) return false;
        if (!status.equals(order.status)) return false;
        return products.equals(order.products);
    }

    @Override
    public int hashCode() {
        int result = (int) orderId;
        result = 31 * result + (int) orderNumber;
        result = 31 * result + status.hashCode();
        result = 31 * result + (int) userId;
        result = 31 * result + itemsCount;
        result = 31 * result + totalSum;
        result = 31 * result + products.hashCode();
        return result;
    }
}
