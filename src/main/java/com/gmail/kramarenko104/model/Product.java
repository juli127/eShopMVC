package com.gmail.kramarenko104.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@Table(name = "products")
@Access(value=AccessType.FIELD)
@DynamicUpdate
@NamedQuery(name="GET_PRODUCT_BY_CATEGORY", query = "from Product p where p.category = :category")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(100)")
    @NotNull
    private String name;

    @Column(nullable = false)
    private int category;

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "varchar(300)")
    private String description;

    @Column(columnDefinition = "varchar(100)")
    private String image;

    @Override
    public String toString() {
        return "{\"productId\":" + productId + ",\"name\":\"" + name + "\",\"price\":" + price + "}";
    }
}
