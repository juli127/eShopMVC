package com.gmail.kramarenko104.model;

import javax.persistence.*;

/*
  userId BIGINT AUTO_INCREMENT,
  login VARCHAR(30) UNIQUE NOT NULL,
  password VARCHAR(50) NOT NULL,
  name VARCHAR(50),
  address VARCHAR(50),
  comment VARCHAR(100),
  PRIMARY KEY (id)
 */

@Entity
@Table(name = "users_test")
@Access(value=AccessType.FIELD)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", unique = true, nullable = false, updatable = false)
    private long userId;

    @Column (unique = true, nullable = false, updatable = false, columnDefinition = "varchar(30)")
    private String login;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String name;

    @Column(columnDefinition = "varchar(50)")
    private String address;

    @Column(columnDefinition = "varchar(100)")
    private String comment;

    public User() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "User[" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", comment='" + comment + '\'' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        if (!name.equals(user.name)) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        return comment != null ? comment.equals(user.comment) : user.comment == null;
    }

    @Override
    public int hashCode() {
        int result = (int) userId;
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
