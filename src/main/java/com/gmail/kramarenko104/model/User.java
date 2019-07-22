package com.gmail.kramarenko104.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
@Table(name = "users")
@Access(value = AccessType.FIELD)
@DynamicUpdate
@NamedQuery(name = "GET_USER_BY_LOGIN", query = "from User u where u.login = :login")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(30)")
    @NotEmpty
    private String login;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    @NotEmpty
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    @NotEmpty
    private String name;

    @Column(columnDefinition = "varchar(50)")
    private String address;

    @Column(columnDefinition = "varchar(100)")
    private String comment;

    @OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Cart cart;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private List<Order> userOrders;

    @Override
    public String toString() {
        return "User{" +
                "userId:" + userId + ", " +
                "login:'" + login + "', name:'" + name + "'}";
    }
}
