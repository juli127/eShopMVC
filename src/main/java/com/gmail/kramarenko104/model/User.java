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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (unique = true, nullable = false, columnDefinition = "varchar(40)")
    private String login;

    @Column(nullable = false, columnDefinition = "varchar(80)")
    private String password;

    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String name;

    @Column(columnDefinition = "varchar(50)")
    private String address;

    @Column(columnDefinition = "varchar(100)")
    private String comment;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
