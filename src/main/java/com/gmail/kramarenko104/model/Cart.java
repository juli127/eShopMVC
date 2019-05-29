package com.gmail.kramarenko104.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
entity 'carts':
  cartId BIGINT AUTO_INCREMENT,
  userId BIGINT,
  productId BIGINT, // taken from Map<Product, Integer> products
  quantity INT,  // taken from Map<Product, Integer> products
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES users(id),
  FOREIGN KEY (productId) REFERENCES products(id)
 */

@Entity
@Getter @Setter
@EqualsAndHashCode
@Table(name = "carts")
@Access(value = AccessType.FIELD)
@NamedQuery(name="GET_CART_BY_USERID", query = "from Cart c where c.user.userId = :userId")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "userId", nullable = false, unique = true, updatable = false)
    private User user;

    @Transient
    private int itemsCount;

    @Transient
    private int totalSum;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "carts_products", joinColumns = @JoinColumn(name = "cartId"))
    @MapKeyJoinColumn(name = "productId", updatable = false)
    @Column(name = "quantity")
    @OrderColumn (name = "cartId")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Map<Product, Integer> products;

    public Cart() {
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", user=" + user +
                ", itemsCount=" + itemsCount +
                ", totalSum=" + totalSum +
                ", products=" + Arrays.asList(products) + "}";
    }
}
