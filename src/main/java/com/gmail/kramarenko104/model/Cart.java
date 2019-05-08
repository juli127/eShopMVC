package com.gmail.kramarenko104.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
entity 'carts_test':
  cartId BIGINT AUTO_INCREMENT,
  userId BIGINT,
  productId BIGINT, // taken from Map<Product, Integer> products
  quantity INT,  // taken from Map<Product, Integer> products
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES users(id),
  FOREIGN KEY (productId) REFERENCES products(id)
 */

@Entity
@Table(name = "carts")
@Access(value = AccessType.FIELD)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    private User user;

    @Transient
    private int itemsCount;

    @Transient
    private int totalSum;

    @ElementCollection
    @CollectionTable(name = "carts_products", joinColumns = @JoinColumn(name = "cartId"))
    @MapKeyJoinColumn(name = "productId", updatable = false)
    @Column(name = "quantity")
    @OrderColumn (name = "cartId")
    private Map<Product, Integer> products;

    public Cart() {
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", user=" + user +
                ", itemsCount=" + itemsCount +
                ", totalSum=" + totalSum +
                ", products=[" + Arrays.asList(products) + "]}";
    }

}
