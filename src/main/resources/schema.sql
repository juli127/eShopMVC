CREATE DATABASE IF NOT EXISTS eshopdb CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS users(
    id INT  AUTO_INCREMENT,
    login VARCHAR(40) UNIQUE NOT NULL,
    password VARCHAR(80) NOT NULL,
    name VARCHAR(30),
    address VARCHAR(50),
    comment VARCHAR(100),
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS categories (
id INT AUTO_INCREMENT,
name VARCHAR(100) UNIQUE NOT NULL,
PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS products (
id INT AUTO_INCREMENT,
name VARCHAR(100) UNIQUE NOT NULL,
category INT,
price INT,
description VARCHAR(300),
image VARCHAR(100),
PRIMARY KEY (id),
FOREIGN KEY (category) REFERENCES categories(id));


CREATE TABLE IF NOT EXISTS carts (
 userId INT,
 productId INT,
 quantity INT,
 FOREIGN KEY (userId) REFERENCES users(id),
 FOREIGN KEY (productId) REFERENCES products(id));
