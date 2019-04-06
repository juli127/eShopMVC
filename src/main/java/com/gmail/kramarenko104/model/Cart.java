package com.gmail.kramarenko104.model;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/*
  id INT AUTO_INCREMENT,
  userId INT,
  productId INT,
  quantity INT,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES users(id),
  FOREIGN KEY (productId) REFERENCES products(id)
 */
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (unique = true, nullable = false)
    private int userId;
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

//////////////
    private int itemsCount;
    private int totalSum;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "product_id"), @JoinColumn(name = "quantity")})
    private Map<Product, Integer> products;
//////////////

    public Cart() {
    }

    public Cart(int userId) {
        this.userId = userId;
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
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

    public int getUserId() {
        return userId;
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


    @Override
    public String toString() {
        return "Cart{" +
                "userId:" + userId +
                ", itemsCount:" + itemsCount +
                ", totalSum:" + totalSum +
                '}';
    }
}
