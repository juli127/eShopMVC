package com.gmail.kramarenko104.model;

import javax.persistence.*;
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
@Table(name = "carts_test")
@Access(value=AccessType.FIELD)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, updatable = false)
    private long cartId;

    @Column (nullable = false, updatable = false)
    private long userId;

    @Transient
    private int itemsCount;

    @Transient
    private int totalSum;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "cart_products", joinColumns = @JoinColumn(name = "cartId") )
    @MapKeyJoinColumn(name = "productId")
    @Column(name="quantity")
    private Map<Product, Integer> products;

    public Cart() {
    }

    public Cart(long userId) {
        this.userId = userId;
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    public long getCartId() {
        return cartId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "userId:" + userId +
                ", itemsCount:" + itemsCount +
                ", totalSum:" + totalSum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;

        Cart cart = (Cart) o;

        if (cartId != cart.cartId) return false;
        if (userId != cart.userId) return false;
        if (itemsCount != cart.itemsCount) return false;
        if (totalSum != cart.totalSum) return false;
        return products.equals(cart.products);
    }

    @Override
    public int hashCode() {
        int result = (int) cartId;
        result = 31 * result + itemsCount;
        result = 31 * result + totalSum;
        result = 31 * result + products.hashCode();
        return result;
    }
}
