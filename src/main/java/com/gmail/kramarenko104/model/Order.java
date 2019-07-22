package com.gmail.kramarenko104.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
entity 'orders':
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
@Getter @Setter
@EqualsAndHashCode
@Table(name = "orders")
@Access(value = AccessType.FIELD)
@DynamicUpdate
@NamedQueries(value =
        {@NamedQuery(name = "GET_ALL_ORDERS_BY_USERID", query = "from Order o where o.user.userId = :userId"),
        @NamedQuery(name = "GET_LAST_ORDER_NUMBER", query = "select distinct max(o.orderNumber) as lastOrderNumber from Order o"),
        @NamedQuery(name = "GET_LAST_ORDER_BY_USERID", query = "from Order o where o.user.userId = :userId order by o.orderNumber DESC")})
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(nullable = false, updatable = false)
    @NotEmpty
    private long orderNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "userId", nullable = false, updatable = false)
    @NotEmpty
    private User user;

    @Column(columnDefinition = "varchar(20)")
    @EqualsAndHashCode.Exclude
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
    @NotEmpty
    private Map<Product, Integer> products;

    public Order() {
        itemsCount = 0;
        totalSum = 0;
        products = new HashMap<>();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", user=" + user +
                ", itemsCount=" + itemsCount +
                ", totalSum=" + totalSum +
                ", products=" + Arrays.asList(products) + "}";
    }

}
