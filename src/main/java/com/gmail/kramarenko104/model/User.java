package com.gmail.kramarenko104.model;

import javax.persistence.*;

/*
  id INT AUTO_INCREMENT,
  login VARCHAR(40) UNIQUE NOT NULL,
  password VARCHAR(80) NOT NULL,
  name VARCHAR(30),
  address VARCHAR(50),
  comment VARCHAR(100),
  PRIMARY KEY (id)
 */

@Entity
@Table(name = "users_test")
public class User {

    private int id;
    private String login;
    private String password;
    private String name;
    private String address;
    private String comment;

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column (unique = true, nullable = false, updatable = false, columnDefinition = "varchar(40)")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(nullable = false, columnDefinition = "varchar(30)")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, columnDefinition = "varchar(80)")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(columnDefinition = "varchar(50)")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(columnDefinition = "varchar(100)")
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

}
