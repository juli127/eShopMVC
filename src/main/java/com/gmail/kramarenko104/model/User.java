package com.gmail.kramarenko104.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
    @NotNull
    @Email
    private String login;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    @NotNull
    @Length(min = 4, message = "Password should have minimum 4 symbols!")
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    @NotNull
    private String name;

    @Column(columnDefinition = "varchar(50)")
    @NotNull
    private String address;

    @Column(columnDefinition = "varchar(100)")
    private String comment;

    @OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Cart cart;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private List<Order> userOrders;

    @OneToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "userId:" + userId + ", " +
                "login:'" + login + "', name:'" + name + "', roles: " + roles + "}";
    }
}