package com.gmail.kramarenko104.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/*
entity 'carts_test':
  id INT AUTO_INCREMENT,
  userId INT,
  productId INT, // taken from Map<Product, Integer> products
  quantity INT,  // taken from Map<Product, Integer> products
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES users(id),
  FOREIGN KEY (productId) REFERENCES products(id)
 */

@Entity
@Table(name = "carts_test")
public class Cart {

    private int id;
    private int userId;
    private Map<Product, Integer> products;

    @Transient
    private int itemsCount;
    @Transient
    private int totalSum;

//////////////
    public Cart() {
    }

    public Cart(int userId) {
        this.userId = userId;
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

    @Column (unique = true, nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

//    HOW  Map<Product, Integer> products should transfer Product -> field 'productId', Integer -> field 'quantity' ????
    @JoinColumns({
            @JoinColumn(name = "productId", table = "products_test", referencedColumnName = "id"),
            @JoinColumn(name = "quantity")})
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy = "cart")
    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
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
        return "Cart{" +
                "userId:" + userId +
                ", itemsCount:" + itemsCount +
                ", totalSum:" + totalSum +
                '}';
    }
}
