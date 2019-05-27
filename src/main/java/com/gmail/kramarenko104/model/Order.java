package com.gmail.kramarenko104.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
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

@Entity
@Table(name = "orders")
@Access(value = AccessType.FIELD)
@NamedQueries(value =
        {@NamedQuery(name = "GET_ALL_ORDERS_BY_USERID", query = "from Order o where o.user.userId = :userId"),
        @NamedQuery(name = "GET_LAST_ORDER_NUMBER", query = "select distinct max(o.orderNumber) as lastOrderNumber from Order o"),
        @NamedQuery(name = "GET_LAST_ORDER_BY_USERID", query = "from Order o where o.user.userId = :userId order by o.orderNumber DESC")})
public class Order  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(nullable = false, updatable = false)
    private long orderNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    private User user;

    @Column(columnDefinition = "varchar(20)")
    private String status;

    @Transient
    private int itemsCount;

    @Transient
    private int totalSum;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "orders_products", joinColumns = @JoinColumn(name = "orderId"))
    @MapKeyJoinColumn(name = "productId", updatable = false)
    @Column(name = "quantity")
    @OrderColumn (name = "orderId")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<Product, Integer> products;

    public Order() {
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
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
                ", user=" + user +
                ", itemsCount=" + itemsCount +
                ", totalSum=" + totalSum +
                ", products=[" + Arrays.asList(products) + "]}";
    }

}
